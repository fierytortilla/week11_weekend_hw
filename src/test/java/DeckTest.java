import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

public class DeckTest {
    private Deck deck;

    @Before
    public void before(){
        deck= new Deck();
    }

    @Test
    public void testInitialDeck(){
        assertEquals(0, deck.getCards().size());
        deck.fillDeck();
        assertEquals(52, deck.getCards().size());
    }

    @Test
    public void testShuffling(){
        deck.clearDeck();
        deck.fillDeck();
        Card card1= new Card(Suit.HEARTS, Rank.ACE);
        Card card2 = new Card(Suit.HEARTS, Rank.TWO);
        Card card3= new Card(Suit.HEARTS, Rank.THREE);
        ArrayList<Card> cards= new ArrayList<Card>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        ArrayList<Card> firstThreeCardsOfUnshuffledDeck = new ArrayList<Card>(deck.getCards().subList(0,3));
        for(int i=0; i< firstThreeCardsOfUnshuffledDeck.size(); i++){
            assertEquals(cards.get(i).getSuit(), firstThreeCardsOfUnshuffledDeck.get(i).getSuit());
            assertEquals(cards.get(i).getRank(), firstThreeCardsOfUnshuffledDeck.get(i).getRank());
        }
        deck.shuffleDeck();
        ArrayList<Card> firstThreeCardsOfShuffledDeck = new ArrayList<Card>(deck.getCards().subList(0,3));
        for(int i=0; i< firstThreeCardsOfShuffledDeck.size(); i++){
            assertNotEquals(cards.get(i).getSuit(), firstThreeCardsOfShuffledDeck.get(i).getSuit());
            assertNotEquals(cards.get(i).getRank(), firstThreeCardsOfShuffledDeck.get(i).getRank());
        }
    }

    @Test
    public void dealCardFromDeck(){
        deck.fillDeck();
        deck.shuffleDeck();
        Card randomCard= deck.dealFromDeck();
        assertEquals(51, deck.getCards().size());
    }
}
