import java.util.HashMap;

public class Main {
    private Azioni azioni = new Azioni();
    private PlayersSetup playersSetup = azioni.getPlayersSetup();


    public static void main(String[] args) {
        Main app = new Main();
        app.startGame();
    }

    public void startGame() {
        playersSetup.giocatoriSetup();
        Player giocatore = playersSetup.getPersonaggi().get(0);
        azioni.iniziaGioco(giocatore);
    }
}