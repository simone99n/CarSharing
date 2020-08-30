package it.unisalento.pps1920.carsharing.view.Listener.ConfirmButtonListener;

import it.unisalento.pps1920.carsharing.view.FinestraSuccessLogin;
import it.unisalento.pps1920.carsharing.view.Listener.BottonLogListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OkLoginListener  implements ActionListener
{
    private FinestraSuccessLogin win;
    public static final String PULSANTE_OK = "PULSANTE_OK";
    public OkLoginListener(FinestraSuccessLogin win)
    {
        this.win=win;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        JButton button = (JButton)e.getSource();
        String command = e.getActionCommand();
        if(PULSANTE_OK.equals(command))
        {
            win.dispose();
        }

    }
}
