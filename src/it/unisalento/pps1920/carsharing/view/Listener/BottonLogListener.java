package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.LoginBusiness;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.model.model_support.Recogniser;
import it.unisalento.pps1920.carsharing.view.*;
import it.unisalento.pps1920.carsharing.view.Listener.BottonErrorListener.AllErrorMessages;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BottonLogListener implements ActionListener
{
    private FinestraLogin win;
    public static final String PULSANTE_ACCEDI = "PULSANTE_ACCEDI";
    public static final String PULSANTE_REGISTRATI = "PULSANTE_REGISTRATI";
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
            if(rec.getId()==0)
            {
                int tipo=1;
                AllErrorMessages u= new AllErrorMessages(tipo);
            }
            else
            {
                win.dispose();
                switch (rec.getType()) {
                    case "Operatore":
                        FinestraOperatore fop = new FinestraOperatore(rec.getId(), rec.getNome());
                        break;
                    case "Cliente":

                        FinestraCliente fcl = new FinestraCliente(rec.getId(), rec.getNome());
                        break;
                    case "Addetto":
                        FinestraAddetto fad = new FinestraAddetto(rec.getId(), rec.getNome());

                        break;
                    case "Amministratore":
                        FinestraAmministratore fam = new FinestraAmministratore(rec.getId(), rec.getNome());
                        break;
                }
            }
        }

        else if(PULSANTE_REGISTRATI.equals(command)) {
            win.dispose();
            FinestraRegistrazione reg= new FinestraRegistrazione();
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
