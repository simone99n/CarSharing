package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.FinestraCliente;
import it.unisalento.pps1920.carsharing.view.FinestraErrorCompilPren;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class BottonBarListener implements ActionListener {

    public static final String PULSANTE_OK = "PULSANTE_OK";
    public static final String PULSANTE_ANNULLA = "PULSANTE_ANNULLA";
    public static final String PULSANTE_ANNULLA2 = "PULSANTE_ANNULLA2";
    public static final String PULSANTE_ANNULLA3 = "PULSANTE_ANNULLA3";
    public static final String PULSANTE_ANNULLA4 = "PULSANTE_ANNULLA4";
    public static final String PULSANTE_NUOVA_PRENOTAZONE = "PULSANTE_NUOVA_PRENOTAZONE";
    public static final String PULSANTE_SALVA_PRENOTAZIONE = "PULSANTE_SALVA_PRENOTAZIONE";
    public static final String PULSANTE_ACCESSORI = "PULSANTE_ACCESSORI";
    public static final String PULSANTE_NO_ACCESSORI = "PULSANTE_NO_ACCESSORI";
    public static final String PULSANTE_MODIFICA = "PULSANTE_MODIFICA";
    public static final String PULSANTE_CANCELLA_ACCESSORIO = "PULSANTE_CANCELLA_ACCESSORIO";
    public static final String PULSANTE_AVANTI = "PULSANTE_AVANTI";
    public static final String PULSANTE_AVANTI2 = "PULSANTE_AVANTI2";
    public static final String PULSANTE_AVANTI3 = "PULSANTE_AVANTI3";

    private FinestraCliente win;

    public BottonBarListener(FinestraCliente win) {
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
            win.mostraTipologiaVeicolo();
        }
        else if(PULSANTE_SALVA_PRENOTAZIONE.equals(command)) {
            System.out.println("win.dataInizio :" + win.dataInizio.getText());
            System.out.println("win.dataFine :" + win.dataFine.getText());
            System.out.println("win.numPostiOccupati :" + win.numPostiOccupati.getText());
            if(win.dataInizio.getText().isEmpty() || win.dataFine.getText().isEmpty() || win.numPostiOccupati.getText().isEmpty()){
                FinestraErrorCompilPren err = new FinestraErrorCompilPren();
                err.setVisible(true);
            }
            else{
                win.mostraAccessori();
                Prenotazione nuova = salvaPrenotazione();
            }

        }
        else if(PULSANTE_ACCESSORI.equals(command)) {
            salvaAccessori();
            win.dispose();
            FinestraCliente win = new FinestraCliente();
            win.setVisible(true);
        }
        else if(PULSANTE_NO_ACCESSORI.equals(command)) {
            win.dispose();
            FinestraCliente win = new FinestraCliente();
            win.setVisible(true);
        }
        else if(PULSANTE_ANNULLA.equals(command)) {
            win.dispose();
            FinestraCliente win = new FinestraCliente();
            win.setVisible(true);
        }
        else if(PULSANTE_MODIFICA.equals(command)) {
            System.out.println("Mod");
        }
        else if(PULSANTE_CANCELLA_ACCESSORIO.equals(command)){
            System.out.println("Canc acc");
        }
        else if(PULSANTE_AVANTI.equals(command)){

                if(win.tipologie.getSelectedItem()=="AUTO"){
                    win.grandezza.removeAllItems();
                    win.mostraGrandezzaAuto();
                }
                else{
                    win.motorizzazioni.removeAllItems();
                    win.mostraSelezionaMotorizzazione();
                }

        }
        else if(PULSANTE_AVANTI2.equals(command)){
            if(win.motorizzazioni.getSelectedItem()==null){
                win.mostraSelezionaMotorizzazione();
            }
            else{
                win.motorizzazioni.removeAllItems();
                win.mostraSelezionaMotorizzazione();
            }

        }
        else if(PULSANTE_ANNULLA2.equals(command)){
            win.mostraTipologiaVeicoloBackButton();
        }
        else if(PULSANTE_ANNULLA3.equals(command)){
            if(win.tipologie.getSelectedItem()=="AUTO"){
                win.grandezza.removeAllItems();
                win.mostraGrandezzaAuto();
            }
            else{
                win.mostraTipologiaVeicoloBackButton();
            }

        }
        else if(PULSANTE_ANNULLA4.equals(command)){
            win.motorizzazioni.removeAllItems();
            win.mostraSelezionaMotorizzazione();
        }
        else if(PULSANTE_AVANTI3.equals(command)){
            win.mostraPannelloNuovaPrenotazione();
        }


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
            win.dispose();
            FinestraCliente win = new FinestraCliente();
            System.out.println("Non ci sono mezzi disponibili");
            return null;
        }

        p.setMezzo(mezzo);
        PrenotazioneBusiness.getInstance().inviaPrenotazione(p,0);
        return p;
    }

    private void salvaAccessori() {
        Accessorio a1 = (Accessorio) win.accessorio1.getSelectedItem();
        Accessorio a2 = (Accessorio) win.accessorio2.getSelectedItem();
        Accessorio a3 = (Accessorio) win.accessorio3.getSelectedItem();
        Accessorio a4 = (Accessorio) win.accessorio4.getSelectedItem();
        Accessorio a5 = (Accessorio) win.accessorio5.getSelectedItem();

        PrenotazioneBusiness.getInstance().inserisciAccessori(a1);
        PrenotazioneBusiness.getInstance().inserisciAccessori(a2);
        PrenotazioneBusiness.getInstance().inserisciAccessori(a3);
        PrenotazioneBusiness.getInstance().inserisciAccessori(a4);
        PrenotazioneBusiness.getInstance().inserisciAccessori(a5);
    }

}
