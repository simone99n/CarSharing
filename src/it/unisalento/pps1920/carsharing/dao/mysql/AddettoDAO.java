package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAddettoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;
import it.unisalento.pps1920.carsharing.model.Addetto;
import it.unisalento.pps1920.carsharing.model.Cliente;

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
}
