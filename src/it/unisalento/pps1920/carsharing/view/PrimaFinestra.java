package it.unisalento.pps1920.carsharing.view;

import javax.swing.*;
import java.awt.*;

public class PrimaFinestra extends JFrame {

    private JLabel label;

    public PrimaFinestra() {
        super("Prima Finestra");
        label = new JLabel("Buona lezione");

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        c.add(label);
        c.add(new JButton("Click me!"));

        setSize(200,200);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}
