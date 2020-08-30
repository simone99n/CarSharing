package it.unisalento.pps1920.carsharing.model;

public class Amministratore extends Utente
{
    String nome;
    String congome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCongome() {
        return congome;
    }

    public void setCongome(String congome) {
        this.congome = congome;
    }
}
