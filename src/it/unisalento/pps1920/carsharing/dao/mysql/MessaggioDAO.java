package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;

import java.util.ArrayList;

public class MessaggioDAO implements IMessaggioDAO {

    @Override
    public Messaggio findById(int id) {
        Messaggio m = null;
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM messaggio WHERE idsegnalazione = "+id+";");

        if(res.size()==1) {

            IMessaggioDAO mDao = new MessaggioDAO();
            IUtenteDAO uDAO = new UtenteDAO();
            Utente utente1;
            Utente utente2;

            String[] riga = res.get(0);

            m = new Messaggio();

            m.setIdSegnalazione(Integer.parseInt(riga[0]));
            m.setTestoMessaggio(riga[1]);
            m.setLetto(Integer.parseInt(riga[2]));
            utente1 = uDAO.findById(Integer.parseInt(riga[3]));
            utente2 = uDAO.findById(Integer.parseInt(riga[4]));
            m.setSorgente(utente1);
            m.setDestinatario(utente2);
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

    public void inserisciMessaggio(int idSorgente, int idDestinatario, String testo){

        String sql1 = "INSERT INTO messaggio (idsegnalazione, messaggio, sorgente, destinatario) VALUES (NULL, '"+testo+"', '"+idSorgente+"', '"+idDestinatario+"');";
        if(DbConnection.getInstance().eseguiAggiornamento(sql1))
            System.out.println("Inserimento messaggio eseguito");
        else
            System.out.println("Inserimento messaggio NON eseguito");

    }

    public ArrayList<String[]> getMessaggiOperatore(){
        Operatore operatoreLoggato = (Operatore) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        String sql1 = "SELECT * FROM messaggio WHERE destinatario='"+operatoreLoggato.getId_operatore()+"' AND letto='0';";
        return DbConnection.getInstance().eseguiQuery(sql1);
    }

    public ArrayList<String[]> getMessaggiAmministratore(){
        Amministratore amministratoreeLoggato = (Amministratore) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        String sql1 = "SELECT * FROM messaggio WHERE destinatario='"+amministratoreeLoggato.getId()+"' AND letto='0';";
        return DbConnection.getInstance().eseguiQuery(sql1);
    }

    public String getNomeFromId(int idSorgente){
        String sql1 = "SELECT nome FROM amministratore WHERE utente_idutente='"+idSorgente+"';";
        return DbConnection.getInstance().eseguiQuery(sql1).get(0)[0];
    }

    public int getIdSorgenteFromIdSegnalazione(int idSegnalazione){
        String sql1 = "SELECT sorgente FROM messaggio WHERE idsegnalazione='"+idSegnalazione+"';";
        return Integer.parseInt(DbConnection.getInstance().eseguiQuery(sql1).get(0)[0]);
    }

    public void setLetto(int id){
        String sql1 = "UPDATE messaggio SET letto='1' WHERE idsegnalazione='"+id+"';";
        DbConnection.getInstance().eseguiAggiornamento(sql1);
    }
}
