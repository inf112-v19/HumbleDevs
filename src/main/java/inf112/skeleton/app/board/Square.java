package inf112.skeleton.app.board;

import inf112.skeleton.app.GameObjects.Robot;

public class Square<T> implements ISquare<T>{
	private Robot robot;
	private T elem;
	
	
	public Square(T elem) {
		this.robot = null;
		this.elem = elem;
	}
	
	public T getElement() {
		return elem;
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
	public boolean addElement(T elem) {
		if(this.elem == null && elem != null) {
			this.elem = elem;
			return true;
		}
		return false;
	}

	@Override
	public void removeRobot() {
		this.robot = null;
	}

	@Override
	public void removeElement() {
		this.elem = null;
	}

	@Override
	public Robot getRobot() {
		if(occupied()) {
			return this.robot;
		}
		return null;
	}

}