package it.unisalento.pps1920.carsharing.view;

import it.unisalento.pps1920.carsharing.model.Prenotazione;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TablePrenotazioniAddetto extends AbstractTableModel
{

    private ArrayList<String[]> prenotazioni;

    public TablePrenotazioniAddetto(ArrayList<String[]> prenotazioni)
    {
        this.prenotazioni = prenotazioni;
    }


    @Override
    public int getRowCount()
    {
        return prenotazioni.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        String[] riga = prenotazioni.get(rowIndex);

        //BINDING
        switch(columnIndex)
        {
            case 0: return riga[0];
            case 1: return riga[2];
            case 2: return riga[3];
            case 3: return riga[1];
        }

        return "-";
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}