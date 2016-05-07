/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokusgui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tomoki-n
 */
public class Connection implements Runnable {
    public final int STATE_NOCONECTION = 00;
    public final int STATE_INIT = 01;
    public final int STATE_GAME = 10;
    public final int STATE_VIEW_GETBOARD = 11;
    public final int STATE_PLAY = 12;
    public final int STATE_PLAY_GETBOARD = 13;
    public final int STATE_FINISH = 20;
    public final int STATE_FINISH_GETBOARD = 21;

    private Socket connectedSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public int state;
    private String myName;
    private String address;
    public boolean check =false; 
    private int PlayerID;
    
    private Pattern MSGPTN = Pattern.compile("([0-9]+) (.*)");
    private Pattern TEAMIDMSGPTN = Pattern.compile("102 TEAMID ([0-1])");
    private Pattern ADVERSARYMSGPTN = Pattern.compile("104 ADVERSARY (.*)");
    private Pattern GAMEENDMSGPTN = Pattern.compile("502 GAMEEND ([0-1])");
    private ArrayList<String> boardInfo;
    private int winner = -1;

    
    public Connection(String name) throws IOException {
        this.myName = name;
        state = STATE_NOCONECTION;
    }
   
    public synchronized  void sendMessage(String message){
        if(this.writer != null){
            this.writer.println(message);
            this.writer.flush();
        }
    }
    
     public boolean connectToServer(String ip, String port) throws UnknownHostException, IOException {
        this.connectedSocket = new Socket(ip, Integer.parseInt(port));
        if(this.connectedSocket.isConnected()){
            this.reader = new BufferedReader(new InputStreamReader(this.connectedSocket.getInputStream(),"UTF-8"));
            this.writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.connectedSocket.getOutputStream(),"UTF-8")));
            Thread msgwait = new Thread(this);
            msgwait.start();
            System.out.println("Connect to Server");
            this.state = STATE_INIT;
            return true;
        } else {
            return false;
        }
    }
    public void sendName(){
        if(this.state != STATE_INIT){
            System.out.println("NO STATE_INIT");
            return;
        }
        this.sendMessage("101 NAME "+ this.myName);
        System.out.println("Send Name");
    }
     
    public synchronized void getMessage(String message) throws InterruptedException, IOException {
        //終了処理
        if (message.toUpperCase().equals("203 EXIT")) {
            sendMessage("200 OK");
            try {
                this.connectedSocket.close();
            } catch (IOException ex) {
                System.out.println("サーバ切断時にエラーが発生しました");
            }
            return;
        }

        //メッセージ解析
        Matcher mc = MSGPTN.matcher(message);
        if (mc.matches()) {
            int num = Integer.parseInt(mc.group(1));
            //GAMEENDメッセージは重要度が高い
            if (num == 502) {
                //502 GAMEEND [T]
                System.out.println("ゲーム終了");
                Matcher gnd = GAMEENDMSGPTN.matcher(message);
                if (gnd.matches()) {
                    this.winner = Integer.parseInt(gnd.group(1));
                    this.state = STATE_FINISH_GETBOARD;
                    this.boardInfo = new ArrayList<String>();
                }
            }

            if (this.state == STATE_INIT) {
                if (num == 102) {
                    //自分のサーバへの接続が完了
                    Matcher nmc = TEAMIDMSGPTN.matcher(message);
                    if (nmc.matches()) {
                        this.PlayerID = Integer.parseInt(nmc.group(1));
                    }
                }
                if (num == 104) {
                    //相手ユーザの接続が完了
                    Matcher nmc = ADVERSARYMSGPTN.matcher(message);
                    if (nmc.matches()) {
                        String advName = nmc.group(1);
                        this.state = STATE_GAME;
                    }
                    if (this.PlayerID == 1) {
                        //後攻から始まる場合
                        
                        
                    }
                }
            } else if (this.state == STATE_GAME) {
                if (num == 404) {
                    //プレイ要求
                    this.state = STATE_PLAY;
                    //ボード状態の取得
                    System.out.println("貴方の手番です。");
                    this.check = true;
                    
                 } 
            } else if (this.state == STATE_PLAY) {
                if (num == 200) {
                    //プレイ結果がOKだった場合
                    this.state = STATE_GAME;
                   
                } else if (num == 303){
                    //不可能な手
                    //this.mainField.addMessage("その位置には移動できません。");
                    this.state = STATE_GAME;
                }
            } 
        } // end of message matcing
    }
    
    
    
    @Override
      public void run() {
        String mssage;
        System.out.println("Call Run Method");
        try {
            while((mssage = this.reader.readLine())!= null){
                this.getMessage(mssage);
            }
          //  this.mainField.addMessage("サーバとの接続が切断しました");
            this.state = STATE_INIT;
           // this.mainField.resetAll();
        } catch (IOException ex) {
            //      this.mainField.addMessage("サーバとの接続が切断しました");
            this.state = STATE_INIT;
            //      this.mainField.resetAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
   
   
}
