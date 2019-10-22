package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe si occupa di
 * 1. implementare i metodi che vengono usati dall'interfaccia utente (classe RegistrationControl)
 * 2. inserire l'utente nel database
 * 3. controllare se l'utente non è già registrato
 */
class Registration {
    private Connection connection;

    /** Metodo che consente di inserire i dati dell'utente nel database
     * @param user classe UTENTE in cui sono definite le informazioni dell'utente, necessarie per la registrazione
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    void insertUserInDatabase(User user) throws SQLException {
        PreparedStatement preparedStatement=null;
        try{
            // Query per inserire un dato nel db
            String query = "INSERT INTO users(username,password,nome,cognome,numero_telefono,email) VALUES(?,?,?,?,?,?)";
            // Connesione al db
            connection = DBConnection.connect();
            // Creazione oggetto per interrogare il db
            preparedStatement = connection.prepareStatement(query);
            // Inserimento stringa
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getNome());
            preparedStatement.setString(4,user.getCognome());
            preparedStatement.setString(5,user.getTelefono());
            preparedStatement.setString(6,user.getEmail());

            // Esecuzione query
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            // Chiusura oggetti
            if(connection!=null) connection.close();
            if(preparedStatement!=null) preparedStatement.close();
        }
    }

    /**
     * Metodo che consente di controllare, se i campi inseriti dall'utente, sono già inserite nel database
     * @param user classe UTENTE in cui sono definite le informazioni dell'utente, necessarie per la registrazione
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
     boolean checkUser(User user) throws SQLException {
         // Query che controlla se le informazioni dell'utente non sono già state inserite
        String query = "SELECT * FROM users WHERE username=? OR numero_telefono=? OR email=?";
        // Connesione al db
        connection = DBConnection.connect();
        // Oggetti per interrogare il db
        ResultSet resultSet=null;
        PreparedStatement preparedStatement=null;

        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getTelefono());
            preparedStatement.setString(3, user.getEmail());

            resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            //Chiusura oggetti
            if(preparedStatement!=null) preparedStatement.close();
            if(resultSet!=null) resultSet.close();
        }

        return false;
    }

    /**
     * Metodo che consente di controllare se l'utente ha inserito dei campi vuoti
     * @param user classe UTENTE, troviamo i campi obbligatori USERNAME, la PASSWORD e il TELEFONO
     * @return true se ci sono spazi vuoti, viceversa false
     */
    boolean checkEmptySpace(User user){
         return user.getUsername().equals("") ||
            user.getTelefono().equals("") ||
            user.getEmail().equals(""); }
}
