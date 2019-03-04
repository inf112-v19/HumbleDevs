package inf112.skeleton.app.card;
import java.util.Stack;

public interface IDeck {

    void shuffle();

    ProgramCard getCard();

    boolean isEmpty();

    void addCard(ProgramCard card);

    int size();



}
