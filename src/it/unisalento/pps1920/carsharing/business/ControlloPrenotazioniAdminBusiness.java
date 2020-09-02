package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.IPrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.model.Prenotazione;

import java.util.ArrayList;

public class ControlloPrenotazioniAdminBusiness {

    public ArrayList<Prenotazione> checkPrenotazioniFromDate(String data) {
        ArrayList<Prenotazione> prenotazioni= new ArrayList<Prenotazione>();
        IPrenotazioneDAO pre = new PrenotazioneDAO();
        prenotazioni=pre.findForData(data);
        return prenotazioni;
    }

    public ArrayList<Prenotazione> checkPrenotazioniFromStation(String name) {
        ArrayList<Prenotazione> prenotazioni= new ArrayList<Prenotazione>();
        PrenotazioneDAO pre = new PrenotazioneDAO();
        prenotazioni=pre.findForStation(name);
        return prenotazioni;
    }

    public ArrayList<Prenotazione> checkPrenotazioniFromModel(String mod) {
        ArrayList<Prenotazione> prenotazioni= new ArrayList<Prenotazione>();
        PrenotazioneDAO pre = new PrenotazioneDAO();
        prenotazioni=pre.findForModel(mod);
        return prenotazioni;
    }

    public ArrayList<Prenotazione> checkPrenotazioniFromBrand(String mod) {
        System.out.println(mod);
        ArrayList<Prenotazione> prenotazioni= new ArrayList<Prenotazione>();
        PrenotazioneDAO pre = new PrenotazioneDAO();
        prenotazioni=pre.findForBrand(mod);
        return prenotazioni;
    }

}