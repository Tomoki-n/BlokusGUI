/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokusgui;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author tomoki-n
 */
public class BlokusPieceLabel extends JLabel {
        public int pieceIndex;

        public BlokusPieceLabel(int pieceIndex, BufferedImage image, int size) {
            super(new ImageIcon(image),size);
            this.pieceIndex = pieceIndex;
        }
 }