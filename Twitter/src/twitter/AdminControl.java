package twitter;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

public class AdminControl {
    @FXML private TextField searchWord;
    @FXML private ListView<String> tweetsWordListView;
    @FXML private ListView<String> tweetsHashtagListView;
    @FXML private ListView<String> tweetsUsersReciveListView;
    @FXML private ListView<String> tweetsUsersSentListView;
    @FXML private Label errorWord;

    private Admin admin = new Admin();

    /**
     * Metodo che consente di visualizzare i TWEET data una parola
     */
    public void searchWordButton(){
        String word = searchWord.getText();
        try{
            if(!word.equals("")){
                ArrayList<String> tweetsWordArrayList = admin.searchWordTweet(word);
                ObservableList<String> tweetsWordList = observableArrayList(tweetsWordArrayList);
                tweetsWordListView.setItems(tweetsWordList);
                errorWord.setText("");
            }else{
                errorWord.setText("Errore: spazio vuoto");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo che consente di mostrare i TWEET in base agli hashtag
     */
    public void showHahtagButton(){
        try{
            ArrayList<String> tweetsHashtagArrayList = admin.showHashtag();
            ObservableList<String> tweetsHashtagList = observableArrayList(tweetsHashtagArrayList);
            tweetsHashtagListView.setItems(tweetsHashtagList);
            errorWord.setText("");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo che consente di mostrare gli utenti in base ai messaggi ricevuti
     */
    public void showUsersReciveButton(){
        try{
            ArrayList<String> tweetsUsersArrayList = admin.showUsersRecive();
            ObservableList<String> tweetsUsersList = observableArrayList(tweetsUsersArrayList);
            tweetsUsersReciveListView.setItems(tweetsUsersList);
            errorWord.setText("");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo che consente di mostrare gli utenti in base ai messaggi inviati
     */
    public void showUsersSentButton(){
        try{
            ArrayList<String> tweetsUsersArrayList = admin.showUsersSent();
            ObservableList<String> tweetsUsersList = observableArrayList(tweetsUsersArrayList);
            tweetsUsersSentListView.setItems(tweetsUsersList);
            errorWord.setText("");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo che consente di uscire dal sito
     * @param event uscita dal sito
     * @throws IOException lancia un eccezione nel caso si verifichino degli errori
     */
    public void logout(ActionEvent event) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Accedi");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }
}
