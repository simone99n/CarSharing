package it.unisalento.pps1920.carsharing.view;

import javax.swing.*;

public class FinestraAmministratore
{
    public FinestraAmministratore(int id,String nome)
    {
        JFrame jf = new JFrame("AMMINISTRATORE :" + nome.toUpperCase());
        jf.setSize(900,900);
        jf.setVisible(true);
    }

}
