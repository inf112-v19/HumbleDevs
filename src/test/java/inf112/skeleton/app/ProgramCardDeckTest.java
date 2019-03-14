package inf112.skeleton.app;

import inf112.skeleton.app.card.Action;
import inf112.skeleton.app.card.IDeck;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.card.ProgramCardDeck;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgramCardDeckTest {

    /**
     * ProgramCardDeck should contain 84 cards
     */
    @Test
    public void TestProgramCardDeckContain84Cards() {
        IDeck deck = new ProgramCardDeck();
        assertEquals(84, deck.size());
    }

    @Test
    public void TestRandomCard(){

    }

    /**
     * ProgramCardDeck should rotate right
     */
    @Test
    public void TestWhenRotateRight() {
        int result = 0;
        IDeck deck = new ProgramCardDeck();
        ProgramCard[] cardDeck = deck.getCards();
        for (int i = 0; i < cardDeck.length; i++) {
            if (cardDeck[i].getAction() == Action.RIGHTTURN) {
                result++;
            }
        }
        assertEquals(18, result);
    }

    /**
     * ProgramCardDeck should rotate left
     */
    @Test
    public void TestWhenRotateLeft() {
        int result = 0;
        IDeck deck = new ProgramCardDeck();
        ProgramCard[] cardDeck = deck.getCards();
        for (int i = 0; i < cardDeck.length; i++) {
            if (cardDeck[i].getAction() == Action.LEFTTURN) {
                result++;
            }
        }
        assertEquals(18, result);
    }

    /**
     * ProgramCardDeck should rotate u-turn
     */
    @Test
    public void TestWhenRotateUTurn() {
        int result = 0;
        IDeck deck = new ProgramCardDeck();
        ProgramCard[] cardDeck = deck.getCards();
        for (int i = 0; i < cardDeck.length; i++) {
            if (cardDeck[i].getAction() == Action.UTURN) {
                result++;
            }
        }
        assertEquals(6, result);
    }

    /**
     * ProgramCardDeck should move backward
     */
    @Test
    public void TestBackward() {
        int result = 0;
        IDeck deck = new ProgramCardDeck();
        ProgramCard[] cardDeck = deck.getCards();
        for (int i = 0; i < cardDeck.length; i++) {
            if (cardDeck[i].getAction() == Action.MOVEBACKWARD) {
                result++;
            }
        }
        assertEquals(6, result);
    }
}
