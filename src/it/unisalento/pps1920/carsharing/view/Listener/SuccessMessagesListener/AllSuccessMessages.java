package it.unisalento.pps1920.carsharing.view.Listener.SuccessMessagesListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AllSuccessMessages extends JFrame
{
    public AllSuccessMessages(int tipo)
    {
        super("SUCCESS");
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
            JLabel info1 = new JLabel("Automezzo preparato correttamente!");
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
