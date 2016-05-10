/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokusgui;

import static blokusgui.Piece.PieceList;
import static blokusgui.PlayerPiecePanel.resizeByScaledInstance;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author tomoki-n
 */
public class GamePanels extends javax.swing.JFrame implements Observer {

    public BlokusPiecePanel[][] boardPanels;
    //public BlokusPiecePanel[][] overlayPanels;
    public int boardpiecepanel[][] = new int[Board.BOARDSIZE][Board.BOARDSIZE];
    
    //自分がまだ使えるピース一覧
    private ArrayList<String> havingPeices;
    private ArrayList<String> enemyhavingPeices;
    
     private ArrayList<String> havingPeices1;
    private ArrayList<String> enemyhavingPeices1;
    
    
    public int pieceIndex = 0;
    public int direction = 0;
    public Game game;
    public final String TitleString = "BlokusGUI";
    public final String VerString = "0.1";
    public Point selected = new Point();
    BlokusPieceLabel label0[] = new BlokusPieceLabel[25];
    BlokusPieceLabel label1[] = new BlokusPieceLabel[25];
    Piece putpiece;
    String id;
    int playerid;
    public boolean check;
    public boolean check1 = true;
    
    public GameMain main;
    public PieceLabelClickListener pcl = new PieceLabelClickListener();
    public PieceLabelClickListener1 pcl1 = new PieceLabelClickListener1();
          
    /**
     * Creates new form GamePanels
     */
    public GamePanels(GameMain at) throws IOException {
        main = at;
        initComponents();
        init();
        this.setTitle(TitleString + " ver." + VerString);
        this.setText("起動が完了しました");
    }

   
    public void init() throws IOException {
        //this.messageDialog = new TextMessageDialog(this,false);
        //this.showMessageDialog();
        //selected.setLocation(0, 0);
         this.havingPeices = new ArrayList<String>();
         this.enemyhavingPeices = new ArrayList<String>();
         this.havingPeices1 = new ArrayList<String>();
         this.enemyhavingPeices1 = new ArrayList<String>();
        
         jButton1.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ButtonPressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        for(String pcid:Piece.PieceIDList){
            this.havingPeices.add(pcid);
        }
        for(String pcid:Piece.PieceIDList){
            this.enemyhavingPeices.add(pcid);
        }
       for(String pcid:Piece.PieceIDList){
            this.havingPeices1.add(pcid);
        }
        for(String pcid:Piece.PieceIDList){
            this.enemyhavingPeices1.add(pcid);
        }
       
        
        BoardClickListener bcl = new BoardClickListener();      
       
        this.boardPanels = new BlokusPiecePanel[Board.BOARDSIZE][Board.BOARDSIZE];
        this.jPanel4.setLayout(new GridLayout(Board.BOARDSIZE, Board.BOARDSIZE));
        
        for (int i = 0; i < Board.BOARDSIZE; i++) {
            for (int j = 0; j < Board.BOARDSIZE; j++) {
                boardpiecepanel[i][j] = -1;
                boardPanels[i][j] = new BlokusPiecePanel();
                boardPanels[i][j].setpoint(i, j);
                boardPanels[i][j].addMouseListener(bcl);
                boardPanels[i][j].addMouseMotionListener(bcl);
                boardPanels[i][j].addMouseWheelListener(bcl);
                this.jPanel4.add(boardPanels[i][j]); 
            }
        }

    }

     public void ButtonPressed(ActionEvent e) throws IOException {
       
                if (check){    
                    String message = "406 PASS";
                    main.sendmessage(message);
                    System.out.println(message);
                    check = false;
                }
    }

