package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.mysql.UtenteDAO;
import it.unisalento.pps1920.carsharing.model.Utente;

public class LoginBusiness
{
    public int checkLogin(Utente user)
    {
        UtenteDAO uDao = new UtenteDAO();
        int state_id;
       state_id=uDao.checkIdpassw(user);
        if(state_id > 0)
            return state_id;
        else
            return -1;
    }
}
