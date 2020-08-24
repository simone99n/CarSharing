package it.unisalento.pps1920.carsharing.dao.mysql;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IUtenteDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.util.ArrayList;
import java.util.Arrays;

public class UtenteDAO implements IUtenteDAO
{


    @Override
    public Utente findById(int id) {
        Utente u=null;
        ArrayList<String[]>res= DbConnection.getInstance().eseguiQuery("SELECT * FROM utente WHERE idutente="+id+";");
        String[] riga=res.get(0);
        u=new Utente();
        u.setIdutente(id);
        u.setUsername(riga[1]);
        u.setEmail(riga[3]);
        return u;
    }

    @Override
    public ArrayList<Utente> findAll() {
        ArrayList<String[]>res=DbConnection.getInstance().eseguiQuery("SELECT * FROM utente");
        ArrayList<Utente>user =new ArrayList<Utente>();
        for (String[] riga : res)
        {
            Utente u=new Utente();
            u.setIdutente(Integer.parseInt(riga[0]));
            u.setUsername(riga[1]);
            u.setEmail(riga[3]);
            user.add(u);
        }
        return user;
    }

    public boolean salvaRegistrazione(Cliente c,Utente user)
    {

        Utente verify_exist_user=findByUsername(user.getUsername());
        Utente verify_exist_email=findByEmail(user.getEmail());
        if(verify_exist_user==null && verify_exist_email==null)
        {
            String sql="INSERT INTO utente VALUES (NULL,"+user.getUsername()+","+user.getPassword()+","+ user.getEmail()+")";
            DbConnection.getInstance().eseguiAggiornamento(sql);
            String sql1="INSERT INTO cliente VALUES (NULL,"+c.getNome()+","+c.getCognome()+","+c.getResidenza().getId_residenza()+","+c.getEta()+","+c.getNum_tel()+","+ Arrays.toString(c.getFoto()) +")";
            DbConnection.getInstance().eseguiAggiornamento(sql1);
            return true;
        }
        if(verify_exist_email!=null)
        {
            System.out.println("Email gia' in uso, scegliere un'altra email");
            return false;
        }
        if(verify_exist_user!=null)
        {
            System.out.println("Username gia' in uso, scegliere un altro username");
            return false;
        }
           return false;
        }


    @Override
    public Utente findByUsername(String username) {
        ArrayList<String[]> res=DbConnection.getInstance().eseguiQuery("SELECT * FROM utente WHERE username="+username+";");
        if(res==null)
            return null;
        String[] riga=res.get(0);
        Utente u=new Utente();
        u.setIdutente(Integer.parseInt(riga[0]));
        u.setUsername(riga[1]);
        u.setEmail(riga[3]);
        return u;
    }

    public Utente findByEmail(String email) {
        ArrayList<String[]> res=DbConnection.getInstance().eseguiQuery("SELECT * FROM utente WHERE email="+email+";");
        if(res==null)
            return null;
        String[] riga=res.get(0);
        Utente u=new Utente();
        u.setIdutente(Integer.parseInt(riga[0]));
        u.setUsername(riga[1]);
        u.setEmail(riga[3]);
        return u;
    }
}
