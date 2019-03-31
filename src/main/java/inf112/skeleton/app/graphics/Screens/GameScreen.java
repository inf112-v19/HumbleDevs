package inf112.skeleton.app.graphics.Screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.card.ProgramCardDeck;
import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.graphics.GUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 This class will represent the gamescreen (board and HUD)
 */

public class GameScreen extends ApplicationAdapter implements Screen {
    final GUI game;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer renderer;
    private Stage stage;
    public BitmapFont font;
    private Table table;
    private Player[] players;
    private ProgramCardDeck programCardDeck;
    private Map<Player, ArrayList> map;
    private int playerCounter;
    private ArrayList<ProgramCard> selectedCards = new ArrayList<>();



    public GameScreen(final GUI game) {
        this.game = game;
        this.stage = new Stage();
        this.table = new Table();
        this.players = new Player[4]; //getPlayers()
        this.playerCounter = 0;
        this.programCardDeck = new ProgramCardDeck();
        font = new BitmapFont();
        //Important: makes us able to click on our stage and process inputs/events
        Gdx.input.setInputProcessor(stage);
        tiledMap = new TmxMapLoader().load("Assets/maps/layeredTestMap.tmx");
        camera = new OrthographicCamera();
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        map = new HashMap<>();
        createCardTable(table);
    }

    public void createCardTable(Table table) {
        table.setWidth(332);
        table.setHeight(600); //temp
        table.setPosition(768, 100);

        presentCards();
        //viktig
        stage.addActor(table);
    }

    public void addCardToSelected(ProgramCard card) {
        selectedCards.add(card);
        if (selectedCards.size() == 5) {
            addPlayerWithCardsToHashmap(selectedCards);
            selectedCards.clear();
        }
    }

    public void addPlayerWithCardsToHashmap (ArrayList<ProgramCard> list) {
        if (playerCounter == players.length - 1) {
            table.clear();
            drawHUD();
            return;
        }
        map.put(players[playerCounter], list);
        playerCounter++;
    }


    private void drawHUD() {
        for (int i = 0; i < players.length; i++) {
            //Her kan vi tegne roboten (typ fargen) på hudden
            Image robot = new Image(new Texture("texture/test.jpg"));
            table.add(robot);
            //getName?
            table.row();
            for (int j = 0; j < 5; j++) {
                //Igjen, hashmap for å hente ritig bilde fra .getMove()
                table.pad(10, 10, 10, 10);
                Image card = new Image(new Texture("texture/robot.png"));
                table.add(card);
            }
            table.row();
        }
    }

    public void presentCards() {
        table.clear();
        final ProgramCard[] cards = programCardDeck.getRandomCards(); // 9 cards here
        Skin skin = new Skin(Gdx.files.internal("assets/UI/uiskin.json"));
        Label infoLabel = new Label("Velg 5 kort", skin);
        Label playerLabel = new Label("Det er " + players[playerCounter] + " sin tur", skin); //getName her når vi får spillerlisten
        table.add(infoLabel); table.row(); table.add(playerLabel); table.row();

        for (int i = 0; i < cards.length; i++) {
            // Her kan vi hente retning av kort og bruke assetmanager til å hente riktig bilde cards[i].getMove();
            Texture texture = new Texture(Gdx.files.internal("texture/test.jpg"));
            Image img = new Image(texture);
            final int finalI = i;
            img.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    addCardToSelected(cards[finalI]);
                    //selectedCards.add() her må vi adde typen kort, altså cards.getMove();
                }
            });
            table.add(img).padBottom(20);
            table.row();
        }
    }



    public void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        renderer.setView(camera);
        renderer.render();

        //stage
        update(delta);
        Actor actor = new Actor();
        stage.addActor(actor);
        stage.draw();

        game.batch.begin();
        //game.font.draw(game.batch, "PLEASE", 800, 300);

        // Her kan vi tegne :D
        game.batch.end();
    }

    public void drawCards(int players) {
        for (int i = 0; i < players; i++) {
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.begin();
            shapeRenderer.rect(0, 0, 768, 300);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
