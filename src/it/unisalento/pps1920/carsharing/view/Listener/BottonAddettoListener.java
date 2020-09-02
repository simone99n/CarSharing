package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.view.FinestraAddetto;
import it.unisalento.pps1920.carsharing.view.FinestraAmministratore;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BottonAddettoListener implements ActionListener {
    private FinestraAddetto win;
    public static final String PULSANTE_VISUALIZZA_AUTOMEZZO = "PULSANTE_VISUALIZZA_AUTOMEZZO";

    public BottonAddettoListener(FinestraAddetto win) {
        this.win = win;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String command = e.getActionCommand();

        if (PULSANTE_VISUALIZZA_AUTOMEZZO.equals(command))
        {
            int id;
            id=Integer.parseInt(win.jt.getText());
            win.visualizzaAutomezzo(id);

        }



    }
}