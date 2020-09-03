package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.DbConnection;
import it.unisalento.pps1920.carsharing.business.ControlloPrenotazioniAdminBusiness;
import it.unisalento.pps1920.carsharing.business.ControlloStatoPrenotazioniBusiness;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.view.Listener.BottonAdminListener;
import it.unisalento.pps1920.carsharing.view.Listener.BottonOperatorListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FinestraOperatore extends  JFrame
{
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    public JTextArea testoRiscontro;
    public JTextField campoId;
    public boolean bufferModifica=false;

    BottonOperatorListener listener;
    public FinestraOperatore(int id, String nome)
    {
        super("OPERATORE : "+nome.toUpperCase());
        this.setSize(1300,900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Container c = new Container();
        c=this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(new JScrollPane(jp1), BorderLayout.CENTER);
        c.add(jp2,BorderLayout.SOUTH);
        c.add(jp3,BorderLayout.NORTH);

        JButton b1= new JButton("Esci");
        JButton b2= new JButton("Pannello Segnalazioni");
        JButton b3= new JButton("Veicoli pronti");

        jp1.setLayout(new BorderLayout());
        setupPannelloPrenotazioni(id);

        jp2.setLayout(new FlowLayout());
        jp2.add(b1);
        jp2.add(b2);
        jp2.add(b3);
        jp3.setLayout(new FlowLayout());
        JLabel tx= new JLabel("<<<TABELLA PRENOTAZIONI>>>");
        jp3.add(tx);
        JLabel bb= new JLabel("  ");
        jp3.add(bb);
        jp2.setBackground(Color.cyan);
        listener = new BottonOperatorListener(this);

        b1.addActionListener(listener);
        b1.setActionCommand(BottonOperatorListener.PULSANTE_ESCI);
        b2.addActionListener(listener);
        b2.setActionCommand(BottonOperatorListener.PULSANTE_SEGNALAZIONI);
        b3.addActionListener(listener);
        b3.setActionCommand(BottonOperatorListener.PULSANTE_VEICOLI_PRONTI);

        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
        this.setVisible(true);
    }


    public void setupPannelloPrenotazioni(int id)
    {
        ArrayList<Prenotazione>prenotazioni= new ArrayList<Prenotazione>();
        ControlloStatoPrenotazioniBusiness cont= new ControlloStatoPrenotazioniBusiness();
        prenotazioni=cont.checkPrenotazioni(id);
        TablePrenotazioniOperatore tmp = new TablePrenotazioniOperatore(prenotazioni);
        JTable tabellaPrenotazioni = new JTable(tmp);
        tabellaPrenotazioni.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2 && tabellaPrenotazioni.getValueAt(tabellaPrenotazioni.getSelectedRow(), 9).toString().equals("NON PAGATO"))
                    setPagato(Integer.parseInt(tabellaPrenotazioni.getValueAt(tabellaPrenotazioni.getSelectedRow(), 0).toString()));
            }
        });

        JTable intestazione = new JTable(1,10);
        intestazione.setValueAt("ID PRENOTAZIONE",0,0);
        intestazione.setValueAt("PARTENZA",0,1);
        intestazione.setValueAt("ARRIVO",0,2);
        intestazione.setValueAt("LOCALITA'",0,3);
        intestazione.setValueAt("POSTI OCCUPATI",0,4);

        intestazione.setValueAt("VEICOLO",0,5);

        intestazione.setValueAt("DATA",0,6);
        intestazione.setValueAt("DATA/ORA INZIO",0,7);
        intestazione.setValueAt("DATA/ORA FINE",0,8);
        intestazione.setValueAt("STATO",0,9);
        jp1.add(intestazione, BorderLayout.NORTH);
        jp1.add(tabellaPrenotazioni,BorderLayout.CENTER);
    }

    private void setPagato(int idPrenotazione) {
        FinestraConfirmModificaOperatore conferma = new FinestraConfirmModificaOperatore(idPrenotazione,this);
        conferma.setVisible(true);
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
        sud.setBackground(Color.cyan);
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
        indietro.setActionCommand(BottonOperatorListener.PULSANTE_INDIETRO);
        avanti.addActionListener(listener);
        avanti.setActionCommand(BottonOperatorListener.PULSANTE_INVIA_RISCONTRO);
        nord.setLayout(new FlowLayout());
        JLabel info = new JLabel("Nuovi messaggi");
        nord.add(info);
        ArrayList<String[]> messaggi = ControlloStatoPrenotazioniBusiness.getInstance().getMessaggiOperatore();




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

    public void mostraVeicoliPronti() {
        //1. eliminare quello che c'è nell'area centrale
        //BorderLayout al = (BorderLayout) this.getContentPane().getLayout();
       // this.getContentPane().remove(al.getLayoutComponent(BorderLayout.CENTER));
        //this.getContentPane().remove(al.getLayoutComponent(BorderLayout.NORTH));
    }

    public void tornaHome() {

        BorderLayout cl = (BorderLayout) this.getContentPane().getLayout();
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.CENTER));
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.NORTH));
        this.getContentPane().remove(cl.getLayoutComponent(BorderLayout.SOUTH));

        this.getContentPane().add(new JScrollPane(jp1), BorderLayout.CENTER);
        this.getContentPane().add(jp3, BorderLayout.NORTH);
        this.getContentPane().add(jp2, BorderLayout.SOUTH);

        this.setSize(1300,900);
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );
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
        home.setActionCommand(BottonOperatorListener.PULSANTE_INDIETRO);
        indietro.addActionListener(listener);
        indietro.setActionCommand(BottonOperatorListener.PULSANTE_SEGNALAZIONI);
        salva.addActionListener(listener);
        salva.setActionCommand(BottonOperatorListener.PULSANTE_INVIA_DEF);
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