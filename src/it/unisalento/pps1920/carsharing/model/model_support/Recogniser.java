package it.unisalento.pps1920.carsharing.model.model_support;

public class Recogniser {

    int id;
    String Type;

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        Type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return Type;
    }
}
