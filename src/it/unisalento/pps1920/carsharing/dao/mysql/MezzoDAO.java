package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IModelloDAO;
import it.unisalento.pps1920.carsharing.model.Mezzo;
import it.unisalento.pps1920.carsharing.model.Modello;

import java.util.ArrayList;

public class MezzoDAO implements IMezzoDAO {
    @Override
    public Mezzo findById(int id) {
        Mezzo m = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzo WHERE idmezzo = "+id+";");

        if(res.size()==1) {
            m=new Mezzo();
            String riga[] = res.get(0);

            m.setId(Integer.parseInt(riga[0]));
            m.setTarga(riga[1]);

            IModelloDAO mDao = new ModelloDAO();
            Modello modello = mDao.findById(Integer.parseInt(riga[2]));
            m.setModello(modello);

        }

        return m;
    }

    @Override
    public ArrayList<Mezzo> findAll() {
        return null;
    }
}
