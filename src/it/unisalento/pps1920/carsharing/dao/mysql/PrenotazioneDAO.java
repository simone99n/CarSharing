package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.DateUtil;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.FinestraErrorCompilPren;
import it.unisalento.pps1920.carsharing.view.FinestraSharing;

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
            p.setMezzoPreparato(Integer.parseInt(riga[9]) != 0);
            p.setPagato(Integer.parseInt(riga[10]));
        }

        return p;
    }
    @Override
    public ArrayList<Prenotazione> findAll() {

        ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT idprenotazione FROM prenotazione ORDER BY idprenotazione DESC");

        for(String[] riga : res) {
            Prenotazione p = findById(Integer.parseInt(riga[0]));
            prenotazioni.add(p);
        }

        return prenotazioni;

    }

    public ArrayList<String> sharingCheck(Prenotazione p){

        Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        //1. Trovare l'id del modello del mezzo scelto
        String query1 = "SELECT modello_idmodello FROM mezzo WHERE idmezzo='"+p.getMezzo().getId()+"';";
        ArrayList<String[]> res1 = DbConnection.getInstance().eseguiQuery(query1);
        int idModelloScelto = Integer.parseInt(res1.get(0)[0]);

        //2. Trovare tutte le auto (mezzi) disponibili di quel modello
        String a = "SELECT idmezzo FROM mezzo WHERE modello_idmodello='"+idModelloScelto+"'";
        ArrayList<String[]> arrayMezzi = DbConnection.getInstance().eseguiQuery(a);

        //3. Trovare il numero di posti del modello
        String query4 = "SELECT num_posti FROM modello WHERE idmodello='"+idModelloScelto+"';";
        ArrayList<String[]> res4 = DbConnection.getInstance().eseguiQuery(query4);
        int postiTotali = Integer.parseInt(res4.get(0)[0]);

        //4.Trovare tutte le prenotazioni con quei mezzi con caratteristiche uguali alla nostra Prenotazione p

        for (String[] strings : arrayMezzi) { //todo controllare se lo scorrimento del "for" si ferma al punto giusto
            String query2 = "SELECT idprenotazione,num_posti_occupati FROM prenotazione WHERE mezzo_idmezzo=" + strings[0] + " AND " +
                    "idstazione_partenza='" + p.getPartenza().getId() + "' AND idstazione_arrivo='" + p.getArrivo().getId() + "' AND localita_idlocalita= '" + p.getLocalita().getId() + "'" +
                    " AND dataInizio='" + DateUtil.stringFromDate(p.getDataInizio()) + "' AND dataFine='" + DateUtil.stringFromDate(p.getDataFine()) + "';";

            ArrayList<String[]> arrayPrenotazioni = DbConnection.getInstance().eseguiQuery(query2);

            //todo aggiungere controllo se query restituisce NULL

            int[] idPrenotazione = new int[arrayPrenotazioni.size()];
            int[] numeroPosti = new int[arrayPrenotazioni.size()];

            for (int j = 0; j < arrayPrenotazioni.size(); j++) {
                idPrenotazione[j] = Integer.parseInt(arrayPrenotazioni.get(j)[0]);
                numeroPosti[j] = Integer.parseInt(arrayPrenotazioni.get(j)[1]);
            }
            for (int k = 0; k < arrayPrenotazioni.size(); k++) {
                int postiComplessivi = p.getNumPostiOccupati() + numeroPosti[k];

                if (postiComplessivi <= postiTotali) {
                    ArrayList<String> finale = new ArrayList<>();
                    finale.add("true");
                    System.out.println("id prenotazione in sharingCheck: "+idPrenotazione[k]);
                    finale.add(String.valueOf(clienteLoggato.getId()));  //id cliente
                    finale.add(String.valueOf(idPrenotazione[k]));       //prenotazione coinvolta nello sharing
                    finale.add(String.valueOf(p.getNumPostiOccupati())); //posti che si vogliono occupare
                    finale.add(String.valueOf(postiComplessivi));        //posti complessivi dello sharing
                    return finale;
                }
            }
        }

        System.out.println("[!!!]");
        ArrayList<String> finale = new ArrayList<>();
        finale.add("false");
        return finale;
    }

    public ArrayList<String[]> salvaPrenotazioneSharing(ArrayList<String> array){
        System.out.println("Il numero di posti consente lo Sharing");
        //Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        String sql1 = "INSERT INTO effettua VALUES (" + array.get(1) + "," + array.get(2) +"," + array.get(3) + ", 99999999, '12-21', 222)";
        //todo sistemare inserimento dati carta
        if (DbConnection.getInstance().eseguiAggiornamento(sql1))
            System.out.println("1. SHARING correttamente salvato nel DB (tabella EFFETTUA)");
        else{
            System.out.println("1. [ERROR] SHARING NON correttamente salvato nel DB (tabella EFFETTUA)");
            System.out.println("Hai già effettuato la prenotazione!!!");
            return null;
        }

        String sql2 = "UPDATE prenotazione SET num_posti_occupati=" + array.get(4) + " WHERE idprenotazione=" + array.get(2) + ";";

        if (DbConnection.getInstance().eseguiAggiornamento(sql2))
            System.out.println("2. SHARING correttamente salvato nel DB (update tabella prenotazione - num_posti_occupati)");
        else
            System.out.println("2. [ERROR] SHARING NON correttamente salvato nel DB (update tabella prenotazione - num_posti_occupati)");


        //inviamo un'email anche all'altro cliente coinvolto
        String sql3 = "SELECT cliente_utente_idutente FROM effettua WHERE prenotazione_idprenotazione="+array.get(2)+";";
        ArrayList<String[]> clienti = DbConnection.getInstance().eseguiQuery(sql3);
        ArrayList<String[]> emails = new ArrayList<>();
        for (String[] strings : clienti) {
            String emailsql = "SELECT email FROM utente WHERE idutente=" + strings[0] + ";";
            emails.add(DbConnection.getInstance().eseguiQuery(emailsql).get(0)); //todo ma funziona?!
        }

        FinestraSharing.idPrenotazione= Integer.parseInt(array.get(2));
        return emails;
    }

    @Override
    public boolean salvaPrenotazione(Prenotazione p) { //INSERIMENTO DELLA PRENOTAZIONE DEL DB

        if(p.getNumPostiOccupati()>p.getMezzo().getModello().getNumPosti()){
            System.out.println("Numero di posti inserito > posti disponibili nel veicolo");
            FinestraErrorCompilPren alert = new FinestraErrorCompilPren();
            alert.setVisible(true);
            return false;
        }

        String strDataPrenotazione = DateUtil.stringFromDate(p.getData());
        String strDataInizio = DateUtil.stringFromDate(p.getDataInizio());
        String strDataFine = DateUtil.stringFromDate(p.getDataFine());

        String sql = "INSERT INTO prenotazione VALUES (NULL, '"+strDataPrenotazione+"',"+p.getMezzo().getId()+","+p.getNumPostiOccupati()+","+
                p.getPartenza().getId()+","+p.getArrivo().getId()+","+p.getLocalita().getId()+",'"+strDataInizio+"','"+strDataFine+"','0','0');";
        //System.out.println(sql);
        if(DbConnection.getInstance().eseguiAggiornamento(sql))
            System.out.println("Prenotazione correttamente salvata nel DB");
        else
            System.out.println("[ERROR] Prenotazione NON salvata nel DB");

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT last_insert_id()");
        p.setId(Integer.parseInt(res.get(0)[0]));

        Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        sql = "INSERT INTO effettua VALUES ("+clienteLoggato.getId()+","+p.getId()+","+p.getNumPostiOccupati()+",123456, '15-21', 666);"; //TODO sistemare inserimento dati carta

        if(DbConnection.getInstance().eseguiAggiornamento(sql))
            System.out.println("Prenotazione correttamente salvata nel DB (tabella EFFETTUA)");
        else
            System.out.println("[ERROR] Prenotazione NON salvata nel DB (tabella EFFETTUA)");


        return true;
    }

    public boolean inserisciAccessori(Accessorio acc){
        if(acc!=null){
            Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
            String sql = "INSERT INTO accessorio_prenotazione VALUES ('" +acc.getId()+ "','" +getLastPrenotazione(clienteLoggato)+"')";
            System.out.println(sql);
            if (DbConnection.getInstance().eseguiAggiornamento(sql)){
                System.out.println(">> Accessorio salvato nel DB (tabella accessorio_prenotazione)");
                return true;
            }
            else{
                System.out.println(">> [ERROR]Accessorio NON salvato nel DB (tabella accessorio_prenotazione)");
                return false;
            }
        }
        return false;
    }

    public void inserisciAccessoriMod(Accessorio a, int idPrenotazione){
        if(a!=null){
            String sql = "INSERT INTO accessorio_prenotazione VALUES ('" +a.getId()+ "','" +idPrenotazione+"')";
            if (DbConnection.getInstance().eseguiAggiornamento(sql)){
                System.out.println(">> Accessorio aggiunto (MOD) nel DB (tabella accessorio_prenotazione)");
            }
            else{
                System.out.println(">> [ERROR]Accessorio NON aggiunto (MOD) nel DB (tabella accessorio_prenotazione)");

            }
        }
    }

    public void eliminaAccessorio(int idAccessorio, int idPrenotazione){
        System.out.println("Eliminazione accessorio!");
        String sql1 = "DELETE FROM accessorio_prenotazione WHERE accessorio_idaccessorio='"+idAccessorio+"' AND prenotazione_idprenotazione='"+idPrenotazione+"';";
        DbConnection.getInstance().eseguiAggiornamento(sql1);
    }

    public int eliminaPrenotazione(int idPrenotazione){
        String sql1="SELECT * FROM effettua WHERE prenotazione_idprenotazione='"+idPrenotazione+"';";
        ArrayList<String[]> tmp = DbConnection.getInstance().eseguiQuery(sql1);
        if(tmp.size()==1){
            String bye =  "DELETE FROM effettua WHERE prenotazione_idprenotazione='" +idPrenotazione+ "';";
            String bye2 = "DELETE FROM accessorio_prenotazione WHERE prenotazione_idprenotazione='" +idPrenotazione+ "';";
            String bye3 = "DELETE FROM prenotazione WHERE idprenotazione='" +idPrenotazione+ "';";

            DbConnection.getInstance().eseguiAggiornamento(bye);
            DbConnection.getInstance().eseguiAggiornamento(bye2);
            DbConnection.getInstance().eseguiAggiornamento(bye3);
            System.out.println("Prenotazione Cancellata");
            return 0; //prenotazione completamente cancellata
        }
        else{
            Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);

            int postiOccupatiCliente = Integer.parseInt(DbConnection.getInstance().eseguiQuery("SELECT posti_occupati FROM effettua WHERE prenotazione_idprenotazione='" +idPrenotazione+"' AND cliente_utente_idutente='"+clienteLoggato.getId()+"';").get(0)[0]);
            int postiOccupatiTotali = Integer.parseInt(DbConnection.getInstance().eseguiQuery("SELECT num_posti_occupati FROM prenotazione WHERE idprenotazione='" +idPrenotazione+"';").get(0)[0]);

            DbConnection.getInstance().eseguiAggiornamento("UPDATE prenotazione SET num_posti_occupati='"+(postiOccupatiTotali-postiOccupatiCliente)+"' WHERE idprenotazione='"+idPrenotazione+"';");
            DbConnection.getInstance().eseguiAggiornamento("DELETE FROM effettua WHERE prenotazione_idprenotazione='" +idPrenotazione+ "' AND cliente_utente_idutente='"+clienteLoggato.getId()+"';");
            System.out.println("Prenotazione Cancellata e sharing annullato");
            return 1; //prenotazione cancellata solo per un cliente, annullamento sharing
        }


    }

    public int getLastPrenotazione(Cliente cliente){
        String sql1 = "SELECT prenotazione_idprenotazione FROM effettua WHERE cliente_utente_idutente='" + cliente.getId()+ "'ORDER BY prenotazione_idprenotazione DESC;";
        return Integer.parseInt(DbConnection.getInstance().eseguiQuery(sql1).get(0)[0]);
    }

    public ArrayList<String[]> getClienteInfo(int idPrenotazione, int index){
        String sql1 = "SELECT cliente_utente_idutente FROM effettua WHERE prenotazione_idprenotazione='" + idPrenotazione+ "';";
        ArrayList<String[]> arrayIdClienti = DbConnection.getInstance().eseguiQuery(sql1);
        String sql2 = "SELECT nome, cognome, residenza, anno_nascita FROM cliente WHERE utente_idutente='" + arrayIdClienti.get(index)[0] + "';";
        return DbConnection.getInstance().eseguiQuery(sql2);
    }

    public int getNumClienti(int idPrenotazione) {
        String sql1 = "SELECT cliente_utente_idutente FROM effettua WHERE prenotazione_idprenotazione='" + idPrenotazione+ "';";
        ArrayList<String[]> arrayIdClienti = DbConnection.getInstance().eseguiQuery(sql1);
        return arrayIdClienti.size();
    }

    public int getPostiTableEffettua(int idPrenotazione){
        Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        int posti = Integer.parseInt(DbConnection.getInstance().eseguiQuery("SELECT posti_occupati FROM effettua WHERE prenotazione_idprenotazione='" +idPrenotazione+"' AND cliente_utente_idutente='"+clienteLoggato.getId()+"';").get(0)[0]);

        return posti;
    }

    public int getIdUltimaPrenotazione(int idCliente){
        return Integer.parseInt(DbConnection.getInstance().eseguiQuery("SELECT LAST_INSERT_ID();").get(0)[0]);
    }

    public ArrayList<Prenotazione> findAllForCliente(){
        Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
        ArrayList<Prenotazione> prenotazioni = new ArrayList<Prenotazione>();
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT prenotazione_idprenotazione FROM effettua WHERE cliente_utente_idutente ='"+clienteLoggato.getId()+"' ORDER BY prenotazione_idprenotazione DESC;");
        for (String[] re : res) {
            Prenotazione p = findById(Integer.parseInt(re[0]));
            prenotazioni.add(p);
        }

        return prenotazioni;
    }

    @Override
    public ArrayList<Prenotazione> findForData(String data) {
        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione WHERE dataInizio LIKE '"+data+"%';");
        ArrayList<Prenotazione> pre = new ArrayList<Prenotazione>();
        if(!res.isEmpty()) {

            for(String[] riga : res) {

                IStazioneDAO sDao = new StazioneDAO();
                IMezzoDAO mDao = new MezzoDAO();
                ILocalitaDAO lDao = new LocalitaDAO();

                Prenotazione p= new Prenotazione();
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
                pre.add(p);
            }
            return pre;

        }

        pre=null;
        return pre;

    }

    @Override
    public ArrayList<Prenotazione> findForStation(String nome) {
        ArrayList<Prenotazione> pre=new ArrayList<>();
        ArrayList<String[] >res= DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione INNER JOIN stazione WHERE prenotazione.idstazione_partenza=stazione.idstazione AND stazione.nome='"+nome+"';");
        if(!res.isEmpty()) {

            for(String[] riga : res) {
                /*Prenotazione p= new Prenotazione();
                p.setId(Integer.parseInt(riga[0]));
                p.setData(DateUtil.dateTimeFromString(riga[1]));
                p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
                p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
                pre.add(p);*/
                IStazioneDAO sDao = new StazioneDAO();
                IMezzoDAO mDao = new MezzoDAO();
                ILocalitaDAO lDao = new LocalitaDAO();

                Prenotazione p= new Prenotazione();
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
                pre.add(p);
            }
            return pre;

        }
        pre=null;
        return pre;
    }

    @Override
    public ArrayList<Prenotazione> findForModel(String mod) {
        ArrayList<Prenotazione>pre= new ArrayList<>();
        ArrayList<String[] >res= DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione INNER JOIN mezzo ON prenotazione.mezzo_idmezzo=mezzo.idmezzo INNER JOIN modello ON mezzo.modello_idmodello=modello.idmodello WHERE modello.tipologia='"+mod+"';");

        if(!res.isEmpty()) {

            for(String[] riga : res) {
               /* Prenotazione p= new Prenotazione();
                p.setId(Integer.parseInt(riga[0]));
                p.setData(DateUtil.dateTimeFromString(riga[1]));
                p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
                p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
                pre.add(p);*/
                IStazioneDAO sDao = new StazioneDAO();
                IMezzoDAO mDao = new MezzoDAO();
                ILocalitaDAO lDao = new LocalitaDAO();

                Prenotazione p= new Prenotazione();
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
                pre.add(p);
            }

            return pre;

        }
        pre=null;
        return pre;
    }
    @Override
    public ArrayList<Prenotazione> findForBrand(String nome) {
        ArrayList<Prenotazione>pre = new ArrayList<>();
        ArrayList<String[] >res= DbConnection.getInstance().eseguiQuery("SELECT * FROM prenotazione INNER JOIN mezzo ON prenotazione.mezzo_idmezzo=mezzo.idmezzo INNER JOIN modello ON mezzo.modello_idmodello=modello.idmodello WHERE modello.nome='"+nome+"';");
        if(!res.isEmpty()) {

            for(String[] riga : res) {
                /*Prenotazione p= new Prenotazione();
                p.setId(Integer.parseInt(riga[0]));
                p.setData(DateUtil.dateTimeFromString(riga[1]));
                p.setDataInizio(DateUtil.dateTimeFromString(riga[7]));
                p.setDataFine(DateUtil.dateTimeFromString(riga[8]));
                pre.add(p);*/
                IStazioneDAO sDao = new StazioneDAO();
                IMezzoDAO mDao = new MezzoDAO();
                ILocalitaDAO lDao = new LocalitaDAO();

                Prenotazione p= new Prenotazione();
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
                pre.add(p);
            }
            return pre;

        }
        pre=null;
        return pre;
    }

    public void setPagato(int idPrenotazione){
        String sql1 = "UPDATE prenotazione SET pagato=1 WHERE idPrenotazione='"+idPrenotazione+"';";
        DbConnection.getInstance().eseguiAggiornamento(sql1);
    }




    //samuele
    @Override
    public void setStatoAutomezzo(int idprenotazione){
        boolean res= DbConnection.getInstance().eseguiAggiornamento("UPDATE prenotazione SET mezzoPreparato=1 WHERE idprenotazione="+idprenotazione+";");
    }

    @Override
    public ArrayList<String[]> findForIdAddettoAndIdStation(int idStazione,int idPrenotazione) {
        ArrayList<String []>res= DbConnection.getInstance().eseguiQuery("SELECT idstazione_partenza,mezzoPreparato FROM prenotazione WHERE idprenotazione="+idPrenotazione+" ;");
        ArrayList<String[]> ret1= new ArrayList<>();
        String[] r1= new String[1];
        if(res.isEmpty()) {
            r1[0]="-1";
            ret1.add(r1);
            return ret1;
        }

        String[] rigaa= res.get(0);
        if(Integer.parseInt(rigaa[0])!=idStazione) {

            r1[0]="-2";
            ret1.add(r1);
            return ret1;
        }
        if(Integer.parseInt(rigaa[1])==1) {
            r1[0]="-3";
            ret1.add(r1);
            return ret1;
        }
        r1=res.get(0);
        ret1.add(r1);
        return ret1;
    }
}
