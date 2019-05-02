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
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    static GUI gui;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private static Stage stage;
    public BitmapFont font;
    public static Table table;
    private static ProgramCardDeck programCardDeck;
    public static boolean newGame;
    public static boolean click;

    public static Map<Robot, ArrayList> map;


    private static int currentRobot;
    private static ArrayList<ProgramCard> selectedCards = new ArrayList<>();
    private static Skin skin;
    private static AssetManager assetManager;
    private final static int TILE_SIZE = 64;
    private final static float STEP_DELAY = 0.1f; // in seconds
    // An actions sequence for turnbased movement
    private static SequenceAction sequenceAction;
    // An action sequence for parallell movement (conveyorbelt)
    private SequenceAction[] parallellAction;
    private static Game game;
    private static boolean aRobotIsDead = false;
    // Variables necessary for animation of laser shot
    private static AnimatedActor laserShotActor;
    private Texture laserShotSheet;
    private int FRAME_COLS = 4;
    private int FRAME_ROWS = 4;

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
            // draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation)
            batch.draw(currentRegion, getX(), getY(), this.getOriginX(), this.getOriginY(), this.getWidth(), this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation());
        }

        public void resetStateTime() {
            stateTime = 0f;
        }

    }


    public GameScreen(GUI gui, Game game, ArrayList<String> playerNames, int robots) {
        this.gui = gui;
        this.game = game;
        this.assetManager = new AssetManager();
        this.stage = new Stage();
        this.table = new Table();
        this.skin = new Skin(Gdx.files.internal("assets/UI/uiskin.json"));
        this.currentRobot = 0;
        this.programCardDeck = new ProgramCardDeck();
        this.sequenceAction = new SequenceAction();
        newGame = false;
        click = false;


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

        // Initiate laser shot animation
        laserShotSheet = new Texture(Gdx.files.internal("texture/laserShot.png"));
        TextureRegion[][] tmp = TextureRegion.split(laserShotSheet, TILE_SIZE, TILE_SIZE);
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        laserShotActor = new AnimatedActor(new Animation<>(1f*STEP_DELAY, walkFrames));
        laserShotActor.setOrigin(TILE_SIZE/2, TILE_SIZE/2);
        laserShotActor.setSize(TILE_SIZE, TILE_SIZE);
        laserShotActor.setScale(1, 1);
        stage.addActor(laserShotActor);
        laserShotActor.setVisible(false);




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
        int numberOfCards = 9 - game.getRobots()[currentRobot].getDamageTokens();
        if (numberOfCards > 5) {
            numberOfCards = 5;
        }
        if (selectedCards.size() == numberOfCards) {
            //Deep copy of the list
            ArrayList<ProgramCard> newList = new ArrayList<>();
            for (ProgramCard pc : selectedCards) {
                newList.add(pc);
            }
            addPlayerWithCardsToHashmap(newList);
            selectedCards.clear();


            if (currentRobot == game.getRobots().length) {
                table.clear();
                for (int i = 0; i < currentRobot; i++) {
                    ProgramCard[] cards = (ProgramCard[]) map.get(game.getRobots()[i]).toArray(new ProgramCard[i]);
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
        map.put(game.getRobots()[currentRobot], list);
        currentRobot++;
    }

    public static void deleteCard (final Robot robot, final ProgramCard card) {
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                    for (int i = 0; i < robot.getCards().length; i++) {
                        if(robot.getCards()[i] != null && robot.getCards()[i] == card && !robot.getCards()[i].isUsed()) {
                            robot.getCards()[i].use();
                            break;

                    }
                }
                }
        }));
    }



    public static void drawHUD(Map<Robot, ArrayList> map) {
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

            if (game.getRobots()[i].isPoweredDown()) {
                Label poweredDown1 = new Label("Powered", skin);
                Label poweredDown2 = new Label("Down", skin);
                table.add(poweredDown1);
                table.add(poweredDown2);
            } else {

                ProgramCard[] arr = game.getRobots()[i].getCards();


                for (int j = 0; j < arr.length; j++) {
                    table.pad(10, 10, 10, 10);
                    ProgramCard card = arr[j];

                    if (card == null) {
                        continue;
                    }

                    Texture texture = assetManager.getTexture(card.getActionAndMovement(card.getAction(), card.getMove()));
                    Image img = new Image(texture);
                    if(card.isUsed()){
                        img.setColor(1,1,1,0.3f);
                    }
                    table.left();
                    table.add(img).padBottom(3).height(img.getPrefHeight()).width(img.getPrefWidth()).size(60);
                }
            }
            table.row();
        }
        // Getting a fresh deck for next round
        programCardDeck = new ProgramCardDeck();
    }

    public static void sequenceDrawHUD() {
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                drawHUD(map);
            }
        }));
    }

    /**
     * This method will present cards to pick from to a player
     * If this "player" is a robot, random cards are chosen.
     */
    public static void presentCards() {
        table.clear();
        table.center();

        if (game.getRobots()[currentRobot] instanceof AI) {
            game.getRobots()[currentRobot].chooseCards(programCardDeck.getRandomCards(9 - game.getRobots()[currentRobot].getDamageTokens()));
            ProgramCard[] pc = game.getRobots()[currentRobot].getCards();
            ArrayList<ProgramCard> pcList = new ArrayList<>(Arrays.asList(pc));
            addAllCardsFromAI(pcList);
            return;
        }
        Texture robotTexture = new Texture(game.getRobots()[currentRobot].getPath());
        Image robotImage = new Image(robotTexture);
        table.add(robotImage).height(robotImage.getPrefHeight()).width(robotImage.getPrefWidth());
        table.row();

        final ProgramCard[] cards = programCardDeck.getRandomCards(9 - game.getRobots()[currentRobot].getDamageTokens()); // 9 cards here
        final Set<ProgramCard> pickedCards = new HashSet<>();
        Label infoLabel = new Label("Velg 5 kort", skin);
        Label playerLabel = new Label("Det er " + game.getRobots()[currentRobot].getName() + " sin tur", skin);
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

            table.add(img).padBottom(3).height(img.getPrefHeight()).width(img.getPrefWidth()).size(50);
            table.row();
        }
        table.row();

        TextButton powerDownButton = new TextButton("Power Down", skin);
        powerDownButton.setHeight(75);
        powerDownButton.setWidth(100);
        table.add(powerDownButton);

        powerDownButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.getRobots()[currentRobot].powerDown();
                selectedCards.clear();
                for (int i = 0; i < 5; i++) {
                    ProgramCard nullCard = new ProgramCard(0, 0, null);
                    addCardToSelected(nullCard);
                }
            }
        });
        game.startRobots();
    }

    /**
     * This method will update the position and direction of a robot on the board
     * performed as animated actions
     * @param robot
     */
    public static void updateBoard(final Robot robot) {

        checkForEndScreen();

        Image curActor = (Image) stage.getActors().get(robot.getId());

        // Toggle robot visibility: Die, fade out
        if(robot.isDestroyed()) {
            AlphaAction a0 = Actions.fadeOut(STEP_DELAY /3);
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
        a1.setDuration(STEP_DELAY);
        a1.setInterpolation(Interpolation.smooth);
        sequenceAction.addAction(a1);

        // Add rotation action
        RotateToAction a2 = Actions.rotateTo(directionToRotation(robot.getDirection()));
        a2.setActor(curActor);
        a2.setDuration(STEP_DELAY *2);
        a2.setInterpolation(Interpolation.linear);
        sequenceAction.addAction(a2);

        // Toggle robot visibility: Respawn, fade in
        if (!robot.isDestroyed()) {
            AlphaAction a0 = Actions.fadeIn(STEP_DELAY);
            a0.setActor(curActor);
            sequenceAction.addAction(a0);
        }

        // Lastly add delay for each step (in seconds)
        DelayAction da = Actions.delay(STEP_DELAY);
        da.setActor(curActor);
        sequenceAction.addAction(da);
        // Set the actor for the sequence
        sequenceAction.setActor(curActor);

    }

    /**
     * Shoots an animated laser beam (added to the action sequence)
     *
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @param dir
     * @param steps Number of tiles the laser beam traverses
     */
    public static void shootRobotLaser(int fromX, int fromY, int toX, int toY, Direction dir, Integer steps) {
        // Add correct rotation to the shot
        RotateToAction rotateAction = Actions.rotateTo(directionToRotation(dir));
        rotateAction.setActor(laserShotActor);
        rotateAction.setInterpolation(Interpolation.linear);
        sequenceAction.addAction(rotateAction);

        // Move shot to correct start position
        MoveToAction positionAction = Actions.moveTo(fromX*TILE_SIZE, fromY*TILE_SIZE);
        positionAction.setActor(laserShotActor);
        sequenceAction.addAction(positionAction);

        // Set visible
        Action visible = Actions.visible(true);
        visible.setActor(laserShotActor);
        sequenceAction.addAction(visible);

        // FIRE!
        MoveToAction a1 = Actions.moveTo(toX*TILE_SIZE, toY*TILE_SIZE);
        a1.setActor(laserShotActor);
        a1.setDuration(STEP_DELAY * steps);
        a1.setInterpolation(Interpolation.smooth);
        sequenceAction.addAction(a1);

        // Remove shot from board
        Action invisible = Actions.visible(false);
        invisible.setActor(laserShotActor);
        sequenceAction.addAction(invisible);

        // Reset animation
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                laserShotActor.resetStateTime();
            }
        }));

        // Add delay before next action
        DelayAction da = Actions.delay(STEP_DELAY);
        da.setActor(laserShotActor);
        sequenceAction.addAction(da);
    }

    /**
     * Signals a new round
     */
    public static void startNewRound() {
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                currentRobot = 0;
                GameScreen.presentCards();
            }
        }));
    }

    public static void checkForEndScreen() {
        final Robot robot = game.finished();
        if (robot != null) {

            sequenceAction.addAction(Actions.run(new Runnable() {
                @Override
                public void run() {
                    GameScreen.drawEndScreen(robot);
                }
            }));
        }
    }

    public static void drawEndScreen (Robot robot) {
        Image robotImage = new Image(new Texture(Gdx.files.internal(robot.getPath())));
        Button butt = new Button(MainScreen.skin);
        Button butt2 = new Button(MainScreen.skin);
        Button butt3 = new Button(MainScreen.skin);
        Label lab = new Label("WINNER IS", MainScreen.skin);
        Label lab2 = new Label("Exit", MainScreen.skin);
        Label lab3 = new Label("New Game", MainScreen.skin);
        butt.add(lab);
        butt.setColor(1,0,0,1);
        butt.add(robotImage);
        butt.setSize(300,300);
        butt.setPosition(290,240);

        butt2.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        butt3.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gui.create();
                newGame = true;
                click = true;
            }
        });

        butt2.add(lab2);
        butt2.setSize(200,100);
        butt2.setPosition(340,200);
        butt2.setColor(0,0,1,1);

        butt3.add(lab3);
        butt3.setSize(200,100);
        butt3.setPosition(340,470);
        butt3.setColor(0,1,0,1);

        stage.addActor(butt);
        stage.addActor(butt2);
        stage.addActor(butt3);
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
        laserShotSheet.dispose();
    }
}