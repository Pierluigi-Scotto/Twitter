package twitter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * La classe si occupa di definire
 * 1. l'interfaccia utente tramite il layout LOGIN.FXML
 * 2. permette di accedere, come UTENTE o ADMIN, tramite bottoni
 * 3. permette di registrarsi al sito, tramite bottoni
 */
public class LoginControl {
    // Variabili fx
   @FXML private TextField usernameLogin;
   @FXML private PasswordField passwordLogin;
   @FXML private Label errorLogin;
   private Login login = new Login();
    // Oggetto utente in cui si immagazinano le informazioni
   private User user = new User();

   /**
     * Metodo che permette di entrare nel sito, cliccando il bottone
     * @param event avvia una nuova finestra come UTENTE definita nel layout TWITTER.FXML
    *             oppure come AMMINISTRATORE definita nel layout ADMIN.FXML
     * @throws IOException lancia un eccezione nel caso si verifichino degli errori
     */
   public void buttonSignIn(ActionEvent event) throws IOException, SQLException {
       // Inserimento username e password nella classe user
       user.setUsername(usernameLogin.getText());
       user.setTelefono(usernameLogin.getText());
       user.setEmail(usernameLogin.getText());
       user.setPassword(passwordLogin.getText());

       // Controlla se l'utente è registrato al sito
       if(!login.checkEmptySpace(user)) {
           if (login.checkUser(user)) {
               // Controlla se l'utente è un amministratore
               // Apre una finistra esclusiva per l'amministratore
               if (login.checkIfAdmin(user)) {
                   ((Node) event.getSource()).getScene().getWindow().hide();
                   Stage primaryStage = new Stage();
                   primaryStage.setTitle("Admin Twitter");
                   Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
                   Scene scene = new Scene(root, 600, 430);
                   primaryStage.setScene(scene);

                   primaryStage.show();
                   //Oppure apre la finestra del sito
               } else {
                   ((Node) event.getSource()).getScene().getWindow().hide();
                   Stage primaryStage = new Stage();
                   primaryStage.setTitle("Twitter");
                   FXMLLoader loader = new FXMLLoader();
                   Pane root = loader.load(getClass().getResource("Twitter.fxml").openStream());
                   TwitterControl twitterControl = loader.getController();
                   twitterControl.getUser(user.getUsername());
                   Scene scene = new Scene(root, 700, 500);
                   primaryStage.setScene(scene);
                   primaryStage.show();
               }
           } else {
               errorLogin.setText("Errore: username e password errati");
           }
       }
       else{ errorLogin.setText("Errore: spazi vuoti"); }
   }

    /**
     * Metodo che permette di registrarsi al sito, cliccando il bottone
     * @param event avvia una nuova finestra definita nel layout REGISTRATION.FXML
     * @throws Exception lancia un eccezione nel caso si verifichino degli errori
     */
   public void buttonSignUp(ActionEvent event) throws Exception{
       ((Node)event.getSource()).getScene().getWindow().hide();
       Stage primaryStage = new Stage();
       primaryStage.setTitle("Registrati");
       Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
       Scene scene = new Scene(root,300,450);
       primaryStage.setScene(scene);
       primaryStage.show();
   }
}
