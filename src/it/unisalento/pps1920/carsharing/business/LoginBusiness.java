package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.mysql.UtenteDAO;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.model.model_support.Recogniser;
import it.unisalento.pps1920.carsharing.view.Listener.BottonBarListener;

import java.util.ArrayList;
import java.util.Arrays;

public class LoginBusiness
{
    public Recogniser checkLogin(Utente user)
    {
        UtenteDAO uDao = new UtenteDAO();
        Recogniser state_id= new Recogniser();
        state_id=uDao.checkIdpassw(user);
        return state_id;
    }
}