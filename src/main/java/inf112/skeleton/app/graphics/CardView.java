package inf112.skeleton.app.graphics;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import inf112.skeleton.app.card.*;


/**
 * A card representing a graphical UI where the player can choose his/hers cards
 */
public class CardView implements ApplicationListener {
    //Backend
    private final int NUMPLAYERS;
    ProgramCard[] arrOfCards;

    // Graphical
    private Stage stage;
    private Skin skin;

    public CardView(int numPlayers) {
        ProgramCardDeck pcd = new ProgramCardDeck();
        this.NUMPLAYERS = numPlayers;
        arrOfCards = pcd.getRandomCards();
    }

    public void pickCards (ProgramCard[] arr) {

    }

    public ProgramCard[] getArrOfCards() {
        return arrOfCards;
    }

    public void setArrOfCards(ProgramCard[] arrOfCards) {
        this.arrOfCards = arrOfCards;
    }

    public void createHUD() {
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("assets/UI/uiskin.json"));
    }


    @Override
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
