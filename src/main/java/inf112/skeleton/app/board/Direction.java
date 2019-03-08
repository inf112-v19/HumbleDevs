package inf112.skeleton.app.board;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;
    
    private Direction opposite;
    private Direction left;
    private Direction right;
	
	static {
		NORTH.opposite = SOUTH;
		SOUTH.opposite = NORTH;
		EAST.opposite = WEST;
		WEST.opposite = EAST;
		NORTH.left = WEST;
		SOUTH.left = EAST;
		EAST.left = NORTH;
		WEST.left = SOUTH;
		
		NORTH.right = EAST;
		SOUTH.right = WEST;
		EAST.right = SOUTH;
		WEST.right = NORTH;
	}
	public Direction getOppositeDirection() {
		return opposite;
	}
	public Direction left() {
		return left;
	}
	public Direction right() {
		return right;
	}

}
