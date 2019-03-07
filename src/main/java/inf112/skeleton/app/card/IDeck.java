package inf112.skeleton.app.card;

/**
 * Class keeps the track of the index of the next card
 *
 * @author Linh Nguyen
 *
 */

public interface IDeck {

    /**
     * Chooses random card from the card pack
     */
    ProgramCard[] getRandomCards();

    /**
     * the size of the deck
     */
    int size();


    /**
     * gets the current cards
     *
     * @return cards
     */
    ProgramCard[] getCards();


}
