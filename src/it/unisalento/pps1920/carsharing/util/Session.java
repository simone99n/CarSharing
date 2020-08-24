package it.unisalento.pps1920.carsharing.util;

import java.util.HashMap;

public class Session {

    private static Session instance;

    private HashMap<String, Object> mappa=new HashMap<String, Object>();

    //definisco le chiavi
    public static final String UTENTE_LOGGATO = "UTENTE_LOGGATO";

    private Cliente clienteLoggato;
    //....    private sessione in lavorazione

    public static synchronized Session getInstance(){
        if(instance == null)     //se non è presente una sessionela crea e la restituisce
            instance=new Session();
        return instance;         //se è presente la restituisce
    }

    private Session(){

    }

    public Cliente getClienteLoggato() {
        return clienteLoggato;
    }

    public void setClienteLoccato(Cliente clienteLoggato) {
        this.clienteLoggato = clienteLoggato;
    }

    public void inserisci(String chiave, Object valore){
        mappa.put(chiave, valore);
    }

    public Object ottieni(String chiave){
        return mappa.get(chiave);
    }

    public void rimuovi(String chiave){
        mappa.remove(chiave);
    }
}
