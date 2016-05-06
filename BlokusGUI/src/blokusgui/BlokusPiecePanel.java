/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokusgui;
/**
 *
 * @author tomoki-n
 */
import java.awt.Color;

public class BlokusPiecePanel extends javax.swing.JPanel {

    private static final Color[] playerColor = { new Color(255,51,51), new Color(153,153,255) };
    private static final Color noplayer = Color.white;
    /**
     * Creates new form BlokusPiecePanel
     */
    public BlokusPiecePanel() {
        initComponents();
    }
    
    public void setColor(int player){
        if(player==-1){
            this.setBackground(noplayer);
        }
        if(player>=0 && player < 2){
            this.setBackground(playerColor[player]);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 26, Short.MAX_VALUE)
        );
    }// </editor-fold>                        
    // Variables declaration - do not modify                     
    // End of variables declaration                   
}

