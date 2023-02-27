/**
 * Gestione del combattimento tra due personaggi Player
 *
 * @version 1.0.0
 */
public class Combat {
    //metodi:
    public void attacco(Player playerAttaccato) {
        //generiamo num casuale da 1 a 5
        int randomNum = (int) ((Math.random() * 5) + 1);
        //se num=1 Colpo schivato
        switch (randomNum) {
            case 2:
                playerAttaccato.setEnergiaAttuale(playerAttaccato.getEnergiaAttuale() - 1);
                System.out.println("Danno: Lieve");
                break;
            case 3:
                playerAttaccato.setEnergiaAttuale(playerAttaccato.getEnergiaAttuale() - 3);
                System.out.println("Danno: Moderato");
                break;
            case 4:
                playerAttaccato.setEnergiaAttuale(playerAttaccato.getEnergiaAttuale() - 5);
                System.out.println("Danno: Grave");
                break;
            case 5:
                playerAttaccato.setEnergiaAttuale(playerAttaccato.getEnergiaAttuale() - 6);
                System.out.println("Danno: Gravissimo");
                break;
            default:
                System.out.println("Colpo schivato"); //danno ==1
                playerAttaccato.setEnergiaAttuale(playerAttaccato.getEnergiaAttuale() + 3);
                System.out.println("ENERGIA di" +playerAttaccato.getNome()+" rimane: "
                        +"==="+ playerAttaccato.getEnergiaAttuale()+"===");
        }
    }
}
