package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IModelloDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.MezzoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.ModelloDAO;
import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.Modello;

import java.awt.*;
import java.util.ArrayList;

public class HomePageBusiness {

    private static HomePageBusiness instance;

    public static synchronized HomePageBusiness getInstance() {
        if(instance == null)
            instance = new HomePageBusiness();
        return instance;
    }

    private HomePageBusiness(){}

    public ArrayList<Modello> getModelliMenoCostosi(){
        IModelloDAO iModello = new ModelloDAO();
        return iModello.getModelliMenoCostosi();
    }
}
