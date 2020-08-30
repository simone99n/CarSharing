package it.unisalento.pps1920.carsharing.view;

import javax.swing.*;

public class FinestraCliente
{
    public FinestraCliente(int id, String nome)
    {
        JFrame jf = new JFrame("CLIENTE : " + nome.toUpperCase());
        jf.setSize(900,900);
        jf.setVisible(true);
    }
}
