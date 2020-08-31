package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.model.Modello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FinestraFotoMezzo extends JFrame{

    public FinestraFotoMezzo(ArrayList<Modello> modelli, int index){
        super("Foto veicolo");

        setSize(1300, 1000);

        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        System.out.println(index);

        Container c=getContentPane();
        c.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        JLabel foto = new JLabel(new ImageIcon(modelli.get(index).getFoto()));
        panel.add(foto);
        c.add(panel);

     //   Dimension sizeFoto = panel.getSize();
     //   System.out.println("<<w<<"+sizeFoto.width);
     //  System.out.println("<<h<<"+sizeFoto.height);
     //   setSize(sizeFoto.width, sizeFoto.height);

        repaint();
        revalidate();
    }
}
