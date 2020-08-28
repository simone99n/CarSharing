package it.unisalento.pps1920.carsharing.view;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.view.Listener.BottonBarListener;
import it.unisalento.pps1920.carsharing.view.Listener.SharingListener;

import javax.swing.*;
import java.awt.*;


public class ConfirmSharing extends JFrame{

    SharingListener listener;
    JPanel nord, sud;

    public ConfirmSharing(){
        super("Conferma sharing");
        setSize(600,400);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        listener = new SharingListener(this);

        Container c=getContentPane();
        c.setLayout(new BorderLayout());
        nord = new JPanel();
        sud = new JPanel();
        c.add(nord, BorderLayout.NORTH);
        c.add(sud, BorderLayout.SOUTH);
        sud.setLayout(new FlowLayout());
        JButton accetta = new JButton("ACCETTA");
        sud.add(accetta);
        JButton rifiuta = new JButton("RIFIUTA");
        sud.add(rifiuta);
        accetta.addActionListener(listener);
        rifiuta.addActionListener(listener);
        accetta.setActionCommand(SharingListener.ACCETTA);
        rifiuta.setActionCommand(SharingListener.RIFIUTA);
    }

}
