package it.unisalento.pps1920.carsharing.view;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.view.Listener.BottonBarListener;
import it.unisalento.pps1920.carsharing.view.Listener.SharingListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class FinestraSharing extends JFrame{

    SharingListener listener;
    JPanel nord, sud;
    public static int idPrenotazione;
    JLabel nome,cognome,residenza,foto;

    public FinestraSharing(float prezzo){
        super("E' stato rilevato uno sharing!");
        setSize(500,400);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        listener = new SharingListener(this);

        Container c=getContentPane();
        c.setLayout(new BorderLayout());
        nord = new JPanel();
        sud = new JPanel();
        c.add(nord, BorderLayout.NORTH);
        c.add(sud, BorderLayout.SOUTH);
        c.add(new JScrollPane(nord), BorderLayout.CENTER);

        sud.setLayout(new FlowLayout());
        JButton accetta = new JButton("ACCETTA E PAGA "+(prezzo/2)+"€");
        JButton rifiuta = new JButton("RIFIUTA E PAGA "+prezzo+"€");
        sud.add(rifiuta);
        sud.add(accetta);
        setupShowCliente(); //-----------------------------------

        accetta.addActionListener(listener);
        rifiuta.addActionListener(listener);
        accetta.setActionCommand(SharingListener.ACCETTA);
        rifiuta.setActionCommand(SharingListener.RIFIUTA);


    }


    public void setupShowCliente(){
     // ArrayList<String[]> nomi = PrenotazioneBusiness.getInstance().getNomeFromIdPrenotazione(idPrenotazione);
     // ArrayList<String[]> cognomi = PrenotazioneBusiness.getInstance().getCognomeFromIdPrenotazione(idPrenotazione);
        ArrayList<String[]> all = null;
        int numClienti = PrenotazioneBusiness.getInstance().getNumClienti(idPrenotazione);
        nord.setLayout(new GridLayout(7*numClienti+2,2));
        nord.add(new JLabel("CLIENTI COINVOLTI NELLO SHARING:"));
        nord.add(new JLabel());
        nord.add(new JLabel());
        nord.add(new JLabel());
        for(int i=0;i<numClienti;i++){
            all=PrenotazioneBusiness.getInstance().getInfoClienteFromIdPrenotazione(idPrenotazione,i);

            nord.add(new JLabel("Cliente "+(i+1)+": "));
            nord.add(new JLabel());
            nord.add(new JLabel("Nome: "));
            nord.add(new JLabel(all.get(0)[0]));
            nord.add(new JLabel("Cognome: "));
            nord.add(new JLabel(all.get(0)[1]));
            nord.add(new JLabel("Residenza: "));
            nord.add(new JLabel(all.get(0)[2]));
            nord.add(new JLabel("Anno di nascita: "));
            nord.add(new JLabel(all.get(0)[3]));
            nord.add(new JLabel("Foto: "));
            nord.add(new JLabel());
            nord.add(new JLabel());
            nord.add(new JLabel());
        }

    }

}
