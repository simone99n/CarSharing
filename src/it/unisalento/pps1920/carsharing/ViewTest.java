package it.unisalento.pps1920.carsharing;

import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.ClienteDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.*;

import javax.swing.*;
import java.awt.*;

public class ViewTest {

    public static void main(String args[]) {

        //PrimaFinestra win = new PrimaFinestra();
        //PrimaFinestraGridLayout win = new PrimaFinestraGridLayout();
        //PrimaFinestraBorderLayout win = new PrimaFinestraBorderLayout();

        FinestraConGerarchia win = new FinestraConGerarchia();  //creazione finestra
        win.setVisible(true);                                   //rendere visibile finestra

        ConfirmSharing win2 = new ConfirmSharing();
        win2.setVisible(true);


        IClienteDAO iDAO = new ClienteDAO();
        Cliente clienteLoggato = iDAO.findById(7);
        Session.getInstance().inserisci(Session.UTENTE_LOGGATO, clienteLoggato);
    }
}
