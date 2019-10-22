package twitter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main che crea una finestra iniziale del login
 * Crea un unica istanza del database
 * Crea le tabelle user e message
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        primaryStage.setTitle("Accedi");
        primaryStage.setScene(new Scene(root, 300, 300));
        primaryStage.show();

        DBConnection db = DBConnection.getInstance();
        db.createTableUser();
        db.createTableMessage();
        db.createTableFollower();
    }

    public static void main(String[] args) { launch(args); }
}
