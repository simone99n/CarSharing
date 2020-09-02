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
    JPanel centro;
    JPanel sud;
    JPanel nord;
    BottonRegListener listener;
    public JTextField nome = new JTextField(20);
    public JTextField cognome = new JTextField(20);
    public JTextField email = new JTextField(20);
    public JTextField username = new JTextField(20);
    public JTextField password = new JTextField(20);
    public JTextField telefono = new JTextField(20);
    public JTextField residenza = new JTextField(20);
    public JTextField eta = new JTextField(20);

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
        centro.add(nome);
        centro.add(new JLabel("     Cognome"));
        centro.add(cognome);
        centro.add(new JLabel("     Email: "));
        centro.add(email);
        centro.add(new JLabel("     Username"));
        centro.add(username);
        centro.add(new JLabel("     Telefono: "));
        centro.add(telefono);
        centro.add(new JLabel("     Password"));
        centro.add(password);
        centro.add(new JLabel("     Residenza: "));
        centro.add(residenza);
        centro.add(new JLabel("     Eta' (Anno di nascita es: 1998): "));
        centro.add(eta);

        sud.setLayout(new FlowLayout());
        this.setSize(400,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);

    }


}