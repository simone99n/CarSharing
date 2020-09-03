package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.dao.mysql.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.MailHelper;
import it.unisalento.pps1920.carsharing.util.PdfHelper;
import it.unisalento.pps1920.carsharing.view.FinestraSharing;
import it.unisalento.pps1920.carsharing.view.Listener.SharingListener;

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

    public boolean inviaPrenotazione(Prenotazione p,int state, float prezzo) {
        // logica di business
        // 1. chiamare il dao prenotazione per salvare la prenotazione
        ArrayList<String> arrayInfo = new PrenotazioneDAO().sharingCheck(p);//controlla se è possibile fare uno sharing

        if(arrayInfo.get(0).equals("true") && state==0) {//SE è possibile fare lo sharing

            FinestraSharing.idPrenotazione = Integer.parseInt(arrayInfo.get(2));
            FinestraSharing confermare = new FinestraSharing(prezzo);
            confermare.setVisible(true);
            SharingListener.p=p;

        }

        if(arrayInfo.get(0).equals("true") && state==1){                                                                            //SE è possibile fare lo sharing

            ArrayList<String[]> emails = new PrenotazioneDAO().salvaPrenotazioneSharing(arrayInfo);                       //sharing effettuato
            if(emails.isEmpty()){
                return false;
            }

            for (String[] email : emails) {
                MailHelper.getInstance().send(email[0], "Prenotazione con Sharing confermata!", "In data: " + p.getData()); // 3. inviare mail di conferma all'utente
                System.out.println("E-mail inviata a cliente coinvolto nello sharing: "+email[0]);
            }
            // 2. inviare mail all'addetto del parco automezzi
            String dest1 = p.getArrivo().getAddetto().getEmail();
            MailHelper.getInstance().send(dest1, "Nuova prenotazione", "In data: "+p.getData());
            System.out.println("E-mail all'addetto parco automezzi");

            ArrayList<String> testo = new ArrayList<String>();
            testo.add("Codice prenotazione con Sharing: "+arrayInfo.get(2));
            testo.add("Veicolo: "+p.getMezzo().getModello().getNome());
            testo.add("TARGA: "+p.getMezzo().getTarga());
            testo.add("Data inizio "+p.getDataInizio());
            testo.add("Data fine "+p.getDataFine());
            testo.add("Date e ora prenotazione: "+p.getData());
            testo.add("Stampa questo file e presentati in stazione");
            PdfHelper.getInstance().creaPdf(testo);                                 // 4. generare pdf per l'utente
            return true;
        }
        else if(arrayInfo.get(0).equals("false") || state==2){

            if(new PrenotazioneDAO().salvaPrenotazione(p)){                         //salva prenotazione senza sharing
                ArrayList<String> testo = new ArrayList<String>();
                testo.add("Codice prenotazione: "+p.getId());
                testo.add("Veicolo: "+p.getMezzo().getModello().getNome());
                testo.add("TARGA: "+p.getMezzo().getTarga());
                testo.add("Data inizio "+p.getDataInizio());
                testo.add("Data fine "+p.getDataFine());
                testo.add("Date e ora prenotazione: "+p.getData());
                testo.add("Stampa questo file e presentati in stazione");
                PdfHelper.getInstance().creaPdf(testo);                                 // 4. generare pdf per l'utente

                String dest2 = p.getCliente().getEmail();
                MailHelper.getInstance().send(dest2, "Prenotazione confermata!", "In data: "+p.getData()); // 3. inviare mail di conferma all'utente
                // 2. inviare mail all'addetto del parco automezzi
                String dest1 = p.getArrivo().getAddetto().getEmail();
                MailHelper.getInstance().send(dest1, "Nuova prenotazione", "In data: "+p.getData()); //email all'addetto
                return true;
            }

        }
        return false;
    }

    public void inserisciAccessori(Accessorio access){
        IPrenotazioneDAO ins = new PrenotazioneDAO();
        ins.inserisciAccessori(access);
    }

    public ArrayList<String[]> getInfoClienteFromIdPrenotazione(int idPrenotazione, int index) {
        IPrenotazioneDAO temp = new PrenotazioneDAO();
        return temp.getClienteInfo(idPrenotazione,index);
    }

    public int getNumClienti(int idPrenotazione) {
        IPrenotazioneDAO temp = new PrenotazioneDAO();
        return temp.getNumClienti(idPrenotazione);
    }

    public ArrayList<Modello> getModelliFromTipologia(String tipologia, String grandezza, String motorizzazione){
        IModelloDAO temp = new ModelloDAO();
        return temp.findByTipologia(tipologia,grandezza,motorizzazione);
    }

    public boolean disponibilitaMezzo(Modello mod) {
        IMezzoDAO mezzotemp= new MezzoDAO();
        return mezzotemp.findOneByModello(mod) != null; //restituisce TRUE se Mezzo è disponibile, FALSE altrimenti
    }

    public Mezzo returnOneMezzo(Modello mod) {
        IMezzoDAO mezzotemp= new MezzoDAO();
        return mezzotemp.findOneByModello(mod);
    }

    public float calcolaPrezzo(Prenotazione nuova, int annoInizio, int meseInizio, int giornoInizio, int annoFine, int meseFine, int giornoFine) {
        float prezzo = nuova.getMezzo().getPrezzo();

        int numAnni=annoFine-annoInizio; //numero di anni

        if(annoFine==annoInizio){                                       //se avviene tutto nello stesso anno
            if(meseFine==meseInizio){                                           //se avviene anche nello stesso mese
                int numGiorni=giornoFine-giornoInizio;
                return prezzo*numGiorni;
            }
            else {                                                              //se avviene in mesi diversi
                int numMesi=meseFine-meseInizio;
                if(giornoFine>giornoInizio){                                            //se giornoFine>giornoInizio
                    int numGiorni=giornoFine-giornoInizio;
                    return (numMesi*30*prezzo)+(numGiorni*prezzo);
                }
                else if(giornoFine<giornoInizio){                                       //se giornoFine<giornoInizio
                    int numGiorni=giornoInizio-giornoFine;
                    return (numMesi*30*prezzo)-(numGiorni*prezzo);
                }
                else {                                                                  //se giornoFine=giornoInizio
                    return (numMesi*30*prezzo);
                }
            }
        }
        else{                                                             //se avviene in anni diversi, C'E' LO SCONTOOOOOOOOOOOOOOOO
            if(meseFine==meseInizio){
                return prezzo*365*(annoFine-annoInizio);
            }
            else if(meseFine>meseInizio){
                int numMesi=meseFine-meseInizio;
                return prezzo*365*(annoFine-annoInizio) + prezzo*30*numMesi;
            }
            else {
                int numMesi=meseInizio-meseFine;
                return prezzo*365*(annoFine-annoInizio) - prezzo*30*numMesi;
            }
        }

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

    public ArrayList<Accessorio> getAccessori(){
        IAccessorioDAO mDao = new AccessorioDAO();
        return mDao.findAll();
    }

    public void automezzoPronto(int idprenotazione)
    {
        IPrenotazioneDAO pdao= new PrenotazioneDAO();
        pdao.setStatoAutomezzo(idprenotazione);
    }

    public ArrayList<Mezzo> getMezzi(){
        IMezzoDAO mDAo = new MezzoDAO();
        return mDAo.findAll();
    }
    public ArrayList<Prenotazione> cercaMatch(Date inizio, Date fine, Stazione partenza, Stazione arrivo, Localita localita, int numPosti) {

        return null;
    }
   /*
    public ArrayList<String[]> getNomeFromIdPrenotazione(int idPrenotazione){
       IPrenotazioneDAO temp = new PrenotazioneDAO();
        return temp.getClienteNome(idPrenotazione);
    }

    public ArrayList<String[]> getCognomeFromIdPrenotazione(int idPrenotazione){
        IPrenotazioneDAO temp = new PrenotazioneDAO();
        return temp.getClienteCognome(idPrenotazione);
    }

    */
}
