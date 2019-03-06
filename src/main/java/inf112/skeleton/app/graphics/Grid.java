package inf112.skeleton.app.graphics;
import inf112.skeleton.app.gameObjects.Items;
import com.badlogic.gdx.maps.tiled.*;
import java.util.ArrayList;

public class Grid {
    private int WIDTH = 0;
    private int HEIGHT = 0;
    private TiledMapTileLayer baseLayer;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer objectLayer;
    private ArrayList grid[][];

    public Grid(TiledMap map) {
        this.WIDTH = (int) map.getProperties().get("width");
        this.HEIGHT = (int) map.getProperties().get("height");
        baseLayer = (TiledMapTileLayer) map.getLayers().get(0);
        playerLayer = (TiledMapTileLayer) map.getLayers().get(1);
        objectLayer = (TiledMapTileLayer) map.getLayers().get(2);
        grid = new ArrayList[WIDTH][HEIGHT];

        for (int r = 0; r < HEIGHT; r++) {
            for (int c = 0; c < WIDTH; c++) {
                // Each cell has a list of items/objects
                ArrayList<Items> list = new ArrayList<>();
                grid[r][c] = list;
                //Fill list with objects!!

             }
        }
    }
}
