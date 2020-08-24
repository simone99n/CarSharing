package it.unisalento.pps1920.carsharing.view;

import javax.swing.*;
import java.awt.*;

public class PrimaFinestraBorderLayout extends JFrame {

    private JLabel label;

    public PrimaFinestraBorderLayout() {
        super("Prima Finestra");
        label = new JLabel("Buona lezione");

        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(label,BorderLayout.NORTH);
        c.add(new JButton("Click me!"), BorderLayout.SOUTH);
        c.add(new JTextField(), BorderLayout.CENTER);

        setSize(800,600);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}
