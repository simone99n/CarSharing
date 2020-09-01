package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.util.DateUtil;
//import sun.tools.jconsole.Tab;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TableModelPrenotazioniCliente extends AbstractTableModel  {

    private ArrayList<Prenotazione> prenotazioni;

    public TableModelPrenotazioniCliente(ArrayList<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    @Override
    public int getRowCount() {
        return prenotazioni.size();
    } //setup numero righe

    @Override
    public int getColumnCount() {
        return 7;
    }                //setup numero colonne

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {       //setup tabella (ogni chiamata di getValueAt restituisce il setup di una sola cella!)

        Prenotazione p = prenotazioni.get(rowIndex);
        JButton mod=new JButton("Modifica");
        //BINDING
        switch(columnIndex) {
            case 0: return p.getId();
            case 1: return p.getPartenza().getNome();
            case 2: return p.getArrivo().getNome();
            case 3: return p.getLocalita().getCitta();
            case 4: return p.getNumPostiOccupati();
            case 5: return DateUtil.stringFromDate(p.getDataInizio());
            case 6: return DateUtil.stringFromDate(p.getDataFine());
            //case 7: return (JButton) mod;
        }

        return "-";
    }

  /*  @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {    //rendere editabili alcune celle
        return (columnIndex > 4 && columnIndex < 7);
    }
  */

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Prenotazione p = prenotazioni.get(rowIndex);

        switch (columnIndex) {
            case 5: p.setDataInizio(DateUtil.dateTimeFromString((String)value)); break;
            case 6: p.setDataFine(DateUtil.dateTimeFromString((String)value)); break;
        }

        //TODO richiamare il metodo di business modificaPrenotazione che chiamerà il DAO per update sql

    }
}
