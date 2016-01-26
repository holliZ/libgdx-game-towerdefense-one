package com.tower.defense.one.game.map;

public class Point {
	float x;
	float y;
	
	public Point(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public boolean isEqual(Point a){
		if(a.x == this.x && a.y == this.y) return true;
		return false;
	}
	
	public boolean isAdjacent(Point a){
		if(a.x == this.x || a.y == this.y) return true;
		return false;
	}
	
	public String toString(){
		return "{" + x + "," + y + "}";
	}
	
	public void set(float x, float y){
		this.x = x;
		this.y = y;
	}
	
}
