package it.unisalento.pps1920.carsharing.model;

public class Amministratore extends Utente
{
    String nome;
    String cognome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}
