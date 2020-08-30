package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Stazione;

public interface IStazioneDAO extends IBaseDAO<Stazione> {
    public Stazione findStationByOperatorId(int id);
}
