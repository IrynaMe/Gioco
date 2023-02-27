import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class PlayersSetup {

    private Properties properties = null;

    private ArrayList<Player> personaggi;//giocatore,avversario,guardia
    String[] preferenzeGiocatore;//nome e livello difficoltà di Gioco

    HashMap<String, Integer> impostazioni = new HashMap<String, Integer>(); //random numeri per opportunità degli avvenimenti

    public HashMap<String, Integer> getImpostazioni() {
        return impostazioni;
    }


    public ArrayList<Player> getPersonaggi() {
        return personaggi;
    }

    //returns preferenze di giocatore -> dipendono dal livello scelto-<per usare in giocatoriSetup()
    public String[] introduzione() {
        Scanner scanner = new Scanner(System.in);
        String livello = "";

        System.out.println("Stai per creare un nuovo giocatore");
        System.out.println("Inserisci il nome: ");
        String nome = scanner.next().trim().toUpperCase();

        System.out.println("Ciao " + nome + " stai per iniziare il gioco!");
        System.out.println("Scegli il livello di gioco: ");
        //avversario Orco: energia 30, Giocatore: energia 60, Guardia=30
        System.out.println("1 -> semplice ");
        //avversario Mostro: energia 50, Giocatore: energia 50, Guardia=40
        System.out.println("2 -> medio ");
        //avversario Goblin: energia 70, Giocatore: energia 40, Guardia=50
        System.out.println("3 -> difficile ");

        boolean flag = false;
        while (!flag) {
            System.out.println("Inserisci la scelta: ");
            livello = scanner.next().trim();
            flag = livello.equals("1") || livello.equals("2") || livello.equals("3");
            if (!flag) {
                System.out.println("Scelta errata");
            }
        }
        preferenzeGiocatore = new String[2];
        preferenzeGiocatore[0] = nome;
        preferenzeGiocatore[1] = livello;
        return preferenzeGiocatore;
    }


    //prende preferenze di giocatore->crea personaggi->returns ArrayList di personaggi
    public ArrayList<Player> giocatoriSetup() {
        preferenzeGiocatore = introduzione();
        String nomeGiocatore = preferenzeGiocatore[0];
        String livello = preferenzeGiocatore[1];
        String nomeGuardia = "Guardia";
        String nomeAvversario = "";

        int energiaStartGiocatore = 0;
        int energiaStartGuardia = 0;
        int energiaStartAvversario = 0;

        int moneteStart = 0;
        int numGuadagno = 0;
        //numero random per entrare nella città dopo primo contatto con la guardia: //1-4 Difficile, 1-3 Medio, 1-2 Facile
        //es.(int) (Math.random() * 2 + 1);
        int numRandFortunaFino50 = 0;
        //numero random per entrare nella città dopo parlare con la guardia: //50% Difficile, 60% Medio, 70% Facile
        int numRandFortunaGrande = 0;


        try {
            FileReader reader = new FileReader("config.properties");
            properties = new Properties();
            properties.load(reader);

            switch (livello) {
                case "1":
                    energiaStartGiocatore = Integer.parseInt(properties.getProperty("GiocatoreSemplice").trim());
                    energiaStartGuardia = Integer.parseInt(properties.getProperty("GuardiaSemplice").trim());
                    nomeAvversario = "ORCO";
                    energiaStartAvversario = Integer.parseInt(properties.getProperty("Orco").trim());

                    numRandFortunaFino50 = Integer.parseInt(properties.getProperty("numRandFortunaFino50_Semplice").trim());
                    numRandFortunaGrande = Integer.parseInt(properties.getProperty("numRandFortunaGrande_Semplice").trim());

                    moneteStart = Integer.parseInt(properties.getProperty("monete_Semplice").trim());
                    numGuadagno = Integer.parseInt(properties.getProperty("numGuadagno_Semplice").trim());

                    break;
                case "2":
                    energiaStartGiocatore = Integer.parseInt(properties.getProperty("GiocatoreMedio").trim());
                    energiaStartGuardia = Integer.parseInt(properties.getProperty("GuardiaMedio").trim());
                    nomeAvversario = "MOSTRO";
                    energiaStartAvversario = Integer.parseInt(properties.getProperty("Mostro").trim());

                    numRandFortunaFino50 = Integer.parseInt(properties.getProperty("numRandFortunaFino50_Medio").trim());
                    numRandFortunaGrande = Integer.parseInt(properties.getProperty("numRandFortunaGrande_Medio").trim());

                    moneteStart = Integer.parseInt(properties.getProperty("monete_Medio").trim());
                    numGuadagno = Integer.parseInt(properties.getProperty("numGuadagno_Medio").trim());
                    break;
                case "3":
                    energiaStartGiocatore = Integer.parseInt(properties.getProperty("GiocatoreDifficile").trim());
                    energiaStartGuardia = Integer.parseInt(properties.getProperty("GuardiaDifficile").trim());
                    nomeAvversario = "GOBLIN";
                    energiaStartAvversario = Integer.parseInt(properties.getProperty("Goblin").trim());

                    numRandFortunaFino50 = Integer.parseInt(properties.getProperty("numRandFortunaFino50_Difficile").trim());
                    numRandFortunaGrande = Integer.parseInt(properties.getProperty("numRandFortunaGrande_Difficile").trim());

                    moneteStart = Integer.parseInt(properties.getProperty("monete_Difficile").trim());
                    numGuadagno = Integer.parseInt(properties.getProperty("numGuadagno_Difficile").trim());
                    break;
                default:
                    System.out.println("Scelta errata");
            }

        } catch (FileNotFoundException e) {
            System.out.println("File non trovato: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Non posso leggere il file: " + e.getMessage());
        }

        impostazioni.put("numRandFortunaFino50", numRandFortunaFino50);
        impostazioni.put("numRandFortunaGrande", numRandFortunaGrande);
        impostazioni.put("numGuadagno", numGuadagno);


        Player giocatore = new Player(nomeGiocatore, energiaStartGiocatore, energiaStartGiocatore, moneteStart, moneteStart);
        Player avversario = new Player(nomeAvversario, energiaStartAvversario, energiaStartAvversario);
        Player guardia = new Player(nomeGuardia, energiaStartGuardia, energiaStartGuardia);
        personaggi = new ArrayList<>();
        personaggi.add(giocatore);
        personaggi.add(avversario);
        personaggi.add(guardia);
        return personaggi;
    }


}
