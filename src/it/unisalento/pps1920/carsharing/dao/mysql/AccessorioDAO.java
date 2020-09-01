package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAccessorioDAO;
import it.unisalento.pps1920.carsharing.model.Accessorio;

import java.util.ArrayList;
import java.util.Arrays;

public class AccessorioDAO implements IAccessorioDAO {

    public Accessorio findById(int id) {
        Accessorio a = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT  nomeaccessorio WHERE idaccessorio = "+id+";");

        if(res.size()==1) {
            String[] riga = res.get(0);
            a = new Accessorio();
            a.setId(id);
            a.setNome(riga[0]);
        }

        return a;
    }

    public ArrayList<Accessorio> findAll() {

        // PRIMO COMPITO DEL DAO: ESEGUIRE LA QUERY
        ArrayList<String[]> risultato = DbConnection.getInstance().eseguiQuery("SELECT * FROM accessorio;");


        // SECONDO COMPITO DEL DAO: ISTANZIARE IL NOSTRO MODEL CON I RISULTATI DELLA QUERY
        ArrayList<Accessorio> accs = new ArrayList<Accessorio>();

        for(String[] riga : risultato) {
            Accessorio a = new Accessorio();
            a.setId(Integer.parseInt(riga[0]));
            a.setNome(riga[1]);
            accs.add(a);
        }

        return accs;

    }

    public ArrayList<Accessorio> findByPrenotazione(int id){
        String sql1="SELECT accessorio_idaccessorio FROM accessorio_prenotazione WHERE prenotazione_idprenotazione="+id+";";
        ArrayList<String[]> accessoriId = DbConnection.getInstance().eseguiQuery(sql1);
        ArrayList<Accessorio> accs = new ArrayList<Accessorio>();

        for(int i=0; i<accessoriId.size();i++){
            String sql2="SELECT nomeaccessorio FROM accessorio WHERE idaccessorio='" +accessoriId.get(i)[0]+"';";
            String[] prova = DbConnection.getInstance().eseguiQuery(sql2).get(0);

            Accessorio a = new Accessorio();
            a.setId(Integer.parseInt(accessoriId.get(i)[0]));
            a.setNome(prova[0]);
            accs.add(a);

        }
        return accs;
    }
}
