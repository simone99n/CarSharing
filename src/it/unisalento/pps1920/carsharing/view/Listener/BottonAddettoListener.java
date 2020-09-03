package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.view.FinestraAddetto;
import it.unisalento.pps1920.carsharing.view.Listener.SuccessMessages.AllSuccessMessages;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BottonAddettoListener implements ActionListener {
    private FinestraAddetto win;
    int idAddetto;
    String nomeAddetto;
    public static final String PULSANTE_VISUALIZZA_ACCESSORI_AUTOMEZZO = "PULSANTE_VISUALIZZA_ACCESSORI_AUTOMEZZO";
    public static final String PULSANTE_PRONTO = "PULSANTE_PRONTO";
    public static final String PULSANTE_NON_PRONTO = "PULSANTE_NON_PRONTO";

    public BottonAddettoListener(FinestraAddetto win,int idAddetto,String nomeAddetto) {

        this.win = win;
        this.idAddetto=idAddetto;
        this.nomeAddetto=nomeAddetto;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String command = e.getActionCommand();

        if (PULSANTE_VISUALIZZA_ACCESSORI_AUTOMEZZO.equals(command))
        {
            int id;
            id=Integer.parseInt(win.jt.getText());
            win.visualizzaAccessoriAutomezzo(id);

        }
        if (PULSANTE_PRONTO.equals(command))
        {
            int id= Integer.parseInt(win.jt.getText());
            PrenotazioneBusiness.getInstance().automezzoPronto(id);
            win.dispose();
            FinestraAddetto ad= new FinestraAddetto(idAddetto,nomeAddetto);
            AllSuccessMessages al= new AllSuccessMessages(1);

        }
        if (PULSANTE_NON_PRONTO.equals(command))
        {
            win.menu();

        }

    }
}
