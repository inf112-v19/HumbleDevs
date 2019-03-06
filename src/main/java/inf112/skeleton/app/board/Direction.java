package inf112.skeleton.app.board;

public enum Direction {
    NORTH, SOUTH, EAST, WEST;
    
    private Direction opposite;
	
	static {
		NORTH.opposite = SOUTH;
		SOUTH.opposite = NORTH;
		EAST.opposite = WEST;
		WEST.opposite = EAST;
	}
	public Direction getOppositeDirection() {
		return opposite;
	}

}
