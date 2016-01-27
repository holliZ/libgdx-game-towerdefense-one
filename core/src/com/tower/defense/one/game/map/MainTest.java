package com.tower.defense.one.game.map;


public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EnemyRoute er = new EnemyRoute();
		er.generateByCorner(new float[][]{{6,0}, {2,3}, {5,7}});
		System.out.println(er.toString());
	}

}
