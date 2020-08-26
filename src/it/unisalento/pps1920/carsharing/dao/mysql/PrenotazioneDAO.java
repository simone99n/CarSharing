package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrenotazioneDAO implements IPrenotazioneDAO {
    @Override
    public Prenotazione findById(int id) {

        Prenotazione p = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE idprenotazione = "+id+";");

        if(res.size()==1) {
            IStazioneDAO sDao = new StazioneDAO();
            IClienteDAO cDao = new ClienteDAO();
            IMezzoDAO mDao = new MezzoDAO();
            ILocalitaDAO lDao = new LocalitaDAO();

            String[] riga = res.get(0);

            p = new Prenotazione();
            p.setId(Integer.parseInt(riga[0]));
            p.setData(DateUtil.dateTimeFromString(riga[1]));
            Mezzo mezzo = mDao.findById(Integer.parseInt(riga[2]));
            p.setNumPostiOccupati(Integer.parseInt(riga[3]));
            Stazione partenza = sDao.findById(Integer.parseInt(riga[4]));
            Stazione arrivo = sDao.findById(Integer.parseInt(riga[5]));
            Localita l = lDao.findById(Integer.parseInt(riga[6]));
            p.setPartenza(partenza);
            p.setArrivo(arrivo);
            p.setMezzo(mezzo);
            p.setLocalita(l);
            p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
            p.setDataFine(DateUtil.dateTimeFromString(riga[8]));

        }

        return p;
    }

    @Override
    public ArrayList<Prenotazione> findAll() {

        ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT idprenotazione FROM prenotazione");

        for(String[] riga : res) {
            Prenotazione p = findById(Integer.parseInt(riga[0]));
            prenotazioni.add(p);
        }

        return prenotazioni;

    }

    @Override
    public void salvaPrenotazione(Prenotazione p) {                         //INSERIMENTO DELLA PRENOTAZIONE DEL DB
        String strDataPrenotazione = DateUtil.stringFromDate(p.getData());
        String strDataInizio = DateUtil.stringFromDate(p.getDataInizio());
        String strDataFine = DateUtil.stringFromDate(p.getDataFine());

        String sql = "INSERT INTO prenotazione VALUES (NULL, '"+strDataPrenotazione+"',"+p.getMezzo().getId()+","+p.getNumPostiOccupati()+","+
                                                        p.getPartenza().getId()+","+p.getArrivo().getId()+","+p.getLocalita().getId()+",'"+strDataInizio+"','"+strDataFine+"');";
        //System.out.println(sql);
        if(DbConnection.getInstance().eseguiAggiornamento(sql))
            System.out.println("Prenotazione correttamente salvata nel DB");
        else
            System.out.println("[ERROR] Prenotazione NON salvata nel DB");
        //sql = "SELECT last_insert_id()";
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT last_insert_id()");
        p.setId(Integer.parseInt(res.get(0)[0]));

        Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        sql = "INSERT INTO effettua VALUES ("+clienteLoggato.getId()+","+p.getId()+","+p.getNumPostiOccupati()+",123456, '15-21', 666);";

        if(DbConnection.getInstance().eseguiAggiornamento(sql))
            System.out.println("Prenotazione correttamente salvata nel DB (tabella EFFETTUA)");
        else
            System.out.println("[ERROR] Prenotazione NON salvata nel DB (tabella EFFETTUA)");

    }
}
