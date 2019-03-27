package inf112.skeleton.app.board;

/**
 * An object to represent a x,y position on a board,
 * with a correlating index that corresponds to a position in an height*width sized list.
 *
 * The index is calculated as: (y * width) + x
 */
public class Position implements IPosition {
    private int xPos;
    private int yPos;

    public Position(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    @Override
    public int getX() {
        return xPos;
    }

    @Override
    public int getY() {
        return yPos;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Position position = (Position) that;
        return xPos == position.xPos && yPos == position.yPos;
    }
    public void moveEast() {
		this.xPos++;
	}
	public void moveWest() {
		this.xPos--;
	}
	public void moveNorth() {
		this.yPos++;
	}
	public void moveSouth() {
		this.yPos--;
	}
	public void move(Direction dir){
        switch(dir){
            case NORTH:
                moveNorth();
                break;
            case WEST:
                moveWest();
                break;
            case SOUTH:
                moveSouth();
                break;
            case EAST:
                moveEast();
                break;
            default:
                break;
        }
    }
    @Override
    public String toString(){
        return "(" + getX() + "," + getY() + ")";
    }
}
