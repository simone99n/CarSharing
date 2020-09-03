package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Localita;
import it.unisalento.pps1920.carsharing.model.Messaggio;
import it.unisalento.pps1920.carsharing.model.Utente;

import java.util.ArrayList;

public interface IMessaggioDAO extends IBaseDAO<Messaggio> {
    public void inserisciMessaggio(int idSorgente, int idDestinatario, String testo);
    public ArrayList<String[]> getMessaggiOperatore();
    public String getNomeFromId(int idSorgente);
    public int getIdSorgenteFromIdSegnalazione(int idSegnalazione);
    public void setLetto(int id);
}
