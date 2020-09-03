package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAmministratoreDAO;
import it.unisalento.pps1920.carsharing.model.Amministratore;
import it.unisalento.pps1920.carsharing.model.Cliente;

import java.util.ArrayList;

public class AmministratoreDAO implements IAmministratoreDAO {
    private int id;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String email;


    @Override
    public Amministratore findById(int id) {
        Amministratore a= null;
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT A.utente_idutente, A.nome, A.cognome, U.username, U.password, U.email FROM amministratore AS A INNER JOIN utente as U  ON U.idutente = A.utente_idutente WHERE A.utente_idutente = "+id+";");
        if(res.size()==1) {
            String[] riga = res.get(0);

            a = new Amministratore();

            a.setId(Integer.parseInt(riga[0]));
            a.setNome(riga[1]);
            a.setCognome(riga[2]);
            a.setUsername(riga[3]);
            a.setPassword(riga[4]);
            a.setEmail(riga[5]);

        }
        return a;
    }

    @Override
    public ArrayList<Amministratore> findAll() {
        return null;
    }
}
