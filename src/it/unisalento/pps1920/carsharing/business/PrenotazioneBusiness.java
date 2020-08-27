package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.dao.mysql.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.MailHelper;
import it.unisalento.pps1920.carsharing.util.PdfHelper;

import java.util.ArrayList;
import java.util.Date;

public class PrenotazioneBusiness {

    private static PrenotazioneBusiness instance;

    public static synchronized PrenotazioneBusiness getInstance() {
        if(instance == null)
            instance = new PrenotazioneBusiness();
        return instance;
    }

    private PrenotazioneBusiness(){}

    public void inviaPrenotazione(Prenotazione p) {
        // logica di business
        // 1. chiamare il dao prenotazione per salvare la prenotazione

        ArrayList<String> arrayInfo = new PrenotazioneDAO().sharingCheckDAO(p);     //controlla se è possibile fare uno sharing
        if(arrayInfo.get(0).equals("true")){                                        //SE è possibile fare lo sharing
                                                                                    //todo -> ALLORA proporre sharing a utente
            new PrenotazioneDAO().salvaPrenotazioneSharing(arrayInfo);                            //sharing effettuato
            ArrayList<String> testo = new ArrayList<String>();
            testo.add("Codice prenotazione: "+arrayInfo.get(2));
            testo.add("Veicolo: "+p.getMezzo().getModello().getNome());
            testo.add("TARGA: "+p.getMezzo().getTarga());
            testo.add("Periodo prenotazione: dal "+p.getDataInizio()+" al "+p.getDataFine());
            testo.add("Date e ora prenotazione: "+p.getData());
            testo.add("Stampa questo file e presentati in stazione");
            PdfHelper.getInstance().creaPdf(testo);                                 // 4. generare pdf per l'utente
        }
        else{

            new PrenotazioneDAO().salvaPrenotazione(p);
            ArrayList<String> testo = new ArrayList<String>();
            testo.add("Codice prenotazione: "+p.getId());
            testo.add("Veicolo: "+p.getMezzo().getModello().getNome());
            testo.add("TARGA: "+p.getMezzo().getTarga());
            testo.add("Periodo prenotazione: dal "+p.getDataInizio()+" al "+p.getDataFine());
            testo.add("Date e ora prenotazione: "+p.getData());
            testo.add("Stampa questo file e presentati in stazione");
            PdfHelper.getInstance().creaPdf(testo);                                 // 4. generare pdf per l'utente
        }

        // 2. inviare mail all'addetto del parco automezzi
        String dest1 = p.getArrivo().getAddetto().getEmail();
        MailHelper.getInstance().send(dest1, "Nuova prenotazione", "In data: "+p.getData());

        // 3. inviare mail di conferma all'utente
        String dest2 = p.getCliente().getEmail();
        MailHelper.getInstance().send(dest2, "Prenotazione confermata!", "In data: "+p.getData());

    }


    public void inviaSharing(Prenotazione p) {

    }


    public boolean modificaPrenotazione(Prenotazione p) {
        // logica di business
        return true;
    }

    public boolean disponibilitaMezzo(Modello mod) {
        IMezzoDAO mezzotemp= new MezzoDAO();
        return mezzotemp.findOneByModello(mod) != null; //restituisce TRUE se Mezzo è disponibile, FALSE altrimenti
    }

    public Mezzo returnOneMezzo(Modello mod) {
        IMezzoDAO mezzotemp= new MezzoDAO();
        return mezzotemp.findOneByModello(mod);
    }

    public ArrayList<Prenotazione> cercaMatch(Date inizio,
                                              Date fine,
                                              Stazione partenza,
                                              Stazione arrivo,
                                              Localita localita,
                                              int numPosti) {

        return null;
    }

    public ArrayList<Stazione> getStazioni(){
        IStazioneDAO sDao = new StazioneDAO();
        return sDao.findAll();
    }

    public ArrayList<Localita> getLocalita(){
        ILocalitaDAO lDao = new LocalitaDAO();
        return lDao.findAll();
    }

    public ArrayList<Modello> getModelli(){
        IModelloDAO mDao = new ModelloDAO();
        return mDao.findAll();
    }

    public ArrayList<Mezzo> getMezzi(){
        IMezzoDAO mDAo = new MezzoDAO();
        return mDAo.findAll();
    }
}
