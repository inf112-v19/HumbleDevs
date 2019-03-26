package inf112.skeleton.app.board;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.skeleton.app.GameObjects.Items.IItem;
import inf112.skeleton.app.GameObjects.Items.ItemFactory;
import inf112.skeleton.app.GameObjects.Robot;

import java.util.ArrayList;


/**
 * Board represented as a grid with width*height size.
 * Contains a Square for each position.
 *
 */
public class Board implements IBoard {
    private final int width;
    private final int height;
    private final int size;
    private ArrayList<Square> map;


    public Board(int width, int height) {
        this.height = height;
        this.width = width;
        this.size = height * width;
        map = new ArrayList<>();
        for (int i = 0; i < height;i++) {
            for (int j = 0; j < width; j++) {
                map.add(new Square());
            }
        }
    }

    public Board(int width, int height, ArrayList<Square> map) {
        this.height = height;
        this.width = width;
        this.size = height * width;
        this.map = map;
    }

    /**
     * Creates a board representation of a TiledMap object (a loaded .tmx file)
     * ItemFactory is used to create the items that is in a square/cell,
     * based on the tile id from the .tmx file.
     * @param tiledMap
     */
    public Board(TiledMap tiledMap) {
        this.height = (int) tiledMap.getProperties().get("height");
        this.width = (int) tiledMap.getProperties().get("width");
        this.size = height * width;
        map = new ArrayList<>();


        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map.add(new Square());
            }
        }


        for (int l = 0; l < tiledMap.getLayers().size(); l++) {
            TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(l);
            for (int r = 0; r < height; r++) {
                for (int c = 0; c < width; c++) {
                    if (layer.getCell(r, c) == null) {
                        continue;
                    }
                    int id = layer.getCell(r, c).getTile().getId();
                    IItem item = createItemFromId(id);

                    if (item != null) {
                        getSquare(r, c).addElement(item);
                    }
                }
            }
        }
    }

    /**
     * Create an item from a given id (tile id)
     * The tile id is 1-indexed and corresponds to the
     * position it has in the tileSetLarge64.png (top left is id 1)
     * @param id
     * @return
     */
    private IItem createItemFromId(int id) {
        return ItemFactory.getItem(id);
    }


    @Override
    public void insertItem(IPosition position, IItem element) {
        Square sq = map.get(position.getIndex());
        sq.addElement(element);
    }

    @Override
    public void insertItem(int x, int y, IItem element) {
        int index = toIndex(x,y);
        Square sq = map.get(index);
        sq.addElement(element);
    }

    public void insertItem(int index, IItem element) {
        Square sq = map.get(index);
        sq.addElement(element);
    }

    //Uses the Tile ID (1-indexed based in position in tileset)
    public void insertItem(int x, int y, int id) {
        insertItem(x, y, createItemFromId(id));
    }

    @Override
    public Square getSquare(int x, int y) {
        Square sq = map.get(toIndex(x, y));
        return sq;
    }

    @Override
    public ArrayList<IItem> getItems(IPosition position) {
        Square sq = map.get(position.getIndex());
        return sq.getElements();
    }

    @Override
    public ArrayList<IItem> getItems(int x, int y) {
        int index = toIndex(x,y);
        Square sq = map.get(index);
        return sq.getElements();
    }

    public Robot getRobot(IPosition position) {
        if(!isFree(position)) {
            Square sq = map.get(position.getIndex());
            return sq.getRobot();
        }
        return null;
    }

    @Override// Sjekker for robot
    public boolean isFree(IPosition position) {
        Square sq = map.get(position.getIndex());
        return !sq.occupied();
    }

    @Override// Sjekker for robot
    public boolean isFree(int x, int y) {
        int index = toIndex(x,y);
        Square sq = map.get(index);
        return !sq.occupied();
    }

    public void removeRobot(Position pos) {
        Square sq = map.get(pos.getIndex());
        sq.removeRobot();
    }

    public void insertRobot(Position pos, Robot rob) {
        Square sq = map.get(pos.getIndex());
        if(isFree(pos)) {
            sq.addRobot(rob);
        }
    }

    @Override
    public int toIndex(int x, int y) {
        return (y * width) + x;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void clear() {
        map.clear();
    }

    public void clearSquare(int x, int y) {
        int index = toIndex(x, y);
        map.get(index).removeElements();
    }
}
