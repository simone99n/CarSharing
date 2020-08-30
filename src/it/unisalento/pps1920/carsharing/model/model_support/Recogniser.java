package it.unisalento.pps1920.carsharing.model.model_support;

public class Recogniser {

    int id;
    String type;
    String nome;
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
