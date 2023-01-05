package chavardage.networkManager;

public class ServerAlreadyOpen extends Throwable {
    private final String serveur;
    public ServerAlreadyOpen(String serveur) {
        this.serveur=serveur;
    }

    public String toString(){
        return "le serveur " + serveur + " a déjà été lancé";
    }
}
