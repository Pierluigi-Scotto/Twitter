package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Classe USER che si occupa di
 * 1. memorizzare le informazioni dell'utente
 * 2. comunicare gli eventi al MEDIATOR
 * Utilizzato nel design patter MEDIATOR
 */
public class User extends UserAbs {
    private Connection connection;

    //Costruttore standard
    User(){}

    //Metodi setter che settano le variabili definite nella classe
    @Override public void setMediator(Mediator mediator) { this.mediator=mediator;}
    @Override public void setUsername(String username){this.username=username;}
    @Override public void setPassword(String password) { this.password = password; }
    @Override public void setNome(String nome) { this.nome = nome; }
    @Override public void setCognome(String cognome) { this.cognome = cognome; }
    @Override public void setTelefono(String numero_telefono) { this.telefono = numero_telefono; }
    @Override public void setEmail(String email) { this.email = email; }

    //Metodi getter che ritornano le variabili definite nella classe
    @Override public Mediator getMediator() { return this.mediator; }
    @Override public String getUsername(){return this.username;}
    @Override public String getPassword(){return this.password;}
    @Override public String getNome(){return this.nome;}
    @Override public String getCognome(){return this.cognome;}
    @Override public String getTelefono(){return this.telefono;}
    @Override public String getEmail(){return this.email;}

    /**
     * Metodo che consente di riceve i messaggi dal mediator.
     * L'utente destinatario salverà il messaggio nel database
     * @param username mediator
     * @param messaggio inviato dal mediator
     * @param hashtag parola che contiene un hastag
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    @Override
    public void receive(String username, String messaggio, String hashtag) throws SQLException {
        // Query che inserisce il messaggio nel db
        String query = "INSERT INTO tweet(mittente,destinatario,corpo,hashtag) VALUES (?,?,?,?)";
        // Oggetti per interrogare il db
        PreparedStatement preparedStatement=null;

        try{
            connection = DBConnection.connect();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,this.getUsername());
            preparedStatement.setString(3,messaggio);
            preparedStatement.setString(4,hashtag);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (preparedStatement!=null) preparedStatement.close();
            if (connection!=null) connection.close();
        }
    }


    /**
     * Metodo che consente di inviare i messaggi al destinatario.
     * @param messaggio scritto dal mediator
     * @param hashtag parola che contiene un hastag
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    @Override
    public void send(String messaggio, String hashtag) throws SQLException {
        this.mediator.sendMessageToAll(messaggio,this,hashtag);
    }
}
