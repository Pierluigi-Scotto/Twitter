package twitter;

import java.sql.SQLException;

/**
 * La classe possiede un riferimento al MEDIATOR
 * Implementa un metodo di notifica di eventi al MEDIATOR
 * Utilizzato nel design patter MEDIATOR
 */

public abstract class UserAbs {
    Mediator mediator;
    protected String username;
    String password;
    String nome;
    String cognome;
    String email;
    String telefono;

    UserAbs(){}

    //Metodi setter che settano le variabili definite nella classe
    public abstract void setMediator(Mediator mediator);
    public abstract void setUsername(String username);
    public abstract void setPassword(String password);
    public abstract void setNome(String nome);
    public abstract void setCognome(String cognome);
    public abstract void setEmail(String email);
    public abstract void setTelefono(String telefono);

    //Meotdi getter che ritornano le variabili definite nella classe
    public abstract Mediator getMediator();
    public abstract String getUsername();
    public abstract String getPassword();
    public abstract String getNome();
    public abstract String getCognome();
    public abstract String getEmail();
    public abstract String getTelefono();

    /**
     * Metodo che consente di riceve i messaggi dal mediator
     * @param username mediator
     * @param messaggio inviato dal mediator
     * @param hashtag parola che contiene un hastag
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    public abstract void receive(String username, String messaggio, String hashtag) throws SQLException;

    /**
     * Metodo che consente di inviare i messaggi dal mediator ai follower
     * @param messaggio scritto dal mediator
     * @param hashtag parola che contiene un hastag
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    public abstract void send(String messaggio, String hashtag) throws SQLException;
}
