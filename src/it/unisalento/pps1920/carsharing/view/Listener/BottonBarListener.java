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

    public static final String PULSANTE_PAGA = "PULSANTE_PAGA";
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
    Prenotazione nuova;

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

            if(win.dataInizio.getText().isEmpty() || win.dataFine.getText().isEmpty() || win.numPostiOccupati.getText().isEmpty()){
                FinestraErrorCompilPren err = new FinestraErrorCompilPren();
                err.setVisible(true);
            }
            else{   //se tutti i campi sono compilati

                String[] annoEmeseInizio = win.dataInizio.getText().split("-");
                String[] giornoEoraInizio = annoEmeseInizio[2].split(" ");
                String[] oreEminutiInizio = giornoEoraInizio[1].split(":");
                String[] annoEmeseFine = win.dataFine.getText().split("-");
                String[] giornoEoraFine = annoEmeseFine[2].split(" ");
                String[] oreEminutiFine = giornoEoraFine[1].split(":");

                if(Integer.parseInt(annoEmeseInizio[0]) > Integer.parseInt(annoEmeseFine[0])){ //se anno inizio > anno fine
                    FinestraErrorCompilPren err = new FinestraErrorCompilPren();
                    err.setVisible(true);
                    System.out.println("Questo carsharing non è una macchina del tempo (anno inizio > anno fine)");
                }
                else if(Integer.parseInt(annoEmeseInizio[0]) == Integer.parseInt(annoEmeseFine[0])){ //se anni coincidono

                    if(Integer.parseInt(annoEmeseInizio[1]) > Integer.parseInt(annoEmeseFine[1])){  //se mese inizio > mese fine
                        FinestraErrorCompilPren err = new FinestraErrorCompilPren();
                        err.setVisible(true);
                        System.out.println("Questo carsharing non è una macchina del tempo(anni coincidono, mese inizio > mese fine)");
                    }
                    else if(Integer.parseInt(annoEmeseInizio[1]) == Integer.parseInt(annoEmeseFine[1])){ //se mesi uguali
                        if(Integer.parseInt(giornoEoraInizio[0]) > Integer.parseInt(giornoEoraFine[0])){ //se giorno inizio > giorno fine
                            FinestraErrorCompilPren err = new FinestraErrorCompilPren();
                            err.setVisible(true);
                            System.out.println("Questo carsharing non è una macchina del tempo(mesi coincidono, giorno inizio > giorno fine)");
                        }
                        else if(Integer.parseInt(giornoEoraInizio[0]) == Integer.parseInt(giornoEoraFine[0])){
                            FinestraErrorCompilPren err = new FinestraErrorCompilPren();
                            err.setVisible(true);
                            System.out.println("Giorno inizio non può coincidere con giorno fine");
                        }
                        else{
                            nuova = salvaPrenotazione();
                            float prezzoFinale;
                            if(nuova!=null){
                                prezzoFinale=PrenotazioneBusiness.getInstance().calcolaPrezzo(nuova, Integer.parseInt(annoEmeseInizio[0]),Integer.parseInt(annoEmeseInizio[1]), Integer.parseInt(giornoEoraInizio[0]),Integer.parseInt(annoEmeseFine[0]),Integer.parseInt(annoEmeseFine[1]),Integer.parseInt(giornoEoraFine[0]));
                                win.mostraPrezzo(nuova,prezzoFinale);
                                win.mostraAccessori();
                            }
                            else{
                                System.out.println("BottonBarListener Error PULSANTE_SALVA_PRENOTAZIONE");
                            }

                        }
                    }
                    else{ //todo uguale all'else precedente
                        nuova = salvaPrenotazione();
                        float prezzoFinale;
                        if(nuova!=null){
                            prezzoFinale=PrenotazioneBusiness.getInstance().calcolaPrezzo(nuova, Integer.parseInt(annoEmeseInizio[0]),Integer.parseInt(annoEmeseInizio[1]), Integer.parseInt(giornoEoraInizio[0]),Integer.parseInt(annoEmeseFine[0]),Integer.parseInt(annoEmeseFine[1]),Integer.parseInt(giornoEoraFine[0]));
                            win.mostraPrezzo(nuova,prezzoFinale);
                            win.mostraAccessori();
                        }
                        else{
                            System.out.println("BottonBarListener Error PULSANTE_SALVA_PRENOTAZIONE");
                        }
                    }
                }
                else{ //todo uguale all'else precedente
                    nuova = salvaPrenotazione();
                    float prezzoFinale;
                    if(nuova!=null){
                        prezzoFinale=PrenotazioneBusiness.getInstance().calcolaPrezzo(nuova, Integer.parseInt(annoEmeseInizio[0]),Integer.parseInt(annoEmeseInizio[1]), Integer.parseInt(giornoEoraInizio[0]),Integer.parseInt(annoEmeseFine[0]),Integer.parseInt(annoEmeseFine[1]),Integer.parseInt(giornoEoraFine[0]));
                        win.mostraPrezzo(nuova,prezzoFinale);
                        win.mostraAccessori();
                    }
                    else{
                        System.out.println("BottonBarListener Error PULSANTE_SALVA_PRENOTAZIONE");
                    }
                }
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
            win.modello.removeAllItems();
            //win.modello.setSelectedIndex(-1);
            //win.foto.removeAll();
            win.state=1;
            win.mostraSelezionaMotorizzazione();
        }
        else if(PULSANTE_AVANTI3.equals(command)){
            //win.modello.setSelectedIndex(-1);
            win.mostraPannelloNuovaPrenotazione();
        }
        else if(PULSANTE_PAGA.equals(command)){
            PrenotazioneBusiness.getInstance().inviaPrenotazione(nuova,0);
            win.dispose();
            FinestraCliente win = new FinestraCliente();
            win.setVisible(true);
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
        //todo è stato rimosso: PrenotazioneBusiness.getInstance().inviaPrenotazione(p,0);
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
