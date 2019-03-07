package inf112.skeleton.app.card;
/**
 * Class keeps the track of the index of the next card
 *
 * @author Linh Nguyen
 *
 */
public interface IDeck {

    /**
     * Chooses card from the card pack
     */
    ProgramCard[] getRandomCards();

    /**
     * the size of the deck
     */
    int size();





}
