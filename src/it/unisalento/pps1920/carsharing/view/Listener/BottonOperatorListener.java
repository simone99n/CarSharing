package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.ControlloStatoPrenotazioniBusiness;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.FinestraOperatore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BottonOperatorListener implements ActionListener {



    private FinestraOperatore win;
    public static final String PULSANTE_ESCI = "PULSANTE_ESCI";
    public static final String PULSANTE_SEGNALAZIONI = "PULSANTE_SEGNALAZIONI";
    public static final String PULSANTE_VEICOLI_PRONTI = "PULSANTE_VEICOLI_PRONTI";
    public static final String PULSANTE_INDIETRO = "PULSANTE_INDIETRO";
    public static final String PULSANTE_INVIA_RISCONTRO = "PULSANTE_INVIA_RISCONTRO";
    public static final String PULSANTE_INVIA_DEF = "PULSANTE_INVIA_DEF";

    public BottonOperatorListener(FinestraOperatore win)
    {
        this.win=win;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(PULSANTE_ESCI.equals(command)) {
            win.dispose();
        }
        else if(PULSANTE_SEGNALAZIONI.equals(command)){
            win.mostraPannelloSegnalazioni();
        }
        else if (PULSANTE_VEICOLI_PRONTI.equals(command)){
            win.mostraVeicoliPronti();
        }
        else if (PULSANTE_INDIETRO.equals(command)){
            /*Operatore operatoreLoggato = (Operatore) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
            win.dispose();
            FinestraOperatore win = new FinestraOperatore(operatoreLoggato.getId_operatore(), operatoreLoggato.getNome());
            win.setVisible(true);*/
            win.tornaHome();
        }
        else if(PULSANTE_INVIA_RISCONTRO.equals(command)){
            win.panelloRiscontro();
        }
        else if (PULSANTE_INVIA_DEF.equals(command)){
            ControlloStatoPrenotazioniBusiness.getInstance().inviaRiscontro(win.testoRiscontro.getText(), Integer.parseInt(win.campoId.getText()));
            win.mostraPannelloSegnalazioni();
        }
    }

}