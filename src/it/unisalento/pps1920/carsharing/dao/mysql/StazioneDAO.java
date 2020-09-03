package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;
import it.unisalento.pps1920.carsharing.util.MailHelper;
import it.unisalento.pps1920.carsharing.util.Session;

import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Date;

public class StazioneDAO implements IStazioneDAO {
    @Override
    public Stazione findById(int id) {
        Stazione s = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT S.idstazione, S.nome, S.latitudine, S.longitudine, S.addetto_utente_idutente, S.operatore_utente_idutente FROM stazione AS S INNER JOIN addetto AS A ON S.addetto_utente_idutente = A.utente_idutente WHERE S.idstazione = "+id+";");

        if(res.size() == 1 ) {
            String riga[] = res.get(0);
            s=new Stazione();
            s.setId(Integer.parseInt(riga[0]));
            s.setNome(riga[1]);
            s.setLatitudine(Double.parseDouble(riga[2]));
            s.setLongitudine(Double.parseDouble(riga[3]));
            IAddettoDAO aDao = new AddettoDAO();
            IOperatoreDAO oDao = new OperatoreDAO();
            s.setAddetto(aDao.findById(Integer.parseInt(riga[4])));
            s.setOperatore(oDao.findById(Integer.parseInt(riga[5])));
        }

        return s;
    }

    @Override
    public ArrayList<Stazione> findAll() {
        ArrayList<Stazione> stazioni = new ArrayList<Stazione>();
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM stazione");

        for(String[] riga : res){
            stazioni.add(findById(Integer.parseInt(riga[0])));
        }

        return stazioni;
    }


    public String getPartenza(int idPrenotazione){
        String sql1 = "SELECT idstazione_partenza FROM prenotazione WHERE idprenotazione='"+idPrenotazione+"';";
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(sql1);
        if(res.size() == 1 ) {
            String sql2 = "SELECT nome FROM stazione WHERE idstazione='"+res.get(0)[0]+"';";
            ArrayList<String[]> nomeStazione = DbConnection.getInstance().eseguiQuery(sql2);
            return nomeStazione.get(0)[0];
        }
        else
            return "null";
    }

    public String getArrivo(int idPrenotazione){
        String sql1 = "SELECT idstazione_arrivo FROM prenotazione WHERE idprenotazione='"+idPrenotazione+"';";
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery(sql1);
        if(res.size() == 1 ) {
            String sql2 = "SELECT nome FROM stazione WHERE idstazione='"+res.get(0)[0]+"';";
            ArrayList<String[]> nomeStazione = DbConnection.getInstance().eseguiQuery(sql2);
            return nomeStazione.get(0)[0];
        }
        else
            return "null";
    }

    public int modificaStazione(int newIdPartenza, int newIdArrivo, int idPrenotazione){

        String sql1="SELECT * FROM effettua WHERE prenotazione_idprenotazione='"+idPrenotazione+"';";
        ArrayList<String[]> tmp = DbConnection.getInstance().eseguiQuery(sql1);
        if(tmp.size()==1){ //se il cliente è unico (No sharing)
            String up1="UPDATE prenotazione SET idstazione_partenza='"+newIdPartenza+"', idstazione_arrivo='"+newIdArrivo+"' WHERE idprenotazione='"+idPrenotazione+"';";
            DbConnection.getInstance().eseguiAggiornamento(up1);
            return 0; //prenotazione completamente modificata
        }
        else{
            Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
            int postiOccupatiCliente = Integer.parseInt(DbConnection.getInstance().eseguiQuery("SELECT posti_occupati FROM effettua WHERE prenotazione_idprenotazione='" +idPrenotazione+"' AND cliente_utente_idutente='"+clienteLoggato.getId()+"';").get(0)[0]);
            int postiOccupatiTotali = Integer.parseInt(DbConnection.getInstance().eseguiQuery("SELECT num_posti_occupati FROM prenotazione WHERE idprenotazione='" +idPrenotazione+"';").get(0)[0]);

            Prenotazione p;
            IPrenotazioneDAO ip = new PrenotazioneDAO();
            p=ip.findById(idPrenotazione);  //prenotazione old


            DbConnection.getInstance().eseguiAggiornamento("DELETE FROM effettua WHERE prenotazione_idprenotazione='" +idPrenotazione+"' AND cliente_utente_idutente='"+clienteLoggato.getId()+"';");
            DbConnection.getInstance().eseguiAggiornamento("UPDATE prenotazione SET num_posti_occupati='"+(postiOccupatiTotali-postiOccupatiCliente)+"' WHERE idprenotazione='"+idPrenotazione+"';");

            Stazione staz = new Stazione();
            IStazioneDAO stazDAO = new StazioneDAO();

            p.setArrivo(stazDAO.findById(newIdArrivo));
            p.setPartenza(stazDAO.findById(newIdPartenza));
            p.setData(new Date());
            p.setNumPostiOccupati(postiOccupatiCliente);

            ip.salvaPrenotazione(p);


            for (String[] strings : tmp) {
                if (Integer.parseInt(strings[0]) != clienteLoggato.getId()) {
                    IClienteDAO c = new ClienteDAO();
                    MailHelper.getInstance().send(c.findById(Integer.parseInt(strings[0])).getEmail(), "Sharing annullato", "La prenotazione n° " + idPrenotazione + "è stata modificata e lo sharing è annullato.");
                }
            }

            return 1; //prenotazione modificata con Sharing
        }
    }

    @Override
    public Stazione findStationByOperatorId(int id) {
        ArrayList<String []>res=DbConnection.getInstance().eseguiQuery("SELECT * FROM stazione WHERE operatore_utente_idutente="+id+";");
        String [] riga = res.get(0);
        Stazione s = new Stazione();
        s.setNome(riga[1]);
        s.setId(Integer.parseInt(riga[0]));
        s.setLatitudine(Double.parseDouble(riga[2]));
        s.setLongitudine(Double.parseDouble(riga[3]));
        return s;
    }
    public Stazione findStationByAddettoId(int id) {
        ArrayList<String []>res=DbConnection.getInstance().eseguiQuery("SELECT * FROM stazione WHERE addetto_utente_idutente="+id+";");
        String [] riga = res.get(0);
        Stazione s = new Stazione();
        s.setNome(riga[1]);
        s.setId(Integer.parseInt(riga[0]));
        s.setLatitudine(Double.parseDouble(riga[2]));
        s.setLongitudine(Double.parseDouble(riga[3]));
        return s;
    }

    public String nomeStazioneFromOperatore(int idOperatore){
        String sql1="SELECT nome FROM stazione WHERE operatore_utente_idutente='"+idOperatore+"'";
        return DbConnection.getInstance().eseguiQuery(sql1).get(0)[0];
    }
}
