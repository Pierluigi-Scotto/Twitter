package twitter;

/**
 * Classe che consente di definire il tweet
 */

public class Tweet {
    private String mittente="";
    private String destinatario="";
    private String corpo="";
    private String hashtag="";

    //Costruttore standard
    public Tweet(){}

    //Costruttore con parametri
    public Tweet(String mittente, String destinatario, String corpo, String hashtag){
        this.mittente=mittente;
        this.destinatario=destinatario;
        this.corpo=corpo;
        this.hashtag=hashtag;
    }
    /**
     * Metodi setter che consentono di settare le variabili definite nella classe
     */
    void setMittente(String mittente) { this.mittente = mittente; }
    void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    void setCorpo(String corpo){this.corpo=corpo;}
    void setHashtag(String hashtag) { this.hashtag = hashtag; }

    /**
     * Metodi getter che consentono di ritornare le variaibli definite nella classe
     */
    String getMittente() { return mittente; }
    String getDestinatario() { return destinatario; }
    String getCorpo() { return corpo; }
    String getHashtag() { return hashtag; }
}
