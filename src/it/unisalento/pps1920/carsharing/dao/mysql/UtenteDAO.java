package it.unisalento.pps1920.carsharing.dao.mysql;
import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.dao.interfaces.IUtenteDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.model.model_support.Recogniser;

import java.io.Serializable;
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
        u.setId(id);
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
            u.setId(Integer.parseInt(riga[0]));
            u.setUsername(riga[1]);
            u.setEmail(riga[3]);
            user.add(u);
        }
        return user;
    }
    @Override
    public boolean salvaRegistrazione(Cliente c, Utente user)
    {

        Utente verify_exist_user= new Utente();
        Utente verify_exist_email= new Utente();
        verify_exist_user=null;
        verify_exist_email=null;
               verify_exist_user =findByUsername(user.getUsername());
        verify_exist_email=findByEmail(user.getEmail());

        if(verify_exist_user==null && verify_exist_email==null)
        {
            String sql="INSERT INTO utente VALUES (NULL,'"+user.getUsername()+"','"+user.getPassword()+"','"+ user.getEmail()+"')";
            DbConnection.getInstance().eseguiAggiornamento(sql);
            String sql1="INSERT INTO cliente VALUES (NULL,'"+c.getNome()+"','"+c.getCognome()+"',"+c.getNum_tel()+",'"+c.getResidenza()+"',"+c.getEta()+","+ Arrays.toString(c.getFoto()) +")";
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

        ArrayList<String[]> res=DbConnection.getInstance().eseguiQuery("SELECT * FROM utente WHERE username='"+username+"';");
        if(res.isEmpty())
        {
            return null;
        }

        String[] riga=res.get(0);
        Utente u=new Utente();
        u.setId(Integer.parseInt(riga[0]));
        u.setUsername(riga[1]);
        u.setPassword(riga[2]);
        u.setEmail(riga[3]);

        return u;
    }
    @Override
    public Utente findByEmail(String email) {
        System.out.println("\n\n"+email);
        ArrayList<String[]> res1=DbConnection.getInstance().eseguiQuery("SELECT * FROM utente WHERE email='"+email+"';");

        if(res1.isEmpty())
        {
            return null;
        }

        String[] riga=res1.get(0);
        Utente u=new Utente();
        u.setId(Integer.parseInt(riga[0]));
        u.setUsername(riga[1]);
        u.setEmail(riga[3]);
        return u;
    }
    @Override
    public Recogniser checkIdpassw(Utente user)
    {
        Recogniser rec = new Recogniser();

        ArrayList<String[]>check_username_passw1= DbConnection.getInstance().eseguiQuery("SELECT idutente FROM utente INNER JOIN cliente WHERE utente.idutente=cliente.utente_idutente AND  username='"+user.getUsername()+"' AND password='"+user.getPassword()+"';");
        ArrayList<String[]>take_name1=DbConnection.getInstance().eseguiQuery("SELECT nome FROM cliente INNER JOIN utente WHERE utente.idutente=cliente.utente_idutente AND username='"+user.getUsername()+"';");
        if(!check_username_passw1.isEmpty())
        {
            String[] riga= check_username_passw1.get(0);
           rec.setId(Integer.parseInt(riga[0]));
            rec.setType("Cliente");
            riga = take_name1.get(0);
            rec.setNome(riga[0]);
            return rec;
        }

        ArrayList<String[]>check_username_passw2= DbConnection.getInstance().eseguiQuery("SELECT idutente FROM utente INNER JOIN addetto WHERE utente.idutente=addetto.utente_idutente AND  username='"+user.getUsername()+"' AND password='"+user.getPassword()+"';");
        ArrayList<String[]>take_name2=DbConnection.getInstance().eseguiQuery("SELECT nome FROM addetto INNER JOIN utente WHERE utente.idutente=addetto.utente_idutente AND username='"+user.getUsername()+"';");
        if(!check_username_passw2.isEmpty())
        {
            String[] riga= check_username_passw2.get(0);
            rec.setId(Integer.parseInt(riga[0]));
            rec.setType("Addetto");
            riga = take_name2.get(0);
            rec.setNome(riga[0]);
            return rec;
        }

        ArrayList<String[]>check_username_passw3= DbConnection.getInstance().eseguiQuery("SELECT idutente FROM utente INNER JOIN operatore WHERE utente.idutente=operatore.utente_idutente AND  username='"+user.getUsername()+"' AND password='"+user.getPassword()+"';");
        ArrayList<String[]>take_name3=DbConnection.getInstance().eseguiQuery("SELECT nome FROM operatore INNER JOIN utente WHERE utente.idutente=operatore.utente_idutente AND username='"+user.getUsername()+"';");
        if(!check_username_passw3.isEmpty())
        {
            String[] riga= check_username_passw3.get(0);
            rec.setId(Integer.parseInt(riga[0]));
            rec.setType("Operatore");
            riga = take_name3.get(0);
            rec.setNome(riga[0]);
            return rec;
        }

        ArrayList<String[]>check_username_passw4= DbConnection.getInstance().eseguiQuery("SELECT idutente FROM utente INNER JOIN amministratore WHERE utente.idutente=amministratore.utente_idutente AND  username='"+user.getUsername()+"' AND password='"+user.getPassword()+"';");
        ArrayList<String[]>take_name4=DbConnection.getInstance().eseguiQuery("SELECT nome FROM amministratore INNER JOIN utente WHERE utente.idutente=amministratore.utente_idutente AND username='"+user.getUsername()+"';");
        if(!check_username_passw4.isEmpty())
        {
            String[] riga= check_username_passw4.get(0);
            rec.setId(Integer.parseInt(riga[0]));
            rec.setType("Amministratore");
            riga = take_name4.get(0);
            rec.setNome(riga[0]);
            return rec;
        }
        rec.setId(0);
        return rec;
    }
}
