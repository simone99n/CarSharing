package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Addetto;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;

import java.util.ArrayList;

public interface IAddettoDAO extends IBaseDAO<Addetto>
{
    public ArrayList<String[]> findByStation(Stazione staz);
    public Modello findModelByIdPrenotazione(int id);
}
