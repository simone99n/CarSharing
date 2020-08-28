package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.ILocalitaDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IStazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.ClienteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.LocalitaDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.MezzoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.StazioneDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.ConfirmSharing;
import it.unisalento.pps1920.carsharing.view.FinestraConGerarchia;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class BottonBarListener implements ActionListener {

    public static final String PULSANTE_OK = "PULSANTE_OK";
    public static final String PULSANTE_ANNULLA = "PULSANTE_ANNULLA";
    public static final String PULSANTE_NUOVA_PRENOTAZONE = "PULSANTE_NUOVA_PRENOTAZONE";
    public static final String PULSANTE_SALVA_PRENOTAZIONE = "PULSANTE_SALVA_PRENOTAZIONE";
    //public static final String MODIFICA_FOTO = "MODIFICA_FOTO";

    private FinestraConGerarchia win;

    public BottonBarListener(FinestraConGerarchia win) {
        this.win = win;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton button = (JButton)e.getSource();

        System.out.println("Pulsante premuto "+button.getText());

        /* SBAGLIATO !!!
        if("OK".equals(button.getText()))
            System.out.println("Valore del campo di testo: "+win.getTesto());
         */

        String command = e.getActionCommand();
        if(PULSANTE_OK.equals(command))
            System.out.println("Valore del campo di testo: "+win.getTesto());
        else if(PULSANTE_NUOVA_PRENOTAZONE.equals(command)) {
            win.mostraPannelloNuovaPrenotazione();
        }
        else if(PULSANTE_SALVA_PRENOTAZIONE.equals(command)) {
            Prenotazione nuova = salvaPrenotazione();
        }
        /*else if(MODIFICA_FOTO.equals(command)){
            System.out.println("PROVA");
        }
        */
    }

    private Prenotazione salvaPrenotazione() {
        Cliente clienteLoggato = (Cliente)Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        Stazione partenza = (Stazione)win.partenza.getSelectedItem();
        Stazione arrivo = (Stazione)win.arrivo.getSelectedItem();
        Localita posto = (Localita)win.localita.getSelectedItem();
        Modello modello = (Modello)win.modello.getSelectedItem();
        String datapartenza = win.dataInizio.getText();
        String dataarrivo = win.dataFine.getText();
        Mezzo mezzo = new Mezzo();
        int postiOccupati = Integer.parseInt(win.numPostiOccupati.getText());

        Prenotazione p = new Prenotazione();
        //p.setDataFine(DateUtil.dateTimeFromString("2019-12-06 19:00:00"));
        p.setDataInizio(DateUtil.dateTimeFromString(datapartenza +":00"));
        p.setDataFine(DateUtil.dateTimeFromString(dataarrivo +":00"));
        p.setData(new Date());
        p.setCliente(clienteLoggato);
        p.setNumPostiOccupati(postiOccupati);
        p.setLocalita(posto);
        p.setPartenza(partenza);
        p.setArrivo(arrivo);
        if(PrenotazioneBusiness.getInstance().disponibilitaMezzo(modello)){     //per ogni modello esistono più mezzi(uno per ogni targa), perciò SE c'è disponibilità di mezzi dato un certo modello
            mezzo = PrenotazioneBusiness.getInstance().returnOneMezzo(modello); //ALLORA se ne seleziona uno
        }
        else{
            //TODO tornare alla home
            System.out.println("Non ci sono mezzi disponibili");
            return null;
        }

        p.setMezzo(mezzo);
        PrenotazioneBusiness.getInstance().inviaPrenotazione(p);
        return p;
    }



}
