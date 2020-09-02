package it.unisalento.pps1920.carsharing.dao.interfaces;

import it.unisalento.pps1920.carsharing.model.Cliente;
import it.unisalento.pps1920.carsharing.model.Utente;
import it.unisalento.pps1920.carsharing.model.model_support.Recogniser;

public interface IUtenteDAO extends IBaseDAO<Utente> {
    public Utente findByUsername(String username);
    public Utente findByEmail(String email);
    public int salvaRegistrazione(Cliente c, Utente user);
    public Recogniser checkIdpassw(Utente user);
}
