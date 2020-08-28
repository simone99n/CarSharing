package it.unisalento.pps1920.carsharing;

import it.unisalento.pps1920.carsharing.business.PrenotazioneBusiness;
import it.unisalento.pps1920.carsharing.dao.interfaces.*;
import it.unisalento.pps1920.carsharing.dao.mysql.*;
import it.unisalento.pps1920.carsharing.model.Localita;
import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;

public class BootCarSharing {

    public static void main(String args[]) {

        ILocalitaDAO lDao = new LocalitaDAO();

        System.out.println("CHIAMO METODO findAll");
        for(Localita loc : lDao.findAll()) {
            System.out.println("ID: "+loc.getId());
            System.out.println("Nome: "+loc.getCitta());
            System.out.println("Lat: "+loc.getLatitudine());
            System.out.println("Lon: "+loc.getLongitudine());
            System.out.println("--------------");
        }

        System.out.println("CHIAMO METODO findById");
        Localita loc = lDao.findById(1);
        if(loc!=null) {
            System.out.println("ID: " + loc.getId());
            System.out.println("Nome: " + loc.getCitta());
            System.out.println("Lat: " + loc.getLatitudine());
            System.out.println("Lon: " + loc.getLongitudine());
            System.out.println("--------------");
        }

        IPrenotazioneDAO pDao = new PrenotazioneDAO();
        ArrayList<Prenotazione> lista = pDao.findAll();

        for(Prenotazione p : lista) {
            System.out.println("ID CLIENTE: "+p.getCliente().getId());
            System.out.println("AUTO "+p.getMezzo().getModello().getNome()+" TARGATA "+p.getMezzo().getTarga());
            System.out.println("STAZIONE DI PARTENZA: "+p.getPartenza().getNome());
            System.out.println("STAZIONE DI ARRIVO: "+p.getArrivo().getNome());
            System.out.println("POSTI OCCUPATI: "+p.getNumPostiOccupati());
        }

        Prenotazione p = new Prenotazione();
        p.setDataInizio(DateUtil.dateTimeFromString("2019-12-03 09:00:00"));
        p.setDataFine(DateUtil.dateTimeFromString("2019-12-06 19:00:00"));
        p.setData(new Date());

        IClienteDAO cDao = new ClienteDAO();
        p.setCliente(cDao.findById(3));
        p.setNumPostiOccupati(1);
        p.setLocalita(lDao.findById(1));

        IStazioneDAO sDao = new StazioneDAO();
        p.setPartenza(sDao.findById(1));
        p.setArrivo(sDao.findById(1));

        IMezzoDAO mDao = new MezzoDAO();
        p.setMezzo(mDao.findById(2));

       // PrenotazioneBusiness.getInstance().inviaPrenotazione(p);

    }

}
