package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.util.ArrayList;

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
    public void salvaPrenotazione(Prenotazione p) {

        String strDataPrenotazione = DateUtil.stringFromDate(p.getData());
        String strDataInizio = DateUtil.stringFromDate(p.getDataInizio());
        String strDataFine = DateUtil.stringFromDate(p.getDataFine());

        String sql = "INSERT INTO prenotazione VALUES (NULL, '"+strDataPrenotazione+"',"+p.getCliente().getId()+","+p.getMezzo().getId()+","+p.getNumPostiOccupati()+","+p.getPartenza().getId()+","+p.getArrivo().getId()+","+p.getLocalita().getId()+",'"+strDataInizio+"','"+strDataFine+"');";

        System.out.println(sql);
        DbConnection.getInstance().eseguiAggiornamento(sql);

        sql = "SELECT last_insert_id()";
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(sql);
        p.setId(Integer.parseInt(res.get(0)[0]));
    }

    @Override
    public ArrayList<Prenotazione> findForData(String data)
    {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE dataInizio LIKE '"+data+"%';");
        ArrayList<Prenotazione> pre = new ArrayList<Prenotazione>();
        if(!res.isEmpty())
        {
            Prenotazione p= new Prenotazione();
            for(String[] riga : res)
            {
                p.setId(Integer.parseInt(riga[0]));
                p.setData(DateUtil.dateTimeFromString(riga[1]));
                p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
                p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
                pre.add(p);
            }
            return pre;

        }
        pre=null;
        return pre;

    }

    @Override
    public ArrayList<Prenotazione> findForStation(String nome)
    {
        ArrayList<Prenotazione> pre=new ArrayList<>();
        ArrayList<String[] >res= DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione INNER JOIN stazione WHERE prenotazione.idstazione_partenza=stazione.idstazione AND stazione.nome='"+nome+"';");
        if(!res.isEmpty())
        {

            for(String[] riga : res)
            {
                Prenotazione p= new Prenotazione();
                p.setId(Integer.parseInt(riga[0]));
                p.setData(DateUtil.dateTimeFromString(riga[1]));
                p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
                p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
                pre.add(p);
            }
            return pre;

        }
        pre=null;
        return pre;
    }

    @Override
    public ArrayList<Prenotazione> findForModel(String mod)
    {
        ArrayList<Prenotazione>pre= new ArrayList<>();
        ArrayList<String[] >res= DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione INNER JOIN mezzo ON prenotazione.mezzo_idmezzo=mezzo.idmezzo INNER JOIN modello ON mezzo.modello_idmodello=modello.idmodello WHERE modello.tipologia='"+mod+"';");

        if(!res.isEmpty())
        {

           for(String[] riga : res)
           {
               Prenotazione p= new Prenotazione();
               p.setId(Integer.parseInt(riga[0]));
               p.setData(DateUtil.dateTimeFromString(riga[1]));
               p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
               p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
               pre.add(p);
           }

           return pre;

        }
        pre=null;
        return pre;
    }
    @Override
    public ArrayList<Prenotazione> findForBrand(String nome)
    {
        ArrayList<Prenotazione>pre = new ArrayList<>();
        ArrayList<String[] >res= DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione INNER JOIN mezzo ON prenotazione.mezzo_idmezzo=mezzo.idmezzo INNER JOIN modello ON mezzo.modello_idmodello=modello.idmodello WHERE modello.nome='"+nome+"';");
        if(!res.isEmpty())
        {
            Prenotazione p= new Prenotazione();
            for(String[] riga : res)
            {
                p.setId(Integer.parseInt(riga[0]));
                p.setData(DateUtil.dateTimeFromString(riga[1]));
                p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
                p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
                pre.add(p);
            }
            return pre;

        }
        pre=null;
        return pre;
    }

    @Override
    public void setStatoAutomezzo(int idprenotazione){
        boolean res= DbConnection.getInstance().eseguiAggiornamento("UPDATE prenotazione SET mezzoPreparato=1 WHERE idprenotazione="+idprenotazione+";");
    }




    @Override
    public ArrayList<String[]> findForIdAddettoAndIdStation(int idStazione,int idPrenotazione)
    {
        ArrayList<String []>res= DbConnection.getInstance().eseguiQuery("SELECT idstazione_partenza FROM prenotazione WHERE idprenotazione="+idPrenotazione+" ;");
        ArrayList<String[]> ret1= new ArrayList<>();
        String[] r1= new String[1];
        if(res.isEmpty())
        {
            r1[0]="-1";
            ret1.add(r1);
            return ret1;
        }

        String[] rigaa= res.get(0);
        if(Integer.parseInt(rigaa[0])!=idStazione)
        {

            r1[0]="-2";
            ret1.add(r1);
            return ret1;
        }
        r1=res.get(0);
        ret1.add(r1);
        return ret1;
    }

}
