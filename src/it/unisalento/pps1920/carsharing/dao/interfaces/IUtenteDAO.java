package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;

public interface IUtenteDAO extends IBaseDAO<Utente> {
    public Utente findByUsername(String username);
    public Utente findByEmail(String email);
    public boolean salvaRegistrazione(Cliente c, Utente user);
}
