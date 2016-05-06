/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blokusgui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author tomoki-n
 */
public class BlokusGUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
      

        JFrame main = new JFrame();
        main.setVisible(true);
        Init test = new Init(main, true);
        test.setVisible(true);   
        // TODO code application logic here
    }
    
    
    
    
    
    
    
    
    
}
