package inf112.skeleton.app.board;

/**
 * An object to represent a x,y position on a board,
 * with a correlating index that corresponds to a position in an height*width sized list.
 *
 * The index is calculated as: (y * width) + x
 */
public class Position implements IPosition {
    private final int x;
    private final int y;
    private final int index;

    public Position(int x, int y, int index) {
        this.x = x;
        this.y = y;
        this.index = index;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getIndex() {
        return index;
    }


    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Position position = (Position) that;
        return x == position.x && y == position.y;
    }
}
