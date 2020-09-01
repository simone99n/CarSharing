package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAccessorioDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IPrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IStazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AccessorioDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.StazioneDAO;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;
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
        IPrenotazioneDAO p = new PrenotazioneDAO();
        if(p.eliminaPrenotazione(idPrenotazione)==0){ //no sharing, cancellazione normale
            Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
            MailHelper.getInstance().send(clienteLoggato.getEmail(), "Prenotazione cancellata", "Hai cancellato la prenotazione n° " + idPrenotazione);
            System.out.println("E-mail cancellazione prenotazione inviata");
        }
        else if(p.eliminaPrenotazione(idPrenotazione)==1){ //sharing coinvolto, semi annullamento prenotazione
            Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
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
        sp.modificaStazione(newIdPartenza,newIdArrivo, idPrenotazione);
    }
}
