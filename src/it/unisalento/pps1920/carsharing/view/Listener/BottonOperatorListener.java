package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.view.FinestraLogin;
import it.unisalento.pps1920.carsharing.view.FinestraOperatore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BottonOperatorListener implements ActionListener {
    private FinestraOperatore win;
    public static final String PULSANTE_ESCI = "PULSANTE_ESCI";
    public static final String PULSANTE_SEGNALAZIONI = "PULSANTE_SEGNALAZIONI";

    public BottonOperatorListener(FinestraOperatore win)
    {
        this.win=win;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton button = (JButton)e.getSource();
        String command = e.getActionCommand();
        if(PULSANTE_ESCI.equals(command))
        {
            win.dispose();
        }
    }
}
