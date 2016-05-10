/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokusgui;

import static blokusgui.Game.STATE_WAIT_PLAYER_PLAY;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tomoki-n
 */
public class GameMain {
    public static final int STATE_WAIT_PLAYER = 0;
    public static final int STATE_WAIT_PLAYER_PLAY = 1;
    public static final int STATE_GAME_END = 2;
    
    private int CurrentPlayer;
    private Board gameBoard;
    private boolean[] playAble;
    private int state;
    public GamePanels panel;
    private String adress;
    private String port;
    private String name;
    private Connection sthread;
    private int[] playerpoint;
    public int playerid = -1;
    private int turncount;
    
    public GameMain() throws IOException, InterruptedException{
        init();
        
    }
    
    public void init() throws IOException, InterruptedException{
        panel = new GamePanels(this);
        panel.setVisible(true);
        Init inits = new Init(panel,true);   
        inits.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                settext(inits.text1.getText(),inits.text2.getText(),inits.text3.getText());
                try {
                    resetAll();
                    
                } catch (InterruptedException ex) {                    
                    Logger.getLogger(GameMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(GameMain.class.getName()).log(Level.SEVERE, null, ex);
                }
                sthread.sendName();
        
            }
        });
        inits.setVisible(true);
        
  
        
    }
    
     public int ememyid(){
        if (this.playerid == 0) return 1;
        else if (this.playerid == 1) return 0;
        return -1;
    }
     
  public void resetAll() throws InterruptedException, IOException {
        this.state = STATE_WAIT_PLAYER;
        if (this.name == null||this.port == null || this.adress == null) {
            System.exit(0);
        }

        //サーバに接続する
        this.sthread = new Connection(this.name,this);
        try {
            boolean connect = this.sthread.connectToServer(this.adress, this.port);
            if (connect == false) {
                this.panel.addText("サーバへの接続に失敗しました。");
                System.exit(0);
            }
        } catch (UnknownHostException ex) {
            this.panel.addText("サーバの指定が不正です。UnknownHostException");
            System.exit(0);
        } catch (IOException ex) {
            this.panel.addText("サーバへの接続に失敗しました。IOException");
            System.exit(0);
        }
        //正しく接続できたら処理開始
        this.playerid = -1;
        this.turncount = 0;
       
       
         this.playerpoint = new int[2];
         playerpoint[0] = 0;
         playerpoint[1] = 0;
               
         this.nextturn();
    }
    
    public void settext(String str1,String str2,String str3){
        this.adress = str1;
        this.port = str2;
        this.name = str3;
        
        
    }
    
    public void sendmessage(String str){
        this.sthread.sendMessage(str);
    }
    
    public void nextturn() {
        this.turncount++;
    }

    public void setboard() {
        this.panel.playerid = this.playerid;
        
        //his.panel.all_label_enable(this.playerid, true);
        
    }
}
