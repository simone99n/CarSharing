package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.model.Stazione;
import it.unisalento.pps1920.carsharing.view.Listener.BottonBarListener;
import it.unisalento.pps1920.carsharing.view.Listener.BottonRegListener;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FinestraRegistrazione extends JFrame
{
    JButton butt= new JButton("REGISTRATI!");
    JPanel centro;
    JPanel sud;
    JPanel nord;
    BottonRegListener listener;
    public JTextField Nome = new JTextField(20);
    public JTextField Cognome = new JTextField(20);
    public JTextField Email = new JTextField(20);
    public JTextField Username = new JTextField(20);
    public JTextField Password = new JTextField(20);
    public JTextField Telefono = new JTextField(20);
    public JTextField Residenza = new JTextField(20);
    public JTextField Eta = new JTextField(20);

    public FinestraRegistrazione()
    {

        super("REGISTRAZIONE");
        listener = new BottonRegListener(this);
        JButton butt1= new JButton("Annulla");
        JButton butt2= new JButton("Registrati");
        butt2.addActionListener(listener);
        butt2.setActionCommand(BottonRegListener.PULSANTE_REGISTRATI);
        butt1.addActionListener(listener);
        butt1.setActionCommand(BottonRegListener.PULSANTE_ANNULLA);
        sud=new JPanel();
        centro= new JPanel();
       // nord=new JPanel();
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(centro,BorderLayout.CENTER);
        c.add(sud,BorderLayout.SOUTH);
       // c.add(nord,BorderLayout.NORTH);
        sud.add(butt1);
        sud.add(butt2);
        sud.setBackground(Color.red);
        centro.setLayout(new GridLayout(8,2));
        centro.setBackground(Color.yellow);
        centro.add(new JLabel("     Nome: "));
        centro.add(Nome);
        centro.add(new JLabel("     Cognome"));
        centro.add(Cognome);
        centro.add(new JLabel("     Email: "));
        centro.add(Email);
        centro.add(new JLabel("     Username"));
        centro.add(Username);
        centro.add(new JLabel("     Telefono: "));
        centro.add(Telefono);
        centro.add(new JLabel("     Password"));
        centro.add(Password);
        centro.add(new JLabel("     Residenza: "));
        centro.add(Residenza);
        centro.add(new JLabel("     Eta': "));
        centro.add(Eta);

        sud.setLayout(new FlowLayout());
        this.setSize(700,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);


    }


}
