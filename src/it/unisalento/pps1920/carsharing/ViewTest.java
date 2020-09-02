package it.unisalento.pps1920.carsharing;

import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.ClienteDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.*;

public class ViewTest {

    public static void main(String args[]) {
/*
        IClienteDAO iDAO = new ClienteDAO();
        Cliente clienteLoggato = iDAO.findById(1);
        Session.getInstance().inserisci(Session.UTENTE_LOGGATO, clienteLoggato);

 */


/*
         FinestraCliente win = new FinestraCliente();  
         win.setVisible(true);
*/


        FinestraHomePage home = new FinestraHomePage();
        home.setVisible(true);

    }
}
