package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.mysql.AccessorioDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AddettoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.OperatoreDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.StazioneDAO;
import it.unisalento.pps1920.carsharing.model.*;

import java.util.ArrayList;

public class ControlloAutomezziAddettoBusiness
{
    public ArrayList<Prenotazione> checkPrenotazioni(int id)
    {
        AddettoDAO adao = new AddettoDAO();
        StazioneDAO sdao = new StazioneDAO();
        Stazione s = new Stazione();
        ArrayList<Prenotazione>pre = new ArrayList<Prenotazione>();
        s=sdao.findStationByAddettoId(id);
        pre= adao.findByStation(s);
        if(pre==null)
        {
            System.out.println("Mi spiace ma al momento non sono presenti delle prenotazioni.");
            return null;
        }
        return pre;
    }

    public Modello getModelFromIdPrenotazione(int id)
    {
        AddettoDAO adao= new AddettoDAO();
        Modello mod = new Modello();
        mod=adao.findModelByIdPrenotazione(id);
        return mod;
    }

    public ArrayList<Accessorio> getAccessoriFromIdPrenotazione(int id)
    {
        AccessorioDAO accdao= new AccessorioDAO();
        ArrayList<Accessorio> a= new ArrayList<>();
        a=accdao.findByPrenotazione(id);
        return a;
    }
}
