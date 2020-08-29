package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.view.FinestraSharing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SharingListener implements ActionListener {

    public static final String ACCETTA = "ACCETTA";
    public static final String RIFIUTA = "RIFIUTA";
    public static Prenotazione p;

    private FinestraSharing win;

    public SharingListener(FinestraSharing win) {
        this.win = win;
    }


    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton)e.getSource();
        String command = e.getActionCommand();

        if(ACCETTA.equals(command)){
            System.out.println("Il cliente ha accettato lo sharing");
            win.dispose();
            PrenotazioneBusiness.getInstance().inviaPrenotazione(p,1);

        }
        else if(RIFIUTA.equals(command)){
            System.out.println("Il cliente ha rifiutato lo sharing");
            win.dispose();
            PrenotazioneBusiness.getInstance().inviaPrenotazione(p,2);

        }


    }


}
