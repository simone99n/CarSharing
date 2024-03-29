package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.LoginBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAddettoDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IAmministratoreDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;
import it.unisalento.pps1920.carsharing.dao.interfaces.IOperatoreDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AddettoDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.AmministratoreDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.ClienteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.OperatoreDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.model.model_support.Recogniser;
import it.unisalento.pps1920.carsharing.util.Session;
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
                        IOperatoreDAO ooop=new OperatoreDAO();
                        Operatore operatoreLoggato = ooop.findById(rec.getId());
                        Session.getInstance().inserisci(Session.UTENTE_LOGGATO, operatoreLoggato);
                        FinestraOperatore fop = new FinestraOperatore(rec.getId(), rec.getNome());
                        break;
                    case "Cliente":
                        IClienteDAO iDAO = new ClienteDAO();
                        Cliente clienteLoggato = iDAO.findById(rec.getId());
                        Session.getInstance().inserisci(Session.UTENTE_LOGGATO, clienteLoggato);
                        FinestraCliente fcl = new FinestraCliente();
                        fcl.setVisible(true);
                        break;
                    case "Addetto":
                        IAddettoDAO adDao = new AddettoDAO();
                        Addetto addettoLoggato = adDao.findById(rec.getId());
                        Session.getInstance().inserisci(Session.UTENTE_LOGGATO, addettoLoggato);
                        FinestraAddetto fad = new FinestraAddetto(rec.getId(), rec.getNome());
                        break;
                    case "Amministratore":
                        IAmministratoreDAO aDAO = new AmministratoreDAO();
                        Amministratore amministratoreLoggato = aDAO.findById(rec.getId());
                        Session.getInstance().inserisci(Session.UTENTE_LOGGATO, amministratoreLoggato);
                        FinestraAmministratore fam = new FinestraAmministratore(rec.getId(), rec.getNome());
                        break;
                }
            }
        }

        else if(PULSANTE_REGISTRATI.equals(command)) {
            win.dispose();
            FinestraRegistrazione reg= new FinestraRegistrazione();
        }
        else if(PULSANTE_ANNULLA.equals(command)){
            win.dispose();
            FinestraHomePage home = new FinestraHomePage();
            home.setVisible(true);
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