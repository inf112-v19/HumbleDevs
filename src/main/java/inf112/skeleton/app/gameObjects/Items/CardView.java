package inf112.skeleton.app.gameObjects.Items;
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
        makeFive(deckOfNine);
        player.setCards(deckOfNine);
    }

    public void pickCards() {
        //TODO a player must be able to pick his own cards out of the 9
    }


    // A TBA method to choose 5 cards randomly for a player
    public ProgramCard[] makeFive(ProgramCard[] pc) {
        ProgramCard[] arr = new ProgramCard[5];
        for (int i = 0; i < 5; i++) {
            arr[i] = deckOfNine[i];
        }
        deckOfNine = arr;
        return deckOfNine;
    }



}
