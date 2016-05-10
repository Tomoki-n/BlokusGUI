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
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Init extends javax.swing.JDialog {
    /** Creates new form InputDialog */
    public Init(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public JTextField text1;
    public JTextField text2;
    public JTextField text3;

    private  JLabel label1;
    private  JLabel label2;
    private  JLabel label3;

    private void initComponents(){
        this.getContentPane().setLayout(new GridLayout(4,1));
        Dimension textFiledSize = new Dimension(200,20);
        Dimension labelsize = new Dimension(50,20);

        JPanel panel1 = new JPanel();
        this.label1 = new JLabel("IP");
        label1.setPreferredSize(labelsize);
        panel1.add(this.label1,BorderLayout.WEST);
        this.text1 = new JTextField("192.168.0.0");
        this.text1.setPreferredSize(textFiledSize);
        panel1.add(this.text1,BorderLayout.EAST);
        this.getContentPane().add(panel1);

        JPanel panel2 = new JPanel();
        this.label2 = new JLabel("Port");
        label2.setPreferredSize(labelsize);
        panel2.add(this.label2,BorderLayout.WEST);
        this.text2 = new JTextField("16041");
        panel2.add(this.text2,BorderLayout.EAST);
        this.text2.setPreferredSize(textFiledSize);
        this.getContentPane().add(panel2);


        JPanel panel3 = new JPanel();
        this.label3 = new JLabel("Name");
        label3.setPreferredSize(labelsize);
        panel3.add(this.label3, BorderLayout.WEST);
        this.text3 = new JTextField("Blokus");
        this.text3.setPreferredSize(textFiledSize);
        panel3.add(this.text3,BorderLayout.EAST);
        this.getContentPane().add(panel3);

        JPanel panel4 = new JPanel();
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Init.this.OKButtonPressed(e);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
                                          @Override
                                          public void actionPerformed(ActionEvent e) {
                                                Init.this.ClearButtonPressed(e);
                                          }
                                      });

                panel4.add(clearButton, BorderLayout.WEST);
        panel4.add(okButton,BorderLayout.EAST);
        this.getContentPane().add(panel4);

        this.pack();
    }

    public void OKButtonPressed(ActionEvent e) throws IOException {
       
       dispose();
    }

    public void ClearButtonPressed(ActionEvent e){

        text1.setText("");
        text2.setText("");
        text3.setText("");
    }
    private ActionListener ClearButtonPressed = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    };

   

}