package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.Modello;

public interface IMezzoDAO extends IBaseDAO<Mezzo> {

    public Mezzo findOneByModello(Modello mod);
}
