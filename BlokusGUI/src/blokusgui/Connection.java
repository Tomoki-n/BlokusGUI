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
    public final int STATE_PLAYED = 14;
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
    public boolean canput = false; 
    private int PlayerID;
    public GameMain main;
    private Pattern MSGPTN = Pattern.compile("([0-9]+) (.*)");
    private Pattern GAMEENDMSGPTN = Pattern.compile("502 GAMEEND ([0-1])");
    private Pattern NAMEMSGPTN = Pattern.compile("101 NAME (.*)");
    private Pattern NAMEMSGPTN1 = Pattern.compile("102 PLAYERID (.*)");
    private Pattern PLAYMSGPTN = Pattern.compile("401 PLAYED ([0-1]) (1?[0-9]) (1?[0-9]) ([0-5][0-9A-F])-([0-8])");
    private Pattern PLAYMSGPTN0 = Pattern.compile("405 PLAY (1?[0-9]) (1?[0-9]) ([0-5][0-9A-F])-([0-8])");
    private Pattern SCORE = Pattern.compile("408 SCORE ([0-9]+) ([0-9]+)");
    
    private String messages;
    private ArrayList<String> boardInfo;
    private int winner = -1;

    
    

    Connection(String name, GameMain aThis) {
        this.main = aThis;
        this.myName = name;
        state = STATE_NOCONECTION;
    }

    
   
    public synchronized  void sendMessage(String message){
        if(this.writer != null){
            this.writer.println(message);
            messages = message;
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
    public int ememyid(){
        if (this.main.playerid == 0) return 1;
        else if (this.main.playerid == 1) return 0;
        return -1;
    }
    public synchronized void getMessage(String message) throws InterruptedException, IOException {
        //終了処理
        if (message.toUpperCase().equals("203 EXIT")) {
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
                    Matcher nmc = this.NAMEMSGPTN1.matcher(message);
                    if (nmc.matches()) {
                        this.main.playerid = Integer.parseInt(nmc.group(1));
                        this.main.setboard();
                        this.state = STATE_GAME;
                        this.main.panel.showRedPlayerPanel(this.main.playerid) ;
                        this.main.panel.showBluePlayerPanel(ememyid());
                        this.main.panel.setback1();
                    
                    }
                }
            } else if (this.state == STATE_GAME) {
                if (num == 404) {
                    //プレイ要求
                     sendMessage("407 GETSCORE"); 
                     this.main.panel.setback0();
                     this.state = STATE_PLAY;
                     this.canput = true;
                     
                }
                if (num == 402){ 
                    this.state = STATE_GAME;
                    //passed
                }
                if (num == 401) {
                    sendMessage("407 GETSCORE");
                    this.state = STATE_GAME;
                    System.out.println("相手が打ちました");
                     Matcher gnd = PLAYMSGPTN.matcher(message);
                    if (gnd.matches()) {
                        int x = Integer.parseInt(gnd.group(2));
                        int y = Integer.parseInt(gnd.group(3));
                        String PieceID = gnd.group(4);
                                int PieceDirection = Integer.parseInt(gnd.group(5));
                                
                                this.main.panel.setpiece(ememyid(), PieceID, PieceDirection,x , y);
                      
                    
                 }
                }
                if (num == 408){
                     Matcher gnd = SCORE.matcher(message);
                    if (gnd.matches()) {
                  
                              this.main.panel.setscore(gnd.group(1), gnd.group(2));
                        }
                     
                     
                 
                }
            } else if (this.state == STATE_PLAY) {
                if (num == 200) {
                    //プレイ結果がOKだった場合
                    this.main.panel.setback1();
                    this.getMessage(this.messages);
                    this.state = STATE_GAME;
                   
                } else if (num == 303){
                    //不可能な手
                    //this.mainField.addMessage("その位置には移動できません。");
                    this.state = STATE_GAME;
                }
                else if (num == 405){
                   System.out.println("OK");
                    Matcher gnd = PLAYMSGPTN0.matcher(message);
                    if (gnd.matches()) {
                                int x = Integer.parseInt(gnd.group(1));
                                int y = Integer.parseInt(gnd.group(2));
                                String PieceID = gnd.group(3);
                                int PieceDirection = Integer.parseInt(gnd.group(4));
                                
                                this.main.panel.setpiece(this.main.playerid, PieceID, PieceDirection,x , y);
                      
                    sendMessage("407 GETSCORE"); 
                 }
                              
                }
                else if (num == 408){
                     Matcher gnd = SCORE.matcher(message);
                    if (gnd.matches()) {
                    
                              this.main.panel.setscore(gnd.group(1), gnd.group(2));
                    }
                }
                
                
            }
           
         // end of message matcing
         this.main.panel.addText(message);
         System.out.println(message);
    }
    
    }
    
    @Override
      public void run() {
        String mssage;
        System.out.println("Call Run Method");
        try {
            while((mssage = this.reader.readLine())!= null){
                this.getMessage(mssage);
                if (this.canput){
                     this.main.panel.check = true;
                     this.canput = false;
                }
                
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
