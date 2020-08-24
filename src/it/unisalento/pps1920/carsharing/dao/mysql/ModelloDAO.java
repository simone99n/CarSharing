package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IModelloDAO;
import it.unisalento.pps1920.carsharing.model.Modello;

import java.util.ArrayList;

public class ModelloDAO implements IModelloDAO {
    @Override
    public Modello findById(int id) {
        Modello m = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM modello WHERE idmodello = "+id+";");

        if(res.size() == 1) {
            m= new Modello();
            String[] riga = res.get(0);

            m.setId(Integer.parseInt(riga[0]));
            m.setNome(riga[1]);
            m.setNumPosti(Integer.parseInt(riga[2]));

            byte[] foto = DbConnection.getInstance().getFoto("SELECT foto FROM modello WHERE idmodello="+id+";");
            m.setFoto(foto);
        }

        return m;
    }

    @Override
    public ArrayList<Modello> findAll() {
        ArrayList<Modello> lista = new ArrayList<Modello>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM modello");
        for(String[] riga : res)
            lista.add(findById(Integer.parseInt(riga[0])));
        return lista;
    }
}
