package inf112.skeleton.app;

import inf112.skeleton.app.graphics.CardView;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardViewTest {

    @Test
    public void nineCardsAreAvailableForSelection() {
        CardView cv = new CardView(4);
        assertEquals(cv.getArrOfCards().length, 9);
    }


}
