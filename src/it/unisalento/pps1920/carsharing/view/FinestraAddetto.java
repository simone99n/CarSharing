package it.unisalento.pps1920.carsharing.view;

import javax.swing.*;
import java.awt.*;

public class FinestraAddetto
{
    JPanel jp1=new JPanel();
    JPanel jp2=new JPanel();
    JButton b1= new JButton("Esci");
    JButton b2= new JButton("Pannello Segnalazioni");
    public FinestraAddetto(int id, String nome)
    {
        JFrame jf = new JFrame("OPERATORE :" + nome.toUpperCase());
        jf.setSize(900,900);
        Container c = new Container();
        c=jf.getContentPane();
        c.add(jp1,BorderLayout.CENTER);
        c.add(jp2,BorderLayout.SOUTH);
        jp1.setLayout(new BoxLayout(jp1,BoxLayout.PAGE_AXIS));
        jp2.setLayout(new FlowLayout());
        jp2.add(b1);
        jp2.add(b2);
        jf.setVisible(true);
        jf.dispose();
    }
}
