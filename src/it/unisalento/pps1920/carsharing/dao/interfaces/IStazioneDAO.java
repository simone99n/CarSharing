package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Stazione;

public interface IStazioneDAO extends IBaseDAO<Stazione> {
    public String getPartenza(int idPrenotazione);
    public String getArrivo(int idPrenotazione);
    public int modificaStazione(int newIdPartenza, int newIdArrivo, int idPrenotazione);
    public Stazione findStationByOperatorId(int id);
    public Stazione findStationByAddettoId(int id);
    public String nomeStazioneFromOperatore(int idOperatore);
}
