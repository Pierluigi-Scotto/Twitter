package twitter;

import java.sql.*;
import java.util.ArrayList;

/**
 * La classe si occupa di
 * 1. trovare un TWEET data una parola
 * 2. trovare TWEET in base all'hashtag
 * 3. trovare numero di UTENTI in base al mumero di TWEET ricevuti o inviati
 */
class Admin {
    private Connection connection;

    /**
     * Metodo che consente di cercare i TWEET in base alla parola inserita
     * @param word parola da cercare
     * @return lista di TWEET
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    ArrayList<String> searchWordTweet(String word) throws SQLException {
        //Query che seleziona il mittente e il corpo data una parola
        String query="SELECT DISTINCT mittente,corpo FROM tweet WHERE corpo LIKE '%"+word+"%'";
        //Oggetti per interrogare il db
        Statement statement=null;
        connection=DBConnection.connect();
        ResultSet resultSet=null;
        //Lista messaggi
        ArrayList<String> tweetsList = new ArrayList<>();

        try {
            statement=connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String mittente = resultSet.getString("mittente");
                String corpo = resultSet.getString("corpo");
                tweetsList.add(mittente+": "+corpo);
            }

            return tweetsList;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (connection!=null) connection.close();
            if (statement!=null) statement.close();
            if(resultSet!=null) resultSet.close();
        }
        return null;
    }

    /**
     * Metodo che consente di cercare i messaggi in base agli HASHTAG
     * @return una lista di TWEET contenenti gli hashatag
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    ArrayList<String> showHashtag() throws SQLException {
        String query = "SELECT DISTINCT COUNT(id),hashtag,corpo FROM tweet GROUP BY id,hashtag,corpo";
        //Oggetti per interrogare il db
        Statement statement=null;
        connection=DBConnection.connect();
        ResultSet resultSet=null;
        //Lista messaggi
        ArrayList<String> hashtagList = new ArrayList<>();

        try {
            statement=connection.createStatement();
            resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String corpo = resultSet.getString("corpo");
                hashtagList.add(corpo);
            }

            return hashtagList;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (connection!=null) connection.close();
            if (statement!=null) statement.close();
            if(resultSet!=null) resultSet.close();
        }
        return null;
    }

    /**
     * Metodo che contente di cercare gli UTENTI in base ai messaggi ricevuti
     * @return lista di utenti che hanno ricevuto un messaggio
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    ArrayList<String> showUsersRecive() throws SQLException {
        String queryMittente = "SELECT DISTINCT username FROM users JOIN tweet on users.username = tweet.destinatario GROUP BY (username)";
        //Oggetti per interrogare il db
        Statement statement=null;
        connection=DBConnection.connect();
        ResultSet resultSet=null;
        //Lista messaggi
        ArrayList<String> usersList = new ArrayList<>();

        try {
            statement=connection.createStatement();
            resultSet = statement.executeQuery(queryMittente);
            while(resultSet.next()){
                String username = resultSet.getString("username");
                usersList.add(username);
            }

            return usersList;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (connection!=null) connection.close();
            if (statement!=null) statement.close();
            if(resultSet!=null) resultSet.close();
        }
        return null;
    }

    /**
     * Metodo che contente di cercare gli UTENTI in base ai messaggi inviati
     * @return lista di utenti che hanno inviato un messaggio
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    ArrayList<String> showUsersSent() throws SQLException {
        String queryMittente = "SELECT DISTINCT username " +
                "FROM users JOIN tweet on users.username = tweet.mittente" +
                " GROUP BY (username)";
        //Oggetti per interrogare il db
        Statement statement=null;
        connection=DBConnection.connect();
        ResultSet resultSet=null;
        //Lista messaggi
        ArrayList<String> usersList = new ArrayList<>();

        try {
            statement=connection.createStatement();
            resultSet = statement.executeQuery(queryMittente);
            while(resultSet.next()){
                String username = resultSet.getString("username");
                usersList.add(username);
            }

            return usersList;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (connection!=null) connection.close();
            if (statement!=null) statement.close();
            if(resultSet!=null) resultSet.close();
        }
        return null;
    }
}
