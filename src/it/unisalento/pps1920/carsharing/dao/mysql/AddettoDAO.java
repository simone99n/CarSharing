package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAddettoDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.util.ArrayList;

public class AddettoDAO implements IAddettoDAO {
    @Override
    public Addetto findById(int id) {
        Addetto a = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT A.utente_idutente, U.username, U.password, U.email FROM addetto AS A INNER JOIN utente as U  ON U.idutente = A.utente_idutente WHERE A.utente_idutente = "+id+";");

        if(res.size()==1) {
            String[] riga = res.get(0);
            a = new Addetto();
            a.setId(Integer.parseInt(riga[0]));
            a.setUsername(riga[1]);
            a.setEmail(riga[3]);
        }

        return a;
    }

    @Override
    public ArrayList<Addetto> findAll() {
        return null;
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

    public ArrayList<Prenotazione> findByStation(Stazione staz) {
        ArrayList<String []>res= DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE idstazione_partenza="+staz.getId()+";");
        if(res.isEmpty())
            return null;

        ArrayList<Prenotazione>pre= new ArrayList<Prenotazione>();
        Prenotazione p;
        for(String[] riga : res) {
            p=new Prenotazione();
            p.setId(Integer.parseInt(riga[0]));
            p.setData(DateUtil.dateTimeFromString(riga[1]));
            p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
            p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
            pre.add(p);
        }
        return pre;
    }





 public ArrayList<String[]> findByStation2(Stazione staz){ // restitiusce i record situati nella tabella appena effettuato il login dell'addetto.

        ArrayList<String []>res= DbConnection.getInstance().eseguiQuery("SELECT idprenotazione,dataInizio FROM prenotazione WHERE idstazione_partenza="+staz.getId()+" AND mezzoPreparato='0' ORDER BY idprenotazione DESC;");
        if(res.isEmpty())
            return null;

        ArrayList<String[]>str= new ArrayList<>();
        Prenotazione p;
        Modello a;
        for(String[] riga : res){

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


    public int getIdAddettoFromIdOperatore(int idOperatore){
        return Integer.parseInt(DbConnection.getInstance().eseguiQuery("SELECT addetto_utente_idutente FROM stazione WHERE operatore_utente_idutente='"+idOperatore+"';").get(0)[0]);
    }
}
