package it.unisalento.pps1920.carsharing.model;

public class Stazione {

    private int id;
    private String nome;
    private Double latitudine;
    private Double longitudine;
    private Addetto addetto;
    private Operatore operatore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Double latitudine) {
        this.latitudine = latitudine;
    }

    public Double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Double longitudine) {
        this.longitudine = longitudine;
    }

    public Addetto getAddetto() {
        return addetto;
    }

    public void setAddetto(Addetto addetto) {
        this.addetto = addetto;
    }

    public Operatore getOperatore() {
        return operatore;
    }

    public void setOperatore(Operatore operatore) {
        this.operatore = operatore;
    }

    public String toString(){
        return nome;
    }
}
