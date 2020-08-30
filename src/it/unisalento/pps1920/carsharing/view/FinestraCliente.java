package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.ModificaPrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.IStazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.StazioneDAO;
import it.unisalento.pps1920.carsharing.model.*;
import it.unisalento.pps1920.carsharing.util.Session;
import it.unisalento.pps1920.carsharing.view.Listener.BottonBarListener;
import it.unisalento.pps1920.carsharing.view.Listener.ImageChangeListener;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.Flow;

public class FinestraCliente extends JFrame {

    JPanel nord, centro, sud;
    JPanel funzionalita1,funzionalitaAccessori;
    JPanel pannelloConTabella;
    BottonBarListener listener;
    ImageChangeListener imageListener;

    public JPanel form;
    public JTextField dataInizio = new JTextField(20);
    public JTextField dataFine = new JTextField(20);
    public JTextField dataInizioMod = new JTextField(20);
    public JTextField dataFineMod = new JTextField(20);
    public JTextField numPostiOccupati = new JTextField(2);
    public JComboBox<Stazione> partenza = new JComboBox<Stazione>();
    public JComboBox<Stazione> arrivo = new JComboBox<Stazione>();
    public JComboBox<Localita> localita = new JComboBox<Localita>();
    public JComboBox<Modello> modello = new JComboBox<Modello>();
    public JComboBox<Stazione> partenzaMod = new JComboBox<Stazione>();
    public JComboBox<Stazione> arrivoMod = new JComboBox<Stazione>();
    public JComboBox<Accessorio> accessorio1 = new JComboBox<Accessorio>();
    public JComboBox<Accessorio> accessorio2 = new JComboBox<Accessorio>();
    public JComboBox<Accessorio> accessorio3 = new JComboBox<Accessorio>();
    public JComboBox<Accessorio> accessorio4 = new JComboBox<Accessorio>();
    public JComboBox<Accessorio> accessorio5 = new JComboBox<Accessorio>();
 /*   private Cliente clienteLoggato = (Cliente) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
    private String nomeCliente = clienteLoggato.getNome();
    private String cognCliente = clienteLoggato.getCognome();

  */

    private JTextField testo;

