package inf112.skeleton.app.graphics.screens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.card.ProgramCard;
import inf112.skeleton.app.card.ProgramCardDeck;
import inf112.skeleton.app.game.Game;
import inf112.skeleton.app.game.Main;
import inf112.skeleton.app.gameObjects.AI;
import inf112.skeleton.app.gameObjects.Robot;
import inf112.skeleton.app.graphics.AssetManager;
import inf112.skeleton.app.graphics.GUI;

import java.util.*;

/*
 This class will represent the gamescreen (board and HUD)
 */

public class GameScreen extends ApplicationAdapter implements Screen {
    final GUI gui;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private static Stage stage;
    public BitmapFont font;
    public static Table table;
    private static ProgramCardDeck programCardDeck;
    private static Map<Robot, ArrayList> map;
    private static int playerCounter;
    private static ArrayList<ProgramCard> selectedCards = new ArrayList<>();
    private static Skin skin;
    private static AssetManager assetManager;
    private final static int TILE_SIZE = 64;
    private final static float GAMESPEED = 0.2f; // in seconds
    // An actions sequence for turnbased movement
    private static SequenceAction sequenceAction;
    // An action sequence for parallell movement (conveyorbelt)
    private SequenceAction[] parallellAction;
    private static Game game;
    private static boolean aRobotIsDead = false;
    // Variables necessary for animation of laser shot
    private static Actor laserShotActor;
    private Texture laserShotSheet;
    private Animation<TextureRegion> laserShotAnimation;
    private SpriteBatch laserBatch;
    private int FRAME_COLS = 4;
    private int FRAME_ROWS = 4;
    private float laserShotStateTime = 0;

    public class AnimatedActor extends Actor {
        private Animation animation;
        private TextureRegion currentRegion;

        private float stateTime = 0f;

        public AnimatedActor(Animation animation) {
            this.animation = animation;
        }

        @Override
        public void act(float delta){
            currentRegion = (TextureRegion) animation.getKeyFrame(stateTime, true);

            super.act(delta);
            stateTime += delta;

        }

        @Override
        public void draw(Batch batch, float alpha) {
            super.draw(batch, alpha);
            batch.draw(currentRegion, getX(), getY());
        }

    }




