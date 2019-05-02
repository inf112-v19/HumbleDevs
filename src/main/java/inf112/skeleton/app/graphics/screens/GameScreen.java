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
    final GUI gui;
    private TiledMap tiledMap;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private static Stage stage;
    public BitmapFont font;
    public static Table table;
    private static ProgramCardDeck programCardDeck;

    private static Map<Robot, ArrayList> map;
    private static Map<Robot, ArrayList<Image>> cardMap;


    private static int currentRobot;
    private static ArrayList<ProgramCard> selectedCards = new ArrayList<>();
    private static Skin skin;
    private static AssetManager assetManager;
    private final static int TILE_SIZE = 64;
    private final static float GAMESPEED = 0.0002f; // in seconds
    // An actions sequence for turnbased movement
    private static SequenceAction sequenceAction;
    // An action sequence for parallell movement (conveyorbelt)
    private SequenceAction[] parallellAction;
    private static Game game;
    private static boolean aRobotIsDead = false;


    public GameScreen(final GUI gui, Game game, ArrayList<String> playerNames, int robots) {
        this.gui = gui;
        this.game = game;
        this.assetManager = new AssetManager();
        this.stage = new Stage();
        this.table = new Table();
        this.skin = new Skin(Gdx.files.internal("assets/UI/uiskin.json"));
        this.currentRobot = 0;
        this.programCardDeck = new ProgramCardDeck();
        this.sequenceAction = new SequenceAction();
        cardMap = new HashMap<>();


        tiledMap = new TmxMapLoader().load("assets/maps/Level3.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        game.setBoard(new Board(tiledMap));

        game.initializePlayers(robots+playerNames.size(), playerNames);

        // Initiate robot actors
        for (int i = 0; i < game.getRobots().length; i++) {
            // Create the robot actors,


            Texture texture = new Texture(Gdx.files.internal(game.getRobots()[i].getPath()));
            TextureRegion region = new TextureRegion(texture, TILE_SIZE, TILE_SIZE);
            Image robotActor = new Image(region);
            robotActor.setOriginX(TILE_SIZE/2);
            robotActor.setOriginY(TILE_SIZE/2);
            robotActor.setPosition(game.getRobots()[i].getX()*TILE_SIZE,game.getRobots()[i].getY()*TILE_SIZE);
            //add it to the stage,
            stage.addActor(robotActor);
        }

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
        //System.out.println(game.getRobots().length);
        if (selectedCards.size() == 5 ) {
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
                    ProgramCard[] cards = (ProgramCard[]) map.get(game.getRobots()[i]).toArray(new ProgramCard[5]);
                    game.getRobots()[i].setCards(cards);
                }
//                for (int i = 0; i < game.getRobots()[0].getCards().length; i++) {
//                    System.out.println(game.getRobots()[0].getCards()[i].getAction());
//                }
//                System.out.println();
//                for (int i = 0; i < game.getRobots()[1].getCards().length; i++) {
//                    System.out.println(game.getRobots()[1].getCards()[i].getAction());
//                }
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
                    if(robot.getCards()[i] != null && robot.getCards()[i].equals(card)) {
                        robot.getCards()[i] = null;
                        //System.out.println(i + " We deleted a card from: " + robot.getName() + ". The card had move: " + card.getMove());
                        return;
                    }
                }
                }
        }));
    }




    private static void drawHUD(Map<Robot, ArrayList> map) {
        table.clear();
        table.top();

       // System.out.println("Drawing..");


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

                    //ProgramCard card = (ProgramCard) cardList.get(j);
                    ProgramCard card = arr[j];

                    if (card == null) {
                        continue;
                    }
                   // System.out.println("Drawing card number: " + j);


                    Texture texture = assetManager.getTexture(card.getActionAndMovement(card.getAction(), card.getMove()));
                    Image img = new Image(texture);
                    table.add(img);
                }
            }
            table.row();
        }
        // Getting a fresh deck for next round
        programCardDeck = new ProgramCardDeck();

        //if(!aRobotIsDead)

    }

    /**
     * This method will present cards to pick from to a player
     * If this "player" is a robot, random cards are chosen.
     */

    public static void presentCards() {
        table.clear();

        if (game.getRobots()[currentRobot] instanceof AI) {
            game.getRobots()[currentRobot].chooseCards(programCardDeck.getRandomCards(9 - game.getRobots()[currentRobot].getDamageTokens()));
            ProgramCard[] pc = game.getRobots()[currentRobot].getCards();
            ArrayList<ProgramCard> pcList = new ArrayList<>(Arrays.asList(pc));
            addAllCardsFromAI(pcList);
            return;
        }
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
            table.add(img).padBottom(20);
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


    public static void startNewRound() {
        sequenceAction.addAction(Actions.run(new Runnable() {
            @Override
            public void run() {
                currentRobot = 0;
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
    }
}