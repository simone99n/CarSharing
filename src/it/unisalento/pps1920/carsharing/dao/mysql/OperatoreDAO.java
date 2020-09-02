package it.unisalento.pps1920.carsharing.dao.mysql;
import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IOperatoreDAO;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.util.ArrayList;

public class OperatoreDAO implements IOperatoreDAO {

    @Override
    public Operatore findById(int id)
    {
        return null;
    }


    @Override
    public ArrayList<Operatore> findAll() {
        return null;
    }

    @Override
    public ArrayList<Prenotazione> findByStation(Stazione staz) // Funzione per il controllo prenotazioni e pagamenti da parte dell'operatore
    {
        ArrayList<String []>res= DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE idstazione_partenza="+staz.getId()+";");
        if(res.isEmpty())
        {
            return null;
        }
        ArrayList<Prenotazione>pre= new ArrayList<Prenotazione>();
        Prenotazione p;
        for(String[] riga : res)
        {
            p=new Prenotazione();
            p.setId(Integer.parseInt(riga[0]));
            p.setData(DateUtil.dateTimeFromString(riga[1]));
            p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
            p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
            pre.add(p);
        }
        return pre;
    }
}