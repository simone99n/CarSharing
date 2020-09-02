package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.HomePageBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.view.Listener.HomePageListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

public class FinestraHomePage extends JFrame {

    public JComboBox<Modello> modelloComboBox = new JComboBox<Modello>();
    HomePageListener listener;
    JPanel nord,centro,sud,principale;
    JPanel nord_1, nord_2, form;
    JButton loginButton, registrazioneButton;
    ArrayList<Modello> modelli, modelliMenoCostosi;


    public FinestraHomePage(){

        super("CarSharing");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200,800);
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        setResizable(false);
        listener = new HomePageListener(this);

        Container c=getContentPane();
        c.setLayout(new BorderLayout());

        principale = new JPanel(new BorderLayout());
        c.add(principale, BorderLayout.CENTER);

        nord = new JPanel();
        centro = new JPanel();
        sud = new JPanel();

        principale.add(nord, BorderLayout.NORTH);
        principale.add(centro, BorderLayout.CENTER);
        principale.add(sud, BorderLayout.SOUTH);

        nord_1 = new JPanel(new BorderLayout());
        nord_2 = new JPanel(new FlowLayout());
        nord.add(nord_1);
        nord.add(nord_2);

        loginButton = new JButton("LOGIN");
        registrazioneButton = new JButton("REGISTRATI ORA!");

        nord_1.add(new JLabel("CarSharing PPS  è un sistema che permette ai suoi clienti di noleggiare automezzi online con opzione di \"car sharing\" "), BorderLayout.NORTH);
        nord_1.add(new JLabel("Realizzato da Norberti Simone e Longo Samuele"), BorderLayout.SOUTH);
        nord_2.add(loginButton);
        nord_2.add(registrazioneButton);
        loginButton.addActionListener(listener);
        registrazioneButton.addActionListener(listener);
        loginButton.setActionCommand(HomePageListener.PULSANTE_LOGIN);
        registrazioneButton.setActionCommand(HomePageListener.PULSANTE_REGISTRATI);

        modelli = PrenotazioneBusiness.getInstance().getModelli();
        for(Modello m : modelli) modelloComboBox.addItem(m);

        modelloComboBox.addActionListener(listener);
        modelloComboBox.setActionCommand(HomePageListener.PULSANTE_CAMBIA_INFO);

        centro.setLayout(new GridLayout(6,4));

        centro.add(new JLabel(" "));
        centro.add(new JLabel("Veicolo: "));
        centro.add(modelloComboBox);
        centro.add(new JLabel(" "));

        centro.add(new JLabel(" "));
        centro.add(new JLabel("Prezzo: "));
        centro.add(new JLabel(String.valueOf(((Modello) Objects.requireNonNull(modelloComboBox.getSelectedItem())).getPrezzo())+" €/giorno"));
        centro.add(new JLabel(" "));

        centro.add(new JLabel(" "));
        centro.add(new JLabel("Numero posti: "));
        centro.add(new JLabel(String.valueOf(((Modello) Objects.requireNonNull(modelloComboBox.getSelectedItem())).getNumPosti())));
        centro.add(new JLabel(" "));

        centro.add(new JLabel(" "));
        centro.add(new JLabel("Tipologia: "));
        centro.add(new JLabel(String.valueOf(((Modello) Objects.requireNonNull(modelloComboBox.getSelectedItem())).getTipologia())));
        centro.add(new JLabel(" "));