    public void showRedPlayerPanel(int x) throws IOException {
        this.jPanel1.setLayout(new GridLayout(5, 5, 3, 3));
        
        for (int i = 1; i <= 21; i++) {
            //JLabel l = new JLabel("" + i, JLabel.CENTER);
            String a = null;
            if (x==0){
                 a = "./images/r";
            }
            else if (x==1){
                 a = "./images/b";
            }
            String b = a + String.valueOf(i) + ".png";
            //System.out.println(b);

            label0[i - 1] = new BlokusPieceLabel(i - 1, resizeByScaledInstance(b, 50, 50), JLabel.CENTER);
            label0[i - 1].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            label0[i - 1].setFont(label0[i - 1].getFont().deriveFont(20f));
            label0[i - 1].addMouseListener(pcl);
            this.jPanel1.add(label0[i - 1]);
        }
        pack();
    }

    
    public void showBluePlayerPanel(int x) throws IOException {
        this.jPanel3.setLayout(new GridLayout(5, 5, 3, 3));

        for (int i = 1; i <= 21; i++) {
           String a = null;
            if (x==0){
                 a = "./images/r";
            }
            else if (x==1){
                 a = "./images/b";
            }
            String b = a + String.valueOf(i) + ".png";
            //System.out.println(b);
            //JLabel l = new JLabel("" + i, JLabel.CENTER);
            label1[i - 1] = new BlokusPieceLabel(i - 1, resizeByScaledInstance(b, 50, 50), JLabel.CENTER);
            label1[i - 1].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            label1[i - 1].setFont(label1[i - 1].getFont().deriveFont(20f));
            //label1[i - 1].addMouseListener(new PieceLabelClickListener1());
            this.jPanel3.add(label1[i - 1]);
        }
        pack();

    }

    public static BufferedImage resizeByScaledInstance(String inputPath, int maxWidth, int maxHeight)
            throws IOException {

        // 元画像の読み込み
        BufferedImage sourceImage = ImageIO.read(new File(inputPath));

        // 縮小比率の計算
        BigDecimal bdW = new BigDecimal(maxWidth);
        bdW = bdW.divide(new BigDecimal(sourceImage.getWidth()), 8, BigDecimal.ROUND_HALF_UP);
        BigDecimal bdH = new BigDecimal(maxHeight);
        bdH = bdH.divide(new BigDecimal(sourceImage.getHeight()), 8, BigDecimal.ROUND_HALF_UP);
        // 縦横比は変えずに最大幅、最大高さを超えないように比率を指定する
        if (bdH.compareTo(bdW) < 0) {
            maxWidth = -1;
        } else {
            maxHeight = -1;
        }

        // スケールは以下から選択
        // Image.SCALE_AREA_AVERAGING 遅いがなめらか
        // Image.SCALE_DEFAULT 普通 速度はFASTと変わらない
        // Image.SCALE_FAST 早くて汚い
        // Image.SCALE_REPLICATE わからん そこそこ汚い
        // Image.SCALE_SMOOTH 遅くてなめらか
        Image targetImage = sourceImage.getScaledInstance(maxWidth, maxHeight, Image.SCALE_SMOOTH);

        // Image -> BufferedImageの変換
        BufferedImage targetBufferedImage = new BufferedImage(targetImage.getWidth(null), targetImage.getHeight(null),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = targetBufferedImage.createGraphics();
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
        Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, targetBufferedImage.getHeight(), targetBufferedImage.getHeight());
        g.fill(rect);
        g.setPaintMode();
        g.drawImage(targetImage, 0, 0, null);

        // 拡張子取得
        String ext = inputPath.substring(inputPath.lastIndexOf(".") + 1);
        // 変換画像の出力
        //ImageIO.write(targetBufferedImage, ext, new File(outputPath));
        return targetBufferedImage;
    }

