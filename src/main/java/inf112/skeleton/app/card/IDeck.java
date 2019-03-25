package inf112.skeleton.app.card;

import java.util.ArrayList;

/**
 * Class keeps the track of the index of the next card
 *
 * @author Linh Nguyen
 *
 */
public interface IDeck {

    /**
     * Chooses random cards from the card pack
     * @param i the number of cards to return
     */
    ProgramCard [] getRandomCards(int i);

    /**
     * the size of the deck
     */
    int size();


    /**
     * gets the current cards
     *
     * @return cards
     */
    ArrayList<ProgramCard> getCards();


}
