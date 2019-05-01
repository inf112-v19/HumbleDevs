package inf112.skeleton.app.graphics.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.card.ProgramCardDeck;
import inf112.skeleton.app.gameObjects.Player;
import inf112.skeleton.app.gameObjects.Robot;
import inf112.skeleton.app.graphics.AssetManager;
import inf112.skeleton.app.graphics.GUI;

import java.util.*;

/*
 This class will represent the gamescreen (board and HUD)
 */

public class GameScreen extends ApplicationAdapter implements Screen {
    final GUI game;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Stage stage;
    public BitmapFont font;
    public Table table;
    private Robot[] robots;
    private ProgramCardDeck programCardDeck;
    private Map<Robot, ArrayList> map;
    private int playerCounter;
    private ArrayList<ProgramCard> selectedCards = new ArrayList<>();
    private Skin skin;
    private AssetManager assetManager;
    //TILE_SIZE = pixel size of one tile (width and height)
    private final int TILE_SIZE = 64;
    private Tiled tiledEditor;
    private final float GAMESPEED = 0.2f; // in seconds
    // An actions sequence for turnbased movement
    private SequenceAction sequenceAction;
    // An action sequence for parallell movement (conveyorbelt)
    private SequenceAction[] parallellAction;



    public GameScreen(final GUI game, Robot[] robots) {
        this.game = game;
        this.assetManager = new AssetManager();
        this.stage = new Stage();
        this.table = new Table();
        this.skin = new Skin(Gdx.files.internal("assets/UI/uiskin.json"));
        this.robots = robots;
        this.playerCounter = 0;
        this.programCardDeck = new ProgramCardDeck();
        this.sequenceAction = new SequenceAction();
        this.parallellAction = new SequenceAction[robots.length];

        // Initiate robot actors
        for (int i = 0; i < robots.length; i++) {
            // Create the robot actors,
            Texture texture = new Texture(Gdx.files.internal(robots[i].getPath()));
            TextureRegion region = new TextureRegion(texture, TILE_SIZE, TILE_SIZE);
            Image robotActor = new Image(region);
            robotActor.setOriginX(TILE_SIZE/2);
            robotActor.setOriginY(TILE_SIZE/2);
            robotActor.setPosition(robots[i].getX()*TILE_SIZE,robots[i].getY()*TILE_SIZE);
            //add it to the stage,
            stage.addActor(robotActor);
            //connect them to their actionsequence
            parallellAction[i] = new SequenceAction();
            parallellAction[i].setActor(robotActor);
        }

        font = new BitmapFont();
        //Important: makes us able to click on our stage and process inputs/events
        Gdx.input.setInputProcessor(stage);


        tiledMap = new TmxMapLoader().load("assets/maps/layeredTestMap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
//        tiledEditor = new Tiled(tiledMap, TILE_SIZE, players);

        camera = new OrthographicCamera();

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
        stage.addActor(table);
    }

    public void addCardToSelected(ProgramCard card) {
        selectedCards.add(card);
        if (selectedCards.size() == 5) {
            //Deep copy av listen
            ArrayList<ProgramCard> newList = new ArrayList<>();
            for (ProgramCard pc : selectedCards) {
                newList.add(pc);
            }
            addPlayerWithCardsToHashmap(newList);
            selectedCards.clear();


            if (playerCounter == robots.length) {
                table.clear();
                for (int i = 0; i < playerCounter; i++) {
                    ProgramCard[] cards = (ProgramCard[]) map.get(robots[i]).toArray(new ProgramCard[5]);
                    robots[i].setCards(cards);
                }
                drawHUD(map);
                return;
            }
            presentCards();
        }
    }

    public void addPlayerWithCardsToHashmap (ArrayList<ProgramCard> list) {
        map.put(robots[playerCounter], list);

        playerCounter++;
    }


    private void drawHUD(Map<Robot, ArrayList> map) {
        table.top();
        table.pad(0, 0, 0, 0);
        for (int i = 0; i < robots.length; i++) {
            Image robot = new Image(new Texture(robots[i].getPath()));
            table.add(robot);
            Label nameLabel = new Label(robots[i].getName(), skin);
            table.add(nameLabel);

            for (int j = 0; j < robots[i].getLifeTokens(); j++) {
                Image lifetoken = new Image(assetManager.getTexture("lifeIcon"));
                table.add(lifetoken);
            }


            table.row();
            ArrayList cardList = map.get(robots[i]);
            for (int j = 0; j < cardList.size(); j++) {
                table.pad(10, 10, 10, 10);

                ProgramCard card = (ProgramCard) cardList.get(j);
                Texture texture = assetManager.getTexture(card.getActionAndMovement(card.getAction(), card.getMove()));
                Image img = new Image(texture);
                table.add(img);
            }
            table.row();
        }
        Robot p1 = robots[0];


        p1.rotateRight();
        updateBoard(p1);
        p1.move(Direction.EAST);
        updateBoard(p1);
        p1.move(Direction.EAST);
        updateBoard(p1);
        p1.rotateLeft();
        updateBoard(p1);
        p1.move(Direction.NORTH);
        updateBoard(p1);
        p1.move(Direction.NORTH);
        updateBoard(p1);
        p1.die();
        updateBoard(p1);
        p1.respawn();
        updateBoard(p1);

    }

    public void presentCards() {
        table.clear();
        final ProgramCard[] cards = programCardDeck.getRandomCards(9 - robots[playerCounter].getDamageTokens()); // 9 cards here
        final Set<ProgramCard> pickedCards = new HashSet<>();
        Label infoLabel = new Label("Velg 5 kort", skin);
        Label playerLabel = new Label("Det er " + robots[playerCounter].getName() + " sin tur", skin);
        table.add(infoLabel); table.row(); table.add(playerLabel); table.row();

        for (int i = 0; i < cards.length; i++) {
            // Her kan vi hente retning av kort og bruke assetmanager til å hente riktig bilde cards[i].getMove();
            Texture cardTexture = assetManager.getTexture(cards[i].getActionAndMovement(cards[i].getAction(), cards[i].getMove()));
            final Image img = new Image(cardTexture);

            final int finalI = i;
            img.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (!pickedCards.contains(cards[finalI])) {
                        addCardToSelected(cards[finalI]);
                        pickedCards.add(cards[finalI]);
                    } // Kan gi beskjed til brukeren her at han/hun ikke kan velge det samme kortet
                    img.setColor(Color.GREEN);
                }
            });
            table.add(img).padBottom(20);
            table.row();
        }
    }
    /**
     * This method will update the position and direction of a robot on the board
     *
     * */
    public void updateBoard(final Robot robot) {
        Image curActor = (Image) stage.getActors().get(robot.getId());

        // Toggle robot visibility: Die, fade out
        if(robot.isDestroyed()) {
            AlphaAction a0 = Actions.fadeOut(GAMESPEED/3);
            a0.setActor(curActor);
            sequenceAction.addAction(a0);
        }

        // Add move action
        MoveToAction a1 = Actions.moveTo(robot.getX()*TILE_SIZE, robot.getY()*TILE_SIZE);
        a1.setActor(curActor);
        a1.setDuration(GAMESPEED);
        a1.setInterpolation(Interpolation.smooth);
        sequenceAction.addAction(a1);

        // Add rotation action
        RotateToAction a2 = Actions.rotateTo(directionToRotation(robot.getDirection()));
        a2.setActor(curActor);
        a2.setDuration(GAMESPEED);
        a2.setInterpolation(Interpolation.linear);
        sequenceAction.addAction(a2);

        // Toggle robot visibility: Respawn, fade in
        if (!robot.isDestroyed()) {
            AlphaAction a0 = Actions.fadeIn(GAMESPEED*2);
            a0.setActor(curActor);
            sequenceAction.addAction(a0);
        }

        // Lastly add delay for each step (in seconds)
        DelayAction da = Actions.delay(GAMESPEED);
        da.setActor(curActor);
        sequenceAction.addAction(da);
        // Set the actor for the sequence
        sequenceAction.setActor(curActor);
    }

    /**
     * Utility function that converts a direction to a counter clockwise degree representation
     * (which is the representation used by the drawing function in GameScreen)
     *
     * NORTH is default zero rotation (assuming texture faces north by default)
     *
     * @param dir
     * @return counter clockwise degree representation
     */
    public static int directionToRotation(Direction dir) {
        int rotation = 0;
        if (dir == Direction.EAST) {
            rotation =  -90;
        } else if (dir == Direction.WEST) {
            rotation = 90;
        } else if (dir == Direction.SOUTH) {
            rotation =  180;
        }
        return rotation;
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

        mapRenderer.setView(camera);
        mapRenderer.render();

        //Act out the sequenced actions for robots
        if(sequenceAction.act(delta)); // action was completed
        //Act out parallell actions for robots
        for (int i = 0; i < parallellAction.length; i++) {
            if(parallellAction[i].act(delta)); //action was completed
        }

        //stage
        update(delta);
        stage.draw(); // important

        game.batch.begin();
        //game.font.draw(game.batch, "PLEASE", 800, 300);

        // Her kan vi tegne :D
        game.batch.end();
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