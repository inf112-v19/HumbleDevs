package inf112.skeleton.app.graphics;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import inf112.skeleton.app.board.Board;
import inf112.skeleton.app.board.Direction;


public class Tiled extends ApplicationAdapter implements InputProcessor {
    private static TiledMap tiledMap;
    private OrthographicCamera camera;
    private TiledMapRenderer tiledMapRenderer;

    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);
        camera.update();
        tiledMap = new TmxMapLoader().load("Assets/maps/layeredTestMap.tmx");
        Board board = new Board(tiledMap);

        drawPlayer(0, 0, "texture/robot1.png", Direction.SOUTH);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        Gdx.input.setInputProcessor(this);
    }

    /**
     * Insert a player/robot texture in a cell on the board.
     * The (x,y) position (0,0) is bottom left
     *
     * drawPlayer(0, 0, "texture/robot1.png"); //should place the robot texture in bottom left corner, facing North
     * @param x
     * @param y
     * @param texturePath
     */
    public static void drawPlayer(int x, int y, String texturePath) {
        drawPlayer(x, y, texturePath, Direction.NORTH);
    }
    public static void drawPlayer(int x, int y, String texturePath, Direction facingDir) {
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
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();
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

    public TiledMap getTiledMap() {
        return tiledMap;
    }


}
