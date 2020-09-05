package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.ControlloAutomezziAddettoBusiness;
import it.unisalento.pps1920.carsharing.business.ControlloStatoPrenotazioniBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.model.Addetto;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.FinestraAddetto;
import it.unisalento.pps1920.carsharing.view.FinestraHomePage;
import it.unisalento.pps1920.carsharing.view.Listener.SuccessMessagesListener.AllSuccessMessages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class BottonAddettoListener implements ActionListener {



    private FinestraAddetto win;
    int idAddetto;
    String nomeAddetto;
    public static final String PULSANTE_VISUALIZZA_ACCESSORI_AUTOMEZZO = "PULSANTE_VISUALIZZA_ACCESSORI_AUTOMEZZO";
    public static final String PULSANTE_PRONTO = "PULSANTE_PRONTO";
    public static final String PULSANTE_NON_PRONTO = "PULSANTE_NON_PRONTO";
    public static final String PULSANTE_CENTRO_SEGNALAZIONI = "PULSANTE_CENTRO_SEGNALAZIONI";
    public static final String PULSANTE_INDIETRO = "PULSANTE_INDIETRO";
    public static final String PULSANTE_INVIA_RISCONTRO = "PULSANTE_INVIA_RISCONTRO";
    public static final String PULSANTE_INVIA_DEF = "PULSANTE_INVIA_DEF";
    public static final String PULSANTE_SEGNALAZIONI = "PULSANTE_SEGNALAZIONI";
    public static final String PULSANTE_LOGOUT = "PULSANTE_LOGOUT";

    public BottonAddettoListener(FinestraAddetto win,int idAddetto,String nomeAddetto) {

        this.win = win;
        this.idAddetto=idAddetto;
        this.nomeAddetto=nomeAddetto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //JButton button = (JButton) e.getSource();
        String command = e.getActionCommand();

        if (PULSANTE_VISUALIZZA_ACCESSORI_AUTOMEZZO.equals(command)) {
            int id;
            if(!win.jt.getText().isBlank()) {
                id=Integer.parseInt(win.jt.getText());
                win.visualizzaAccessoriAutomezzo(id);
            }
        }
        if (PULSANTE_PRONTO.equals(command)) {
            int id= Integer.parseInt(win.jt.getText());
            PrenotazioneBusiness.getInstance().automezzoPronto(id);
            win.dispose();
            FinestraAddetto ad= new FinestraAddetto(idAddetto,nomeAddetto);
            AllSuccessMessages al= new AllSuccessMessages(1);
        }
        if (PULSANTE_NON_PRONTO.equals(command)) {
            win.menu();
        }
        if (PULSANTE_CENTRO_SEGNALAZIONI.equals(command)){
            win.mostraPannelloSegnalazioni();
        }
        if(PULSANTE_INDIETRO.equals(command)){
            win.indietro();
        }
        if(PULSANTE_SEGNALAZIONI.equals(command)){
            win.mostraPannelloSegnalazioni();
        }
        if(PULSANTE_INVIA_RISCONTRO.equals(command)){
            win.panelloRiscontro();
        }
        if(PULSANTE_INVIA_DEF.equals(command)){
            Addetto addettoLoggato = (Addetto) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
            String data = DateUtil.stringFromDate(new Date());
            ControlloAutomezziAddettoBusiness.getInstance().inviaRiscontroAddetto("[Addetto della "+ControlloStatoPrenotazioniBusiness.getInstance().nomeStazioneFromAddetto(addettoLoggato.getId())+" ] "+data+":        "+win.testoRiscontro.getText(), Integer.parseInt(win.campoId.getText()));
            win.mostraPannelloSegnalazioni();
        }
        if (PULSANTE_LOGOUT.equals(command)){
            Session.getInstance().rimuovi(Session.UTENTE_LOGGATO);
            FinestraHomePage home = new FinestraHomePage();
            home.setVisible(true);
            win.dispose();
        }

    }
}