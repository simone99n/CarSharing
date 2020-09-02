package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IModelloDAO;
import it.unisalento.pps1920.carsharing.model.Modello;

import java.util.ArrayList;

public class ModelloDAO implements IModelloDAO {
    @Override
    public Modello findById(int id) {
        Modello m = null;

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM modello WHERE idmodello = "+id+";");

        if(res.size() == 1) {
            m= new Modello();
            String[] riga = res.get(0);

            m.setId(Integer.parseInt(riga[0]));
            m.setNome(riga[1]);
            m.setNumPosti(Integer.parseInt(riga[2]));


            byte[] foto = DbConnection.getInstance().getFoto("SELECT foto FROM modello WHERE idmodello="+id+";");
            m.setFoto(foto);
            m.setTipologia(riga[4]);
            m.setPrezzo(Float.parseFloat(riga[5]));
        }

        return m;
    }

    @Override
    public ArrayList<Modello> findAll() {
        ArrayList<Modello> lista = new ArrayList<Modello>();

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM modello");
        for(String[] riga : res)
            lista.add(findById(Integer.parseInt(riga[0])));
        return lista;
    }

    public ArrayList<Modello> findByTipologia(String tipologia, String grandezza, String motorizzazione){
        ArrayList<Modello> lista = new ArrayList<Modello>();

        if(tipologia.equals("AUTO")){
            System.out.println("SELECT * FROM modello INNER JOIN categoriaauto ON modello.idmodello=categoriaauto.modello_idmodello " + "INNER JOIN mezzo ON modello.idmodello=mezzo.modello_idmodello WHERE modello.tipologia='"+ tipologia+"' AND categoriaauto.categoria='"+grandezza+"' AND mezzo.alimentazione='"+motorizzazione+"';");
            ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM modello INNER JOIN categoriaauto ON modello.idmodello=categoriaauto.modello_idmodello " +
                    "INNER JOIN mezzo ON modello.idmodello=mezzo.modello_idmodello WHERE modello.tipologia='"+ tipologia+"' AND categoriaauto.categoria='"+grandezza+"' AND mezzo.alimentazione='"+motorizzazione+"';");

            for(String[] riga : res)
                lista.add(findById(Integer.parseInt(riga[0])));
            return lista;
        }

        ArrayList<String[]> res = DbConnection.getInstance().eseguiQuery("SELECT * FROM modello INNER JOIN mezzo ON modello.idmodello=mezzo.modello_idmodello WHERE tipologia='"+tipologia+"'AND mezzo.alimentazione='"+motorizzazione+"';");
        for(String[] riga : res)
            lista.add(findById(Integer.parseInt(riga[0])));
        return lista;

    }

    public ArrayList<Modello> getModelliMenoCostosi(){

        ArrayList<String[]> arrayList = DbConnection.getInstance().eseguiQuery("SELECT idmodello FROM modello ORDER BY prezzo ASC;");
        ArrayList<Modello> res = new ArrayList<Modello>();
        for(int i=0;i<5;i++){
            res.add(findById(Integer.parseInt(arrayList.get(i)[0])));
        }
        return res;
    }

}
