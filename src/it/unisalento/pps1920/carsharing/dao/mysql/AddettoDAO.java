package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAddettoDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.util.ArrayList;

public class AddettoDAO implements IAddettoDAO {
    @Override
    public Addetto findById(int id) {
        Addetto a = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT A.utente_idutente, U.username, U.password, U.email FROM addetto AS A INNER JOIN utente as U  ON U.idutente = A.utente_idutente WHERE A.utente_idutente = "+id+";");

        if(res.size()==1) {
            String[] riga = res.get(0);
            a = new Addetto();
            a.setId(Integer.parseInt(riga[0]));
            a.setUsername(riga[1]);
            a.setEmail(riga[3]);
        }

        return a;
    }



    @Override
    public ArrayList<Addetto> findAll() {
        return null;
    }


    public Modello findModelByIdPrenotazione(int id)

    {
        Modello mod=new Modello();
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT modello.tipologia,modello.nome FROM modello INNER JOIN mezzo ON modello.idmodello=mezzo.modello_idmodello INNER JOIN prenotazione ON prenotazione.mezzo_idmezzo=mezzo.idmezzo WHERE prenotazione.idprenotazione="+id+" ;");
        if(!res.isEmpty())
        {
            String[] riga= res.get(0);
            mod.setNome(riga[1]);
            mod.setTipologia(riga[0]);
            return mod;
        }
        mod.setId(0);
       return mod;
    }

    public ArrayList<Prenotazione> findByStation(Stazione staz)
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
