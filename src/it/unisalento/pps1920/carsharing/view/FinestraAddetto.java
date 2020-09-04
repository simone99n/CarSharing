package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.ControlloAutomezziAddettoBusiness;
import it.unisalento.pps1920.carsharing.business.ControlloStatoPrenotazioniBusiness;
import it.unisalento.pps1920.carsharing.dao.mysql.PrenotazioneDAO;
import it.unisalento.pps1920.carsharing.model.Accessorio;
import it.unisalento.pps1920.carsharing.model.Modello;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.view.Listener.BottonAddettoListener;
import it.unisalento.pps1920.carsharing.view.Listener.BottonAdminListener;
import it.unisalento.pps1920.carsharing.view.Listener.BottonErrorListener.AllErrorMessages;
import it.unisalento.pps1920.carsharing.view.Listener.BottonOperatorListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FinestraAddetto extends  JFrame {

    int idAdd;
    int stato=0;
    JButton butt = new JButton("Visualizza Accessori Automezzo");
    JLabel lab = new JLabel("ID PRENOTAZIONE: ");
    JPanel jp1=new JPanel(new BorderLayout());
    JPanel jp2=new JPanel(new BorderLayout());
    JPanel jp3=new JPanel(new FlowLayout());
    JPanel jp2_1= new JPanel(new FlowLayout());
    JPanel jp2_2= new JPanel(new BorderLayout());
    public JTextField jt = new JTextField(20);
    public JTextField campoId;
    public JTextArea testoRiscontro;
    JButton b2= new JButton("PANNELLO SEGNALAZIONI");
    BottonAddettoListener listener;

    public FinestraAddetto(int idAddetto, String nomeAddetto) {

        super("ADDETTO : "+nomeAddetto.toUpperCase());
        getIdAdd(idAddetto);
        listener = new BottonAddettoListener(this,idAddetto,nomeAddetto);
        setSize(800,800);
        Container c = new Container();
        c=this.getContentPane();
        c.add(new JScrollPane(jp1),BorderLayout.CENTER);
        c.add(jp2,BorderLayout.NORTH);
        c.add(jp3,BorderLayout.SOUTH);
        jp2.add(jp2_1,BorderLayout.NORTH);
        jp2.add(jp2_2,BorderLayout.SOUTH);
        jp3.add(b2);
        JLabel bb= new JLabel("  ");
        jp3.add(bb);
        jp3.setBackground(Color.yellow);

        butt.addActionListener(listener);
        butt.setActionCommand(BottonAddettoListener.PULSANTE_VISUALIZZA_ACCESSORI_AUTOMEZZO);
        b2.addActionListener(listener);
        b2.setActionCommand(BottonAddettoListener.PULSANTE_CENTRO_SEGNALAZIONI);
        jp2_1.add(lab);
        jp2_1.add(jt);
        jp2_1.add(butt);
        setupPannelloPrenotazioni();

        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        setVisible(true);
        this.setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public void setupPannelloPrenotazioni() {

        ArrayList<String[]>prenotazioni= new ArrayList<String[]>();
        //ControlloAutomezziAddettoBusiness cont = new ControlloAutomezziAddettoBusiness();
        //prenotazioni=cont.checkPrenotazioni(idAdd);
        prenotazioni=ControlloAutomezziAddettoBusiness.getInstance().checkPrenotazioni(idAdd);
        if(prenotazioni==null) {
            jp2_1.add(new JLabel("Non ci sono automezzi da preparare in questo momento!"));
            menu();
            jp2_1.remove(butt);
            jp2_1.remove(lab);
            jp2_1.remove(jt);
            AllErrorMessages al= new AllErrorMessages(3);

        }
        else {
            stato=1;
            TablePrenotazioniAddetto tmp = new TablePrenotazioniAddetto(prenotazioni);
            JTable tabellaPrenotazioni = new JTable(tmp);
            JTable intestazione = new JTable(1,4);
            intestazione.setValueAt("ID PRENOTAZIONE",0,0);
            intestazione.setValueAt("MARCA",0,1);
            intestazione.setValueAt("TIPOLOGIA",0,2);
            intestazione.setValueAt("DATA INIZIO NOLEGGIO",0,3);
            //jp2_2.add(intestazione, BorderLayout.SOUTH);
            jp1.add(intestazione,BorderLayout.NORTH);
            jp1.add(tabellaPrenotazioni,BorderLayout.CENTER);
        }

    }


    public void visualizzaAccessoriAutomezzo(int idPrenotazione) {
        int state=0;
        ControlloAutomezziAddettoBusiness cont = new ControlloAutomezziAddettoBusiness();
        ArrayList<String[]> st= new ArrayList<>();
        st=cont.checkPrenotazioniFilteredByIdStation(idAdd,idPrenotazione);

        if(st.get(0)[0].equals("-1")) {
            state=1;
            menu();
            AllErrorMessages msg= new AllErrorMessages(9);
        }

        if(st.get(0)[0].equals("-2")) {
            state=2;
            menu();
            AllErrorMessages msg= new AllErrorMessages(10);
        }
        if(st.get(0)[0].equals("-3")) {
            state=3;
            menu();
            AllErrorMessages msg= new AllErrorMessages(12);
        }

        ArrayList<Accessorio> acc= new ArrayList<>();
        acc=cont.getAccessoriFromIdPrenotazione(idPrenotazione);

        if(state==0) {
            BorderLayout al = (BorderLayout) this.getContentPane().getLayout();
            this.getContentPane().remove(al.getLayoutComponent(BorderLayout.CENTER));
            this.getContentPane().remove(al.getLayoutComponent(BorderLayout.NORTH));

            JPanel pan = new JPanel(new GridLayout(acc.size()+1,2));
            JPanel pan1 = new JPanel(new BorderLayout());
            this.getContentPane().add(pan, BorderLayout.CENTER);
            this.getContentPane().add(pan1, BorderLayout.NORTH);


            for(int c=0; c< acc.size() ; c++) {
                pan.add(new JLabel("Accessorio "+(c+1)));
                pan.add(new JLabel(acc.get(c).getNome()));
            }
            JButton butt1= new JButton("Veicolo preparato");
            JButton butt2= new JButton("Veicolo non ancora preparato");
            butt1.addActionListener(listener);
            butt1.setActionCommand(BottonAddettoListener.PULSANTE_PRONTO);
            butt2.addActionListener(listener);
            butt2.setActionCommand(BottonAddettoListener.PULSANTE_NON_PRONTO);
            pan.add(butt1);
            pan.add(butt2);
            butt1.setBackground(Color.green);
            butt2.setBackground(Color.red);
            if(acc.isEmpty()) {
                pan.remove(butt1);
                pan.remove(butt2);
                pan.setLayout(new BorderLayout());
                JPanel pan2= new JPanel(new FlowLayout());
                JPanel pan3= new JPanel(new FlowLayout());
                JLabel labb= new JLabel("Non sono presenti accessori!");
                pan.add(pan2,BorderLayout.CENTER);
                pan.add(pan3,BorderLayout.SOUTH);
                pan3.add(butt1);
                pan3.add(butt2);
                pan2.add(labb);
                this.setSize(450,300);
            }

            else
                this.setSize(450,600);
            repaint();
            revalidate();
        }
        state=0;

    }

    public void menu() {
        if(stato==1){ //se ==1 almeno una volta
            BorderLayout cl = (BorderLayout) this.getContentPane().getLayout();
            this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.CENTER));
            this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.NORTH));

            this.getContentPane().add(new JScrollPane(jp1), BorderLayout.CENTER);
            this.getContentPane().add(jp2, BorderLayout.NORTH);
            this.setSize(800,800);
            setupPannelloPrenotazioni();
            repaint();
            revalidate();
            Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
            setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        }

        repaint();
        revalidate();
    }

    public void indietro() {
        BorderLayout cl = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.NORTH));
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.SOUTH));

        this.getContentPane().add(new JScrollPane(jp1), BorderLayout.CENTER);
        this.getContentPane().add(jp2, BorderLayout.NORTH);
        this.getContentPane().add(jp3,BorderLayout.SOUTH);

        jp3.add(b2);
        JLabel bb= new JLabel("  ");
        jp3.add(bb);
        jp3.setBackground(Color.yellow);
        b2.addActionListener(listener);
        b2.setActionCommand(BottonAddettoListener.PULSANTE_CENTRO_SEGNALAZIONI);

        this.setSize(800,800);
        setupPannelloPrenotazioni();

        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        repaint();
        revalidate();
    }

    void getIdAdd(int idAddetto) {
        this.idAdd=idAddetto;
    }

    public void mostraPannelloSegnalazioni() {


        //1. eliminare quello che c'è nell'area centrale
        BorderLayout al = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(al.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(al.getLayoutComponent(BorderLayout.NORTH));
        this.getContentPane().remove(al.getLayoutComponent(BorderLayout.SOUTH));

        //2. inserire pannello della funzionalita specifica
        this.setSize(850,400);
        this.setResizable(false);
        JPanel nord = new JPanel(new BorderLayout());
        JPanel centro = new JPanel(new BorderLayout());
        JPanel sud = new JPanel(new FlowLayout());
        sud.setBackground(Color.yellow);
        this.getContentPane().add(new JScrollPane(centro), BorderLayout.CENTER);
        this.getContentPane().add(nord, BorderLayout.NORTH);
        this.getContentPane().add(sud, BorderLayout.SOUTH);
        JButton indietro = new JButton("<- INDIETRO");
        JButton avanti = new JButton("INVIA RISCONTRO");
        JLabel spazio = new JLabel("                                                                                ");
        JLabel idLabel = new JLabel("Inserisci id: ");
        campoId = new JTextField(6);
        sud.add(indietro);
        sud.add(spazio);
        sud.add(idLabel);
        sud.add(campoId);
        sud.add(avanti);
        indietro.addActionListener(listener);
        indietro.setActionCommand(BottonAddettoListener.PULSANTE_INDIETRO);
        avanti.addActionListener(listener);
        avanti.setActionCommand(BottonAddettoListener.PULSANTE_INVIA_RISCONTRO);
        nord.setLayout(new FlowLayout());
        JLabel info = new JLabel("Nuovi messaggi");
        nord.add(info);
        ArrayList<String[]> messaggi = ControlloStatoPrenotazioniBusiness.getInstance().getMessaggiAddetto();




        if(messaggi.size()!=0){
            centro.setLayout(new GridLayout(messaggi.size()*3,1));
            for (String[] strings : messaggi) {
                //JLabel idSegn = new JLabel(strings[1]);
                JLabel nome = new JLabel("[ID: "+strings[0]+"]     [ADMIN: "+ControlloStatoPrenotazioniBusiness.getInstance().getNomeFromId(Integer.parseInt(strings[3])) + "] ");
                JLabel testo = new JLabel(strings[1]);
                centro.add(nome);
                centro.add(testo);

                centro.add(new JLabel("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"));

            }
        }
        else{
            System.out.println("non ci sono messaggi non letti");
        }



        //3. refresh della UI
        repaint();
        revalidate();


    }

    public void panelloRiscontro() {
        BorderLayout cl = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.NORTH));
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.SOUTH));
        this.setSize(350,250);
        JPanel nord = new JPanel(new FlowLayout());
        JPanel centro = new JPanel(new FlowLayout());
        JPanel sud = new JPanel(new FlowLayout());
        sud.setBackground(Color.cyan);
        this.getContentPane().add(centro, BorderLayout.CENTER);
        this.getContentPane().add(nord, BorderLayout.NORTH);
        this.getContentPane().add(sud, BorderLayout.SOUTH);
        nord.add(new JLabel("INSERISCI MESSAGGIO: "));
        testoRiscontro = new JTextArea(5,25);
        centro.add(testoRiscontro);
        JButton home = new JButton("HOME");
        JButton indietro = new JButton("<-INDIETRO");
        JButton salva = new JButton("INVIA");
        home.addActionListener(listener);
        home.setActionCommand(BottonAddettoListener.PULSANTE_INDIETRO);
        indietro.addActionListener(listener);
        indietro.setActionCommand(BottonAddettoListener.PULSANTE_SEGNALAZIONI);
        salva.addActionListener(listener);
        salva.setActionCommand(BottonAddettoListener.PULSANTE_INVIA_DEF);
        sud.add(home);
        sud.add(indietro);
        sud.add(new JLabel("                "));
        sud.add(salva);
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );

        repaint();
        revalidate();
    }
}