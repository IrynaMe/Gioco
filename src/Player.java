import java.time.LocalTime;

public class Player {
    //nome -energia
    private String nome;
    private boolean isOvest;
    private boolean isScappato; //se scappa dalla guardia
    private boolean isSconfittoGuardia; //se vince guardia
    private boolean isPermesso;//se ha una lettera metodo ovest()
    private boolean isSconfittoAvversario; //se vince suo avversario
    private boolean isCacciatoreArrabbiato; //se scappa con lettera dopo incontro con cacciatore-> metodo ovest()
    private boolean isNord; //dove incontrato il avversario
    private boolean isTaverna; //dove incontrato il avversario
    private boolean isCitta; // se entrato in città


    private int energiaAttuale;
    private int energiaStart;
    private int moneteStart;
    private int moneteAttuale;


    public void setCitta(boolean citta) {
        isCitta = citta;
    }

    public boolean isOvest() {
        return isOvest;
    }


    public void setOvest(boolean ovest) {
        isOvest = ovest;
    }

    public boolean isNord() {
        return isNord;
    }

    public void setNord(boolean nord) {
        isNord = nord;
    }

    public boolean isTaverna() {
        return isTaverna;
    }

    public void setTaverna(boolean taverna) {
        isTaverna = taverna;
    }

    LocalTime bevutoAqua; //quando bevuto dalla fiume e ripreso energia ->metodo nord()

    public LocalTime getBevutoAqua() {
        return bevutoAqua;
    }

    public void setBevutoAqua(LocalTime bevutoAqua) {
        this.bevutoAqua = bevutoAqua;
    }

    public boolean isSconfittoGuardia() {
        return isSconfittoGuardia;
    }

    public void setSconfittoGuardia(boolean sconfittoGuardia) {
        isSconfittoGuardia = sconfittoGuardia;
    }


    public int getMoneteAttuale() {
        return moneteAttuale;
    }

    public void setMoneteAttuale(int moneteAttuale) {
        this.moneteAttuale = moneteAttuale;
    }


    public boolean isCacciatoreArrabbiato() {
        return isCacciatoreArrabbiato;
    }

    public void setCacciatoreArrabbiato(boolean cacciatoreArrabbiato) {
        isCacciatoreArrabbiato = cacciatoreArrabbiato;
    }

    public boolean isSconfittoAvversario() {
        return isSconfittoAvversario;
    }

    public void setSconfittoAvversario(boolean sconfittoAvversario) {
        isSconfittoAvversario = sconfittoAvversario;
    }

    public boolean isPermesso() {
        return isPermesso;
    }

    public void setPermesso(boolean permesso) {
        isPermesso = permesso;
    }


    public boolean isScappato() {
        return isScappato;
    }

    public void setScappato(boolean scappato) {
        isScappato = scappato;
    }

    //costruttore per giocatore
    public Player(String nome, int energiaStart, int energiaAttuale, int moneteStart, int moneteAttuale) {
        this.nome = nome;
        this.energiaStart = energiaStart;
        this.energiaAttuale = energiaAttuale;
        this.moneteStart = moneteStart;
        this.moneteAttuale = moneteAttuale;
    }

    //costruttore per altri personaggi
    public Player(String nome, int energiaStart, int energiaAttuale) {
        this.nome = nome;
        this.energiaStart = energiaStart;
        this.energiaAttuale = energiaAttuale;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEnergiaAttuale() {
        return energiaAttuale;
    }

    public void setEnergiaAttuale(int energia) {
        this.energiaAttuale = energia;
    }

    public int getEnergiaStart() {
        return energiaStart;
    }

    @Override
    public String toString() {
        return "Player{" +
                "nome='" + nome + '\'' +
                ", energiaStart=" + energiaStart +
                ", moneteStart=" + moneteStart +
                '}';
    }
}
