package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;

import java.util.ArrayList;

public class ClienteDAO implements IClienteDAO {
    @Override
    public Cliente findById(int id) {
        Cliente c = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT C.utente_idutente, U.username, U.password, U.email FROM cliente AS C INNER JOIN utente as U  ON U.idutente = C.utente_idutente WHERE C.utente_idutente = "+id+";");

        if(res.size()==1) {
            String[] riga = res.get(0);
            c = new Cliente();
            c.setId(Integer.parseInt(riga[0]));
            c.setUsername(riga[1]);
            c.setEmail(riga[3]);
        }

        return c;
    }

    @Override
    public ArrayList<Cliente> findAll() {
        return null;
    }
}
