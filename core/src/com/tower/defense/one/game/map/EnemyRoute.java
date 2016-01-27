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
	
	public EnemyRoute(float[][] points){//new EnemyRoute(new float[][]{{1,2},{2,3},{3,4}});
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
	
	public void addPoint(float x, float y){
		points.add(new Point(x,y));
		size++;
		
	}
	
	public Point getPoint(int step) {
		return points.get(step);
	}
	
	public boolean isLastStep(int step){
		return step >= size;
	}
	
	public void generateByCorner(float[][] cornerPoints){
		if(cornerPoints.length > 0) {
			Point lastCorner = new Point(cornerPoints[0][0], cornerPoints[0][1]);
			Point curCorner;
			float obliqueLength;
			float x, y;
			float xDValue, yDValue;
			addPoint(lastCorner);
			for(int i = 1; i < cornerPoints.length; i++){
				curCorner = new Point(cornerPoints[i][0], cornerPoints[i][1]);
				xDValue = curCorner.x - lastCorner.x;
				yDValue = curCorner.y - lastCorner.y;
				obliqueLength = (float) Math.sqrt(Math.pow(Math.abs(xDValue),2) + Math.pow(Math.abs(yDValue), 2));
				for(float oblique = 1.0f; oblique < obliqueLength; oblique += 1.0f) {
					x = xDValue * oblique/obliqueLength;
					y = yDValue * oblique/obliqueLength;
					addPoint(lastCorner.x + x, lastCorner.y + y);
				}
				lastCorner = curCorner;
				addPoint(lastCorner);
			}
		}
	}
	
	public String toString(){
		String str ="{";
		for(int i = 0; i < size; i++){
			Point p = this.points.get(i);
			str += "{" + p.x + "," + p.y + "}";
		}
		str += "}";
		return str;
	}
}
