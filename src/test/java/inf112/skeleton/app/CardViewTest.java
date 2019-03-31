package inf112.skeleton.app;

import inf112.skeleton.app.gameObjects.Items.CardView;
import inf112.skeleton.app.gameObjects.Player;
import org.junit.Test;
import static org.junit.Assert.*;

public class CardViewTest {

    @Test
    public void playerGetsFiveCards() {
        Player player = new Player(null, 0, 0, null);
        CardView cv = new CardView(player);
        assertEquals(player.getCards().length, 5);
    }

}
