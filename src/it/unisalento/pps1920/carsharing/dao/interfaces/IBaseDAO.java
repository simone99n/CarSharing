package it.unisalento.pps1920.carsharing.dao.interfaces;

import java.util.ArrayList;

public interface IBaseDAO<T> {

    public T findById(int id);

    public ArrayList<T> findAll();
}
