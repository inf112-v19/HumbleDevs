package inf112.skeleton.app.board;

import inf112.skeleton.app.gameObjects.Robot;

import java.util.ArrayList;

public class Square<IItem> implements ISquare<IItem>{
	private Robot robot;
	private ArrayList<IItem> elements;


	public Square() {
		this.robot = null;
		elements = new ArrayList<>();
	}

	public ArrayList<IItem> getElements() {
		ArrayList<IItem> list = (ArrayList<IItem>) elements.clone();
		return list;
	}


	public boolean occupied() {
		if(robot == null) {
			return false;
		}
		return true;
	}

	@Override
	public boolean addRobot(Robot robot) {
		if(!occupied()) {
			this.robot = robot;
			return true;
		}
		return false;
	}
	@Override
	public void addElement(IItem elem) {
		elements.add(elem);
	}

	@Override
	public void removeRobot() {
		this.robot = null;
	}

	@Override
	public void removeElements() {
		elements.clear();
	}

	@Override
	public Robot getRobot() {
		if(occupied()) {
			return this.robot;
		}
		return null;
	}
}