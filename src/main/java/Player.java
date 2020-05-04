import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cards;
    private boolean winner;


    public Player(String name){
        this.name= name;
        cards= new ArrayList<Card>();
    }

    public void receiveCard(Card card){
        this.cards.add(card);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void winsGame(){
        this.cards.clear();
        this.winner= true;
    }

    public void losesGame(){
        this.cards.clear();
        this.winner= false;
    }

    public boolean getGameStatus(){
        return this.winner;
    }


    public String getName() {
        return this.name;
    }

    public void clearHand() {
        this.cards.clear();
    }
}
