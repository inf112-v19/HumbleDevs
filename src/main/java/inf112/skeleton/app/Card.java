package inf112.skeleton.app;

public class Card {
	private int priority;
	private Movement mov;
	
	
	public Card(int p) {
		this.priority = p;
	}
	
	public int getPriority() {
		return priority;
	}
	public Movement getMovement() {
		return this.mov;
	}
}
