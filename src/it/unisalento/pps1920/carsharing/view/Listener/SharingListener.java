package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.view.ConfirmSharing;
import it.unisalento.pps1920.carsharing.view.FinestraConGerarchia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;



public class SharingListener implements ActionListener {

    public static final String ACCETTA = "ACCETTA";
    public static final String RIFIUTA = "RIFIUTA";
    public static Prenotazione p;

    private ConfirmSharing win;

    public SharingListener(ConfirmSharing win) {
        this.win = win;
    }


    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton)e.getSource();
        String command = e.getActionCommand();

        if(ACCETTA.equals(command)){
            System.out.println("Il cliente ha accettato lo sharing");
            PrenotazioneBusiness.getInstance().inviaPrenotazione(p,1);
            win.dispose();
        }
        else if(RIFIUTA.equals(command)){
            System.out.println("Il cliente ha rifiutato lo sharing");
            PrenotazioneBusiness.getInstance().inviaPrenotazione(p,2);
            win.dispose();
        }


    }


}
