package it.unisalento.pps1920.carsharing.view;

import javax.swing.*;
import java.awt.*;

public class PrimaFinestraGridLayout extends JFrame {

    private JLabel label;

    public PrimaFinestraGridLayout() {
        super("Prima Finestra");
        label = new JLabel("Buona lezione");

        Container c = getContentPane();
        c.setLayout(new GridLayout(4,4));
        c.add(label);
        for(int i=0;i<12;i++)
            c.add(new JButton("Click me! "+i));

        setSize(200,200);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}
