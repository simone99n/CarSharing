package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.RegistrazioneBusiness;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.view.FinestraCliente;
import it.unisalento.pps1920.carsharing.view.FinestraLogin;
import it.unisalento.pps1920.carsharing.view.FinestraRegistrazione;
import it.unisalento.pps1920.carsharing.view.Listener.BottonErrorListener.AllErrorMessages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BottonRegListener implements ActionListener {
    Utente user= new Utente();
    Cliente cli=new Cliente();
    private FinestraRegistrazione win;
    public static final String PULSANTE_REGISTRATI = "PULSANTE_REGISTRATI";
    public static final String PULSANTE_ANNULLA = "PULSANTE_ANNULLA";
    public BottonRegListener(FinestraRegistrazione win) {
        this.win=win;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        String command = e.getActionCommand();
        if(PULSANTE_REGISTRATI.equals(command)) {
            int state;
            state=salvaRegistrazione();
            if(state==3) {
                win.dispose();
                FinestraLogin login = new FinestraLogin();
                login.setVisible(true);
            }
            else if(state==-1) {
                int tipo=4;
                AllErrorMessages u= new AllErrorMessages(tipo);
            }
            else if(state==-2) {
                int tipo=5;
                AllErrorMessages u= new AllErrorMessages(tipo);
            }


        }

        if(PULSANTE_ANNULLA.equals(command))
            win.dispose();

    }

    private int salvaRegistrazione() {
        int state;
        RegistrazioneBusiness reg = new RegistrazioneBusiness();
        cli.setNome(win.nome.getText());
        cli.setCognome(win.cognome.getText());
        cli.setAnnoNascita(Integer.parseInt(win.eta.getText()));
        cli.setTelefono(win.telefono.getText());
        cli.setResidenza(win.residenza.getText());
        user.setUsername(win.username.getText());
        user.setPassword(win.password.getText());
        user.setEmail(win.email.getText());
        state=reg.inviaRegistrazione(cli,user);
        return state;

    }
}