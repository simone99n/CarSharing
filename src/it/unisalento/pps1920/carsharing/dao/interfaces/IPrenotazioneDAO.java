package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Prenotazione;

import java.util.ArrayList;

public interface IPrenotazioneDAO extends IBaseDAO<Prenotazione> {

    //creare altri metodi diversi da findById e findAll
    //che possono essere utili per la traccia, es.
    //dammi tutte le prenotazioni di un cliente

    public void salvaPrenotazione(Prenotazione p);
    public ArrayList<Prenotazione> findForData(String data);
    public ArrayList<Prenotazione> findForModel(String mod);
    public ArrayList<Prenotazione> findForStation(String nome);
    public ArrayList<Prenotazione> findForBrand(String nome);
    public void setStatoAutomezzo(int idprenotazione);
    public ArrayList<String[]> findForIdAddettoAndIdStation(int idStazione,int idAddetto);
}
