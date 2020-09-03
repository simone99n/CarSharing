package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.ControlloPrenotazioniAdminBusiness;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.FinestraAmministratore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BottonAdminListener implements ActionListener
{

    private FinestraAmministratore win;
    public static final String PULSANTE_DATA = "PULSANTE_DATA";
    public static final String PULSANTE_SEGNALAZIONI = "PULSANTE_SEGNALAZIONI";
    public static final String PULSANTE_STAZIONE = "PULSANTE_STAZIONE";
    public static final String PULSANTE_MARCA = "PULSANTE_MARCA";
    public static final String PULSANTE_MODELLO = "PULSANTE_MODELLO";
    public static final String PULSANTE_CERCA = "PULSANTE_CERCA";
    public static final String PULSANTE_MENU = "PULSANTE_MENU";
    public static final String PULSANTE_CERCA_STAZ = "PULSANTE_CERCA_STAZ";
    public static final String PULSANTE_CERCA_MODELLO = "PULSANTE_CERCA_MODELLO";
    public static final String PULSANTE_CERCA_MARCA = "PULSANTE_CERCA_MARCA";
    public static final String PULSANTE_STAMPA_PDF = "PULSANTE_STAMPA_PDF";
    public static final String PULSANTE_STAMPA_PDF_DATA = "PULSANTE_STAMPA_PDF_DATA";
    public static final String PULSANTE_STAMPA_PDF_STAZIONE = "PULSANTE_STAMPA_PDF_STAZIONE";
    public static final String PULSANTE_STAMPA_PDF_MODELLO = "PULSANTE_STAMPA_PDF_MODELLO";
    public static final String PULSANTE_STAMPA_PDF_MARCA = "PULSANTE_STAMPA_PDF_MARCA";
    public static final String PULSANTE_SEGNALAZIONE_ADDETTO = "PULSANTE_SEGNALAZIONE_ADDETTO";
    public static final String PULSANTE_SEGNALAZIONE_OPERATORE = "PULSANTE_SEGNALAZIONE_OPERATORE";
    public static final String PULSANTE_INVIA_SEGNALAZIONE = "PULSANTE_INVIA_SEGNALAZIONE";

    public BottonAdminListener(FinestraAmministratore win) {
        this.win=win;
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
       // JButton button = (JButton)e.getSource();
        String command = e.getActionCommand();
        if(PULSANTE_DATA.equals(command))
            win.mostraPannelloPrenotazioniPerData();

        if(PULSANTE_CERCA.equals(command)) {
            System.out.println("entro action listner bottone");
            win.showPrenotazioniPerData(win.jt.getText());
        }
        if(PULSANTE_MENU.equals(command)) {
            win.partenza.removeAll();
            win.menu();
        }
        if(PULSANTE_STAZIONE.equals(command)) {
            win.mostraPannelloPrenotazioniPerStazione();
        }
        if(PULSANTE_CERCA_STAZ.equals(command)) {
            Stazione staz = new Stazione();
            staz = (Stazione) win.partenza.getSelectedItem();
            win.showPannelloPrenotazioniPerStazione(staz);
        }

        if(PULSANTE_MODELLO.equals(command)) {
            win.mostraPannelloPrenotazioniPerModello();
        }
        if(PULSANTE_CERCA_MODELLO.equals(command)) {
            String mod;
            mod = (String) win.modello.getSelectedItem();
            win.showPannelloPrenotazioniPerModello(mod);
        }
        if(PULSANTE_MARCA.equals(command)) {
            win.mostraPannelloPrenotazionePerMarca();
        }
        if(PULSANTE_CERCA_MARCA.equals(command)) {
            Modello mod= new Modello();
            mod=(Modello) win.marca.getSelectedItem();
            if(mod!=null)
                win.showPannelloPrenotazionePerMarca(mod);
        }
        if(PULSANTE_STAMPA_PDF.equals(command)) {
            win.stampaPdf();
        }
        if(PULSANTE_STAMPA_PDF_DATA.equals(command)) {
            String data;
            data=win.jt.getText();
            win.stampaPdfDataFiltering(data);
        }
        if(PULSANTE_STAMPA_PDF_STAZIONE.equals(command)) {
            Stazione staz = new Stazione();
            staz = (Stazione) win.partenza.getSelectedItem();
            win.stampaPdfStationFiltering(staz);
        }

        if(PULSANTE_STAMPA_PDF_MODELLO.equals(command)) {
            String mod;
            mod = (String) win.modello.getSelectedItem();
            win.stampaPdfModelFiltering(mod);
        }
        if(PULSANTE_STAMPA_PDF_MARCA.equals(command)) {
            Modello mod= new Modello();
            mod=(Modello) win.marca.getSelectedItem();
            win.stampaPdfBrandFiltering(mod);
        }
        if(PULSANTE_SEGNALAZIONI.equals(command)){
            win.addettoComboBox.removeAllItems();
            win.operatoreComboBox.removeAllItems();
            win.mostraPannelloSegnalazioni();
        }
        if(PULSANTE_SEGNALAZIONE_ADDETTO.equals(command)){
            win.inserisciTestoSegnalazioneAddetto();
        }
        if(PULSANTE_SEGNALAZIONE_OPERATORE.equals(command)){
           win.inserisciTestoSegnalazioneOperatore();
        }
        if(PULSANTE_INVIA_SEGNALAZIONE.equals(command)){
            System.out.println("Pulsante invia prenotazione premutp");
            Amministratore amministratoreLoggato = (Amministratore) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);

            if(win.operatoreComboBox.getSelectedItem()==null){
                System.out.println("entro win.operatoreComboBox==null");
                Stazione stazione = (Stazione) win.addettoComboBox.getSelectedItem();
                ControlloPrenotazioniAdminBusiness.getInstance().inviaSegnalazione(amministratoreLoggato.getId(), stazione.getAddetto().getId(), win.messJTA.getText()); //(idSorgente,idDestinatario)
                win.partenza.removeAll();
                win.menu();
            }
            else if(win.addettoComboBox.getSelectedItem()==null){
                System.out.println("win.addettoComboBox==null");
                Stazione stazione = (Stazione) win.operatoreComboBox.getSelectedItem();
                ControlloPrenotazioniAdminBusiness.getInstance().inviaSegnalazione(amministratoreLoggato.getId(), stazione.getOperatore().getId_operatore(), win.messJTA.getText()); //(idSorgente,idDestinatario)
                win.partenza.removeAll();
                win.menu();
            }

        }

    }
}