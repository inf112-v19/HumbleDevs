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
    private int index;

    public Position(int x, int y) {
        this.xPos = x;
        this.yPos = y;
        this.index = (y*12)+x;
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
    public int getIndex() {
        return index;
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
		this.index++;
	}
	public void moveWest() {
		this.xPos--;
		this.index--;
	}
	public void moveNorth() {
		this.yPos++;
		this.index= this.index + 12;
	}
	public void moveSouth() {
		this.yPos--;
		this.index = this.index - 12;
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

}
