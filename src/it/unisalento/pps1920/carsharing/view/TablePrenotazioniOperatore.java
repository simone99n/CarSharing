package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.model.Prenotazione;
import it.unisalento.pps1920.carsharing.util.DateUtil;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TablePrenotazioniOperatore extends AbstractTableModel
{

    private ArrayList<Prenotazione> prenotazioni;

    public TablePrenotazioniOperatore(ArrayList<Prenotazione> prenotazioni)
    {
        this.prenotazioni = prenotazioni;
    }

    @Override
    public int getRowCount() {
        return prenotazioni.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Prenotazione p = prenotazioni.get(rowIndex);

        //BINDING
        switch(columnIndex) {
            case 0: return p.getId();
            case 1: return p.getData();
            case 2: return p.getDataInizio();
            case 3: return p.getDataFine();
            case 4: return "CONFERMATA";
        }

        return "-";
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}