package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.view.FinestraCliente;
import it.unisalento.pps1920.carsharing.view.FinestraHomePage;
import it.unisalento.pps1920.carsharing.view.FinestraLogin;
import it.unisalento.pps1920.carsharing.view.FinestraRegistrazione;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePageListener implements ActionListener {

    public static final String PULSANTE_CAMBIA_INFO = "PULSANTE_CAMBIA_INFO";
    public static final String PULSANTE_LOGIN = "PULSANTE_LOGIN";
    public static final String PULSANTE_REGISTRATI = "PULSANTE_REGISTRATI";

    private FinestraHomePage home;

    public HomePageListener(FinestraHomePage home) {
        this.home = home;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //JButton button = (JButton)e.getSource();
        String command = e.getActionCommand();

        if(PULSANTE_CAMBIA_INFO.equals(command)){
            home.setupInfo();
        }
        else if(PULSANTE_LOGIN.equals(command)){
            home.dispose();
            FinestraLogin login = new FinestraLogin();
            login.setVisible(true);
        }
        else if(PULSANTE_REGISTRATI.equals(command)){
            home.dispose();
            FinestraRegistrazione registrazione = new FinestraRegistrazione();
            registrazione.setVisible(true);
        }
    }
}
