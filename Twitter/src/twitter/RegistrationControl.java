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


/**
 * La classe si occupa di
 * 1. definire l'interfaccia utente
 * 2. registrasi con appositi bottoni
 */

public class RegistrationControl {
    // Variabili FX
    @FXML private TextField usernameRegistration;
    @FXML private PasswordField passwordRegistration;
    @FXML private TextField nomeRegistration;
    @FXML private TextField cognomeRegistration;
    @FXML private TextField numeroRegistration;
    @FXML private TextField emailRegistration;
    @FXML private Label errorRegistration;
    private Registration registration = new Registration();
    // Oggetto utente in cui si immagazinano le informazioni
    private User user = new User();

    /**
     * Metodo che consente di registrare l'utente al servizio
     * @param event avvia una nuova finestra twitter dopo la registrazione
     * @throws Exception lancia un eccezione nel caso si verifichino degli errori
     */
    public void buttonSubscribe(ActionEvent event) throws Exception {
        // Nuovo utente
        user.setUsername(usernameRegistration.getText());
        user.setPassword(passwordRegistration.getText());
        user.setNome(nomeRegistration.getText());
        user.setCognome(cognomeRegistration.getText());
        user.setTelefono(numeroRegistration.getText());
        user.setEmail(emailRegistration.getText());

        // Controlla se l'username
        // o il numero di telefono
        // o l'email sono campi vuoti
        if(!registration.checkEmptySpace(user)){
            // Controlla se l'utente non è già registrato
            if(!registration.checkUser(user)){
                // Inserimento nel database
                registration.insertUserInDatabase(user);

                // Apertura nuova finestra
                ((Node)event.getSource()).getScene().getWindow().hide();
                Stage primaryStage = new Stage();
                primaryStage.setTitle("Twitter");
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("Twitter.fxml").openStream());
                TwitterControl twitterControl = loader.getController();
                twitterControl.getUser(user.getUsername());
                Scene scene = new Scene(root,700,425);
                primaryStage.setScene(scene);
                primaryStage.show();
            } else{
                errorRegistration.setText("Utente già registrato");
            }
        }else{
            errorRegistration.setText("Campi vuoti");
            }
    }
}
