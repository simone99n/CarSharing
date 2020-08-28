package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.LoginBusiness;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.model.model_support.Recogniser;
import it.unisalento.pps1920.carsharing.view.FinestraLogin;
import it.unisalento.pps1920.carsharing.view.FinestraRegistrazione;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BottonLogListener implements ActionListener
{
    private FinestraLogin win;
    public static final String PULSANTE_ACCEDI = "PULSANTE_ACCEDI";
    public static final String PULSANTE_ANNULLA = "PULSANTE_ANNULLA";
    public BottonLogListener(FinestraLogin win)
    {
        this.win=win;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton button = (JButton)e.getSource();
        String command = e.getActionCommand();
        if(PULSANTE_ACCEDI.equals(command))
        {
            Recogniser rec = new Recogniser();
            rec=accedi();
            if(rec==null){
                System.out.println("Username o password inseriti sono errati! \n Riprova oppure crea un nuovo account.");
            }
            else
            {
                System.out.println("Login Effettuato con successo! \n Il tuo id e': "+rec.getId());
                System.out.println("E sei un : "+rec.getType());
            }
        }

        else if(PULSANTE_ANNULLA.equals(command)) {
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    }
    private Recogniser accedi()
    {
        int state;
        Utente user = new Utente();
        user.setUsername(win.username.getText());
        user.setPassword(win.password.getText());
        LoginBusiness log = new LoginBusiness();
        Recogniser rec = new Recogniser();
        rec=log.checkLogin(user);
        return rec;
    }
}
