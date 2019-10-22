package twitter;

import java.sql.*;

/**
 * La classe si occupa di
 * 1. stabilire una connesione con il database Sqlite
 * 2. creare le tabelle
 * 3. popolare le tabelle
 * Utilizza il pattern Singleton per creare un unica istanza della classe DBConnection
 */
class DBConnection {
    //Variabili
    private static DBConnection instance;

    //Costruttore default Database
     private DBConnection(){ }

    /**
     * Metodo che consente la creazione e la connesione al database
     * Il database utilizzato è l'sqlite
     */
    static Connection connect() throws SQLException {
        String url = "jdbc:sqlite:lib/DB";
        return DriverManager.getConnection(url);
    }

    /**
     * Metodo che consente la creazione della tabella USER
     * Consente di tenere traccia di tutti gli utenti che si registrano al servizio twitter
     * I campi obbligatori da inserire sono USERNAME, PASSWORD, NUMERO DI TELEFONO e EMAIL
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
    */
    void createTableUser() throws SQLException {
        //Connesione database
        Connection connection=null;
        // Oggetto per interrogare il database
        Statement statement=null;
        try{
            // Query che crea la tabella user
            String query = "CREATE TABLE IF NOT EXISTS users(" +
                    "id integer PRIMARY KEY,"+
                    "username varchar(50) NOT NULL UNIQUE,"+
                    "password varchar(20) NOT NULL," +
                    "nome varchar(20)," +
                    "cognome varchar(20)," +
                    "numero_telefono varchar(10) NOT NULL UNIQUE," +
                    "email varchar(50) NOT NULL UNIQUE)";
            // Connesione al db
            connection = connect();
            // Creazione dell'oggeto per interrogare il db
            statement = connection.createStatement();
            // Esecuzione della query
            statement.execute(query);
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura oggetti
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
    }

    /** Metodo che consente la creazione della tabella TWEET
     * Consente di tenere traccia di tutti i tweet che vengono scritti dagli utenti
     * I campi obbligatori da inserire sono MITTENTE, DESTINATARIO, CORPO
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    void createTableMessage() throws SQLException {
        // Oggetto per interrogare il database
        Statement statement=null;
        // Connesione al database
        Connection connection=null;
        try{
            // Query che crea la tabella tweet
            String query = "CREATE TABLE IF NOT EXISTS tweet(" +
                    "id integer PRIMARY KEY," +
                    "mittente varchar(20) NOT NULL," +
                    "destinatario varchar(20) NOT NULL," +
                    "corpo varchar(140) NOT NULL," +
                    "hashtag varchar(20))";
            // Connesione al db
            connection=connect();
            // Creazione dell'oggetto per interrogare il db
            statement = connection.createStatement();
            // Esecuzione della query
            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura oggetti
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
    }

    /** Metodo che consente la creazione della tabella FOLLOWER
     * Consente di tenere traccia degli utenti che vogliono seguire i "follower"
     * I campi obbligatori da inserire sono USERNAME e USERNAMEFOLLOWER
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    void createTableFollower() throws SQLException {
        // Oggetto per interrogare il database
        Statement statement=null;
        // Connesione database
        Connection connection=null;
        try{
            String query = "CREATE TABLE IF NOT EXISTS follower(" +
                    "id integer PRIMARY KEY," +
                    "username varchar(20) NOT NULL," +
                    "usernameFollower varchar(20) NOT NULL)";
            // Connesione al db
            connection=connect();
            // Creazione dell'oggetto per interrogare il db
            statement = connection.createStatement();
            // Esecuzione query
            statement.execute(query);
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura oggetti
            if(statement!=null) statement.close();
            if(connection!=null) connection.close();
        }
    }

    /**
     * Metodo che crea un unica istanza del database
     * PATTERN SINGLETON
     * @return istanza del database
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    static DBConnection getInstance() throws SQLException{
        if(instance == null)
            instance = new DBConnection();
        else if(connect().isClosed())
            instance = new DBConnection();

        return instance;
    }
}