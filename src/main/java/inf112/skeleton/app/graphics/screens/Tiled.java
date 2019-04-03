package inf112.skeleton.app.graphics.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.skeleton.app.board.Direction;
import inf112.skeleton.app.graphics.GUI;

public class Tiled extends ApplicationAdapter implements Screen {
    Texture robotTexture;
    TiledMap tiledMap;
    OrthographicCamera camera;
    TiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private final GUI game;

    public Tiled(final GUI game) {
        this.game = game;
        camera = new OrthographicCamera();

    }

    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        tiledMap = new TmxMapLoader().load("Assets/maps/layeredTestMap.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor((InputProcessor) this);
    }

    /**
     * Insert a player/robot texture in a cell on the board.
     * The (x,y) position (0,0) is bottom left
     *
     * drawPlayer(0, 0, "texture/robot1.png"); //should place the robot texture in bottom left corner, facing North
     * @param x position
     * @param y position
     * @param texturePath the path to the texture
     */
    public void drawPlayer(int x, int y, String texturePath) {
        drawPlayer(x, y, texturePath, Direction.NORTH);
    }
    public void drawPlayer(int x, int y, String texturePath, Direction facingDir) {
        //Placing a player on the board
        //Create a new texture with the robot picture
        Texture robotTexture = new Texture(Gdx.files.internal(texturePath));
        //Create a TextureRegion that is the entire size of the texture
        TextureRegion textureRegion = new TextureRegion(robotTexture, 64, 64);
        //Create a cell(tile) to add to the layer
        Cell cell = new Cell();
        //Set the graphic for the new cell
        cell.setTile(new StaticTiledMapTile(textureRegion));
        //Rotate
        if(facingDir == Direction.WEST) cell.setRotation(1);
        else if(facingDir == Direction.SOUTH) cell.setRotation(2);
        else if(facingDir == Direction.EAST) cell.setRotation(3);
        else cell.setRotation(0); //By default no rotation (should be facing NORTH)
        //Get layer to put cell in
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("players");
        //place cell on layer in (x,y) coordinate
        layer.setCell(x, y, cell);
    }

    public void render() {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

    }

    public void draw() {

    }

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT)
            camera.translate(-32, 0);
        if (keycode == Input.Keys.RIGHT)
            camera.translate(32, 0);
        if (keycode == Input.Keys.UP)
            camera.translate(0, -32);
        if (keycode == Input.Keys.DOWN)
            camera.translate(0, 32);
        if (keycode == Input.Keys.NUM_1)
            tiledMap.getLayers().get(0).setVisible(!tiledMap.getLayers().get(0).isVisible());
        if (keycode == Input.Keys.NUM_2)
            tiledMap.getLayers().get(1).setVisible(!tiledMap.getLayers().get(1).isVisible());
        return false;
    }

    public boolean keyTyped(char character) {

        return false;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void hide() {

    }
}