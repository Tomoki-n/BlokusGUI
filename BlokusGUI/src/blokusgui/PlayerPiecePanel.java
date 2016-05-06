/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokusgui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 *
 * @author tomoki-n
 */
public class PlayerPiecePanel {
   
    public PlayerPiecePanel() throws IOException{
        player0piece();
        player1piece();     
    }
    
    JLabel label0[] = new JLabel[25];
    JLabel label1[] = new JLabel[25];
    JPanel panel;
    JPanel panel1;
    JFrame f;
    JFrame f1;
    
    
    private void player0piece() throws IOException{
         f = new JFrame("Red Player");

         panel = new JPanel(new GridLayout(5, 5, 3, 3));

        for (int i = 1; i <= 21; i++) {
            //JLabel l = new JLabel("" + i, JLabel.CENTER);
            String a = "./images/r";
            String b = a + String.valueOf(i) + ".png";
            System.out.println(b);
                   
            label0[i-1] = new JLabel(new ImageIcon(resizeByScaledInstance(b,80,80)), JLabel.CENTER);
            label0[i-1].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            label0[i-1].setFont(label0[i-1].getFont().deriveFont(20f));
            label0[i-1].setText(String.valueOf(i-1));
            panel.add(label0[i-1]);
        }

        f.setContentPane(panel);
        f.setSize(500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
     private void player1piece() throws IOException{
        f1 = new JFrame("Blue Player");

       panel = new JPanel(new GridLayout(5, 5, 3, 3));

        for (int i = 1; i <= 21; i++) {
            String a = "./images/b";
            String b = a + String.valueOf(i) + ".png";
            System.out.println(b);
            //JLabel l = new JLabel("" + i, JLabel.CENTER);
            label1[i-1] = new JLabel(new ImageIcon(resizeByScaledInstance(b,80,80)), JLabel.CENTER);
            label1[i-1].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            label1[i-1].setFont(label0[i-1].getFont().deriveFont(20f));
            label1[i-1].setText(String.valueOf(i-1));
            panel.add(label0[i-1]);
        }

        f.setContentPane(panel);
        f.setSize(500, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    
  public static class BlokusPieceLabel extends JLabel {
        public int pieceIndex;

        public BlokusPieceLabel(int pieceIndex) {
            this.pieceIndex = pieceIndex;
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

}
    

