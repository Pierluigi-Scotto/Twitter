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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static javafx.collections.FXCollections.observableArrayList;

/**
 * La classe si occupa di
 * 1. definire l'interfaccia utente
 * 2. mandare un tweet
 * 3. cercare un follower
 * 4. visualizzare lista tweet
 * 5. visualizzare lista follower
 */
public class TwitterControl {
    //Variabili FXML
    @FXML private ListView<String> tweetsListView;
    @FXML private ListView<String> followerListView;
    @FXML private ListView<String> followingListView;
    @FXML private TextField searchFollowerText;
    @FXML private TextArea tweetTextArea;
    @FXML private Label errorFollower;
    @FXML private Label welcomeBack;
    //Variabili
    private Twitter twitter = new Twitter();
    private Tweet tweet = new Tweet();
    private ConcreteMediator concreteMediator = new ConcreteMediator();
    private User user = new User();
    private UserAbs userAbs = new User();

    /**
     * Metodo che consente di ricavare l'utente che ha effettuato l'accesso
     * @param username utente che ha effettuato l'acceso
     */
    void getUser(String username){
        this.user.setUsername(username);
        welcomeBack.setText("Bentornato "+user.getUsername());
    }

    /**
     * Metodo che consente di visualizzare la lista dei TWEET dei FOLLOWER dell'utente
     */
    public void showListTweet(){
        try {
            ArrayList<String> tweetsArrayList = twitter.showTweet(user.getUsername());
            ObservableList<String> tweetsList = observableArrayList(tweetsArrayList);
            tweetsListView.setItems(tweetsList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che consente di visualizzare la lista dei FOLLOWING dell'utente
     */
    public void showListFollowing(){
        try {
            ArrayList<String> followingArrayList = twitter.showFollowing(user.getUsername());
            ObservableList<String> listFollowing = observableArrayList(followingArrayList);
            followingListView.setItems(listFollowing);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Metodo che consente di visualizzare la lista dei FOLLOWER dell'utente
     */
    public void showListFollower(){
        try {
            ArrayList<String> followersArrayList = twitter.showFollower(user.getUsername());
            ObservableList<String> listFollowers = observableArrayList(followersArrayList);
            followerListView.setItems(listFollowers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo che consente di scrivere un TWEET e inviarlo agli utenti che seguono l'utente
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    public void buttonTweet() throws SQLException {
        if(!tweetTextArea.getText().equals(""))
        {
            tweet.setCorpo(tweetTextArea.getText());
            tweet.setHashtag(twitter.searchHashtag(tweet.getCorpo()));
            userAbs.setUsername(user.getUsername());
            userAbs.setMediator(concreteMediator);
            concreteMediator.getFollow(userAbs.getUsername());
            userAbs.send(tweet.getCorpo(), tweet.getHashtag());
            tweetTextArea.setText("");
        }
    }

    /**
     * Metodo che consente di cercare un FOLLOWER e lo inserisce nel database
     * @throws SQLException lancia un eccezione nel caso si verifichino degli errori
     */
    public void buttonSearchFollower() throws SQLException{
        //Controlla se non ci sono spazi vuoti
        if(!twitter.checkEmptySpace(searchFollowerText.getText())) {
            //Controlla se esiste il follower e non lo si sta già seguendo
            //Controlla se non si inserisce l'utente che ha effettuato l'accesso
            if (twitter.checkFollower(searchFollowerText.getText())
                    && !twitter.checkFollower(user,searchFollowerText.getText())
                    && !user.getUsername().equals(searchFollowerText.getText())) {
                twitter.insertFollowerInDatabase(user, searchFollowerText.getText());
                searchFollowerText.setText("");
                errorFollower.setText("");
            }
            else if(twitter.checkFollower(user,searchFollowerText.getText())){
                searchFollowerText.setText("");
                errorFollower.setText("Errore: utente già seguito");
            }
            else if(!twitter.checkFollower(searchFollowerText.getText())){
                searchFollowerText.setText("");
                errorFollower.setText("Errore: utente non trovato");
            }
            else if(user.getUsername().equals(searchFollowerText.getText())){
                searchFollowerText.setText("");
                errorFollower.setText("Errore: non puoi seguirti");
            }
        }
        else{
            errorFollower.setText("Errore: spazio vuoto");
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