    public GameScreen(final GUI gui, Game game, ArrayList<String> playerNames, int robots) {
        this.gui = gui;
        this.game = game;
        this.assetManager = new AssetManager();
        this.stage = new Stage();
        this.table = new Table();
        this.skin = new Skin(Gdx.files.internal("assets/UI/uiskin.json"));
        this.playerCounter = 0;
        this.programCardDeck = new ProgramCardDeck();
        this.sequenceAction = new SequenceAction();


        tiledMap = new TmxMapLoader().load("assets/maps/Level1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        game.setBoard(new Board(tiledMap));

        game.initializePlayers(robots+playerNames.size(), playerNames);

        // Initiate robot actors
        for (int i = 0; i < game.getRobots().length; i++) {
            // Create the robot actors
            Texture texture = new Texture(Gdx.files.internal(game.getRobots()[i].getPath()));
            TextureRegion region = new TextureRegion(texture, TILE_SIZE, TILE_SIZE);
            Image robotActor = new Image(region);
            robotActor.setOriginX(TILE_SIZE/2);
            robotActor.setOriginY(TILE_SIZE/2);
            robotActor.setPosition(game.getRobots()[i].getX()*TILE_SIZE,game.getRobots()[i].getY()*TILE_SIZE);
            //add it to the stage,
            stage.addActor(robotActor);
        }

        // Instantiate laser shot animation
        laserShotSheet = new Texture(Gdx.files.internal("texture/laserShot.png"));
        TextureRegion[][] tmp = TextureRegion.split(laserShotSheet, TILE_SIZE, TILE_SIZE);
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        laserShotActor = new AnimatedActor(new Animation<TextureRegion>(0.025f, walkFrames));

        stage.addActor(laserShotActor);
        laserShotActor.setVisible(false);




//        laserBatch = new SpriteBatch();




        font = new BitmapFont();
        //Important: makes us able to click on our stage and process inputs/events
        Gdx.input.setInputProcessor(stage);

        camera = new OrthographicCamera();

        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();

        map = new HashMap<>();

        createCardTable(table);
    }


    public static void createCardTable(Table table) {
        table.setWidth(332);
        table.setHeight(600); //temp
        table.setPosition(768, 100);

        presentCards();
        stage.addActor(table);
    }

    public static void addAllCardsFromAI(ArrayList<ProgramCard> pcList) {
        for (ProgramCard pc : pcList) {
            addCardToSelected(pc);
        }
    }


    /**
     * Helper method to add a card to a players deck of cards
     */

    public static void addCardToSelected(ProgramCard card) {
        selectedCards.add(card);
        if (selectedCards.size() == 5) {
            //Deep copy of the list
            ArrayList<ProgramCard> newList = new ArrayList<>();
            for (ProgramCard pc : selectedCards) {
                newList.add(pc);
            }
            addPlayerWithCardsToHashmap(newList);
            selectedCards.clear();


            if (playerCounter == game.getRobots().length) {
                table.clear();
                for (int i = 0; i < playerCounter; i++) {
                    ProgramCard[] cards = (ProgramCard[]) map.get(game.getRobots()[i]).toArray(new ProgramCard[5]);
                    game.getRobots()[i].setCards(cards);
                }

                drawHUD(map);
                Main.readyToLaunch = true;
                return;
            }
            presentCards();
        }
    }


    /**
     * Adds a players card to a hashmap, where the player itself is the key
     */
    public static void addPlayerWithCardsToHashmap (ArrayList<ProgramCard> list) {
        map.put(game.getRobots()[playerCounter], list);
        playerCounter++;
    }


    private static void drawLifeTokens() {

    }

    private static void drawHUD(Map<Robot, ArrayList> map) {
        table.clear();
        table.top();


        table.pad(0, 0, 0, 0);
        for (int i = 0; i < game.getRobots().length; i++) {
            Image robot = new Image(new Texture(Gdx.files.internal(game.getRobots()[i].getPath())));
            table.add(robot);
            Label nameLabel = new Label(game.getRobots()[i].getName(), skin);
            table.add(nameLabel);

            for (int j = 0; j < game.getRobots()[i].getLifeTokens(); j++) {
                Image lifeToken = new Image(assetManager.getTexture("lifeIcon"));
                table.add(lifeToken);
            }

            table.row();
            ArrayList cardList = map.get(game.getRobots()[i]);
            for (int j = 0; j < cardList.size(); j++) {
                table.pad(10, 10, 10, 10);

                ProgramCard card = (ProgramCard) cardList.get(j);
                Texture texture = assetManager.getTexture(card.getActionAndMovement(card.getAction(), card.getMove()));
                Image img = new Image(texture);
                table.add(img);
            }
            table.row();
        }
        // Getting a fresh deck for next round
        programCardDeck = new ProgramCardDeck();

//        shootLaser(3, 1, 3, 7, Direction.NORTH);
//        shootLaser(4, 1, 4, 7, Direction.NORTH);
//        shootLaser(5, 1, 5, 7, Direction.NORTH);
//        shootLaser(6, 1, 6, 7, Direction.NORTH);
//        shootLaser(0,4,7,4, Direction.EAST);
//        shootLaser(7,4,0,4, Direction.WEST);
//
//        shootLaser(3, 7, 3, 1, Direction.SOUTH);


    }

    /**
     * This method will present cards to pick from to a player
     * If this "player" is a robot, random cards are chosen.
     */

    public static void presentCards() {
        table.clear();

        if (game.getRobots()[playerCounter] instanceof AI) {
            game.getRobots()[playerCounter].chooseCards(programCardDeck.getRandomCards(9 - game.getRobots()[playerCounter].getDamageTokens()));
            ProgramCard[] pc = game.getRobots()[playerCounter].getCards();
            ArrayList<ProgramCard> pcList = new ArrayList<>(Arrays.asList(pc));
            addAllCardsFromAI(pcList);
            return;
        }

        final ProgramCard[] cards = programCardDeck.getRandomCards(9 - game.getRobots()[playerCounter].getDamageTokens()); // 9 cards here
        final Set<ProgramCard> pickedCards = new HashSet<>();
        Label infoLabel = new Label("Velg 5 kort", skin);
        Label playerLabel = new Label("Det er " + game.getRobots()[playerCounter].getName() + " sin tur", skin);
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
    public static void updateBoard(final Robot robot) {
        Image curActor = (Image) stage.getActors().get(robot.getId());

        // Toggle robot visibility: Die, fade out
        if(robot.isDestroyed()) {
            AlphaAction a0 = Actions.fadeOut(GAMESPEED/3);
            a0.setActor(curActor);
            sequenceAction.addAction(a0);

            sequenceAction.addAction(Actions.run(new Runnable() {
                @Override
                public void run() {
                    aRobotIsDead = true;
                }
            }));
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
        a2.setDuration(GAMESPEED*2);
        a2.setInterpolation(Interpolation.linear);
        sequenceAction.addAction(a2);

        // Toggle robot visibility: Respawn, fade in
        if (!robot.isDestroyed()) {
            AlphaAction a0 = Actions.fadeIn(GAMESPEED);
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

    public static void shootLaser(int fromX, int fromY, int toX, int toY, Direction dir) {
        // Add correct rotation to the shot
        RotateToAction rotateAction = Actions.rotateTo(directionToRotation(dir));
//        rotateAction.setActor(laserShotActor);
//        rotateAction.setDuration(GAMESPEED);
        rotateAction.setInterpolation(Interpolation.linear);
        sequenceAction.addAction(rotateAction);

        // Move shot to correct start position
        MoveToAction positionAction = Actions.moveTo(fromX*TILE_SIZE, fromY*TILE_SIZE);
//        positionAction.setActor(laserShotActor);

        sequenceAction.addAction(positionAction);

        // Set visible
        Action visible = Actions.visible(true);
        sequenceAction.addAction(visible);

        // Do firemove
        MoveToAction a1 = Actions.moveTo(toX*TILE_SIZE, toY*TILE_SIZE);
        a1.setActor(laserShotActor);
        a1.setDuration(GAMESPEED*2);
        a1.setInterpolation(Interpolation.smooth);
        sequenceAction.addAction(a1);

        // Remove shot from board
        Action invisible = Actions.visible(false);
//        invisible.setActor(laserShotActor);
        sequenceAction.addAction(invisible);

        sequenceAction.setActor(laserShotActor);
    }


    public static void startNewRound() {
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                playerCounter = 0;
                GameScreen.presentCards();
            }
        }));
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
        gui.batch.setProjectionMatrix(camera.combined);

        mapRenderer.setView(camera);
        mapRenderer.render();


        if (aRobotIsDead) {
            drawHUD(map);
            aRobotIsDead = false;
        }

        //Act out the sequenced actions for robots
        if(sequenceAction.act(delta)); // action was completed

        // Shoot laser
//        laserShotStateTime += delta;
//        TextureRegion currentFrame = laserShotAnimation.getKeyFrame(laserShotStateTime, true);
//        laserBatch.begin();
//        laserBatch.draw(currentFrame, 50, 50); // Draw current frame at (50, 50)
//        laserBatch.end();

        //stage
        update(delta);
        stage.draw(); // important
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
        laserBatch.dispose();
        laserShotSheet.dispose();
    }
}