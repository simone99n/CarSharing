package it.unisalento.pps1920.carsharing.view.Listener;

import it.unisalento.pps1920.carsharing.view.FinestraCliente;
import it.unisalento.pps1920.carsharing.view.FinestraFotoMezzo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImageChangeListener implements ActionListener{

    public static final String MODIFICA_FOTO = "MODIFICA_FOTO";

    private FinestraCliente win;

    public ImageChangeListener(FinestraCliente win) {
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
