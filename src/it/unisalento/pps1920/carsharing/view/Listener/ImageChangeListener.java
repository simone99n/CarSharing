package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.view.FinestraConGerarchia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ImageChangeListener implements ActionListener{

    public static final String MODIFICA_FOTO = "MODIFICA_FOTO";

    private FinestraConGerarchia win;

    public ImageChangeListener(FinestraConGerarchia win) {
        this.win = win;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(MODIFICA_FOTO.equals(command)){
            win.setupImage();
        }
    }
}
