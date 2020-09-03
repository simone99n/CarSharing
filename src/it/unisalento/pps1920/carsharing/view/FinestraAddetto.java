package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.ControlloAutomezziAddettoBusiness;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.view.Listener.BottonAddettoListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FinestraAddetto extends  JFrame
{
    JButton butt = new JButton("Visualizza Automezzo");
    JLabel lab = new JLabel("ID PRENOTAZIONE: ");
    JPanel jp1=new JPanel(new BorderLayout());
    JPanel jp2=new JPanel(new BorderLayout());
    JPanel jp3=new JPanel(new FlowLayout());
    JPanel jp2_1= new JPanel(new FlowLayout());
    JPanel jp2_2= new JPanel(new BorderLayout());
    public JTextField jt = new JTextField(20);
    JButton b2= new JButton("Pannello Segnalazioni");
    BottonAddettoListener listener;

    public FinestraAddetto(int id, String nome)
    {
        super("ADDETTO : "+nome.toUpperCase());
        listener = new BottonAddettoListener(this);
        butt.addActionListener(listener);
        butt.setActionCommand(BottonAddettoListener.PULSANTE_VISUALIZZA_AUTOMEZZO);
        setSize(800,800);
        Container c = new Container();
        c=this.getContentPane();
        c.add(jp1,BorderLayout.CENTER);
        c.add(jp2,BorderLayout.NORTH);
        c.add(jp3,BorderLayout.SOUTH);
        jp2.add(jp2_1,BorderLayout.NORTH);
        jp2.add(jp2_2,BorderLayout.SOUTH);
        jp2_1.add(lab);
        jp2_1.add(jt);
        jp3.add(b2);
        JLabel bb= new JLabel("  ");
        jp3.add(bb);
        jp2_1.add(butt);
        jp3.setBackground(Color.RED);
        setupPannelloPrenotazioni(id);
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }


    public void setupPannelloPrenotazioni(int id)
    {
        ArrayList<Prenotazione>prenotazioni= new ArrayList<Prenotazione>();
        ControlloAutomezziAddettoBusiness cont = new ControlloAutomezziAddettoBusiness();
        prenotazioni=cont.checkPrenotazioni(id);
        TablePrenotazioniAmministratore tmp = new TablePrenotazioniAmministratore(prenotazioni);
        JTable tabellaPrenotazioni = new JTable(tmp);
        JTable intestazione = new JTable(1,5);
        intestazione.setValueAt("ID PRENOTAZIONE",0,0);
        intestazione.setValueAt("DATA",0,1);
        intestazione.setValueAt("DATA/ORA INZIO",0,2);
        intestazione.setValueAt("DATA/ORA FINE",0,3);
        intestazione.setValueAt("STATO",0,4);
        jp2_2.add(intestazione, BorderLayout.SOUTH);
        jp1.add(tabellaPrenotazioni,BorderLayout.CENTER);
    }

    public void visualizzaAutomezzo(int id)
    {
        BorderLayout al = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(al.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(al.getLayoutComponent(BorderLayout.NORTH));

        ControlloAutomezziAddettoBusiness cont = new ControlloAutomezziAddettoBusiness();
        Modello mod = new Modello();
        ArrayList<Accessorio> acc= new ArrayList<>();
        mod=cont.getModelFromIdPrenotazione(id);
        acc=cont.getAccessoriFromIdPrenotazione(id);

        /*
        //ricreare la table per la visualizzazione delle auto
        TableMezziAddetto tmp = new TableMezziAddetto(prenotazioni);
        JTable tabellaPrenotazioni = new JTable(tmp);
        JTable intestazione = new JTable(1,5);
        intestazione.setValueAt("ID PRENOTAZIONE",0,0);
        intestazione.setValueAt("DATA",0,1);
        intestazione.setValueAt("DATA/ORA INZIO",0,2);
        intestazione.setValueAt("DATA/ORA FINE",0,3);
        intestazione.setValueAt("STATO",0,4);
        */

        JPanel pan = new JPanel(new BorderLayout());
        JPanel pan1 = new JPanel(new BorderLayout());
        this.getContentPane().add(pan, BorderLayout.CENTER);
        this.getContentPane().add(pan1, BorderLayout.NORTH);
        pan.setLayout(new FlowLayout());
        pan1.setLayout(new FlowLayout());
        repaint();
        revalidate();
    }
}