package it.unisalento.pps1920.carsharing.view.Listener.BottonErrorListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AllErrorMessages extends JFrame
{
    public AllErrorMessages(int tipo)
    {
        super("WARNING");
        setSize(350,100);
        setResizable(false);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit ( ).getScreenSize ( );
        setLocation ( ( screenSize.width / 2 ) - ( this.getWidth ( ) / 2 ), (screenSize.height / 2 ) - ( this.getHeight ( ) / 2 ) );

        Container c=getContentPane();
        c.setLayout(new BorderLayout());
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JButton button = new JButton("OK");
        if(tipo==1)
        {
            JLabel info1 = new JLabel("Username o password errati!");
            JLabel info2 = new JLabel("");
            panel1.add(info1);
            panel2.add(info2);
        }
       else if(tipo==2)
        {
            JLabel info1 = new JLabel("Non sono presenti prenotazioni per questo modello!");
            JLabel info2 = new JLabel("");
            panel1.add(info1);
            panel2.add(info2);
        }
        else if(tipo==3)
        {
            JLabel info1 = new JLabel("Non ci sono prenotazioni per questa stazione!");
            JLabel info2 = new JLabel("");
            panel1.add(info1);
            panel2.add(info2);
        }
        else if(tipo==4)
        {
            JLabel info1 = new JLabel("L'email inserita e' gia' in uso!");
            JLabel info2 = new JLabel("");
            panel1.add(info1);
            panel2.add(info2);
        }
        else if(tipo==5)
        {
            JLabel info1 = new JLabel("L'username inserito e' gia' in uso!");
            JLabel info2 = new JLabel("");
            panel1.add(info1);
            panel2.add(info2);
        }

        else if(tipo==6)
        {
            JLabel info1 = new JLabel("Non ci sono prenotazioni per questa marca!");
            JLabel info2 = new JLabel("");
            panel1.add(info1);
            panel2.add(info2);
        }
        c.add(panel1,BorderLayout.NORTH);
        c.add(panel2,BorderLayout.CENTER);
        c.add(panel3,BorderLayout.SOUTH);
        setVisible(true);
        panel3.add(button);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {
                    close();
                }
            }
        });
    }

    private void close(){
        this.dispose();

    }
}
