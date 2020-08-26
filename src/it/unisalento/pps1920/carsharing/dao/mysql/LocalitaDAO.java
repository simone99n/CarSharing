package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.ILocalitaDAO;
import it.unisalento.pps1920.carsharing.model.Localita;

import java.util.ArrayList;

public class LocalitaDAO implements ILocalitaDAO {

    public Localita findById(int id)
    {
        Localita loc;
        ArrayList<String[]> risultato = DbConnection.getInstance().eseguiQuery("SELECT * FROM localita WHERE idlocalita="+id+";");
        if(risultato.size()==1) {
            loc = new Localita();
            String[] riga = risultato.get(0);

            loc.setId(Integer.parseInt(riga[0]));
            loc.setCitta(riga[1]);
            loc.setLatitudine(Double.parseDouble(riga[2]));
            loc.setLongitudine(Double.parseDouble(riga[3]));

        }
        else {
            loc = null; //LOCALITA NON TROVATA
        }

        return loc;
    }

    public ArrayList<Localita> findAll() {

        // PRIMO COMPITO DEL DAO: ESEGUIRE LA QUERY
        ArrayList<String[]> risultato = DbConnection.getInstance().eseguiQuery("SELECT * FROM localita;");


        // SECONDO COMPITO DEL DAO: ISTANZIARE IL NOSTRO MODEL CON I RISULTATI DELLA QUERY
        ArrayList<Localita> locs = new ArrayList<Localita>();

        for(String[] riga : risultato) {
            Localita l = new Localita();
            l.setId(Integer.parseInt(riga[0]));
            l.setCitta(riga[1]);
            l.setLatitudine(Double.parseDouble(riga[2]));
            l.setLongitudine(Double.parseDouble(riga[3]));
            locs.add(l);
        }

        return locs;

    }
}
