package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.ILocalitaDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IStazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.LocalitaDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.MezzoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.StazioneDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
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
    }

    private Prenotazione salvaPrenotazione() {

        Stazione partenza = (Stazione)win.partenza.getSelectedItem();

        //TODO rimuovere i riferimenti ai DAO sostituendo con metodi di business
        Prenotazione p = new Prenotazione();
        p.setDataInizio(DateUtil.dateTimeFromString("2019-12-03 09:00:00"));
        p.setDataFine(DateUtil.dateTimeFromString("2019-12-06 19:00:00"));
        p.setData(new Date());

        //IClienteDAO cDao = new ClienteDAO();
        Cliente clienteLoggato = (Cliente)Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        p.setCliente(clienteLoggato);

        p.setNumPostiOccupati(1);
        ILocalitaDAO lDao = new LocalitaDAO();
        p.setLocalita(lDao.findById(1));
        IStazioneDAO sDao = new StazioneDAO();
        p.setPartenza(partenza);
        p.setArrivo(sDao.findById(1));
        IMezzoDAO mDao = new MezzoDAO();
        p.setMezzo(mDao.findById(2));
        PrenotazioneBusiness.getInstance().inviaPrenotazione(p);

        return p;
    }

}
