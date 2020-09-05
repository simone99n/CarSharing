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
        ArrayList<String []>res= DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE idstazione_partenza="+staz.getId()+" ORDER BY idprenotazione DESC;");
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

    public Modello findModelByIdPrenotazione(int id) {
        Modello mod=new Modello();
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT modello.tipologia,modello.nome FROM modello INNER JOIN mezzo ON modello.idmodello=mezzo.modello_idmodello INNER JOIN prenotazione ON prenotazione.mezzo_idmezzo=mezzo.idmezzo WHERE prenotazione.idprenotazione="+id+" ;");
        if(!res.isEmpty()) {
            String[] riga= res.get(0);
            mod.setNome(riga[1]);
            mod.setTipologia(riga[0]);
            return mod;
        }
        mod.setId(0);
        return mod;
    }

    public ArrayList<String[]> findByStation3(Stazione staz){

        ArrayList<String []>res= DbConnection.getInstance().eseguiQuery("SELECT idprenotazione,dataInizio FROM prenotazione WHERE idstazione_partenza="+staz.getId()+" AND mezzoPreparato='1' ORDER BY idprenotazione DESC;");
        if(res.isEmpty())
            return null;

        ArrayList<String[]>str= new ArrayList<>();
        Prenotazione p;
        Modello a;
        for(String[] riga : res) {
            String[] record= new String[4];
            p=new Prenotazione();
            a=new Modello();
            a=findModelByIdPrenotazione(Integer.parseInt(riga[0]));
            record[0]=riga[0];
            record[1]=riga[1];
            record[2]=a.getNome();
            record[3]=a.getTipologia();
            str.add(record);
        }
        return str;
    }
}