    /**
     *
     *
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setText("Red:");

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel2.setText("Blue:");

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel3.setText("0");

        jLabel4.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel4.setText("0");

        jButton1.setText(" Surrender");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel4.setPreferredSize(new java.awt.Dimension(600, 600));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 550, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel3)
                        .addGap(47, 47, 47)
                        .addComponent(jLabel2)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel4)
                        .addGap(57, 57, 57)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(249, 249, 249))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 301, Short.MAX_VALUE)
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jLabel5.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel5.setText("自分");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel6.setText("相手");

        jLabel7.setText("未設定");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel6)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(19, 19, 19))))
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2)
                .addGap(11, 11, 11))
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    public void addText(String string) {
        this.jTextArea1.setText(this.jTextArea1.getText() + "\n" + string);
        this.jTextArea1.setCaretPosition(this.jTextArea1.getText().length());
    }

    private void setText(String string) {
        this.jTextArea1.setText(string);
    }

    private void drawBorder() {
        JComponent piece = (JComponent) jPanel1.getComponent(pieceIndex);
        piece.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
    }

    private void clearBorder() {
        JComponent piece = (JComponent) jPanel1.getComponent(pieceIndex);
        piece.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    private void drawBorder1() {
        JComponent piece = (JComponent) jPanel3.getComponent(pieceIndex);
        piece.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
    }

    private void clearBorder1() {
        JComponent piece = (JComponent) jPanel3.getComponent(pieceIndex);
        piece.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
    }

    public void clearboard(){
        for (int i = 0; i < Board.BOARDSIZE; i++) {
            for (int j = 0; j < Board.BOARDSIZE; j++) {
                if (this.boardpiecepanel[i][j] == -1){
                   this.boardPanels[i][j].setColor(-1);
                }else if (this.boardpiecepanel[i][j] == 0){
                   this.boardPanels[i][j].setColor(0);
                }else if (this.boardpiecepanel[i][j] == 1){
                   this.boardPanels[i][j].setColor(1);
                }
            }   
         }   
    }
    
        
   
    public void drawboard(int dir) {
       
        id = this.havingPeices.get(this.pieceIndex);
        putpiece = new Piece(id,dir);
        int nowpiece[][] = putpiece.getPiecePattern();
           
        int width = nowpiece[0].length;
        int hight = nowpiece.length;
       
        for (int i = 0 ;i < width; i++){
            for (int j = 0 ;j < hight; j++){
                  if(nowpiece[j][i] == 1){ 
                   this.boardPanels[j+selected.x][i+selected.y].setColor(playerid);
                  }
                }
             
            }
           
        }
        

    
    
    
    public void drawboard() {
        id = this.havingPeices.get(this.pieceIndex);
        putpiece = new Piece(id);
        int nowpiece[][] = putpiece.getPiecePattern();
           
        int width = nowpiece[0].length;
        int hight = nowpiece.length;
       
        for (int i = 0 ;i < width; i++){
            for (int j = 0 ;j < hight; j++){
                  if(nowpiece[i][j] == 1){ 
                     this.boardPanels[i+selected.x][j+selected.y].setColor(1);
                  }
                 
                }
             
            }
           
        }
        

    
    private void rotateClockwise(){
        this.putpiece.setDirction(turn());
        clearboard();
        direction = this.putpiece.getdirection();
        drawboard(this.putpiece.getdirection());
    }

    private void rotateCounterClockwise(){
        this.putpiece.setDirction(reverse_turn());
        clearboard();
        direction = this.putpiece.getdirection();
        drawboard(this.putpiece.getdirection());
    }
    private void filppiece(){
        this.putpiece.setDirction(reverse());
        clearboard();
        direction = this.putpiece.getdirection(); 
        drawboard(this.putpiece.getdirection());
    }
    
    private class PieceLabelClickListener implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            BlokusPieceLabel bp = (BlokusPieceLabel) e.getComponent();
            clearBorder();
            pieceIndex = bp.pieceIndex;
            direction = 0;
            drawBorder();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    private class PieceLabelClickListener1 implements MouseListener {

        @Override
        public void mousePressed(MouseEvent e) {
            BlokusPieceLabel bp = (BlokusPieceLabel) e.getComponent();
            clearBorder1();
            pieceIndex = bp.pieceIndex;
            drawBorder1();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }

    }

    class SurrenderListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    class BoardClickListener implements MouseListener, MouseMotionListener, MouseWheelListener {

        public void mouseClicked(MouseEvent e) {
            check1 = false;
            if (e.getButton() == MouseEvent.BUTTON3) {
               filppiece();
            } 
            check1 = true;
        }

        public void mousePressed(MouseEvent e) {
            check1 = false;
            if (e.getButton() == MouseEvent.BUTTON3) {
               
            } else{
                if (check){    
                    putpiece = new Piece(id,direction);
                    String str =  putpiece.getPieceIDwithDirection();
                    String message = "405 PLAY "+selected.y+" "+selected.x+" "+str;
                    main.sendmessage(message);
                    System.out.println(message);
                    check = false;
                }  
            }
            check1 = true;
        }
        

        public void mouseReleased(MouseEvent e) {

        }

        public void mouseEntered(MouseEvent e) {

        }

        public void mouseExited(MouseEvent e) {
            //selected = null;
          //  board.resetOverlay();
            clearboard();
        }

        public void mouseDragged(MouseEvent e) {

        }

        public void mouseMoved(MouseEvent e) {
               
               BlokusPiecePanel bp = (BlokusPiecePanel) e.getComponent();
               
               if (!selected.equals(bp.getpoint())&&check1){
                //System.out.println("aaa");
                selected = bp.getpoint();
                clearboard();
                drawboard(direction);
               }
        }

        public void mouseWheelMoved(MouseWheelEvent e) {
            if (e.getWheelRotation() > 0) {
                rotateClockwise();
                System.out.println("rote");
            } else {
                rotateCounterClockwise();
                 System.out.println("rote2");
            }
        }

    
        
    }
    
     public void setpiece(int id,String piece,int dir,int x,int y){
        System.out.println("SetPiece");
            
        delete_label_image(id,piece);
        putpiece = new Piece(piece,dir);
        int nowpiece[][] = putpiece.getPiecePattern();
           
        int width = nowpiece[0].length;
        int hight = nowpiece.length;
       
        for (int i = 0 ;i < width; i++){
            for (int j = 0 ;j < hight; j++){
                  if (nowpiece[j][i] == 1){
                   this.boardpiecepanel[j+y][i+x] = id;
                  }
                }
             
            }
         clearboard(); 
     }
    
     public void setscore(String a,String b){
         this.jLabel3.setText(a);
         this.jLabel4.setText(b);
         pack();
     }
     
      public void setback0(){
        this.jLabel7.setText("あなたの手番です");
         pack();
     }
      public void setback1(){
        this.jLabel7.setText("相手の手を待っています");
         pack();
     }
     public void all_label_enable(int id,boolean check){
            if (id == 0){
                 for (int i = 1; i <= 21; i++) {
                    label0[i - 1].setEnabled(check);
                    label1[i - 1].setEnabled(false);
                 }  
            }
            else if (id == 1){
                 for (int i = 1; i <= 21; i++) {
                    label1[i - 1].setEnabled(check);
                    label0[i - 1].setEnabled(false);
                 }
            }
        }
        
        public void delete_label_image(int id,String piece){
            System.out.println("id "+id);
                    
            if (this.main.playerid == id){
                int i = this.havingPeices.indexOf(piece);
                this.enemyhavingPeices.remove(piece);
                this.pieceIndex = this.havingPeices.indexOf(this.enemyhavingPeices.get(0));
                label0[i].setEnabled(false);
                label0[i].removeMouseListener(pcl); 
            }
            else{
                int i = this.havingPeices1.indexOf(piece);
                this.enemyhavingPeices1.remove(piece);
                label1[i].setEnabled(false);
            }
           
       }
            
        
    public int turn(){
     int x = this.putpiece.getdirection();
        if (x == 0){direction = x; return 1;}
        else if  (x == 1){ direction = x;  return 2;}
        else if  (x == 2){ direction = x;  return 3;}
        else if  (x == 3){ direction = x;  return 5;}
        else if  (x == 5){ direction = x;  return 6;}
        else if  (x == 6){ direction = x;  return 7;}
        else if  (x == 7){ direction = x;  return 5;}
       
        return 0;
    }
    public int reverse_turn(){
     int x = this.putpiece.getdirection();
        if (x == 0) return 3;
        else if  (x == 1)  return 0;
        else if  (x == 2)  return 1;
        else if  (x == 3)  return 2;
        else if  (x == 4)  return 7;
        else if  (x == 5)  return 4;
        else if  (x == 6)  return 5;
        else if  (x == 7)  return 6;
       
        return 0;
    }
    public int reverse(){
     int x = this.putpiece.getdirection();
        if (x == 0) return 4;
        else if  (x == 1)  return 5;
        else if  (x == 2)  return 6;
        else if  (x == 3)  return 7;
        else if  (x == 4)  return 0;
        else if  (x == 5)  return 1;
        else if  (x == 6)  return 2;
        else if  (x == 7)  return 3;
       
        return 0;
    }
    
    
    private javax.swing.JPanel jPanel5;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
}
