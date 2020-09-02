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
            m.setPrezzo(Float.parseFloat(riga[4]));
        }

        return m;
    }

    public Mezzo findOneByModello(Modello mod) {
        //si vuole selezionare un mezzo targato dato un selezionato modello
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM mezzo WHERE modello_idmodello = (SELECT idmodello FROM modello WHERE nome = '"+mod.getNome()+"');");
        Mezzo mezzoFinded = new Mezzo();
        if(res.size()>=1) {

            String[] riga = res.get(0); //prende la row restituita dalla query SQL

            mezzoFinded.setId(Integer.parseInt(riga[0]));
            mezzoFinded.setTarga(riga[1]);
            mezzoFinded.setModello(mod);
            mezzoFinded.setAlimentazione(riga[3]);
            mezzoFinded.setPrezzo(Float.parseFloat(riga[4]));
           /* IModelloDAO mDao = new ModelloDAO();
            Modello modello = mDao.findById(Integer.parseInt(riga[2]));
            m.setModello(modello);
            */
            return mezzoFinded;
        }

        return null;
    }

    @Override
    public ArrayList<Mezzo> findAll() {
        return null;
    }
}