    public FinestraCliente() {
        super("CARSHARING: Cliente ");

        setSize(900,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        listener = new BottonBarListener(this);
        imageListener = new ImageChangeListener(this);

        Container c=getContentPane();
        c.setLayout(new BorderLayout());

        nord = new JPanel();
        centro = new JPanel();
        sud = new JPanel();
        pannelloConTabella = new JPanel(new BorderLayout());

        setupPannelloNuovaPrenotazione();

        c.add(nord, BorderLayout.NORTH);
        c.add(new JScrollPane(pannelloConTabella), BorderLayout.CENTER);
        c.add(sud, BorderLayout.SOUTH);

        setupPannelloConTabella();

        nord.setLayout(new FlowLayout());
        testo = new JTextField(20);
        //nord.add(testo);
        JButton btn1 = new JButton("Nuova prenotazione");
        //JButton btn2 = new JButton("Modifica una prenotazione");
        nord.add(new JLabel("<<<ELENCO PRENOTAZIONI EFFETTUATE>>>"));

        centro.setLayout(new GridLayout(50,1));
        //centro.add(new JCheckBox("Opzione 1"));
        for(int i=0;i<50;i++)
            centro.add(new JCheckBox("Opzione "+(i+1)));

        sud.setLayout(new FlowLayout());
        //JButton ok = new JButton("OK");
        //JButton annulla = new JButton("Annulla");
        sud.add(btn1);
        //sud.add(btn2);
        //sud.add(annulla);

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

        btn1.addActionListener(listener);
        btn1.setActionCommand(BottonBarListener.PULSANTE_NUOVA_PRENOTAZONE);
        //btn2.addActionListener(listener);
        //btn2.setActionCommand(BottonBarListener.PULSANTE_MODIFICA);
        /*
        ok.addActionListener(listener);
        ok.setActionCommand(BottonBarListener.PULSANTE_OK);
        annulla.addActionListener(listener);
        annulla.setActionCommand(BottonBarListener.PULSANTE_ANNULLA);

         */
    }

    public void mostraModificaPrenotazione(int id){
        //1. eliminare quello che c'è nell'area centrale
        BorderLayout bl = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.NORTH));
        this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.SOUTH));

        //2. setup
        Prenotazione p = ModificaPrenotazioneBusiness.getInstance().getPrenotazione(id);

        ArrayList<Accessorio> acc = ModificaPrenotazioneBusiness.getInstance().getAccessori(id); //accessori della prenotazione selezionata

        JPanel cestino = new JPanel();
        JPanel sopra = new JPanel();
        JPanel meta = new JPanel();
        JPanel sotto = new JPanel();

        cestino.setLayout(new BorderLayout());
        cestino.add(sopra, BorderLayout.NORTH);
        cestino.add(meta, BorderLayout.CENTER);
        cestino.add(sotto, BorderLayout.SOUTH);

        sopra.setLayout(new FlowLayout());
        sotto.setLayout(new FlowLayout());
        meta.setLayout(new GridLayout(6+acc.size(),3));
        meta.add(new JLabel("ID prenotazione: "));
        meta.add(new JLabel(String.valueOf(id)));
        meta.add(new JLabel("       "));
        meta.add(new JLabel("Numero posti: "));
        meta.add(new JLabel(String.valueOf(p.getNumPostiOccupati())));
        meta.add(new JLabel("       "));
        meta.add(new JLabel("Stazione partenza: "));
        meta.add(partenzaMod);
        meta.add(new JLabel("       "));
        meta.add(new JLabel("Stazione arrivo: "));
        meta.add(arrivoMod);
        meta.add(new JLabel("       "));
        meta.add(new JLabel("Data inizio: [aaaa-MM-gg hh:mm]"));
        meta.add(dataInizioMod);
        meta.add(new JLabel("       "));
        meta.add(new JLabel("Data fine: [aaaa-MM-gg hh:mm]"));
        meta.add(dataFineMod);
        meta.add(new JLabel("       "));

        for(int i=0; i<acc.size();i++){
            meta.add(new JLabel("Accessorio "+(i+1)));
            meta.add(new JLabel(acc.get(i).getNome()));
            JPanel tmp = new JPanel();
            tmp.setLayout(new FlowLayout());

            meta.add(tmp);
            JButton butt = new JButton("X");
            tmp.add(butt, FlowLayout.LEFT);
            butt.addActionListener(listener);
            butt.setActionCommand(BottonBarListener.PULSANTE_CANCELLA_ACCESSORIO);

        }

        ArrayList<Stazione> stazioni = PrenotazioneBusiness.getInstance().getStazioni();
        for(Stazione s : stazioni) partenzaMod.addItem(s);
        for(Stazione s : stazioni) arrivoMod.addItem(s);

        JButton avanti = new JButton("SALVA MODIFICA");
        JButton indietro = new JButton("<- TORNA INDIETRO");
        sotto.add(avanti);
        sotto.add(indietro);
        sopra.add(new JLabel("<<<MODIFICA PRENOTAZIONE>>>"));
        avanti.addActionListener(listener);
        avanti.setActionCommand(BottonBarListener.PULSANTE_MODIFICA);
        indietro.addActionListener(listener);
        indietro.setActionCommand(BottonBarListener.PULSANTE_ANNULLA);

        this.getContentPane().add(sopra, BorderLayout.NORTH);
        this.getContentPane().add(sotto, BorderLayout.SOUTH);
        this.getContentPane().add(meta, BorderLayout.CENTER);

        //3. refresh della UI
        repaint();
        revalidate();
    }

    public void mostraPannelloNuovaPrenotazione() {
        //1. eliminare quello che c'è nell'area centrale
        BorderLayout bl = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.NORTH));
        this.getContentPane().remove(bl.getLayoutComponent(BorderLayout.SOUTH));

        //2. inserire pannello della funzionalita specifica
        this.getContentPane().add(funzionalita1, BorderLayout.CENTER);

        //3. refresh della UI
        repaint();
        revalidate();
    }

    public void setupPannelloNuovaPrenotazione() {
        funzionalita1 = new JPanel();
        JPanel spazioBottoni = new JPanel(new FlowLayout());
        JPanel sopra = new JPanel();
        sopra.setLayout(new FlowLayout());
        sopra.add(new JLabel("<<<FORM NUOVA PRENOTAZIONE>>>"));
        funzionalita1.setLayout(new BorderLayout());
        funzionalita1.add(sopra, BorderLayout.NORTH);

        JButton avanti = new JButton("PRENOTA ORA!");
        JButton indietro = new JButton("<- TORNA INDIETRO");
        avanti.addActionListener(listener);
        avanti.setActionCommand(BottonBarListener.PULSANTE_SALVA_PRENOTAZIONE);
        indietro.addActionListener(listener);
        indietro.setActionCommand(BottonBarListener.PULSANTE_ANNULLA);

        funzionalita1.add(spazioBottoni, BorderLayout.SOUTH);
        spazioBottoni.add(avanti);
        spazioBottoni.add(indietro);

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

    public void mostraAccessori() {
        //1. rimuovo schermata vecchia
        this.getContentPane().remove(funzionalita1);
        nord.removeAll();
        sud.removeAll();
        //2.setup nuova schermata
        nord.setLayout(new FlowLayout());
        JPanel pulsantiera = new JPanel(new FlowLayout());
        nord.add(new JLabel("<<<ACCESSORI DISPONIBILI: >>>"));

        JButton accessoriButton = new JButton("Aggiungi accessori");
        JButton noAccessoriButton = new JButton("Non aggiungere");
        accessoriButton.addActionListener(listener);
        noAccessoriButton.addActionListener(listener);
        accessoriButton.setActionCommand(BottonBarListener.PULSANTE_ACCESSORI);
        noAccessoriButton.setActionCommand(BottonBarListener.PULSANTE_NO_ACCESSORI);

        sud.add(accessoriButton);
        sud.add(noAccessoriButton);

        funzionalitaAccessori = new JPanel();

        ArrayList<Accessorio> accessori = PrenotazioneBusiness.getInstance().getAccessori();

        funzionalitaAccessori.setLayout(new GridLayout(5,2));

 /*       for(Accessorio a : accessori){
            funzionalitaAccessori.add(new JLabel(a.getNome()));
            funzionalitaAccessori.add(new JCheckBox());
            funzionalitaAccessori.add(new JLabel("  "));
            //funzionalitaAccessori.add(new JLabel());
        }

  */

        funzionalitaAccessori.add(new JLabel("Accessorio 1: "));
        funzionalitaAccessori.add(accessorio1);
        funzionalitaAccessori.add(new JLabel("Accessorio 2: "));
        funzionalitaAccessori.add(accessorio2);
        funzionalitaAccessori.add(new JLabel("Accessorio 3: "));
        funzionalitaAccessori.add(accessorio3);
        funzionalitaAccessori.add(new JLabel("Accessorio 4: "));
        funzionalitaAccessori.add(accessorio4);
        funzionalitaAccessori.add(new JLabel("Accessorio 5: "));
        funzionalitaAccessori.add(accessorio5);

        accessorio1.addItem(null);
        accessorio2.addItem(null);
        accessorio3.addItem(null);
        accessorio4.addItem(null);
        accessorio5.addItem(null);
        for(Accessorio a : accessori){
            accessorio1.addItem(a);
            accessorio2.addItem(a);
            accessorio3.addItem(a);
            accessorio4.addItem(a);
            accessorio5.addItem(a);
        }

        this.getContentPane().add(sud, BorderLayout.SOUTH);
        this.getContentPane().add(nord, BorderLayout.NORTH);
        this.getContentPane().add(funzionalitaAccessori, BorderLayout.CENTER);

        //3. Aggiorno UI
        repaint();
        revalidate();
    }

    public void setupImage() {
        form.remove(15);
        ArrayList<Modello> modelli = PrenotazioneBusiness.getInstance().getModelli();
        JLabel foto = new JLabel(new ImageIcon(modelli.get(modello.getSelectedIndex()).getFoto()));
        form.add(foto);
        repaint();
        revalidate();
    }

    public void setupPannelloConTabella() {

        String[][] data = new String[10][3];
        for(int i=0;i<10;i++)
            for(int j=0;j<3;j++)
                data[i][j]=i+" "+j;

        JTable tabella = new JTable(data, new String[]{"col1","col2","col3"});

        //ArrayList<Prenotazione> prenotazioni = new PrenotazioneDAO().findAll();
        ArrayList<Prenotazione> prenotazioni = new PrenotazioneDAO().findAllForCliente();
        TableModelPrenotazioni tmp = new TableModelPrenotazioni(prenotazioni);
        JTable tabellaPrenotazioni = new JTable(tmp);

        tabellaPrenotazioni.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    mostraModificaPrenotazione(Integer.parseInt(tabellaPrenotazioni.getValueAt(tabellaPrenotazioni.getSelectedRow(), 0).toString()));
                }
            }
        });


        //tabellaPrenotazioni.getValueAt(tabellaPrenotazioni.getSelectedRow(),0); //restiruisce l'id della prenotazione effettuata

        JTable intestazione = new JTable(1,7);
        intestazione.setValueAt("ID PRENOTAZIONE",0,0);
        intestazione.setValueAt("PARTENZA",0,1);
        intestazione.setValueAt("ARRIVO",0,2);
        intestazione.setValueAt("LOCALITA'",0,3);
        intestazione.setValueAt("POSTI OCCUPATI",0,4);
        intestazione.setValueAt("DATA/ORA INZIO",0,5);
        intestazione.setValueAt("DATA/ORA FINE",0,6);

        pannelloConTabella.add(intestazione, BorderLayout.NORTH);
        pannelloConTabella.add(tabellaPrenotazioni,BorderLayout.CENTER);
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