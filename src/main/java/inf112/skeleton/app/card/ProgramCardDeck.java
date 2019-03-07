package inf112.skeleton.app.card;
import java.util.Collections;
import java.util.Random;
/**
 * An implementation of the IDeck interface
 *
 * @author Linh Nguyen
 *
 */

public class ProgramCardDeck implements IDeck{

    private static ProgramCard [] cardDeck;


    /**
     * Initializes the ProgramCardDeck with standard cards from rulebook
     */
    public ProgramCardDeck(){

        cardDeck = new ProgramCard[84]; //Standard amount from the rulebook

        for(int i = 0; i < 18; i ++) { //rotate right
            cardDeck[i] = new ProgramCard(0,80+20*i,Action.RIGHT);
        }

        for(int j = 0; j < 18; j ++) { //rotate left
            cardDeck[j+18] = new ProgramCard(0,70+20*j , Action.LEFT );
        }

        for(int k = 0; k < 6; k ++) {   //rotate 180
            cardDeck[k+36]=new ProgramCard(0, 10+10*k, Action.UTURN );
        }

        for(int l = 0; l < 18; l ++) { //move forward 1
            cardDeck[l+54]=new ProgramCard(1, 490+10*l, Action.MOVEFORWARD );
        }

        for(int m = 0; m < 12; m ++) { //move forward 2
            cardDeck[m+66] = new ProgramCard(2, 670+10*m,Action.MOVEFORWARD );
        }

        for(int n = 0; n < 6; n ++) { //move forward 3
            cardDeck[n +72]= new ProgramCard(3 , 790+10*n, Action.MOVEFORWARD);
        }

        for(int o = 0; o < 6; o ++) { //move backward
            cardDeck[o+78] = new ProgramCard(-1, 430+10*o, Action.MOVEBACKWARD);
        }

    }

    /**
     * Chooses card from the card pack
     * @return 9 random playing cards
     */
    @Override
    public ProgramCard[] getRandomCards(){
        Random rng = new Random();
        ProgramCard[] playingCards = new ProgramCard[9];
        for(int i = 0; i < playingCards.length; i++){
            int random = rng.nextInt(cardDeck.length);
            playingCards[i] = cardDeck[random];

        }
        return playingCards;
    }

    /**
     * the size of the deck
     */
    @Override
    public int size(){
        return cardDeck.length;

    }

}
