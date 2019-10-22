package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe si occupa di implementare l'interfaccia Mediator
 * Implementa l'invio e la ricezione dei messaggi
 * Possiede riferimenti verso USERABS
 * Utilizzato nel design patter MEDIATOR
 */

public class ConcreteMediator implements Mediator {
    // Lista utenti
    private List<UserAbs> followersList;
    
    // Costruttore
    ConcreteMediator(){this.followersList = new ArrayList<>(); }

    /**
     * Metodo che consente di inviare un messaggio agli utenti che sono associati al mediator
     * @param messaggio scritto dall'utente
     * @param username utente
     * @param hashtag se il messaggio contiene un hashtag
     */
    @Override
    public void sendMessageToAll(String messaggio, UserAbs username, String hashtag) throws SQLException {
        for(UserAbs u:this.followersList)
            if(u != username)
                u.receive(username.getUsername(),messaggio,hashtag);

        followersList.clear();
    }

    /**
     * Metodo che consnte di riceve dal database il follower
     * @param username utente follower
     */
    @Override
    public void getFollow(String username) throws SQLException {
        // Query che seleziona gli username dell'utente
        String query = "SELECT username FROM follower WHERE usernameFollower=?";
        // Oggetti per interrogare il db
        Connection connection = DBConnection.connect();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet = null;

        try{
            preparedStatement=connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                followersList.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (preparedStatement!=null) preparedStatement.close();
            if (resultSet!=null) preparedStatement.close();
            if (connection!=null) connection.close();
        }
    }
}
