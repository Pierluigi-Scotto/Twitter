package twitter;

import java.sql.*;
import java.util.ArrayList;

/**
 * Classe che si occupa di
 * 1. inserire un follower nel database
 * 2. cambiare email o cellulare in username, nel caso in cui l'utente inserisca uno dei due campi
 * 3. controllare se esiste il follower nella tabella FOLLOWER
 * 4. controllare gli spazi vuoti
 * 5. prendere l'hashtag dal messaggio
 * 6. creare una lista di tweet per l'interfaccia utente
 * 7. creare una lista di follower per l'interfaccia utente
 */
class Twitter {
    private Connection connection;

    /**
     * Metodo che consente di inserire nel database un follower
     * @param user classe UTENTE in cui sono definite le informazioni dell'utente, utente che ha effettuato l'accesso
     * @param follower utente che inserisce nella barra di ricerca il FOLLOWER da seguire
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    void insertFollowerInDatabase(User user, String follower) throws SQLException {
        // Query per inserire un dato nel db
        String query = "INSERT INTO follower(username,usernameFollower) VALUES(?,?)";
        // Creazione oggetto per interrogare il db
        PreparedStatement preparedStatement = null;
        //Se trova l'email o il cellulare, lo cambia in username
        String username = changeEmailCell(follower);
        connection = DBConnection.connect();

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if(preparedStatement!=null) preparedStatement.close();
            if (connection!=null) connection.close();
        }
    }

    /**
     * Metodo che consente di controllare, nel caso in cui l'utente inserisce come follower l'EMAIL o il CELLULARE, di
     * cambiare uno dei due campi in USERNAME
     * @param email_cell EMAIL o CELLULARE dell'utente follower da cercare
     * @return l'USERNAME dell'utente FOLLOWER
     * @throws SQLException  lancia un eccezione nel caso si verifichino degli errori
     */
    private String changeEmailCell(String email_cell) throws SQLException {
        //Query che seleziona l'username
        String query = "SELECT username FROM users where username=? OR email=? OR numero_telefono=?";
        //Oggetti per interrogare il database
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        connection = DBConnection.connect();
        //Variabile di ritorno
        String username=null;

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,email_cell);
            preparedStatement.setString(2,email_cell);
            preparedStatement.setString(3,email_cell);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                username = resultSet.getString("username");
            }

            return username;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //Chiusura oggetti
            if (resultSet!=null) resultSet.close();
            if(preparedStatement!=null) preparedStatement.close();
            if (connection!=null) connection.close();
        }
        return null;
    }

    /**
     * Metodo che consente di controllare se l'utente esiste già nel database
     * @param user classe UTENTE in cui sono definite le informazioni dell'utente FOLLOWER
     * @return true se esiste, false viceversa
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    boolean checkFollower(String user) throws SQLException {
        //Query per cercare l'utente
        String query = "SELECT * FROM users WHERE username=? OR email=? OR numero_telefono=?";
        //Oggetti per interrogare il db
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        connection = DBConnection.connect();

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, user);
            preparedStatement.setString(3, user);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet!=null) resultSet.close();
            if(preparedStatement!=null) preparedStatement.close();
            if (connection!=null) connection.close();
        }
        return false;
    }

    /**
     * Metodo che consente di controllare se l'utente sta già seguendo il FOLLOWER che ha inserito
     * @param user classe UTENTE in cui sono definite le informazioni dell'utente che ha effettuato l'accesso
     * @param follower utente da seguire
     * @return true se esite, false viceversa
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    boolean checkFollower(User user, String follower) throws SQLException {
        //Query che cerca l'utente
        String query = "SELECT * FROM follower WHERE username=? AND usernameFollower=?";
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        connection = DBConnection.connect();

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,follower);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (resultSet!=null) resultSet.close();
            if(preparedStatement!=null) preparedStatement.close();
            if (connection!=null) connection.close();
        }
        return false;
    }

    /**
     * Metodo che consente di controllare se ci sono spazi vuoti
     * @param user utente follower
     * @return true se ci soono spazi vuoti, false viceversa
     */
    boolean checkEmptySpace(String user){ return user.equals(""); }

    /**
     * Metodo che consente di cercare l'hashtag nel TWEET
     * @param tweet tweet dell'utente
     * @return parola che contiene tutto l'hashtag, viceversa una stringa vuota
     */
    String searchHashtag(String tweet){
        for(String word:tweet.split("\\s+"))
            if(word.contains("#"))
                return word;
        return  " ";
    }

    /**
     * Metodo che consente di ritornare una lista di TWEET
     * @param username utente che ha effettuato l'accesso
     * @return lista di tweet
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    ArrayList<String> showTweet(String username) throws SQLException {
        //Query che prende i messaggi del destinatario
        String query="SELECT * FROM tweet WHERE destinatario=?";
        //Oggetti per interrogare il db
        PreparedStatement preparedStatement=null;
        connection=DBConnection.connect();
        ResultSet resultSet=null;
        //Lista messaggi
        ArrayList<String> tweetsList = new ArrayList<>();

        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Tweet tweet = new Tweet();
                String mittente = resultSet.getString("mittente");
                String destinatario = resultSet.getString("destinatario");
                String corpo = resultSet.getString("corpo");
                String hashtag = resultSet.getString("hashtag");

                tweet.setMittente(mittente);
                tweet.setDestinatario(destinatario);
                tweet.setCorpo(corpo);
                tweet.setHashtag(hashtag);

                tweetsList.add(tweet.getMittente()+": "+tweet.getCorpo());
            }

            return tweetsList;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (connection!=null) connection.close();
            if (preparedStatement!=null) preparedStatement.close();
            if(resultSet!=null) resultSet.close();
        }
        return null;
    }

    /**
     * Metodo che consente di ritornare una lista di FOLLOWER
     * @param username utente che ha effettuato l'accesso
     * @return lista di follower dell'utente che ha effettuato l'accesso
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    ArrayList<String> showFollower(String username) throws SQLException {
        //Query che prende i messaggi del destinatario
        String query="SELECT * FROM follower WHERE username=?";
        //Oggetti per interrogare il db
        PreparedStatement preparedStatement=null;
        connection=DBConnection.connect();
        ResultSet resultSet=null;
        //Lista messaggi
        ArrayList<String> followersList = new ArrayList<>();

        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String follower = resultSet.getString("usernameFollower");
                followersList.add(follower);
            }

            return followersList;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (connection!=null) connection.close();
            if (preparedStatement!=null) preparedStatement.close();
            if(resultSet!=null) resultSet.close();
        }
        return null;
    }

    /**
     * Metodo che consente di ritornare una lista di FOLLOWING
     * @param username utente che ha effettuato l'accesso
     * @return lista di follower dell'utente che ha effettuato l'accesso
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    ArrayList<String> showFollowing(String username) throws SQLException {
        //Query che prende i messaggi del destinatario
        String query="SELECT * FROM follower WHERE usernameFollower=?";
        //Oggetti per interrogare il db
        PreparedStatement preparedStatement=null;
        connection=DBConnection.connect();
        ResultSet resultSet=null;
        //Lista messaggi
        ArrayList<String> followingList = new ArrayList<>();

        try {
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                String follower = resultSet.getString("username");
                followingList.add(follower);
            }

            return followingList;
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (connection!=null) connection.close();
            if (preparedStatement!=null) preparedStatement.close();
            if(resultSet!=null) resultSet.close();
        }
        return null;
    }
}