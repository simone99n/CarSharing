package it.unisalento.pps1920.carsharing.model;

public class Messaggio {
    private int idSegnalazione;
    private String testoMessaggio;
    private int letto;      //    0: non letto     1:letto
    private Utente sorgente;
    private Utente destinatario;

    public int getIdSegnalazione() {
        return idSegnalazione;
    }

    public void setIdSegnalazione(int idSegnalazione) {
        this.idSegnalazione = idSegnalazione;
    }

    public String getTestoMessaggio() {
        return testoMessaggio;
    }

    public void setTestoMessaggio(String testoMessaggio) {
        this.testoMessaggio = testoMessaggio;
    }

    public int getLetto() {
        return letto;
    }

    public void setLetto(int letto) {
        this.letto = letto;
    }

    public Utente getSorgente() {
        return sorgente;
    }

    public void setSorgente(Utente sorgente) {
        this.sorgente = sorgente;
    }

    public Utente getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Utente destinatario) {
        this.destinatario = destinatario;
    }
}