        centro.add(new JLabel(" "));
        centro.add(new JLabel("Foto: "));
        JLabel foto = new JLabel(new ImageIcon(modelli.get(modelloComboBox.getSelectedIndex()).getFoto())); //TODO sistemare visualizzazioni immagini
        centro.add(foto);
        foto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    FinestraFotoMezzo fin = new FinestraFotoMezzo(modelli,modelloComboBox.getSelectedIndex());
                    fin.setVisible(true);
                }
            }
        });
        centro.add(new JLabel("     Clicca sull'immagine per ingrandirla!"));

        centro.add(new JLabel(" ")); centro.add(new JLabel(" ")); centro.add(new JLabel(" ")); centro.add(new JLabel(" "));


        modelliMenoCostosi = HomePageBusiness.getInstance().getModelliMenoCostosi();

        sud.setLayout(new BorderLayout());
        JPanel sud_nord = new JPanel();
        JPanel sud_sud = new JPanel();
        sud.add(sud_nord, BorderLayout.NORTH);
        sud.add(sud_sud, BorderLayout.SOUTH);

        JLabel offerte =new JLabel("----------------------------------------------------------------------------------------------------------------------------------" +
                "    OFFERTE SPECIALI    ----------------------------------------------------------------------------------------------------------------------------------");
        offerte.setForeground( Color.red );
        sud_nord.setLayout(new FlowLayout());
        sud_nord.add(offerte);

        sud_sud.setLayout(new GridLayout(5,5));

        sud_sud.add(new JLabel(" "));
        sud_sud.add(new JLabel(" "));
        sud_sud.add(new JLabel(" "));
        sud_sud.add(new JLabel(" "));
        sud_sud.add(new JLabel(" "));

        sud_sud.add(new JLabel(modelliMenoCostosi.get(0).getNome()));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(1).getNome()));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(2).getNome()));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(3).getNome()));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(4).getNome()));

        sud_sud.add(new JLabel(modelliMenoCostosi.get(0).getPrezzo() +" €/giorno"));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(1).getPrezzo() +" €/giorno"));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(2).getPrezzo() +" €/giorno"));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(3).getPrezzo() +" €/giorno"));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(4).getPrezzo() +" €/giorno"));

        sud_sud.add(new JLabel(modelliMenoCostosi.get(0).getNumPosti() +" posti"));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(1).getNumPosti() +" posti"));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(2).getNumPosti() +" posti"));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(3).getNumPosti() +" posti"));
        sud_sud.add(new JLabel(modelliMenoCostosi.get(4).getNumPosti() +" posti"));


        sud_sud.add(new JLabel(" "));
        sud_sud.add(new JLabel(" "));
        sud_sud.add(new JLabel(" "));
        sud_sud.add(new JLabel(" "));


        repaint();
        revalidate();
    }

    public void setupInfo() {
        centro.removeAll();

        centro.add(new JLabel(" "));
        centro.add(new JLabel("Veicolo: "));
        centro.add(modelloComboBox);
        centro.add(new JLabel(" "));

        centro.add(new JLabel(" "));
        centro.add(new JLabel("Prezzo: "));
        centro.add(new JLabel(String.valueOf(((Modello) Objects.requireNonNull(modelloComboBox.getSelectedItem())).getPrezzo())+" €/giorno"));
        centro.add(new JLabel(" "));

        centro.add(new JLabel(" "));
        centro.add(new JLabel("Numero posti: "));
        centro.add(new JLabel(String.valueOf(((Modello) Objects.requireNonNull(modelloComboBox.getSelectedItem())).getNumPosti())));
        centro.add(new JLabel(" "));

        centro.add(new JLabel(" "));
        centro.add(new JLabel("Tipologia: "));
        centro.add(new JLabel(String.valueOf(((Modello) Objects.requireNonNull(modelloComboBox.getSelectedItem())).getTipologia())));
        centro.add(new JLabel(" "));

        centro.add(new JLabel(" "));
        centro.add(new JLabel("Foto: "));
        JLabel foto = new JLabel(new ImageIcon(modelli.get(modelloComboBox.getSelectedIndex()).getFoto())); //TODO sistemare visualizzazioni immagini
        centro.add(foto);
        foto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    FinestraFotoMezzo fin = new FinestraFotoMezzo(modelli,modelloComboBox.getSelectedIndex());
                    fin.setVisible(true);
                }
            }
        });
        centro.add(new JLabel("     Clicca sull'immagine per ingrandirla!"));


        repaint();
        revalidate();
    }
}
