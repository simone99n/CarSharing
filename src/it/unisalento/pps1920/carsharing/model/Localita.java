package it.unisalento.pps1920.carsharing.model;

public class Localita {

    private int id;
    private String citta;
    private Double latitudine;
    private Double longitudine;

    public Localita() {
    }

    public Localita(int id, String citta, Double latitudine, Double longitudine) {
        this.id = id;
        this.citta = citta;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
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

    public String toString(){
        return citta;
    }
}
