package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.IAddettoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMessaggioDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AccessorioDAO;


import it.unisalento.pps1920.carsharing.dao.interfaces.IPrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.Listener.BottonErrorListener.AllErrorMessages;

import java.util.ArrayList;

public class ControlloAutomezziAddettoBusiness {

    private static ControlloAutomezziAddettoBusiness instance;

    public static synchronized ControlloAutomezziAddettoBusiness getInstance() {
        if(instance == null)
            instance = new ControlloAutomezziAddettoBusiness();
        return instance;
    }

    /*public ArrayList<String[]> mostraVeicoliPronti(int idOperatore) {
        ArrayList<String[]>pre = new ArrayList<String[]>();
        OperatoreDAO odao = new OperatoreDAO();
        StazioneDAO sdao = new StazioneDAO();
        Stazione s = new Stazione();
        IAddettoDAO iDao = new AddettoDAO();
        s=sdao.findStationByAddettoId(iDao.getIdAddettoFromIdOperatore(idOperatore));
        pre= odao.findByStation3(s);
        return pre;
    }*/

    public ArrayList<String[]> checkPrenotazioni(int id)
    {
        ArrayList<String[]>pre = new ArrayList<String[]>();
        AddettoDAO adao = new AddettoDAO();
        StazioneDAO sdao = new StazioneDAO();
        Stazione s = new Stazione();
        s=sdao.findStationByAddettoId(id);
        pre= adao.findByStation2(s);
        return pre;
    }

    public ArrayList<String[]> checkPrenotazioniFilteredByIdStation(int idAddetto,int idPrenotazione) {

        StazioneDAO sdao = new StazioneDAO();
        Stazione s = new Stazione();
        s=sdao.findStationByAddettoId(idAddetto);
        IPrenotazioneDAO pre = new PrenotazioneDAO();
        ArrayList<String[]>prenotazioni = new ArrayList<String[]>();
        prenotazioni=pre.findForIdAddettoAndIdStation(s.getId(),idPrenotazione);
        return prenotazioni;
    }

    public Modello getModelFromIdPrenotazione(int id)
    {
        AddettoDAO adao= new AddettoDAO();
        Modello mod = new Modello();
        mod=adao.findModelByIdPrenotazione(id);
        return mod;
    }

    public void inviaRiscontroAddetto(String testo, int idSegnalazione){
        IMessaggioDAO mDAO = new MessaggioDAO();
        int idAdmin = mDAO.getIdSorgenteFromIdSegnalazione(idSegnalazione);
        Addetto addettoLoggato = (Addetto) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        mDAO.inserisciMessaggio(addettoLoggato.getId(), idAdmin, testo);
        mDAO.setLetto(idSegnalazione);
    }

    public ArrayList<Accessorio> getAccessoriFromIdPrenotazione(int id)
    {
        AccessorioDAO accdao= new AccessorioDAO();
        ArrayList<Accessorio> a= new ArrayList<>();
        a=accdao.findByPrenotazione(id);
        return a;
    }
}