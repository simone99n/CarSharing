package it.unisalento.pps1920.carsharing;

import it.unisalento.pps1920.carsharing.business.LoginBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IClienteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.ClienteDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.UtenteDAO;
import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.model.model_support.Recogniser;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewTest {

    public static void main(String args[]) {
      Utente user= new Utente();
        //FinestraOperatore op= new FinestraOperatore(1,"samuele");
        //FinestraRegistrazione reg = new FinestraRegistrazione();
       //FinestraLogin log = new FinestraLogin();
      //FinestraOperatore op = new FinestraOperatore(1,"samuele");
        FinestraLogin log= new FinestraLogin();
        //FinestraAmministratore am= new FinestraAmministratore(1,"diego");
       // FinestraRegistrazione reg= new FinestraRegistrazione();
        UtenteDAO ut=new UtenteDAO();
        //reg.setVisible(true);
      /* LoginBusiness log = new LoginBusiness();
        Utente u = new Utente();
        u.setUsername("samu");
        u.setPassword("fuck");
        Recogniser type= log.checkLogin(u);
        System.out.println(""+type.getId());
        System.out.println(""+type.getType()); */

        //PrimaFinestra win = new PrimaFinestra();
        //PrimaFinestraGridLayout win = new PrimaFinestraGridLayout();
        //PrimaFinestraBorderLayout win = new PrimaFinestraBorderLayout();

        //FinestraConGerarchia win = new FinestraConGerarchia();  //creazione finestra
        //win.setVisible(true);                                   //rendere visibile finestra

        IClienteDAO iDAO = new ClienteDAO();
        Cliente clienteLoggato = iDAO.findById(3);
        Session.getInstance().inserisci(Session.UTENTE_LOGGATO, clienteLoggato);


    }
}
