package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.IMessaggioDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.MessaggioDAO;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.util.Session;

import java.util.ArrayList;

public class SegnalazioneBusiness
{
    private static SegnalazioneBusiness instance;

    public static synchronized SegnalazioneBusiness getInstance() {
        if(instance == null)
            instance = new SegnalazioneBusiness();
        return instance;
    }
    public ArrayList<String[]> getMessaggiOperatore(){
        IMessaggioDAO mDAO = new MessaggioDAO();
        return mDAO.getMessaggiOperatore();
    }

    public ArrayList<String[]> getMessaggiAddetto() {
        IMessaggioDAO mDAO = new MessaggioDAO();
        return mDAO.getMessaggiAddetto();
    }

    public ArrayList<String[]> getMessaggiAmministratore() {
        IMessaggioDAO mDAO = new MessaggioDAO();
        return mDAO.getMessaggiAmministratore();
    }

    public String getNomeFromId(int idSorgente){
        IMessaggioDAO mDAO = new MessaggioDAO();
        return mDAO.getNomeFromId(idSorgente);
    }

    public void inviaRiscontroOperatore(String testo, int idSegnalazione){
        IMessaggioDAO mDAO = new MessaggioDAO();
        int idAdmin = mDAO.getIdSorgenteFromIdSegnalazione(idSegnalazione);
        Operatore operatoreLoggato = (Operatore) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        mDAO.inserisciMessaggio(operatoreLoggato.getId_operatore(), idAdmin, testo);
        mDAO.setLetto(idSegnalazione);
    }

    public void inviaSegnalazione(int idSorgente,int idDestinatario, String testo){
        IMessaggioDAO tmp = new MessaggioDAO();
        tmp.inserisciMessaggio(idSorgente,idDestinatario,testo);
    }

}
