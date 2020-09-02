package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.ControlloStatoPrenotazioniBusiness;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.view.Listener.BottonLogListener;
import it.unisalento.pps1920.carsharing.view.Listener.BottonOperatorListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FinestraOperatore extends  JFrame
{

    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    JButton b1= new JButton("Esci");
    JButton b2= new JButton("Pannello Segnalazioni");

    BottonOperatorListener listener;
    public FinestraOperatore(int id, String nome)
    {
        super("OPERATORE : "+nome.toUpperCase());
       this.setSize(900,900);
        Container c = new Container();
        c=this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new JScrollPane(jp1), BorderLayout.CENTER);
        jp1.setLayout(new BorderLayout());
        setupPannelloPrenotazioni(id);
        c.add(jp2,BorderLayout.SOUTH);
        c.add(jp3,BorderLayout.NORTH);
        jp2.setLayout(new FlowLayout());
        jp2.add(b1);
        jp2.add(b2);
        jp3.setLayout(new FlowLayout());
        JLabel tx= new JLabel("<<<TABELLA PRENOTAZIONI>>>");
        jp3.add(tx);
        JLabel bb= new JLabel("  ");
        jp3.add(bb);
        jp3.setBackground(Color.RED);
        listener = new BottonOperatorListener(this);
        b1.addActionListener(listener);
        b1.setActionCommand(BottonOperatorListener.PULSANTE_ESCI);
        b2.addActionListener(listener);
        b2.setActionCommand(BottonOperatorListener.PULSANTE_SEGNALAZIONI);
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        this.setVisible(true);
    }


    public void setupPannelloPrenotazioni(int id)
    {
        ArrayList<Prenotazione>prenotazioni= new ArrayList<Prenotazione>();
        ControlloStatoPrenotazioniBusiness cont= new ControlloStatoPrenotazioniBusiness();
        prenotazioni=cont.checkPrenotazioni(id);
        TablePrenotazioniOperatore tmp = new TablePrenotazioniOperatore(prenotazioni);
        JTable tabellaPrenotazioni = new JTable(tmp);
        JTable intestazione = new JTable(1,5);
        intestazione.setValueAt("ID PRENOTAZIONE",0,0);
        intestazione.setValueAt("DATA",0,1);
        intestazione.setValueAt("DATA/ORA INZIO",0,2);
        intestazione.setValueAt("DATA/ORA FINE",0,3);
        intestazione.setValueAt("STATO",0,4);
        jp1.add(intestazione, BorderLayout.NORTH);
        jp1.add(tabellaPrenotazioni,BorderLayout.CENTER);
    }

}
