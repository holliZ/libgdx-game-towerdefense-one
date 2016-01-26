package com.tower.defense.one.game.map;

import com.badlogic.gdx.utils.Array;


public class EnemyRoute {

	Array<Point> points;
	int size;
	
	public EnemyRoute(){
		points = new Array<Point>();
		size = 0;
	}
	
	public EnemyRoute(Point[] points){
		this.points = new Array<Point>();
		for(Point point: points) {
			this.points.add(point);
		}
		this.size = points.length;
	}
	
	public EnemyRoute(int[][] points){//new EnemyRoute(new int[][]{{1,2},{2,3},{3,4}});
		this.points = new Array<Point>();
		for(int i = 0; i < points.length; i++){
			this.points.add(new Point(points[i][0], points[i][1]));
		}
		this.size = points.length;
	}
	
	public void addPoint(Point point){
		points.add(point);
		size++;
		
	}
	
	public void addPoint(int x, int y){
		points.add(new Point(x,y));
		size++;
		
	}
	
	public Point getPoint(int step) {
		return points.get(step);
	}
	
	public boolean isLastStep(int step){
		return step >= size;
	}
}
