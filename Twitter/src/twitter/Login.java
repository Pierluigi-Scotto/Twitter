package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * La classe si occupa di
 * 1. implementare i metodi che vengono usati dall'interfaccia utente (classe LoginControl)
 * 2. controllare se esiste l'utente
 * 3. controllare se l'utente è un amministratore
 */
class Login {
    /**
     * Metodo che consente di controllare, tramite accesso database, se l'utente che inserisce
     * le credenziali sono corrette per entrare nel sito
     * @param user classe UTENTE in cui sono definite le informazioni dell'utente, necessarie per l'accesso
     *             Troviamo i campi obbligatori USERNAME, EMAIL, TELEFONO e PASSWORD
     * @return true se l'utente è nel database, false viceversa
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    boolean checkUser(User user) throws SQLException {
        // Query che controlla se esistono le informazioni inserite dall'utente
        String query = "SELECT * FROM users WHERE (username=? OR email=? OR numero_telefono=?)AND password=?";
        // Oggetti per interrogare il database
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        // Connesione al database
        Connection connection = DBConnection.connect();

        try {
            // Controlla che non ci siano spazi vuoti
            if(!checkEmptySpace(user)){
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,user.getUsername());
                preparedStatement.setString(2,user.getEmail());
                preparedStatement.setString(3,user.getTelefono());
                preparedStatement.setString(4,user.getPassword());

                resultSet = preparedStatement.executeQuery();

                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (resultSet!=null) resultSet.close();
            if (preparedStatement!=null) preparedStatement.close();
            if (connection !=null) connection.close();
        }
        return false;
    }

    /**
     * Metodo che consente di controllare se l'utente è un admin
     * @param user classe UTENTE in cui sono definite le informazioni dell'utente necessarie per l'accesso, troviamo i
     *             campi obbligatori USERNAME, EMAIL, TELEFONO e PASSWORD
     * @return true se l'utente è un admin, viceversa false
     */
    // Controlla se l'utente è un admin
    boolean checkIfAdmin(User user){
        return (user.getUsername().equals("admin")
                || user.getEmail().equals("admin@test.it")
                || user.getTelefono().equals("0000000000"))
                && user.getPassword().equals("admin");
    }

    /**
     * Metodo che consente di controllare se l'utente ha inserito dei campi vuoti
     * @param user classe UTENTE, troviamo i campi USERNAME e la PASSWORD
     * @return true se ci sono spazi vuoti, viceversa false
     */
     boolean checkEmptySpace(User user){ return user.getUsername().equals("") && user.getPassword().equals(""); }
}
