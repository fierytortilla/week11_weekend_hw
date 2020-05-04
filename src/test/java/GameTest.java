import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GameTest {
    private Game game;
    private Player player;
    private Player dealer;
    private Deck deck;

    @Before
    public void before(){
        deck= new Deck();
        player = new Player("Jamie");
        dealer = new Player("Jim Bob");
        game = new Game(player, dealer, deck);
    }

    @Test
    public void playRound(){
        game.restartGame();
        assertEquals(52, deck.getCards().size());
        assertEquals(0, player.getCards().size());
        assertEquals(0, dealer.getCards().size());
        game.dealTwoCardsOut(player, dealer);
        assertEquals(50, deck.getCards().size());
        assertEquals(1, player.getCards().size());
        assertEquals(1, dealer.getCards().size());

    }

    @Test
    public void playGameWherePlayerWins(){
        game.restartGame();
        Card card1 = new Card(Suit.HEARTS, Rank.KING);
        Card card2= new Card(Suit.DIAMONDS, Rank.ACE);
        Card card3 = new Card(Suit.SPADES, Rank.TEN);
        Card card4= new Card(Suit.CLOVERS, Rank.JACK);
        player.receiveCard(card1);
        player.receiveCard(card2);
        dealer.receiveCard(card3);
        dealer.receiveCard(card4);
        assertEquals(RoundResult.PLAYER, game.playFirstRound(player, dealer));
    }

    @Test
    public void playGameWhereDealerWins(){
        game.restartGame();
        Card card1 = new Card(Suit.HEARTS, Rank.KING);
        Card card2= new Card(Suit.DIAMONDS, Rank.ACE);
        Card card3 = new Card(Suit.SPADES, Rank.TEN);
        Card card4= new Card(Suit.CLOVERS, Rank.JACK);
        player.receiveCard(card3);
        player.receiveCard(card4);
        dealer.receiveCard(card1);
        dealer.receiveCard(card2);
        assertEquals(RoundResult.DEALER, game.playFirstRound(player, dealer));
    }

    @Test
    public void playGameWhereRoundIsUncalled(){
        game.restartGame();
        Card card1 = new Card(Suit.HEARTS, Rank.KING);
        Card card2= new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card card3 = new Card(Suit.SPADES, Rank.TEN);
        Card card4= new Card(Suit.CLOVERS, Rank.JACK);
        player.receiveCard(card1);
        player.receiveCard(card2);
        dealer.receiveCard(card3);
        dealer.receiveCard(card4);
        RoundResult result= game.playFirstRound(player, dealer);
        assertEquals(RoundResult.UNCALLED, result);
    }

    @Test
    public void playGameWhereRoundIsBust(){
        game.restartGame();
        Card card1 = new Card(Suit.HEARTS, Rank.KING);
        Card card2= new Card(Suit.DIAMONDS, Rank.QUEEN);
        Card card3 = new Card(Suit.SPADES, Rank.TEN);
        Card card4 = new Card(Suit.CLOVERS, Rank.JACK);
        Card card5 = new Card(Suit.CLOVERS, Rank.FIVE);
        Card card6 = new Card(Suit.SPADES, Rank.SEVEN);
        player.receiveCard(card1);
        player.receiveCard(card2);
        player.receiveCard(card5);
        dealer.receiveCard(card3);
        dealer.receiveCard(card4);
        dealer.receiveCard(card6);
        RoundResult result= game.playFirstRound(player, dealer);
        assertEquals(RoundResult.BUST, result);
    }

    @Test
    public void playGameWithTwoRounds(){
        game.restartGame();
        Card card1 = new Card(Suit.HEARTS, Rank.KING);
        Card card2= new Card(Suit.DIAMONDS, Rank.NINE);
        Card card3 = new Card(Suit.SPADES, Rank.TEN);
        Card card4 = new Card(Suit.CLOVERS, Rank.JACK);
        player.receiveCard(card1);
        player.receiveCard(card2);
        dealer.receiveCard(card3);
        dealer.receiveCard(card4);
        RoundResult firstResult= game.playFirstRound(player, dealer);
        assertEquals(RoundResult.UNCALLED, firstResult);
        Card card5 = new Card(Suit.HEARTS, Rank.TWO);
        player.receiveCard(card5);
        RoundResult secondResult= game.playAnotherRound(player);
        assertEquals(RoundResult.PLAYER, secondResult);
    }
}
