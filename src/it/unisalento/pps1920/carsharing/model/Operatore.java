package it.unisalento.pps1920.carsharing.model;

public class Operatore
{
    int id_operatore;
    String nome;
    String cognome;

    public int getId_operatore() {
        return id_operatore;
    }

    public void setId_operatore(int id_operatore) {
        this.id_operatore = id_operatore;
    }

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
