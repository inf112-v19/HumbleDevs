package inf112.skeleton.app.card;
import java.util.Stack;

public interface IDeck {

    void shuffle();

    Stack<ProgramCard> draw (int n);

    boolean isEmpty();



}
