package inf112.skeleton.app.card;

import java.util.ArrayList;

/**
 * Interface of a deck of cards. A deck contains a number of cards and for every card drawn, the deck gets smaller.
 */
public interface IDeck {

    /**
     * Chooses i random cards from the card pack
     * @param i the number of cards to return
     */
    ProgramCard [] getRandomCards(int i);

    /**
     * The size of the deck
     */
    int size();

    /**
     * Get the cards that remains in the deck
     * @return cards
     */
    ArrayList<ProgramCard> getCards();
}