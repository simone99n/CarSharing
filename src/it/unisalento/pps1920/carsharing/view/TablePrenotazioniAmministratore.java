package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TablePrenotazioniAmministratore extends AbstractTableModel {

    private ArrayList<Prenotazione> prenotazioni;

    public TablePrenotazioniAmministratore(ArrayList<Prenotazione> prenotazioni)
    {
        this.prenotazioni = prenotazioni;
    }

    @Override
    public int getRowCount() {
        return prenotazioni.size();
    }

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Prenotazione p = prenotazioni.get(rowIndex);

        //BINDING
        switch(columnIndex) {
            case 0: return p.getId();
            case 1: return p.getPartenza().getNome();
            case 2: return p.getArrivo().getNome();
            case 3: return p.getLocalita().getCitta();
            case 4: return p.getNumPostiOccupati();

            case 5: return p.getMezzo().getModello().getNome();

            case 6: return DateUtil.stringFromDate(p.getData());
            case 7: return DateUtil.stringFromDate(p.getDataInizio());
            case 8: return DateUtil.stringFromDate(p.getDataFine());
            case 9: return "CONFERMATA";
        }

        return "-";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}