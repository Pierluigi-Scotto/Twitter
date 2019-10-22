package twitter;

import java.sql.SQLException;

/**
 * Interfaccia del mediator
 * Specifica l'interfaccia per la comunicazione da parte di USERABS
 * Design patter Mediator
 */

public interface Mediator {
    /**
     * Metodo che consente di inviare il messaggio e l'hastag a tutti i partecipanti della comunicazione
     * @param messaggio scritto dall'utente
     * @param username follower che segue l'utente
     * @param hashtag parola che contiene un hashtag
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    void sendMessageToAll(String messaggio, UserAbs username, String hashtag) throws SQLException;

    /**
     * Metodo che consente di ricavare il follower dal database
     * @param username follower dell'utente
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    void getFollow(String username) throws SQLException;
}
