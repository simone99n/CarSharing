package it.unisalento.pps1920.carsharing.model;

public class Messaggio
{
    int idsegnalazione;
    String testo;
    boolean letto;
    String sorgente;
    String destinatario;


    public int getIdsegnalazione() {
        return idsegnalazione;
    }

    public void setIdsegnalazione(int idsegnalazione) {
        this.idsegnalazione = idsegnalazione;
    }

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public boolean isLetto() {
        return letto;
    }

    public void setLetto(boolean letto) {
        this.letto = letto;
    }

    public String getSorgente() {
        return sorgente;
    }

    public void setSorgente(String sorgente) {
        this.sorgente = sorgente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
}
