package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Modello;

import java.util.ArrayList;

public interface IModelloDAO extends IBaseDAO<Modello> {
    public ArrayList<Modello> findByTipologia(String tipologia, String grandezza, String motorizzazione);
}
