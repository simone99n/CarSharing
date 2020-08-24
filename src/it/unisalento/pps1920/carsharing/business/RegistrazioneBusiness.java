package it.unisalento.pps1920.carsharing.business;
import it.unisalento.pps1920.carsharing.dao.mysql.UtenteDAO;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.util.MailHelper;

import java.util.GregorianCalendar;

public class RegistrazioneBusiness
{
    private static RegistrazioneBusiness instance;
    public static synchronized RegistrazioneBusiness getInstance()
    {
        if(instance==null)
        instance=new RegistrazioneBusiness();
            return instance;
    }

    public boolean inviaRegistrazione(Cliente c,Utente user)
    {
        UtenteDAO u=new UtenteDAO();
        boolean state = u.salvaRegistrazione(c,user);
    if(state)
    {
        String dest = user.getEmail();
        MailHelper.getInstance().send(dest, "Conferma Registrazione", "La registrazione da te effettuata il giorno "+GregorianCalendar.getInstance().getTime()+" è stata effettuata con successo! \\n In seguito ti forniremo  un riepilogo complessivo dei dati a te associati: \n Username: "+user.getUsername()+"\n Password: "+user.getPassword()+"\n Email: "+user.getEmail()+"\n\n\n");
    }
        return state;
    }
}
