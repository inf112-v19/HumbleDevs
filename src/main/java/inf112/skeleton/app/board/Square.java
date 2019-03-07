package inf112.skeleton.app.board;

import inf112.skeleton.app.GameObjects.Robot;

import java.util.ArrayList;

public class Square<IItem> implements ISquare<IItem>{
	private Robot robot;
	private ArrayList<IItem> listOfItems;
	
	
	public Square(IItem elem) {
		this.robot = null;
		listOfItems = new ArrayList<>();
	}

	public ArrayList<IItem> getListOfItems() {
	    ArrayList<IItem> list = (ArrayList<IItem>) listOfItems.clone();
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
	public boolean addElement(IItem elem) {
			listOfItems.add(elem);
			return true;
	}

	@Override
	public void removeRobot() {
		this.robot = null;
	}

	@Override
	public Robot getRobot() {
		if(occupied()) {
			return this.robot;
		}
		return null;
	}

}