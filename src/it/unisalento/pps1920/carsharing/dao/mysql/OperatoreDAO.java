package it.unisalento.pps1920.carsharing.dao.mysql;
import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.ILocalitaDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IMezzoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IOperatoreDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IStazioneDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.util.ArrayList;

public class OperatoreDAO implements IOperatoreDAO {
    private int id;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private String email;

    @Override
    public Operatore findById(int id) {
        Operatore o = null;
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT O.utente_idutente, O.nome, O.cognome, U.username, U.password, U.email FROM operatore AS O INNER JOIN utente as U  ON U.idutente = O.utente_idutente WHERE O.utente_idutente = "+id+";");
        if(res.size()==1) {
            String[] riga = res.get(0);

            o = new Operatore();

            o.setId_operatore(Integer.parseInt(riga[0]));
            o.setNome(riga[1]);
            o.setCognome(riga[2]);
            o.setUsername(riga[3]);
            o.setPassword(riga[4]);
            o.setEmail(riga[5]);

        }
        return o;
    }


    @Override
    public ArrayList<Operatore> findAll() {
        return null;
    }

    @Override
    public ArrayList<Prenotazione> findByStation(Stazione staz) // Funzione per il controllo prenotazioni e pagamenti da parte dell'operatore
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
            /*p=new Prenotazione();
            p.setId(Integer.parseInt(riga[0]));
            p.setData(DateUtil.dateTimeFromString(riga[1]));
            p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
            p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
            pre.add(p);*/
            IStazioneDAO sDao = new StazioneDAO();
            IMezzoDAO mDao = new MezzoDAO();
            ILocalitaDAO lDao = new LocalitaDAO();

            p= new Prenotazione();
            Mezzo mezzo = mDao.findById(Integer.parseInt(riga[2]));
            Stazione partenza = sDao.findById(Integer.parseInt(riga[4]));
            Stazione arrivo = sDao.findById(Integer.parseInt(riga[5]));
            Localita l = lDao.findById(Integer.parseInt(riga[6]));

            p.setId(Integer.parseInt(riga[0]));
            p.setData(DateUtil.dateTimeFromString(riga[1]));
            p.setMezzo(mezzo);
            p.setNumPostiOccupati(Integer.parseInt(riga[3]));

            p.setPartenza(partenza);
            p.setArrivo(arrivo);
            p.setLocalita(l);
            p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
            p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
            p.setMezzoPreparato(Integer.parseInt(riga[9]) != 0);
            p.setPagato(Integer.parseInt(riga[10]));
            pre.add(p);
        }
        return pre;
    }
}