package it.unisalento.pps1920.carsharing.business;
import it.unisalento.pps1920.carsharing.dao.mysql.UtenteDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
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

    public int inviaRegistrazione(Cliente c, Utente user)
    {
        UtenteDAO u=new UtenteDAO();
       int state = u.salvaRegistrazione(c,user);
    if(state==1)
    {
        String dest = user.getEmail();
        MailHelper.getInstance().send(dest, "Conferma Registrazione", "La registrazione da te effettuata il giorno "+GregorianCalendar.getInstance().getTime()+" è stata effettuata con successo! \n In seguito ti forniremo  un riepilogo complessivo dei dati a te associati: \n Nome: "+c.getNome()+" \n Cognome: "+c.getCognome()+" \n Username: "+user.getUsername()+"\n Password: "+user.getPassword()+"\n Email: "+user.getEmail()+"\n Informazioni secondarie:\n Residenza: "+c.getResidenza()+"\n Età: "+c.getEta()+"\n Numero Tel/cell : "+c.getTelefono()+" \n\n GRAZIE PER ESSERTI REGISTRATO, BUONA GUIDA! ");
        state=3;
    }
        return state;
    }
}
