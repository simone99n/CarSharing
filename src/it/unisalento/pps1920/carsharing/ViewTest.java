package it.unisalento.pps1920.carsharing;

import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.ClienteDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.*;

public class ViewTest {

    public static void main(String args[]) {

        //PrimaFinestra win = new PrimaFinestra();
        //PrimaFinestraGridLayout win = new PrimaFinestraGridLayout();
        //PrimaFinestraBorderLayout win = new PrimaFinestraBorderLayout();


        IClienteDAO iDAO = new ClienteDAO();
        Cliente clienteLoggato = iDAO.findById(7);
        Session.getInstance().inserisci(Session.UTENTE_LOGGATO, clienteLoggato);

        FinestraCliente win = new FinestraCliente();  //creazione finestra
        win.setVisible(true);                                   //rendere visibile finestra
/*
        ConfirmSharing win2 = new ConfirmSharing();
        win2.setVisible(true);
*/


/*
        IClienteDAO cliente = new ClienteDAO();
        System.out.println(cliente.findById(2).getNome());
     */
    }
}
