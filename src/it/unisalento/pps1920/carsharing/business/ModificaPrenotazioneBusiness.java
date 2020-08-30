package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.IAccessorioDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IPrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AccessorioDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.Prenotazione;

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

    public ArrayList<Accessorio> getAccessori(int idPrenotazione){
        IAccessorioDAO a = new AccessorioDAO();
        return a.findByPrenotazione(idPrenotazione);
    }
}
