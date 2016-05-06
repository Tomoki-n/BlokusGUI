/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokusgui;

import static blokusgui.PlayerPiecePanel.resizeByScaledInstance;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
    public int pieceIndex;
       
    public final String TitleString = "BlokusGUI";
    public final String VerString = "0.1";
   
    BlokusPieceLabel label0[] = new BlokusPieceLabel[25];
    BlokusPieceLabel label1[] = new BlokusPieceLabel[25];
    
    /**
     * Creates new form GamePanels
     */
    public GamePanels() throws IOException {
        initComponents();
        init();
         this.setTitle(TitleString + " ver."+VerString);
         this.setText("起動が完了しました");
    }
    
    
    public void init() throws IOException{
        //this.messageDialog = new TextMessageDialog(this,false);
        //this.showMessageDialog();
       
        this.showRedPlayerPanel();
        this.showBluePlayerPanel();
        
        this.boardPanels = new BlokusPiecePanel[Board.BOARDSIZE][Board.BOARDSIZE];
            this.jPanel4.setLayout(new GridLayout(Board.BOARDSIZE,Board.BOARDSIZE));
        for(int i=0;i<Board.BOARDSIZE;i++){
            for(int j=0;j<Board.BOARDSIZE;j++){
                boardPanels[i][j] = new BlokusPiecePanel();
                this.jPanel4.add(boardPanels[i][j]);
            }
        }
        
      
    }
    
    public void showRedPlayerPanel() throws IOException{
         this.jPanel1.setLayout(new GridLayout(5, 5, 3, 3));

          for (int i = 1; i <= 21; i++) {
            //JLabel l = new JLabel("" + i, JLabel.CENTER);
            String a = "./images/r";
            String b = a + String.valueOf(i) + ".png";
            System.out.println(b);
                   
            label0[i-1] = new BlokusPieceLabel(i-1,resizeByScaledInstance(b,50,50), JLabel.CENTER);
            label0[i-1].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            label0[i-1].setFont(label0[i-1].getFont().deriveFont(20f));
            label0[i-1].addMouseListener(new PieceLabelClickListener());
            this.jPanel1.add(label0[i-1]);
        }
        
    }
    public void showBluePlayerPanel() throws IOException{
        this.jPanel3.setLayout(new GridLayout(5, 5, 3, 3));

         for (int i = 1; i <= 21; i++) {
            String a = "./images/b";
            String b = a + String.valueOf(i) + ".png";
            System.out.println(b);
            //JLabel l = new JLabel("" + i, JLabel.CENTER);
            label1[i-1] = new BlokusPieceLabel(i-1,resizeByScaledInstance(b,50,50), JLabel.CENTER);
            label1[i-1].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            label1[i-1].setFont(label1[i-1].getFont().deriveFont(20f));
            label1[i-1].addMouseListener(new PieceLabelClickListener1());
            this.jPanel3.add(label1[i-1]);
        }
        
        
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
        }
        else {
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
Rectangle2D.Double rect = new Rectangle2D.Double(0,0,targetBufferedImage.getHeight(),targetBufferedImage.getHeight());
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
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(249, 249, 249))
        );

        jPanel1.setBackground(new java.awt.Color(255, 51, 0));

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

        jPanel3.setBackground(new java.awt.Color(102, 255, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 301, Short.MAX_VALUE)
        );

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

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
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GamePanels.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GamePanels.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GamePanels.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GamePanels.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GamePanels().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(GamePanels.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

     public void addText(String string) {
        this.jTextArea1.setText(this.jTextArea1.getText()+"\n"+string);
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
    
     private class PieceLabelClickListener implements MouseListener {
           
        @Override
        public void mousePressed(MouseEvent e) {
            BlokusPieceLabel bp = (BlokusPieceLabel) e.getComponent();
                clearBorder();
                pieceIndex = bp.pieceIndex;
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
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
