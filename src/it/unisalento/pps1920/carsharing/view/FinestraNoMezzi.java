package it.unisalento.pps1920.carsharing.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FinestraNoMezzi extends JFrame {

    public FinestraNoMezzi(){
        super("WARNING");
        setSize(300,130);
        setResizable(false);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );

        Container c=getContentPane();
        c.setLayout(new BorderLayout());
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JButton button = new JButton("OK");
        JLabel info1 = new JLabel("Non sono disponibili veicoli");
        JLabel info2 = new JLabel("con queste caratteristiche!");
        panel1.add(info1);
        panel2.add(info2);
        c.add(panel1,BorderLayout.NORTH);
        c.add(panel2,BorderLayout.CENTER);
        c.add(panel3,BorderLayout.SOUTH);
        panel3.add(button);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    close();
                }
            }
        });
    }

    private void close(){
        this.dispose();
    }
}
