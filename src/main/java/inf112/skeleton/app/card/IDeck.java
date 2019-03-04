package inf112.skeleton.app.card;
import java.util.Stack;

public interface IDeck {

    /*
    randomize stack of cards
     */
    void shuffle();

    ProgramCard getCard();

    boolean isEmpty();

    void addCard(ProgramCard card);

    /*
    number of cards in the stack
     */
    int size();



}
