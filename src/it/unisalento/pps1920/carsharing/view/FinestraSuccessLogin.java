package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.view.Listener.BottonLogListener;
import it.unisalento.pps1920.carsharing.view.Listener.ConfirmButtonListener.OkLoginListener;

import javax.swing.*;
import java.awt.*;

public class FinestraSuccessLogin extends JFrame
{
    OkLoginListener listener;
    JButton b1 = new JButton("OK");

    public FinestraSuccessLogin()
    {
        super("AUTENTICAZIONE RIUSCITA");
        listener = new OkLoginListener(this);
        b1.addActionListener(listener);
        b1.setActionCommand(OkLoginListener.PULSANTE_OK);
        setSize(700,150);
        Container c = new Container();
        c=this.getContentPane();
        JPanel jp1= new JPanel();
        JPanel jp2 = new JPanel();
        c.add(jp1,BorderLayout.CENTER);
        c.add(jp2,BorderLayout.NORTH);
        jp1.setLayout(new FlowLayout());
        jp2.add(new JLabel("     AUTENTICAZIONE RIUSCITA CON SUCCESSO! VERRAI REINDIRIZZATO NELLA TUA HOMEPAGE. "));
        jp1.add(b1);
        setVisible(true);
    }

}
