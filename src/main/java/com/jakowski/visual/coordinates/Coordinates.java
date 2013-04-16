
package com.jakowski.visual.coordinates;

/**
 * Coordinates is need to visualization solution the Fifteen Puzzle
 *
 * @author Sebastian Jakowski
 */

public class Coordinates {
	
	private int x;
	private int y;
	private int value;
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	public Coordinates(int x, int y, int value) {
		super();
		this.x = x;
		this.y = y;
		this.value = value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	

}
