package inf112.skeleton.app.board;

import java.util.ArrayList;

import inf112.skeleton.app.GameObjects.Robot;

public class Square<T> implements ISquare<T>{
	private Robot robot;
	private ArrayList<T> elements;
	
	
	public Square(ArrayList<T> elem) {
		this.robot = null;
		this.elements = elem;
	}
	
	public ArrayList<T> getElements() {
		return elements;
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
	public void addElement(T elem) {
		this.elements.add(elem);
	}

	@Override
	public void removeRobot() {
		this.robot = null;
	}

	@Override
	public void removeElements() {
		this.elements.clear();
	}

	@Override
	public Robot getRobot() {
		if(occupied()) {
			return this.robot;
		}
		return null;
	}

}