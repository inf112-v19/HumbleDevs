package inf112.skeleton.app.board;

import java.util.ArrayList;
import java.util.List;

/**
 * Board represented as a grid with width*height size.
 * Contains an element for each position.
 *
 * The list of cells contain the elements, while the positions-list is an immutable list for position objects.
 *
 * An empty cell is currently null.
 *
 * @param <T>
 */
public class Board<T> implements IBoard<T> {
    protected final int width;
    protected final int height;
    protected final int size;
    protected final List<Position> positions;
    private List<T> cells;


    public Board(int width, int height) {
        this.height = height;
        this.width = width;
        this.size = height * width;
        positions = new ArrayList<>();
        cells = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                positions.add(new Position(x, y, (y * width) + x));
                cells.add(null);
            }
        }

    }

    @Override
    public void insertElement(IPosition position, T element) {
        cells.add(position.getIndex(), element);
    }

    @Override
    public void insertElement(int x, int y, T element) {
        cells.add(toIndex(x, y), element);
    }

    public void insertElement(int index, T element) { cells.add(index, element); }

    @Override
    public T getElement(IPosition position) {
        return cells.get(position.getIndex());
    }

    @Override
    public T getElement(int x, int y) {
        return cells.get(toIndex(x, y));
    }

    @Override
    public boolean isFree(IPosition position) {
        return cells.get(position.getIndex()) == null;
    }

    @Override
    public boolean isFree(int x, int y) {
        return cells.get(toIndex(x, y)) == null;
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
        cells.clear();
    }
}
