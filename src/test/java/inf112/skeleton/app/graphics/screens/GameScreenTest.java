package inf112.skeleton.app.graphics.screens;

import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import inf112.skeleton.app.graphics.GUI;
import inf112.skeleton.app.gameObjects.Player;


import static org.junit.Assert.*;

public class GameScreenTest {

    GameScreen gameScreen;
    @Before
    public void setup(){
        GUI game = new GUI();
        Player[] players = new Player[5];
        gameScreen = new GameScreen(game, players);


    }
    @Test
    public void createCardTable() {
        Table table = new Table();
        gameScreen.createCardTable(table);
        assertEquals(table.getWidth(), 332);
        assertEquals(table.getHeight(), 600);


    }

    @Test
    public void addCardToSelected() {

    }

    @Test
    public void addPlayerWithCardsToHashmap() {
    }

    @Test
    public void presentCards() {
    }

    @Test
    public void update() {
    }
}