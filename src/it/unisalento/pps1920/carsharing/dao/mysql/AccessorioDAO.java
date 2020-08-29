package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAccessorioDAO;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.Addetto;
import it.unisalento.pps1920.carsharing.model.Localita;

import java.util.ArrayList;

public class AccessorioDAO implements IAccessorioDAO {

    public Accessorio findById(int id) {
        Accessorio a = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT  nomeaccessorio WHERE idaccessorio = "+id+";");

        if(res.size()==1) {
            String[] riga = res.get(0);
            a = new Accessorio();
            a.setId(id);
            a.setNome(riga[0]);
        }

        return a;
    }


    public ArrayList<Accessorio> findAll() {

        // PRIMO COMPITO DEL DAO: ESEGUIRE LA QUERY
        ArrayList<String[]> risultato = DbConnection.getInstance().eseguiQuery("SELECT * FROM accessorio;");


        // SECONDO COMPITO DEL DAO: ISTANZIARE IL NOSTRO MODEL CON I RISULTATI DELLA QUERY
        ArrayList<Accessorio> accs = new ArrayList<Accessorio>();

        for(String[] riga : risultato) {
            Accessorio a = new Accessorio();
            a.setId(Integer.parseInt(riga[0]));
            a.setNome(riga[1]);
            accs.add(a);
        }

        return accs;

    }
}
