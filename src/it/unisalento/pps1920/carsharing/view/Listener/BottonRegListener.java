package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.LoginBusiness;
import it.unisalento.pps1920.carsharing.business.RegistrazioneBusiness;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.model.model_support.Recogniser;
import it.unisalento.pps1920.carsharing.view.FinestraRegistrazione;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BottonRegListener implements ActionListener {

    private FinestraRegistrazione win;
    public static final String PULSANTE_REGISTRATI = "PULSANTE_REGISTRATI";
    public static final String PULSANTE_ANNULLA = "PULSANTE_ANNULLA";
    public BottonRegListener(FinestraRegistrazione win)
    {
        this.win=win;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton button = (JButton)e.getSource();
        String command = e.getActionCommand();
        if(PULSANTE_REGISTRATI.equals(command))
        {
            boolean state;
            state=salvaRegistrazione();
        }

        else if(PULSANTE_ANNULLA.equals(command)) {
            win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

    }

    private boolean salvaRegistrazione()
    {

       boolean state;
        RegistrazioneBusiness reg = new RegistrazioneBusiness();
        Utente user= new Utente();
        Cliente cli=new Cliente();
        cli.setNome(win.nome.getText());
        cli.setCognome(win.cognome.getText());
        cli.setEta(Integer.parseInt(win.eta.getText()));
        cli.setNum_tel(win.telefono.getText());
        cli.setResidenza(win.residenza.getText());
        user.setUsername(win.username.getText());
        user.setPassword(win.password.getText());
        user.setEmail(win.email.getText());
        state=reg.inviaRegistrazione(cli,user);
        return state;

    }
}
