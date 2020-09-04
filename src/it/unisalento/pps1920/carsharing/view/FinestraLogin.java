package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.view.Listener.BottonLogListener;
import it.unisalento.pps1920.carsharing.view.Listener.BottonRegListener;

import javax.swing.*;
import java.awt.*;

public class FinestraLogin extends JFrame
{
    public JTextField username=new JTextField();
    public JTextField password=new JTextField();
    JPanel centro = new JPanel();
    JPanel sud = new JPanel();
    JButton b3 = new JButton("ANNULLA");
    JButton b2 = new JButton("REGISTRATI");
    JButton b1 = new JButton("ACCEDI");
    BottonLogListener listener;


    public FinestraLogin()
    {
        super("Login");
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        listener = new BottonLogListener(this);
        b3.addActionListener(listener);
        b3.setActionCommand(BottonLogListener.PULSANTE_ANNULLA);
        b1.addActionListener(listener);
        b1.setActionCommand(BottonLogListener.PULSANTE_ACCEDI);
        b2.addActionListener(listener);
        b2.setActionCommand(BottonLogListener.PULSANTE_REGISTRATI);
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(centro,BorderLayout.CENTER);
        c.add(sud,BorderLayout.SOUTH);
        sud.setLayout(new FlowLayout());
        sud.setBackground(Color.red);
        centro.setBackground(Color.yellow);
        centro.setLayout(new GridLayout(2,2));
        centro.add(new JLabel("     Username: "));
        centro.add(username);
        centro.add(new JLabel("     Password: "));
        centro.add(password);
        sud.add(b3);
        sud.add(b1);
        //sud.add(b2);

        this.setSize(300,150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

}