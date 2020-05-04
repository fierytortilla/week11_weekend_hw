import java.util.ArrayList;

public class Game {
    private Player player;
    private Player dealer;
    private Deck deck;
    private int playersScore;
    private int dealersScore;
    private boolean gameStillPlaying;

    public Game(Player player, Player dealer, Deck deck) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.gameStillPlaying = true;
    }

    public void restartGame(){
        this.player.clearHand();
        this.dealer.clearHand();
        this.deck.fillDeck();
        this.deck.shuffleDeck();
    }

    public void dealTwoCardsOut(Player player, Player dealer) {
        player.receiveCard(this.deck.dealFromDeck());
        player.receiveCard(this.deck.dealFromDeck());
        dealer.receiveCard(this.deck.dealFromDeck());
        dealer.receiveCard(this.deck.dealFromDeck());
    }

    public ArrayList<Integer> calculateScore(ArrayList<Card> playersCards) {
        ArrayList<Integer> playersTotalScore = new ArrayList<Integer>();
        int firstScore = 0;
        int secondScore = 0;
        for (Card card : playersCards) {
            firstScore += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                secondScore += (firstScore + card.getRank().getValue2() - 1);
            }
        }
        playersTotalScore.add(firstScore);
        playersTotalScore.add(secondScore);
        return playersTotalScore;
    }

    public int evaluateScoresWithAces(ArrayList<Integer> potentialScores){
        int score;
        if(potentialScores.get(0)>21 && potentialScores.get(1)>21){
            potentialScores.remove(0);
        } else if(potentialScores.get(1)>21){
            potentialScores.remove(1);
        } else if(potentialScores.get(0)>21){
            potentialScores.remove(0);
        } else if((21 - potentialScores.get(1)) > (21 - potentialScores.get(0))){
            potentialScores.remove(1);
        } else if((21 - potentialScores.get(0)) > (21 - potentialScores.get(1))){
            potentialScores.remove(0);
        }
        score= potentialScores.get(0);
        return score;
    }

    public RoundResult evaluateScore(){
        if (this.playersScore == 21 && this.dealersScore == 21) {
            return RoundResult.DEALER;
        } else if (this.playersScore > 21 && this.dealersScore > 21) {
            return RoundResult.BUST;
        } else if (this.dealersScore <= 21 && this.playersScore > 21) {
            return RoundResult.DEALER;
        } else if (this.playersScore <= 21 && this.dealersScore > 21) {
            return RoundResult.PLAYER;
        } else if(this.dealersScore == 21 && this.playersScore < 21){
            return RoundResult.DEALER;
        } else if(this.playersScore == 21 && this.dealersScore < 21){
            return RoundResult.PLAYER;
        } else {
            return RoundResult.UNCALLED;
        }
    }


    public RoundResult playFirstRound(Player player, Player dealer) {
//        this.dealTwoCardsOut(player, dealer);
        ArrayList<Integer> playersScores = this.calculateScore(player.getCards());
        ArrayList<Integer> dealersScores = this.calculateScore(dealer.getCards());
        if(player.getCards().stream().anyMatch(i -> i.getRank() == Rank.ACE) && dealer.getCards().stream().anyMatch(i -> i.getRank() == Rank.ACE)){
            this.playersScore = this.evaluateScoresWithAces(playersScores);
            this.dealersScore = this.evaluateScoresWithAces(dealersScores);
        } else if(dealer.getCards().stream().anyMatch(i -> i.getRank() == Rank.ACE)){
            this.dealersScore = this.evaluateScoresWithAces(dealersScores);
            this.playersScore = playersScores.get(0);
        } else if(player.getCards().stream().anyMatch(i -> i.getRank() == Rank.ACE)){
            this.playersScore = this.evaluateScoresWithAces(playersScores);
            this.dealersScore = dealersScores.get(0);
        } else {
            this.playersScore = playersScores.get(0);
            this.dealersScore = dealersScores.get(0);
        }
        return this.evaluateScore();
    }

    public RoundResult playAnotherRound(Player player){
//        player.receiveCard(this.deck.dealFromDeck());
        ArrayList<Integer> playersScores = this.calculateScore(player.getCards());
        if(player.getCards().stream().anyMatch(i -> i.getRank() == Rank.ACE)){
            this.playersScore = this.evaluateScoresWithAces(playersScores);
        }
        this.playersScore= playersScores.get(0);
        return this.evaluateScore();
    }


    public void playGame(Player player, Player dealer) {
        this.dealTwoCardsOut(player, dealer);
        RoundResult firstRoundResult = this.playFirstRound(player, dealer);
        while (gameStillPlaying) {
            if (firstRoundResult == RoundResult.BUST) {
                player.losesGame();
                dealer.losesGame();
                this.gameStillPlaying = false;
            } else if(firstRoundResult == RoundResult.DEALER){
                dealer.winsGame();
                player.losesGame();
                this.gameStillPlaying= false;
            } else if(firstRoundResult== RoundResult.PLAYER){
                player.winsGame();
                dealer.losesGame();
                this.gameStillPlaying= false;
            } else if(firstRoundResult== RoundResult.UNCALLED){
                this.gameStillPlaying= false;
            }
        }
    }
}
