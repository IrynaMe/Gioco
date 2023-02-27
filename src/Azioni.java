
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;

public class Azioni {

    private Properties properties = null;
    Conversazioni conversazioni = new Conversazioni();
    Scanner scanner = new Scanner(System.in);
    Combat combat = new Combat();

    ArrayList<Integer> indovinelliCoretto = new ArrayList<>();//per risposte corrette nel metodo est()
    ArrayList<Integer> indovinelliErrato = new ArrayList<>();//per risposte sbagliate nel metodo est()->si distrugge al fine del metodo
    ArrayList<Integer> indovinelliErratoBackup = new ArrayList<>();//per controllo del est()->non si distrugge
    PlayersSetup playersSetup = new PlayersSetup();

    public PlayersSetup getPlayersSetup() {
        return playersSetup;
    }

    HashMap<String, Integer> impostazioni = playersSetup.getImpostazioni();

    public void iniziaGioco(Player player) {
        System.out.println("* Hai un pacco importante da consegnare in città Citabella per un Signore Daipacchetti.");
        System.out.println("* Quando entri in città, trova una taverna, là ti daranno indicazioni per incontrare il Signore Daipacchetti.");
        System.out.println("* Hai fatto una strada lunga e finalmente sei vicino la città.");
        System.out.println("* La tua energia é: " + player.getEnergiaAttuale());
        System.out.println("* Hai " + player.getMoneteAttuale() + " monete.");
        System.out.println("Buone avventure!");
        try {
            entrataCitta(player);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //entra se: 1 ha vinto il avversario(boolean)
    // 2 ha lettera di invito (boolean)
    // 3 sceglie di parlare e ha fortuna(random)->puo provare 2 volte, ogni volta in caso di sfortuna -perde energia
    // 4 attacca e vince guardia
    public void entrataCitta(Player player) throws InterruptedException {
        String scelta1 = "";
        String scelta2 = "";
        String scelta3 = "";
        String scelta4 = "";
        String scelta5 = "";

        int numA = impostazioni.get("numRandFortunaFino50");//range 1-4 diff, 1-3 media, 1-2facile
        int numRandFortunaFino50 = 0;
        int numB = impostazioni.get("numRandFortunaGrande");// range 1-100 diff, 1-86 medio, 1-75 facile
        int numRandFortunaGrande = 0;
        String risposta = "";


        Thread.sleep(1000);
        System.out.println("------------------------------------------------------------------");
        System.out.println("* Sei all'entrata della città.");
        System.out.println("* Una guardia è all'impiedi davati a te.");
        System.out.println();
        System.out.println("* Cosa pensi di fare?");
        System.out.println();
        System.out.println("P -> Parli con la guardia");
        System.out.println("A -> Attacchi la guardia");
        System.out.println("S -> Scappi dalla guardia");
        System.out.println("* La tua energia é: " + player.getEnergiaAttuale());
        System.out.println("------------------------------------------------------------------");

        boolean flag = false;
        while (!flag) {
            System.out.println("Inserisci la scelta: ");
            scelta1 = scanner.next().trim().toUpperCase();
            flag = scelta1.equals("P") || scelta1.equals("A") || scelta1.equals("S");
            if (!flag) {
                System.out.println("Scelta errata.");
            }
        }

        //Blocco parlare->2 volte possibilità di entrare (random)
        if (scelta1.equals("P")) {
            //se player scappato dalla guardia (boolean)
            if (player.isScappato() && !player.isPermesso()&&!player.isGuardiaContenta()) {
                Thread.sleep(1000);
                System.out.println("GUARDIA: Ciao straniero, ti ricordo così il tuo nome è "
                        + player.getNome());
                Thread.sleep(1000);
                System.out.println("GUARDIA:...Hai scappato da me l'altra volta, cosa vuoi adesso?");

                Thread.sleep(1000);
                System.out.println(player.getNome() + ": si, avevo un impegno urgente all'incrocio, " +
                        "ma sono tornato.");

                if (player.isSconfittoGuardia()) {
                    Thread.sleep(1000);
                    System.out.println("GUARDIA: ti conosco " + player.getNome() + "!");
                    Thread.sleep(1000);
                    System.out.println("Hai mandato il mio collega in ospedale! Sei fortunato, lui non mi piaceva tanto");
                    Thread.sleep(1000);
                    System.out.println("Ma sei ricercato. Allora pagami 15 monete o ti butto in prigione! ");

                    Thread.sleep(1000);
                    System.out.println("------------------------------------------------------------------");
                    System.out.println("Hai " + player.getMoneteAttuale() + " monete.");
                    System.out.println("* La tua energia é: " + player.getEnergiaAttuale());
                    System.out.println("P -> pagare | A -> Attaccare | S -> scappare");
                    System.out.println("Fai la scelta");

                    scelta4 = scanner.next().toUpperCase();
                    if (scelta4.equals("P")) {
                        if (player.getMoneteAttuale() > 15) {
                            player.setMoneteAttuale(player.getMoneteAttuale() - 15);
                            Thread.sleep(1000);
                            System.out.println("Hai pagato la multa. Adesso rimangono " + player.getMoneteAttuale());

                            Thread.sleep(1000);
                            System.out.println("GUARDIAN: Bravo. Ti faccio un favore: elimino la tua procedura in ufficio");
                            Thread.sleep(1000);
                            System.out.println("...Non sei ricercato più.");

                            player.setSconfittoGuardia(false);

                            Thread.sleep(1000);
                            System.out.println(player.getNome() + " Grazie, adesso posso entrare?");


                        } else {
                            Thread.sleep(1000);
                            System.out.println("* Non hai abbastanza monete. Devi attaccare o scappare. Fai la scelta");
                            scelta5 = scanner.next().toUpperCase(); //
                        }
                    }
                }
            } else {
                Thread.sleep(1000);
                System.out.println("GUARDIA: Ciao straniero, come ti chiami?");
            }

            //random prima opportunità di entrare dipende dal livello: 1/2 Semplice; 1/3 medio, 1/4 difficile
            numRandFortunaFino50 = (int) (Math.random() * numA + 1);
            if (numRandFortunaFino50 != 1 && !player.isSconfittoAvversario()) {
                Thread.sleep(1000);
                System.out.println(player.getNome() + ": ...sono " + player.getNome() + ", posso passare?");
                Thread.sleep(1000);
                if (!player.isSconfittoAvversario()) {
                    System.out.println("GUARDIA: Mi dispiace ma noi non facciamo entrare stranieri nella nostra città.");
                    //giocatore perde energia random da 1 a 15
                    int numMulta = (int) (Math.random() * 15 + 1);
                    player.setEnergiaAttuale(player.getEnergiaAttuale() - numMulta);
                    controlloEnergia(player);

                    Thread.sleep(1000);
                    System.out.println("------------------------------------------------------");
                    System.out.println("* Sei deluso e hai perso " + numMulta + " di energia.");
                    System.out.println("* Adesso la energia è: " + player.getEnergiaAttuale());
                    System.out.println();
                    System.out.println("* Cosa pensi di fare?");
                    System.out.println("P: Provare di convincere la guardia a farti entrare");
                    System.out.println("A: Attacchi la guardia");
                    System.out.println("S: Scappi dalla guardia");


                    boolean flag1 = false;
                    while (!flag1) {
                        System.out.println("Inserisci la scelta: ");
                        scelta2 = scanner.next().toUpperCase();
                        flag1 = scelta2.equals("P") || scelta2.equals("A") || scelta2.equals("S");
                        if (!flag1) {
                            System.out.println("Scelta errata.");
                        }
                    }

                    if (scelta2.equals("P")) {
                        int numRanRispostaGiocatore = (int) (Math.random() * 8); //for array conversazioni ind 0-7
                        risposta = conversazioni.getConvincereGuardia()[numRanRispostaGiocatore];
                        Thread.sleep(1000);
                        System.out.println(player.getNome() + ": " + risposta);

                        //random seconda opportunità entrare circa 50% per entrare-difficile, 60% medio, 70% semplice
                        numRandFortunaGrande = (int) (Math.random() * numB);// range 1-100 diff, 1-86 medio, 1-75 facile
                        if (numRandFortunaGrande > 50) {
                            Thread.sleep(1000);
                            System.out.println("----------------------------------------------------------");
                            System.out.println("* La Guardia sembra arrabbiata, non ti fa entrare.");
                            //giocatore perde energia random da 5 a 15
                            int multaEnergia = (int) (Math.random() * 10 + 5);
                            player.setEnergiaAttuale(player.getEnergiaAttuale() - multaEnergia);
                            controlloEnergia(player);

                            System.out.println("* Sei deluso e hai perso " + multaEnergia + " di energia.");
                            System.out.println("* Adesso la energia è: " + player.getEnergiaAttuale());
                            Thread.sleep(1000);
                            System.out.println("----------------------------------------------------------");
                            System.out.println("* Allora non è utile a parlare, devi attaccare o scappare, cosa fai?");
                            System.out.println("A: Attacchi la guardia");
                            System.out.println("S: Scappi dalla guardia");


                            boolean flag3 = false;
                            while (!flag3) {
                                System.out.println("Inserisci la scelta: ");
                                scelta3 = scanner.next().toUpperCase();
                                flag3 = scelta3.equals("A") || scelta3.equals("S");
                                if (!flag3) {
                                    System.out.println("Scelta errata.");
                                }
                            }
                        }
                    }
                }
            }
        }

        // Blocco che permette entrare:
        // puoi entrare se: -hai fortuna(random)
        // -hai la lettera dal Capitano: dopo metodo ovest()
        // -sconfitto avversario :dopo metodo startAttacco()
        if ((numRandFortunaGrande <= 50 && numRandFortunaGrande != 0)
                || numRandFortunaFino50 == 1 || player.isPermesso() || player.isSconfittoAvversario()) {
            if (numRandFortunaFino50 == 1 && !player.isPermesso() && !player.isSconfittoAvversario()) {

                Thread.sleep(1000);
                System.out.println(player.getNome() + ": Ciao buon uomo, sono " + player.getNome());
                Thread.sleep(1000);
                System.out.println("...Ho fatto un viaggio lungo per arrivare quà," +
                        "cerco un Signore per consegnarlo un pacco importante");
            //se sconfitto avversario ma guardia non lo sa(boolean isGuardiaContenta)
            } else if (player.isSconfittoAvversario()&&player.isGuardiaContenta()==false) {
                Thread.sleep(1000);
                System.out.println("GUARDIA: Perchè hai un livido sotto l'occhio?");
                Thread.sleep(1000);
                System.out.println(player.getNome() + ": " +
                        "Ho ucciso questo " + playersSetup.getPersonaggi().get(1).getNome() + " che mi dava fastidio..");
                Thread.sleep(1000);
                System.out.println("GUARDIA: Hai ucciso " + playersSetup.getPersonaggi().get(1).getNome() + "?!");
                Thread.sleep(1000);
                System.out.println("GUARDIA: Sei un bravo uomo!");
                player.setGuardiaContenta(true);
                //se player ha lettera ->metodo ovest()->boolean isPermesso
            } else if (player.isPermesso()) {
                Thread.sleep(1000);
                System.out.println(player.getNome()+":...Ho la lettera, firmata dal Capitano della guardia! Sbrigati!");
            //se player sconfitto avversario (boolean) e l'ho gia detto a guardia prima(boolean isGuardiaContenta)
            } else if (player.isSconfittoAvversario()&&player.isGuardiaContenta()==true) {
                System.out.println("GUARDIA: Ah! Ti ricordo: sei "+player.getNome()+"! Hai salvato la nostra città!");
            }

            Thread.sleep(1000);
            System.out.println("GUARDIA: Va bene, va bene, puoi entrare.");

            int numBonus = (int) (Math.random() * 25 + 10);
            Thread.sleep(1000);
            System.out.println("----------------------------------------------------------");
            System.out.println("* Auguri! Puoi entrare in città!");
            if (numRandFortunaFino50 == 1 || numRandFortunaGrande <= 50) {
                System.out.println("* Sei fortunato: la Guardia aveva un buon umore.");
                System.out.println("* Ma la prossima volta può essere non cosi' facile.");

            }
            player.setEnergiaAttuale(player.getEnergiaAttuale() + numBonus);
            Thread.sleep(1000);
            System.out.println("----------------------------------------------------------");
            System.out.println("* Tu hai ottenuto bonus: +" + numBonus + " di energia!");
            System.out.println("* Adesso la energia è: " + player.getEnergiaAttuale());
            player.setCitta(true);
            dentroCitta(player);
        }


        //Blocco Attaccare->se vinci ricevi bonus energia=energiaStart di Guardia
        if (scelta1.equals("A") || scelta2.equals("A") || scelta3.equals("A") || scelta5.equals("A")) {
            Player guardia = playersSetup.getPersonaggi().get(2);
            startAttacco(player, guardia);
        }


        //Blocco Scappare->se scappi ricevi Multa diminuire energia o bonus energia->random 10-25
        if (scelta1.equals("S") || scelta2.equals("S") || scelta3.equals("S") || scelta5.equals("S")) {
            Thread.sleep(1000);
            System.out.println("----------------------------------------------------------");
            System.out.println("* Sei riuscito a scappare!");
            player.setScappato(true);
            //multa di energia
            int numEnergia = (int) (Math.random() * 16 + 5);
            if (numEnergia % 2 == 0) {
                numEnergia *= (-1);
                player.setEnergiaAttuale(player.getEnergiaAttuale() + numEnergia);
                controlloEnergia(player);
                System.out.println("* Hai fatto fatica, così hai " + numEnergia + " di energia");
            } else {
                player.setEnergiaAttuale(player.getEnergiaAttuale() + numEnergia);
                System.out.println("* Ti senti molto contente, la energia è elevata: hai +" + numEnergia + " di energia");
            }
            System.out.println("* Adesso la energia è: " + player.getEnergiaAttuale());
            incrocio(player);
        }

    }


    // se vince->bonus energia=energiaStart di guargia/avversario, altro dipende con chi combatte:
    // se vince la guardia-entra la città
    // se vince avversario-va a parlare con la Guardia per entrare nella città + taverna diventa aperta(metodo dentroCitta())
    public void startAttacco(Player giocatoreA, Player giocatoreB) {
        System.out.println("* Comincia il combattimento! Buona fortuna!");
        while (giocatoreA.getEnergiaAttuale() > 0 || giocatoreB.getEnergiaAttuale() > 0) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("-----------------------------------------------------------");
            System.out.println("* Attacca il Giocatore " + giocatoreA.getNome().toUpperCase());
            System.out.println("* ENERGIA del " + giocatoreB.getNome().toUpperCase() + ": ==="
                    + giocatoreB.getEnergiaAttuale()+"===");

            combat.attacco(giocatoreB);
            if (giocatoreB.getEnergiaAttuale() < 0) {
                giocatoreB.setEnergiaAttuale(0);
            }

            if (giocatoreB.getEnergiaAttuale() == 0 || giocatoreA.getEnergiaAttuale() == 0) {
                break;
            }

            System.out.println("-----------------------------------------------------------");
            System.out.println("* Attacca il giocatore " + giocatoreB.getNome().toUpperCase());
            System.out.println("* ENERGIA di " + giocatoreA.getNome().toUpperCase() + ": ==="
                    + giocatoreA.getEnergiaAttuale()+"===");
            combat.attacco(giocatoreA);//parte sempre playerA
            if (giocatoreA.getEnergiaAttuale() < 0) {
                giocatoreA.setEnergiaAttuale(0);
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        if (giocatoreB.getEnergiaAttuale() == 0) {
            giocatoreA.setEnergiaAttuale(giocatoreA.getEnergiaAttuale() + giocatoreB.getEnergiaStart());
            System.out.println("----------------------------------------------------------");
            System.out.println("* Hai vinto il combattimento! Hai ricevuto il bonus: energia dallo sfidante: "
                    + giocatoreB.getEnergiaStart());
            if (giocatoreB.equals(playersSetup.getPersonaggi().get(2))) {
                giocatoreA.setSconfittoGuardia(true);

                System.out.println("* La Guardia non può firmarti più, ora poi entrare in città e trovare la taverna!");
                System.out.println("* Ma stai attento: la Guardia non dimenticherà questo combattimento!");
                try {
                    dentroCitta(giocatoreA);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            if (giocatoreA.isNord()) {
                System.out.println("* Adesso sei un Eroe! Puoi parlare con la Guardia per entrare in città.");
                giocatoreA.setSconfittoAvversario(true);
                try {
                    entrataCitta(giocatoreA);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
            if (giocatoreA.isTaverna()) {
                System.out.println("* Non è puù pericoloso in città, taverna è aperta!");
                System.out.println("* Parla con la proprietaria e scopri come trovare il Signore Daipacchetti ");
                giocatoreA.setSconfittoAvversario(true);
                try {
                    dentroCitta(giocatoreA);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        if (giocatoreA.getEnergiaAttuale() == 0) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Purtroppo hai perso.");
            controlloEnergia(giocatoreA);
        }
    }

//sceglie 4 direzioni
    public void incrocio(Player player) {

        String scelta = "";
        System.out.println("----------------------------------------------------------");
        System.out.println("* Sei venuto all'incrocio, dove vuoi andare");
        System.out.println("N -> Nord");
        System.out.println("O -> Ovest");
        System.out.println("E -> Est");
        System.out.println("S -> Sud");

        boolean flag = false;
        while (!flag) {
            System.out.println("Inserisci la scelta: ");
            scelta = scanner.next().toUpperCase();
            flag = scelta.equals("N") || scelta.equals("E") || scelta.equals("S") || scelta.equals("O");
            if (!flag) {
                System.out.println("Scelta errata.");
            }
        }
        switch (scelta) {
            case "N":
                try {
                    nord(player);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "E":
                try {
                    est(player);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "O":
                try {
                    ovest(player);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "S":
                try {
                    entrataCitta(player);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                break;
        }
    }

    // puo riprendere energia ->se non ha già bevuto da 2 min
    // dopo scelta A) torna a incrocio B) incontra un avversario->
    // B1 scappa all'incrocio e perde energia oppure
    // B2 combatte. se sconfitta ->entrataCitta():va a parlare con Guardia chi lo farà passare. perde-game over
    public void nord(Player player) throws InterruptedException {

        System.out.println("------------------------------------------------------------------");
        LocalTime adesso = LocalTime.now();
        long minutes = 0;
        if (player.getBevutoAqua() != null) {
            LocalTime bevutoAqua = player.getBevutoAqua();
            Duration duration = Duration.between(bevutoAqua, adesso);
            minutes = duration.toMinutes();
            if (minutes < 2) {
                System.out.println("* C'è un fiume. Ma l'acqua sembra sporca. Meglio tornare quà dopo.");
            }
        }
        if (player.getBevutoAqua() == null || minutes >= 2) {
            System.out.println("* C'è un fiume. Puoi bere acqua e stare a riva.");
            int numBonus = (int) (Math.random() * 15 + 5);
            player.setEnergiaAttuale(player.getEnergiaAttuale() + numBonus);
            player.setBevutoAqua(LocalTime.now());
            Thread.sleep(1000);

            System.out.println("----------------------------------------------------------");
            System.out.println("* Hai ripreso energia!");
            System.out.println("* Adesso la energia è: " + player.getEnergiaAttuale());
        }

        System.out.println("* Ora puoi tornare all'incrocio o andare avanti a nord. cosa fai?");
        System.out.println();
        System.out.println("I -> Incrocio ");
        System.out.println("Altro -> Nord ");

        String scelta = scanner.next().toUpperCase();

        if (scelta.equals("I")) {
            incrocio(player);
        } else {
            Thread.sleep(1000);
            System.out.println("----------------------------------------------------------");
            System.out.println("* Camminando, sei avvicinato alla palude. C'è qualche movimento..");
            System.out.println("* Una figura spaventosa appare dalla palude: ");
            Player avversario = playersSetup.getPersonaggi().get(1);
            Thread.sleep(1000);
            System.out.println("* è un " + avversario.getNome() + "!");
            Thread.sleep(1000);
            System.out.println("----------------------------------------------------------");
            System.out.println("* La tua energia é: " + player.getEnergiaAttuale());
            System.out.println("* Fai la scelta:");
            System.out.println("S: Scappi da " + avversario.getNome());
            System.out.println("Alto: Attacchi " + avversario.getNome());
            String sceltaCombat = scanner.next().toUpperCase();
            if (sceltaCombat.equals("S")) {
                int numMulta = (int) (Math.random() * 15 + 5);
                player.setEnergiaAttuale(player.getEnergiaAttuale() - numMulta);
                Thread.sleep(1000);
                System.out.println("----------------------------------------------------------");
                System.out.println("* Sei scappato ma sei stanco: hai perso " + numMulta + " di energia.");
                controlloEnergia(player);
                System.out.println("* Adesso la energia è: " + player.getEnergiaAttuale());
                System.out.println("* Meglio tornate all'incrocio.");
                incrocio(player);
            } else {
                player.setNord(true);
                startAttacco(player, avversario);
            }
        }
    }

    //Prima visita:
    // trova lettera con cui puo entrare in città: 1 scappa o 2 parla con cacciatore
    // 2a-scappa (fortuna random)
    // 2b-cacciatore permette prendere lettera +multa energia
    // 2c-torna senza lettera all'incrocio
    //Altre visite: puoi guadagnare monete, ma se in prima visita sei scappato con lettera->cacciatore arrabbiato
    public void ovest(Player player) throws InterruptedException {
        String risposta = "";

        if (player.isOvest() || player.isSconfittoAvversario()) {
            System.out.println("---------------------------------------------------------");
            System.out.println("* Sei uscito in una radura e vedi un'antica capanna");
            System.out.println("* Il cacciatore ti incontra.");
            if (player.isCacciatoreArrabbiato()) {
                Thread.sleep(1000);
                System.out.println("CACCIATORE: Non pensavo che avresti osato apparire quà! Hai rubato la mia lettera! ");
                Thread.sleep(1000);
                System.out.println(player.getNome() + "Eh..Chiedo di scusarmi, mi dispiace tanto, dovevo per forza entrare in città..");
                Thread.sleep(1000);
                System.out.println("CACCIATORE: Ecco cosa ho deciso: fai il lavoro per me e saremo pari, altrimenti ti sparerò. Comincia!");
                int numMulta = (int) (Math.random() * 15 + 5);
                player.setEnergiaAttuale(player.getEnergiaAttuale() - numMulta);
                Thread.sleep(1000);
                System.out.println("---------------------------------------------------------");
                System.out.println("* Un'ora e passata, sei stanco, hai perso " + numMulta + " di energia");
                controlloEnergia(player);
                System.out.println("* Adesso la energia è: " + player.getEnergiaAttuale());
                player.setCacciatoreArrabbiato(false);

                Thread.sleep(1000);
                System.out.println("---------------------------------------------------------");
                System.out.println(player.getNome() + ": Speravo guadagnare un paio monete..");
                Thread.sleep(1000);
                System.out.println("CACCIATORE: non ho più bisogna di lavoro, torna dopo oppure vai a est.");
                Thread.sleep(1000);
                System.out.println("---------------------------------------------------------");
                System.out.println("* Fai la scelta: E-> Est, Altro -> Incrocio");
                String scelta = scanner.next().toUpperCase();
                if (scelta.equals("E")) {
                    est(player);
                } else {
                    incrocio(player);
                }
            } else {
                Thread.sleep(1000);
                System.out.println("---------------------------------------------------------");
                System.out.println("CACCIATORE: Cosa ti serve, viaggiatore?");
                Thread.sleep(1000);
                System.out.println(player.getNome() + ": Speravo guadagnare un paio monete, hai un lavoro da fare?");
                Thread.sleep(1000);

                int numRandLavoro = (int) (Math.random() * 6); //for array conversazioni ind 0-5
                risposta = conversazioni.getLavoroCacciatore()[numRandLavoro];
                System.out.println("CACCIATORE: Sei arrivato in tempo! Mi serve " + risposta);
                int guadagno = impostazioni.get("numGuadagno");
                Thread.sleep(1000);
                System.out.println("CACCIATORE: Ti pagherò " + guadagno + " monete.");
                int numMulta = (int) (Math.random() * 15 + 5);
                player.setEnergiaAttuale(player.getEnergiaAttuale() - numMulta);
                Thread.sleep(1000);
                System.out.println("------------------------------------------------");
                System.out.println("* Un'ora e passata, sei stanco, hai perso " + numMulta + " di energia");
                controlloEnergia(player);
                player.setMoneteAttuale(player.getMoneteAttuale() + guadagno);
                System.out.println("* Hai ricevuto " + guadagno + " monete!");
                System.out.println("* Adesso hai " + player.getMoneteAttuale() + " monete!");
                System.out.println("* Ora puoi continuare la tua strada!");
                incrocio(player);
            }

        } else {
            Thread.sleep(1000);
            System.out.println("---------------------------------------------------------");
            System.out.println("* La porta è aperta e su un tavola c'è una lettera:");
            System.out.println("* è un invito alla fiera firmato dal Capitano della Guardia!");
            System.out.println("* Ti puoi servire per entrare e uscire dalla città senza problemi!");
            Thread.sleep(1000);
            System.out.println();
            System.out.println("* Cosa fai?");
            System.out.println("P -> prendi la lettera e scappi");
            System.out.println("Altro -> Torni all'incrocio");
            String scelta = scanner.next().toUpperCase();
            if (scelta.equals("P")) {
                int numA = impostazioni.get("numRandFortunaFino50");//range 1-4 diff, 1-3 media, 1-2facile
                int numRiesciScappare = (int) (Math.random() * numA + 1);
                if (numRiesciScappare == 1) {
                    System.out.println("* Sei scappato con successo!");
                    player.setPermesso(true);
                    player.setOvest(true);
                    try {
                        entrataCitta(player);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Thread.sleep(1000);
                    System.out.println("---------------------------------------------------------");
                    System.out.println("* Appena esci dalla capanna, incontri un cacciatore, che sta tornando a casa");
                    Thread.sleep(1000);
                    System.out.println("* Puoi scappare o parlare con cacciatore");
                    System.out.println();
                    System.out.println("* Cosa fai?");
                    System.out.println("P -> Parli con cacciatore");
                    System.out.println("Altro -> Scappi");
                    String scelta1 = scanner.next().toUpperCase();
                    if (scelta1.equals("P")) {
                        Thread.sleep(1000);
                        System.out.println("---------------------------------------------------------");
                        System.out.println("CACCIATORE: Fermati! Chi sei? Cosa fai quà?");
                        Thread.sleep(1000);
                        System.out.println(player.getNome() + ": Ciao, mi sono perso e sto cercando la strada per la città");
                        int numCacciatoreCrede = (int) (Math.random() * numA + 1);
                        if (numCacciatoreCrede == 1) {
                            Thread.sleep(1000);
                            System.out.println("CACCIATORE: allora vai all'incrocio e dopo a sud! Ciao!");
                            Thread.sleep(1000);
                            System.out.println("---------------------------------------------------------");
                            System.out.println("* Sei fortunato! Sei sfuggito con la lettera! ");
                            System.out.println("* Adesso puoi parlare con la Guardia e mostrarla la lettera per entrare  in città!");
                            player.setCacciatoreArrabbiato(true);
                            player.setPermesso(true);
                            player.setOvest(true);
                            entrataCitta(player);
                        } else {
                            Thread.sleep(1000);
                            System.out.println("CACCIATORE: La città e lontano, sei sospettoso, non ti credo!");
                            Thread.sleep(1000);
                            System.out.println(player.getNome() + ": Va bene, ti dico la verità:");
                            Thread.sleep(1000);
                            System.out.println(player.getNome()+"...La guardia non mi fa entrare nella città, cosi cerco un modo...");
                            Thread.sleep(1000);
                            System.out.println(player.getNome()+"...Ho visto la tua casa era aperta e ho trovato la lettera, la preso, ecco..");
                            int numCacciatoreCapisce = (int) (Math.random() * numA + 1);
                            if (numCacciatoreCapisce == 1) {
                                Thread.sleep(1000);
                                System.out.println("CACCIATORE: Ahaha! Puoi prenderla, non volevo andare comunque!");
                                Thread.sleep(1000);
                                System.out.println("CACCIATORE:...Ma come punizione per il furto, mi aiuterai a tagliare la legna!");
                                int numMulta = (int) (Math.random() * 15 + 5);
                                player.setEnergiaAttuale(player.getEnergiaAttuale() - numMulta);
                                Thread.sleep(1000);
                                System.out.println("---------------------------------------------------------");
                                System.out.println("* Un'ora e passata, sei stanco, hai perso " + numMulta + " di energia");
                                controlloEnergia(player);
                                System.out.println("* Adesso la energia è: " + player.getEnergiaAttuale());
                                player.setPermesso(true);
                                player.setOvest(true);
                                entrataCitta(player);
                            } else {
                                Thread.sleep(1000);
                                System.out.println("CACCIATORE: Non mi piacciono i ladri! Pagami 5 monete, dammi la lettera e vai via se non vuoi che ti spari!");
                                if (player.getMoneteAttuale() < 5) {
                                    Thread.sleep(1000);
                                    System.out.println(player.getNome() + ": Ho solo " + player.getMoneteAttuale() + " monete");
                                    Thread.sleep(1000);
                                    System.out.println("CACCIATORE: Allora ne prendo tutte, via!");
                                    player.setMoneteAttuale(0);
                                    Thread.sleep(1000);
                                    System.out.println("---------------------------------------------------------");
                                    System.out.println("* Hai perso tutte le monete");
                                } else {
                                    player.setMoneteAttuale(player.getMoneteAttuale() - 5);
                                    System.out.println("* Hai perso 5 monete");
                                }
                                int numMulta = (int) (Math.random() * 15 + 5);
                                player.setEnergiaAttuale(player.getEnergiaAttuale() - numMulta);
                                controlloEnergia(player);
                                System.out.println("* Hai perso anche " + numMulta + " di energia.");
                                System.out.println("* Adesso la energia è: " + player.getEnergiaAttuale());
                                System.out.println("* Le monete rimangono: " + player.getMoneteAttuale());
                                System.out.println("* Meglio tornare all'incrocio ");
                                player.setOvest(true);
                                incrocio(player);
                            }
                        }
                    } else {
                        int numMulta = (int) (Math.random() * 15 + 5);
                        player.setEnergiaAttuale(player.getEnergiaAttuale() - numMulta);
                        Thread.sleep(1000);
                        System.out.println("---------------------------------------------------------");
                        System.out.println("* Sei scappato, ma hai perso " + numMulta + " di energia");
                        controlloEnergia(player);
                        System.out.println("* Adesso la energia è: " + player.getEnergiaAttuale());
                        System.out.println("* Ora puoi parlare con la Guardia e mostrarla la lettera per entrare  in città!");
                        player.setCacciatoreArrabbiato(true);
                        player.setPermesso(true);
                        player.setOvest(true);
                        entrataCitta(player);
                    }
                }
            } else {
                player.setOvest(true);
                incrocio(player);
            }
        }


    }

    //viene in villaggio e puo: 1 fare indovinelli->paga 1 moneta per provare indovino, se vince->riceve 5 monete
    // 2 tornare al incrocio
    public void est(Player player) throws InterruptedException {
        if (indovinelliCoretto.size() == conversazioni.getIndovini().length) {
            System.out.println("----------------------------------------------------------");
            System.out.println("* Hai risolto tutti gli indovinelli, torna all'incrocio");
            incrocio(player);
        }
        System.out.println("----------------------------------------------------------");
        System.out.println("* Camminando entri in un villaggio piccolo.");
        System.out.println("* Vedi una cabina in cui si trova una vecchia signora.");
        System.out.println("* Sulla cabina c'è una scritta: Vuoi arricchirti? Vieni quà!");
        System.out.println("* Avvicini alla cabina");
        Thread.sleep(1000);
        System.out.println("----------------------------------------------------------");
        System.out.println(player.getNome() + ": Buongiorno signora, come posso arricchirmi?");
        Thread.sleep(1000);
        System.out.println("SIGNORA: Ciao, viaggiatore! Per 1 moneta ti do un indovino, ");
        Thread.sleep(1000);
        System.out.println("SIGNORA:...Se dai una risposta giusta, ricevi " + impostazioni.get("numGuadagno") + " monete.");
        Thread.sleep(1000);
        System.out.println("----------------------------------------------------------");
        System.out.println("* Hai " + player.getMoneteAttuale() + " monete.");
        System.out.println();
        System.out.println("* Cosa fai? ");
        System.out.println("I -> indovinelli");
        System.out.println("Altro -> torna al incrocio");
        String scelta = scanner.next().toUpperCase();
        String rispostaIndovino = "";
        String sceltaSeContinuare = "";
        int numRanIndovino = 0;
        boolean isFinito = false;
        while (scelta.equals("I") || sceltaSeContinuare.equals("S") || isFinito == false) {
            scelta = "";
            if (player.getMoneteAttuale() <= 0) {
                Thread.sleep(1000);
                System.out.println("----------------------------------------------------------");
                System.out.println("* Non hai abbastanza monette per fare indovinelli.");
                System.out.println("* Devi tornare all'incrocio");
                incrocio(player);
            }
            player.setMoneteAttuale(player.getMoneteAttuale() - 1);
            //genera nuovo numero se indovinello e già stato mostrato
            do {
                numRanIndovino = (int) (Math.random() * 9); //for array indovini ind 0-8
                if ((indovinelliErrato.size() > 0 && indovinelliCoretto.size() > 0) &&
                        (indovinelliCoretto.size() + indovinelliErrato.size() == conversazioni.getIndovini().length)
                        || indovinelliCoretto.size() == conversazioni.getIndovini().length) {
                    isFinito = true;
                    break;
                }
            } while (indovinelliCoretto.contains(numRanIndovino) || indovinelliErrato.contains(numRanIndovino)

            );
            //se player ha visto tutte indovinelli (anche se con risposte sbagliate)
            if (isFinito) {
                System.out.println("SIGNORA: Non ci sono più indovinelli per te, torna l'altra volta");
                //domande per risposte sbagliate possono apparire la prossima volta
                indovinelliErratoBackup = indovinelliErrato;
                indovinelliErrato = new ArrayList<>();
                incrocio(player);
            }
            String indovino = conversazioni.getIndovini()[numRanIndovino];
            String rispostaGiusta = conversazioni.getRisposteIndovini()[numRanIndovino];
            System.out.println("INDOVINELLO: " + indovino);
            rispostaIndovino = scanner.next().toUpperCase().trim();

            if (rispostaIndovino.equals(rispostaGiusta)) {
                player.setMoneteAttuale(player.getMoneteAttuale() + impostazioni.get("numGuadagno"));
                indovinelliCoretto.add(numRanIndovino);

                System.out.println("----------------------------------------------------------");
                System.out.println("SIGNORA: Che bravo! Hai guadagnato " + impostazioni.get("numGuadagno") + " monete! Vuoi provare ancora?");
            } else {
                indovinelliErrato.add(numRanIndovino);
                System.out.println("----------------------------------------------------------");
                System.out.println("SIGNORA: Mi dispiace, non è la risposta giusta. Vuoi provare ancora?");
            }
            System.out.println("Inserisci la scelta: S-> Si | Altro -> No");
            sceltaSeContinuare = scanner.next().toUpperCase();

        }
        //domande per risposte sbagliate possono apparire la prossima volta chiamando est()
        indovinelliErrato = new ArrayList<>();
        incrocio(player);
    }


    //1 se hai sconfitto il avversario prima->taverna aperta
    //1a parli con proprietaria, se hai abbastanza soldi-compri la cena e vinci
    //1b se non hai abbastanza monete-> vai a guadagnare
    //2 se non hai sconfitto il avversario prima->taverna chiusa e appare avversario: scelta scappare o attaccare

    public void dentroCitta(Player player) throws InterruptedException {
        if (!player.isTaverna()) {
            Thread.sleep(1000);
            System.out.println("----------------------------------------------------------");
            System.out.println("* Adesso devi andare alla taverna per trovare il Signore Daipacchetti e consegnarlo il pacco.");
            System.out.println("* Ecco la taverna!");
        }
        if (player.isSconfittoAvversario()) {
            Thread.sleep(1000);
            System.out.println("----------------------------------------------------------");
            System.out.println("PROPRIETARIA: Ciao straniere! Ho sentito hai sconfitto il " + playersSetup.getPersonaggi().get(1).getNome() + "!");
            Thread.sleep(1000);
            System.out.println("PROPRIETARIA:...Sei un eroe!");
            Thread.sleep(1000);
            System.out.println(player.getNome() + ": Eh.. si..Ho fatto ciò che dovevo..");
            Thread.sleep(1000);
            System.out.println("PROPRIETARIA: cosa cerchi nella nostra città?");
            Thread.sleep(1000);
            System.out.println(player.getNome()+": Devo trovare il Signore Daipacchetti, speravo che tu mi aiuti.");
            Thread.sleep(1000);
            System.out.println("PROPRIETARIA: Eh..si, lo conosco e ti aiuto se ordini il nostro piatto del giorno. Costa 16 monete");
            Thread.sleep(1000);
            System.out.println("----------------------------------------------------------");
            System.out.println("Hai " + player.getMoneteAttuale() + " monette.");
            if (player.getMoneteAttuale() < 16) {
                Thread.sleep(1000);
                System.out.println("----------------------------------------------------------");
                System.out.println(player.getNome() + ": Non ho abbastanza monete, cosa posso fare? ");
                Thread.sleep(1000);
                System.out.println("PROPRIETARIA: ho sentito che a ovest c'e un cacciatore che ti darà un lavoro per guadagnare un po' ");
                Thread.sleep(1000);
                System.out.println("PROPRIETARIA:...oppure a est c'e un villaggio dove puoi fare indovinelli e ricevi monete se sei bravo");
                Thread.sleep(1000);
                System.out.println("* Cosa fai? E -> andare a est | Altro -> andare a ovest");
                String scelta = scanner.next().toUpperCase();
                if (scelta.equals("E")) {
                    try {
                        est(player);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {

                    try {
                        ovest(player);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                Thread.sleep(1000);
                System.out.println("----------------------------------------------------------");
                System.out.println(player.getNome() + ": Perfetto! Non ho mangiato tutto il giorno!");
                Thread.sleep(1000);
                System.out.println("* PROPRIETARIA porta il piatto. Lo mangi in un attimo, e molto gustoso e tu avevi tanta fame.");
                Thread.sleep(1000);
                System.out.println("PROPRIETARIA va via e torna con il Signore Daipacchetti");
                Thread.sleep(1000);
                System.out.println("* Finalmente hai consegnato il pacco!");
                System.out.println("* Congratulazioni! Hai vinto nel gioco!");
                Thread.sleep(1000);
                System.out.println("********  Fine ********");
                System.out.println("Vuoi provare ancora? S -> Si | Altro ->No");
                String scelta = scanner.next().toUpperCase();
                if (scelta.equals("S")) {
                    playersSetup.giocatoriSetup();
                    Player giocatore = playersSetup.getPersonaggi().get(0);
                    iniziaGioco(giocatore);
                }
            }

        } else {
            Thread.sleep(1000);
            System.out.println("* Ma la porta è chiusa.");
            Thread.sleep(1000);
            System.out.println("* Una bambina corre vicino e gride: " + playersSetup.getPersonaggi().get(1).getNome() + "!! Scappa!");
            Thread.sleep(1000);
            System.out.println("* Da dietro l'angolo appare un " + playersSetup.getPersonaggi().get(1).getNome());
            Thread.sleep(1000);
            System.out.println("* La tua energia é: " + player.getEnergiaAttuale());
            System.out.println("* Cosa fai?");
            System.out.println(" S -> Scappare | A -> Attaccare");
            String scelta = scanner.next().toUpperCase();
            if (scelta.equals("S")) {
                incrocio(player);
            } else {
                player.setTaverna(true);
                startAttacco(player, playersSetup.getPersonaggi().get(1));
            }

        }
    }

    public void controlloEnergia(Player player) {
        if (player.getEnergiaAttuale() < 10&&player.getEnergiaAttuale()>0) {
            System.out.println("* La tua energia e meno di 10. Cerca di ricuperarla, prova andare a nord");
        }
        if (player.getEnergiaAttuale() <= 0) {
            player.setEnergiaAttuale(0);
            System.out.println("La tua energia attuale è 0.");
            System.out.println("Il gioco è finito.");
            System.out.println("Vuoi provare ancora? S -> Si | Altro ->No");
            String scelta = scanner.next().toUpperCase();
            if (scelta.equals("S")) {
                playersSetup.giocatoriSetup();
                Player giocatore = playersSetup.getPersonaggi().get(0);
                iniziaGioco(giocatore);
            }
        }
    }

}//
