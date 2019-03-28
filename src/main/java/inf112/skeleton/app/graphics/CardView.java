package inf112.skeleton.app.graphics;
import inf112.skeleton.app.card.*;
import inf112.skeleton.app.gameObjects.Player;

/**
 * A class representing a graphical UI where the player can choose his/hers cards
 */

public class CardView {
    //the player holding these cards
    private Player player;
    private ProgramCard[] deckOfNine;

    /*/
        @param the player to draw cards
     */
    public CardView(Player player) {
        ProgramCardDeck pcd = new ProgramCardDeck();
        deckOfNine = pcd.getRandomCards();
    }


    public void pickCards() {
        //TODO a player must be able to pick his own cards out of the 9
    }

    public ProgramCard[] makeFive(ProgramCard[] pc) {
        ProgramCard[] arr = new ProgramCard[5];
        for (int i = 0; i < deckOfNine.length; i++) {
            arr[i] = deckOfNine[i];
        }
        deckOfNine = arr;
        return deckOfNine;
    }



}
