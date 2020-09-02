
package it.unisalento.pps1920.carsharing.business;

import it.unisalento.pps1920.carsharing.dao.mysql.OperatoreDAO;
import it.unisalento.pps1920.carsharing.dao.mysql.StazioneDAO;
import it.unisalento.pps1920.carsharing.model.Operatore;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.model.Stazione;

import java.util.ArrayList;

public class ControlloStatoPrenotazioniBusiness            //Eseguita dall'operatore
{
    public ArrayList<Prenotazione> checkPrenotazioni(int id)
    {
        OperatoreDAO odao = new OperatoreDAO();
        StazioneDAO sdao = new StazioneDAO();
        Stazione s = new Stazione();
        ArrayList<Prenotazione>pre = new ArrayList<Prenotazione>();
        s=sdao.findStationByOperatorId(id);
        pre= odao.findByStation(s);
        if(pre==null)
        {
            System.out.println("Mi spiace ma al momento non sono presenti delle prenotazioni.");
            return null;
        }
        return pre;
    }

}