Serve il file config.properties

Per vincere nel gioco si deve per forza (ordine non importa):
- entrare in città
- sconfiggere il avversario(dipende dal livello)
- guadagnare le monete -> andare ovest(incontrare il cacciatore) o est(fare indovinelli)
- comprare il cibo in taverna(avere monete)
per riprendere energia serve andare a nord->bere aqua



 public void est(Player player) throws InterruptedException {

        System.out.println("----------------------------------------------------------");
        System.out.println("* Camminando entri in un villaggio piccolo.");
        System.out.println("* Vedi una cabina in cui si trova una vecchia signora.");
        System.out.println("* Sulla cabina c'è una scritta: Vuoi arricchirti? Vieni quà!");
        System.out.println("* Avvicini alla cabina");
        Thread.sleep(2000);
        System.out.println("----------------------------------------------------------");
        System.out.println(player.getNome() + ": Buongiorno signora, come posso arricchirmi?");
        Thread.sleep(2000);
        System.out.println("SIGNORA: Ciao, viaggiatore! Per 1 moneta ti do un indovino, ");
        Thread.sleep(1000);
        System.out.println("...Se dai una risposta giusta, ricevi " + impostazioni.get("numGuadagno") + " monete.");
        Thread.sleep(2000);
        System.out.println("----------------------------------------------------------");
        System.out.println("* Hai " + player.getMoneteAttuale() + " monete.");
        System.out.println("Cosa fai? ");
        System.out.println("I -> indovinelli");
        System.out.println("Altro -> torna al incrocio");
        String scelta = scanner.next().toUpperCase();
        String rispostaIndovino = "";
        if (scelta.equals("I")) {
            boolean flag = false;
            String sceltaSeContinuare="";
            do {
                if (player.getMoneteAttuale() <= 0) {
                    Thread.sleep(1000);
                    System.out.println("----------------------------------------------------------");
                    System.out.println("* Non hai abbastanza monette per fare indovinelli.");
                    System.out.println("* Devi tornare all'incrocio");
                    incrocio(player);
                }
                    player.setMoneteAttuale(player.getMoneteAttuale() - 1);
                    int numRanIndovino = (int) (Math.random() * 8); //for array indovinelli ind 0-7
                    String indovino = conversazioni.getIndovini()[numRanIndovino];
                    String rispostaGiusta = conversazioni.getRisposteIndovini()[numRanIndovino];
                    System.out.println(player.getNome() + ": " + indovino);
                    rispostaIndovino = scanner.next().toUpperCase().trim();
                    if (rispostaIndovino.equals(rispostaGiusta)) {
                        player.setMoneteAttuale(player.getMoneteAttuale() + impostazioni.get("numGuadagno"));

                        System.out.println("----------------------------------------------------------");
                        System.out.println("SIGNORA: Che bravo! Hai guadagnato " + impostazioni.get("numGuadagno") + " monete! Vuoi provare ancora?");
                    } else {

                        System.out.println("----------------------------------------------------------");
                        System.out.println("Mi dispiace, non è la risposta giusta. Vuoi provare ancora?");
                    }

                    System.out.println("Inserisci la scelta: S-> Si | Altro -> No");
                   sceltaSeContinuare = scanner.next().toUpperCase();
                    if (sceltaSeContinuare.equals("S")) {
                        flag = true;
                    }
            }while (flag);
        } else {
            incrocio(player);
        }
        incrocio(player);
    }
