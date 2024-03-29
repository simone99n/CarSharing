package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Accessorio;

import java.util.ArrayList;

public interface IAccessorioDAO extends IBaseDAO<Accessorio> {
    public ArrayList<Accessorio> findAll();
    public ArrayList<Accessorio> findByPrenotazione(int id);
}
