package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IStazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.StazioneDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.view.Listener.BottonBarListener;
import it.unisalento.pps1920.carsharing.view.Listener.ImageChangeListener;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FinestraConGerarchia extends JFrame {

    JPanel nord, centro, sud;
    JPanel funzionalita1;
    JPanel pannelloConTabella;
    BottonBarListener listener;
    ImageChangeListener imageListener;

    public JPanel form;
    public JTextField dataInizio = new JTextField(20);
    public JTextField dataFine = new JTextField(20);
    public JTextField numPostiOccupati = new JTextField(2);
    public JComboBox<Stazione> partenza = new JComboBox<Stazione>();
    public JComboBox<Stazione> arrivo = new JComboBox<Stazione>();
    public JComboBox<Localita> localita = new JComboBox<Localita>();
    public JComboBox<Modello> modello = new JComboBox<Modello>();

    private JTextField testo;

    public FinestraConGerarchia() {

        super("Finestra con gerarchia");

        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        listener = new BottonBarListener(this);
        imageListener = new ImageChangeListener(this);

        Container c=getContentPane();

        c.setLayout(new BorderLayout());

        nord = new JPanel();
        centro = new JPanel();
        sud = new JPanel();
        pannelloConTabella = new JPanel();

        setupPannelloNuovaPrenotazione();

        c.add(nord, BorderLayout.NORTH);
        //c.add(new JScrollPane(centro), BorderLayout.CENTER);
        c.add(new JScrollPane(pannelloConTabella), BorderLayout.CENTER);
        c.add(sud, BorderLayout.SOUTH);

        setupPannelloConTabella();

        nord.setLayout(new FlowLayout());
        testo = new JTextField(20);
        JButton btn1 = new JButton("Nuova prenotazione");
        nord.add(new JLabel("Selezionare"));
        nord.add(testo);
        nord.add(btn1);

        btn1.addActionListener(listener);
        btn1.setActionCommand(BottonBarListener.PULSANTE_NUOVA_PRENOTAZONE);

        centro.setLayout(new GridLayout(50,1));
        //centro.add(new JCheckBox("Opzione 1"));
        for(int i=0;i<50;i++)
            centro.add(new JCheckBox("Opzione "+(i+1)));

        sud.setLayout(new FlowLayout());

        JButton ok = new JButton("OK");
        sud.add(ok);
        JButton annulla = new JButton("Annulla");
        sud.add(annulla);


        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu edit = new JMenu("Edit");
        JMenu move = new JMenu("Move");

        bar.add(file);
        bar.add(edit);
        edit.add(move);

        setJMenuBar(bar);

        JMenuItem random = new JMenuItem("Random");
        JMenuItem up = new JMenuItem("Up");
        JMenuItem down = new JMenuItem("Down");

        edit.add(random);
        move.add(up);
        move.add(down);

        ok.addActionListener(listener);
        ok.setActionCommand(BottonBarListener.PULSANTE_OK);
        annulla.addActionListener(listener);
        annulla.setActionCommand(BottonBarListener.PULSANTE_ANNULLA);

    }

    public void mostraPannelloNuovaPrenotazione() {
        //1. eliminare quello che c'è nell'area centrale
        BorderLayout bl = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));

        //2. inserire pannello della funzionalita specifica
        this.getContentPane().add(funzionalita1, BorderLayout.CENTER);

        //3. refresh della UI
        repaint();
        revalidate();
    }

    private void setupPannelloNuovaPrenotazione() {
        funzionalita1 = new JPanel();

        funzionalita1.setLayout(new BorderLayout());

        funzionalita1.add(new JLabel("Nuova prenotazione"), BorderLayout.NORTH);
        JButton avanti = new JButton("PRENOTA ORA!");
        avanti.addActionListener(listener);
        avanti.setActionCommand(BottonBarListener.PULSANTE_SALVA_PRENOTAZIONE);
        funzionalita1.add(avanti, BorderLayout.SOUTH);

        form = new JPanel();
        form.setLayout(new GridLayout(8,2));

        funzionalita1.add(form, BorderLayout.CENTER);


        form.add(new JLabel("Data inizio: [aaaa-MM-gg hh:mm]"));
        form.add(dataInizio);

        /*   Calendar calendar = Calendar.getInstance();
        form.add(calendar);
        Date date =  calendar.getTime();
        */

        form.add(new JLabel("Data fine: [aaaa-mm-gg hh:mm]"));
        form.add(dataFine);
        form.add(new JLabel("Numero di posti: "));
        form.add(numPostiOccupati);
        form.add(new JLabel("Stazione di partenza: "));
        form.add(partenza);
        form.add(new JLabel("Stazione di arrivo: "));
        form.add(arrivo);
        form.add(new JLabel("Località di visita: "));
        form.add(localita);
        form.add(new JLabel("Modello del mezzo: "));
        form.add(modello);
      /*  modello.addActionListener(listener);
        modello.setActionCommand(BottonBarListener.MODIFICA_FOTO); */

        ArrayList<Stazione> stazioni = PrenotazioneBusiness.getInstance().getStazioni();
        ArrayList<Localita> localitas = PrenotazioneBusiness.getInstance().getLocalita();
        ArrayList<Modello> modelli = PrenotazioneBusiness.getInstance().getModelli();

        for(Stazione s : stazioni) partenza.addItem(s);
        for(Stazione s : stazioni) arrivo.addItem(s);
        for(Localita l : localitas) localita.addItem(l);
        for(Modello m : modelli) modello.addItem(m);

        form.add(new JLabel("Foto del mezzo: "));

        JLabel foto = new JLabel(new ImageIcon(modelli.get(modello.getSelectedIndex()).getFoto())); //TODO sistemare visualizzazioni immagini
        form.add(foto);

        modello.addActionListener(imageListener);
        modello.setActionCommand(ImageChangeListener.MODIFICA_FOTO);

    }

    public void setupImage() {
        form.remove(15);
        ArrayList<Modello> modelli = PrenotazioneBusiness.getInstance().getModelli();
        JLabel foto = new JLabel(new ImageIcon(modelli.get(modello.getSelectedIndex()).getFoto()));
        form.add(foto);
        repaint();
        revalidate();
    }


    private void setupPannelloConTabella() {

        String[][] data = new String[10][3];
        for(int i=0;i<10;i++)
            for(int j=0;j<3;j++)
                data[i][j]=i+" "+j;

        JTable tabella = new JTable(data, new String[]{"col1","col2","col3"});

        ArrayList<Prenotazione> prenotazioni = new PrenotazioneDAO().findAll();
        TableModelPrenotazioni tmp = new TableModelPrenotazioni(prenotazioni);
        JTable tabellaPrenotazioni = new JTable(tmp);

        pannelloConTabella.add(tabellaPrenotazioni);
    }

    public String getTesto() {
        return testo.getText();
    }

    class MioAscoltatore implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
