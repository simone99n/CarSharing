package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.dao.mysql.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.MailHelper;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.FinestraSharing;

import java.util.ArrayList;

public class ModificaPrenotazioneBusiness {

    private static ModificaPrenotazioneBusiness instance;

    public static synchronized ModificaPrenotazioneBusiness getInstance() {
        if(instance == null)
            instance = new ModificaPrenotazioneBusiness();
        return instance;
    }

    private ModificaPrenotazioneBusiness(){}

    public Prenotazione getPrenotazione(int idPrenotazione){
        IPrenotazioneDAO p = new PrenotazioneDAO();
        return p.findById(idPrenotazione);
    }

    public void cancellaPrenotazione(int idPrenotazione){
        System.out.println("Entro in cancella prenotazione");
        IPrenotazioneDAO p = new PrenotazioneDAO();

        int statoCancellazione=p.eliminaPrenotazione(idPrenotazione);
        if(statoCancellazione==0){ //no sharing, cancellazione normale
            System.out.println("!!!CANCELLAZIONE NORMALE!!!");
            Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
            MailHelper.getInstance().send(clienteLoggato.getEmail(), "Prenotazione cancellata", "Hai cancellato la prenotazione n° " + idPrenotazione);
            System.out.println("E-mail cancellazione prenotazione inviata");
        }
        else if(statoCancellazione==1){ //sharing coinvolto, semi annullamento prenotazione
            Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
            System.out.println("!!!CANCELLAZIONE SHARING!!!");
            String query1 = "SELECT cliente_utente_idutente FROM effettua WHERE prenotazione_idprenotazione="+idPrenotazione+";";
            ArrayList<String[]> clienti = DbConnection.getInstance().eseguiQuery(query1);
            ArrayList<String[]> emails = new ArrayList<>();
            for (String[] strings : clienti) {
                String emailsql = "SELECT email FROM utente WHERE idutente=" + strings[0] + ";";
                emails.add(DbConnection.getInstance().eseguiQuery(emailsql).get(0));
            }

            for (String[] email : emails) {
                if (!email[0].equals(clienteLoggato.getEmail())) { //se email diversa da email cliente
                    MailHelper.getInstance().send(email[0], "Prenotazione con Sharing cancellata", "Il cliente " + clienteLoggato.getCognome() + " " + clienteLoggato.getNome() + " non parteciperà più allo Sharing - prenotazione n° " + idPrenotazione);
                    System.out.println("E-mail invaita al cliente coinvolto nello sharing");
                }
            }
            MailHelper.getInstance().send(clienteLoggato.getEmail(), "Prenotazione cancellata", "Hai cancellato la prenotazione n° " + idPrenotazione);
            System.out.println("E-mail invaita al cliente che ha cancellato lo sharing");

        }
        System.out.println("Finiti if di cancella prenotazione");
    }

    public ArrayList<Accessorio> getAccessori(int idPrenotazione){
        IAccessorioDAO a = new AccessorioDAO();
        return a.findByPrenotazione(idPrenotazione);
    }

    public void inserisciAccessori(Accessorio access){
        IPrenotazioneDAO ins = new PrenotazioneDAO();
        ins.inserisciAccessori(access);
    }

    public void inserisciAccessoriMod(Accessorio access, int idPrenotazione){
        IPrenotazioneDAO p = new PrenotazioneDAO();
        p.inserisciAccessoriMod(access,idPrenotazione);
    }

    public void eliminaAccessorio(int idAccessorio, int idPrenotazione) {
        IPrenotazioneDAO p = new PrenotazioneDAO();
        p.eliminaAccessorio(idAccessorio, idPrenotazione);
    }

    public String getPartenza(int idPrenotazione){
        IStazioneDAO s = new StazioneDAO();
        return s.getPartenza(idPrenotazione);
    }

    public String getArrivo(int idPrenotazione){
        IStazioneDAO s = new StazioneDAO();
        return s.getArrivo(idPrenotazione);
    }

    public void modificaStazione(int newIdPartenza, int newIdArrivo, int idPrenotazione){
        IStazioneDAO sp = new StazioneDAO();
        if(sp.modificaStazione(newIdPartenza,newIdArrivo, idPrenotazione)==0){
            Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
            MailHelper.getInstance().send(clienteLoggato.getEmail(), "Prenotazione modificata", "Hai modificato la prenotazione n° " + idPrenotazione);
        }
        else if(sp.modificaStazione(newIdPartenza,newIdArrivo, idPrenotazione)==1){
            Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
            MailHelper.getInstance().send(clienteLoggato.getEmail(), "Prenotazione modificata,Sharing annullato", "Hai modificato la prenotazione, annullando lo sharing");
        }
    }

    public int getPostiOccupatiCliente(int idPrenotazione) {
        IPrenotazioneDAO pren = new PrenotazioneDAO();
        return pren.getPostiTableEffettua(idPrenotazione);
    }

    public int getIdUltimaPrenotazione(int idCliente){
        IPrenotazioneDAO pren = new PrenotazioneDAO();
        return pren.getIdUltimaPrenotazione(idCliente);
    }

    public Prenotazione findById(int idPrenotazione){
        IPrenotazioneDAO pren = new PrenotazioneDAO();
        return pren.findById(idPrenotazione);
    }

    public boolean salvaPrenotazione(Prenotazione p){
        IPrenotazioneDAO pren = new PrenotazioneDAO();
        return pren.salvaPrenotazione(p);
    }

    public float calcolaPrezzo(Prenotazione nuova, int annoInizio, int meseInizio, int giornoInizio, int annoFine, int meseFine, int giornoFine) {
        int idMezzo = nuova.getMezzo().getId();

        IMezzoDAO mezzoInterface = new MezzoDAO();
        float prezzo = mezzoInterface.findById(idMezzo).getPrezzo();

        System.out.println("idMezzo in inizio ModificaBussiness calcolaPrezzo= "+idMezzo);

        System.out.println("Prezzo in inizio ModificaBussiness calcolaPrezzo= "+prezzo);

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
}
