package inf112.skeleton.app.card;
import java.util.ArrayList;
import java.util.Random;

/**
 * A deck of cards for program cards
 */
public class ProgramCardDeck implements IDeck{
    private static ArrayList<ProgramCard> cardDeck;

    /**
     * Initializes the ProgramCardDeck with standard cards from the rulebook
     */
    public ProgramCardDeck(){
        cardDeck =  new ArrayList<>();

        for(int i = 0; i < 18; i ++) { //rotate right cards
            cardDeck.add(new ProgramCard(0,80+20*i,Action.RIGHTTURN));
        }

        for(int j = 0; j < 18; j ++) { //rotate left cards
            cardDeck.add(new ProgramCard(0,70+20*j , Action.LEFTTURN));
        }

        for(int k = 0; k < 6; k ++) {   //rotate 180 cards
            cardDeck.add(new ProgramCard(0, 10+10*k, Action.UTURN ));
        }

        for(int l = 0; l < 18; l ++) { //move forward 1 cards
            cardDeck.add(new ProgramCard(1, 490+10*l, Action.MOVEFORWARD ));
        }

        for(int m = 0; m < 12; m ++) { //move forward 2 cards
            cardDeck.add(new ProgramCard(2, 670+10*m,Action.MOVEFORWARD ));
        }

        for(int n = 0; n < 6; n ++) { //move forward 3 cards
            cardDeck.add(new ProgramCard(3 , 790+10*n, Action.MOVEFORWARD));
        }

        for(int o = 0; o < 6; o ++) { //move backward cards
            cardDeck.add(new ProgramCard(1, 430+10*o, Action.MOVEBACKWARD));
        }
    }

    @Override
    public ProgramCard[] getRandomCards(int number){
        Random rng = new Random();
        ProgramCard[] playingCards = new ProgramCard[number];
        for(int i = 0; i < playingCards.length; i++){
            int random = rng.nextInt(cardDeck.size());
            playingCards[i] = cardDeck.get(random);
            cardDeck.remove(random);
        }
        return playingCards;
    }

    @Override
    public int size(){
        return cardDeck.size();
    }

    @Override
    public ArrayList<ProgramCard> getCards() {
        return cardDeck;
    }
}