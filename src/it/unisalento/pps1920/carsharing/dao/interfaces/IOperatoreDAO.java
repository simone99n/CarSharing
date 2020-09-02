package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;

import java.util.ArrayList;

public interface IOperatoreDAO extends IBaseDAO<Operatore> {
    public ArrayList<Prenotazione> findByStation(Stazione staz);
}