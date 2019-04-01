//package inf112.skeleton.app;
//
//import inf112.skeleton.app.card.Action;
//import inf112.skeleton.app.card.IDeck;
//import inf112.skeleton.app.card.ProgramCard;
//import inf112.skeleton.app.card.ProgramCardDeck;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import static org.junit.Assert.assertEquals;
//
//public class ProgramCardDeckTest {
//    private  IDeck deck;
//
//    @Before
//    public void init(){
//        deck = new ProgramCardDeck();
//    }
//
//    /**
//     * ProgramCardDeck should contain 84 cards
//     */
//    @Test
//    public void testProgramCardDeckContain84Cards() {
//        assertEquals(84, deck.size());
//    }
//
//    /**
//     * The deck should get smaller as cards are drawn from it
//     */
//    @Test
//    public void testDeckLength(){
//        deck.getRandomCards(9);
//        assertEquals(75, deck.size());
//    }
//
//    /**
//     * ProgramCardDeck should rotate right
//     */
//    @Test
//    public void testWhenRotateRight() {
//        int result = 0;
//        ArrayList<ProgramCard> cardDeck = deck.getCards();
//        for (int i = 0; i < cardDeck.size(); i++) {
//            if (cardDeck.get(i).getAction() == Action.RIGHTTURN) {
//                result++;
//            }
//        }
//        assertEquals(18, result);
//    }
//
//    /**
//     * ProgramCardDeck should rotate left
//     */
//    @Test
//    public void testWhenRotateLeft() {
//        int result = 0;
//        ArrayList<ProgramCard> cardDeck = deck.getCards();
//        for (int i = 0; i < cardDeck.size(); i++) {
//            if (cardDeck.get(i).getAction() == Action.LEFTTURN) {
//                result++;
//            }
//        }
//        assertEquals(18, result);
//    }
//
//    /**
//     * ProgramCardDeck should rotate u-turn
//     */
//    @Test
//    public void testWhenRotateUTurn() {
//        int result = 0;
//        ArrayList<ProgramCard> cardDeck = deck.getCards();
//        for (int i = 0; i < cardDeck.size(); i++) {
//            if (cardDeck.get(i).getAction() == Action.UTURN) {
//                result++;
//            }
//        }
//        assertEquals(6, result);
//    }
//
//    /**
//     * ProgramCardDeck should move backward
//     */
//    @Test
//    public void testBackward() {
//        int result = 0;
//        ArrayList<ProgramCard> cardDeck = deck.getCards();
//        for (int i = 0; i < cardDeck.size(); i++) {
//            if (cardDeck.get(i).getAction() == Action.MOVEBACKWARD) {
//                result++;
//            }
//        }
//        assertEquals(6, result);
//    }
//}