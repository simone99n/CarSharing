package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.util.ArrayList;

public class MessaggioDAO implements IMessaggioDAO {

    @Override
    public Messaggio findById(int id) {
        Messaggio m = null;
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM messaggio WHERE idsegnalazione = "+id+";");

        if(res.size()==1) {

            IMessaggioDAO mDao = new MessaggioDAO();

            Utente utente1;
            Utente utente2;

            String[] riga = res.get(0);

            m = new Messaggio();

            m.setIdSegnalazione(Integer.parseInt(riga[0]));
            m.setTestoMessaggio(riga[1]);
            m.setLetto(Integer.parseInt(riga[2]));

/*
            Mezzo mezzo = mDao.findById(Integer.parseInt(riga[2]));
            p.setMezzo(mezzo);
 */
        }

        return m;

    }

    @Override
    public ArrayList<Messaggio> findAll() {
        return null;
    }

    public void inserisciMessaggio(Utente sorgente, Utente destinatario, String testo){

    //    String sql1 = "INSERT INTO messaggio (NULL, messaggio, sorgente, destinatario) VALUES ('"+testo+"', '"+idSorgente+"', '"+idDestinatario+"');";

    }
}
