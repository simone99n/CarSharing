package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.business.ControlloStatoPrenotazioniBusiness;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.util.Session;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FinestraConfirmModificaOperatore extends JFrame {

    JPanel nord, sud;

    public FinestraConfirmModificaOperatore(int idPrenotazione, FinestraOperatore finOp) {
        super("Prenotazione pagata");
        setSize(400, 100);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width / 2) - (this.getWidth() / 2), (screenSize.height / 2) - (this.getHeight() / 2));
        //setDefaultCloseOperation(EXIT_ON_CLOSE);


        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        nord = new JPanel();
        sud = new JPanel();
        c.add(nord, BorderLayout.CENTER);
        c.add(sud, BorderLayout.SOUTH);
        nord.add(new JLabel("Conferma avvenuto pagamento"));

        sud.setLayout(new FlowLayout());
        JButton accetta = new JButton("PAGATO");
        accetta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    Operatore operatoreLoggato = (Operatore) Session.getInstance().ottieni(Session.UTENTE_LOGGATO);
                    ControlloStatoPrenotazioniBusiness.getInstance().inserisciPagato(idPrenotazione);
                    finOp.dispose();
                    FinestraOperatore win = new FinestraOperatore(operatoreLoggato.getId_operatore(), operatoreLoggato.getNome());
                    win.setVisible(true);
                    close();
                }
            }
        });
        JButton rifiuta = new JButton("ANNULLA");
        rifiuta.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    close();
                }
            }
        });
        sud.add(rifiuta);
        sud.add(accetta);

    }

    private void close() {
        this.dispose();
    }


}