package com.tower.defense.one.game.actor.player;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.StaticVariable;
import com.tower.defense.one.game.actor.MyActor;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.map.Point;

public class Tower extends MyActor {

	float ATK = 20;// 攻击力
	int ATKNum = 1;// 最多可以同时攻击多少个敌人;为0时指范围攻击
	int ATKSpeed = 1;// 攻击速度
	Array<Point> enemyPoints = new Array<Point>(0);//同時攻擊的敵人
	float offsetX, offsetY;
	Color towerColor;
	Color ATKColor;
	static Color ATKBoundColor = new Color(219f/255, 163f/255, 27f/255, 0.5f);

	private int ATKCount = 0;// 本次已经攻击的敌人数目
	private Ellipse ATKEllipse;
	private Ellipse boundEllipse;
	private long lastATKTime = 0;// 用来判定该塔是否可以攻击
	private boolean showAction = false;

	public Tower(float x, float y) {//传入中心位置
		offsetX = x;
		offsetY = y;
		setBounds(x - Const.TOWER_WIDTH/2, y - Const.TOWER_HEIGHT/2, Const.TOWER_WIDTH, Const.TOWER_HEIGHT);
		ATKEllipse = new Ellipse(x, y, 0, 0);//椭圆为了判定hit需要传入的是中心位置已经宽高，但是绘制的时候需要传入的是顶点位置，这一点需要注意
		boundEllipse = new Ellipse(x , y, Const.TOWER_WIDTH, Const.TOWER_HEIGHT);
		towerColor = new Color(219f/255, 163f/255, 27f/255, 0.5f);
		ATKColor = new Color(1, 0, 0, 1);
	}
	
	public void initTower() {
		Array<Point> points = generateTowerOptionPosition(offsetX, offsetY);
		
		newTowerOption(points.get(0).getX(), points.get(0).getY(), TowerOption.CANNON, getName());
		newTowerOption(points.get(1).getX(), points.get(1).getY(), TowerOption.CRYPT, getName());
		newTowerOption(points.get(2).getX(), points.get(2).getY(), TowerOption.CROSSBOW, getName());
		newTowerOption(points.get(3).getX(), points.get(3).getY(), TowerOption.APPRENTICE, getName());
		newTowerOption(points.get(4).getX(), points.get(4).getY(), TowerOption.POISON, getName());
	}
	
	private Array<Point> generateTowerOptionPosition(float x, float y){
		Array<Point> points = new Array<Point>();
		points.add(new Point(x, y + 100));
		points.add(new Point(x - MathUtils.sinDeg(72) * 100, y + MathUtils.cosDeg(72) * 100));
		points.add(new Point(x - MathUtils.sinDeg(36) * 100, y - MathUtils.cosDeg(36) * 100));
		points.add(new Point(x + MathUtils.sinDeg(72) * 100, y + MathUtils.cosDeg(72) * 100));
		points.add(new Point(x + MathUtils.sinDeg(36) * 100, y - MathUtils.cosDeg(36) * 100));
		return points;
	}
	
	private void newTowerOption(float x, float y, int type, String towerName){
		TowerOption TowerOption= new TowerOption(x, y , type);
		TowerOption.setName(towerName + "_" + type);
		TowerOption.setVisible(false);
		getParent().addActorAfter(this, TowerOption);
	}
	
	void updateATKBound(float atkWidth, float atkHeight){
		ATKEllipse.setSize(atkWidth, atkHeight);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.end();

		StaticVariable.shapeRenderer.begin(ShapeType.Filled);
		StaticVariable.shapeRenderer.setColor(towerColor.r, towerColor.g, towerColor.b, towerColor.a);
		StaticVariable.shapeRenderer.ellipse(boundEllipse.x - boundEllipse.width / 2,
				boundEllipse.y - boundEllipse.height / 2, boundEllipse.width,
				boundEllipse.height);
		StaticVariable.shapeRenderer.end();

		StaticVariable.shapeRenderer.begin(ShapeType.Line);
		StaticVariable.shapeRenderer.setColor(ATKBoundColor.r, ATKBoundColor.g, ATKBoundColor.b, ATKBoundColor.a);
		StaticVariable.shapeRenderer.ellipse(ATKEllipse.x - ATKEllipse.width / 2, ATKEllipse.y
				- ATKEllipse.height / 2, ATKEllipse.width, ATKEllipse.height);
		StaticVariable.shapeRenderer.setColor(ATKColor.r, ATKColor.g, ATKColor.b, ATKColor.a);
		Iterator<Point> it = enemyPoints.iterator();
		while (it.hasNext()) {
			Point point = it.next();
			StaticVariable.shapeRenderer.line(ATKEllipse.x, ATKEllipse.y, point.getX(),
					point.getY());
		}
		StaticVariable.shapeRenderer.end();

		batch.begin();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		enemyPoints.clear();
		if (canATK()) {
			for (int i = 0; i < StaticVariable.enemyNum; i++) {
				Enemy enemy = getParent().findActor(Const.ENEMY_ACTOR + i);
				if (enemy != null && hitEnemy(enemy)) {
					ATKCount++;
					if (ATKNum == 0 || ATKCount < ATKNum) {
						continue;
					} else {
						break;
					}
				}
			}
		}
	}

	private boolean hitEnemy(Enemy enemy) {
		if (ATKEllipse.contains(enemy.getX(), enemy.getY())
				|| ATKEllipse.contains(enemy.getX() + enemy.getWidth(),
						enemy.getY())
				|| ATKEllipse.contains(enemy.getX(),
						enemy.getY() + enemy.getHeight())
				|| ATKEllipse.contains(enemy.getX() + enemy.getWidth(),
						enemy.getY() + enemy.getHeight())) {
			enemyPoints.add(new Point(enemy.getX() + enemy.getWidth() / 2,
					enemy.getY() + enemy.getHeight() / 2));
			enemy.hited(ATK);
			lastATKTime = TimeUtils.nanoTime();
			return true;
		}
		return false;
	}

	private boolean canATK() {
		return TimeUtils.nanoTime() - lastATKTime > Const.ONE_SECOND * ATKSpeed;
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && !isTouchable())
			return null;
		return boundEllipse.contains(getX() + x, getY() + y) ? this : null;
	}
	
	public void hideWhenClickOthers(){
		if(showAction) {
			showOrHideOption();
		}
	}
	
	public void onClick(){
		Gdx.app.debug("Tower", "OnClick");
		if(StaticVariable.lastTouchActorName != getName() && !StaticVariable.lastTouchActorName.startsWith(getName())) {
			super.onClick();
		}
		showOrHideOption();
	}
	
	void showOrHideOption(){
		showAction = !showAction;
		showOrHideOption(TowerOption.CANNON);
		showOrHideOption(TowerOption.CRYPT);
		showOrHideOption(TowerOption.CROSSBOW);
		showOrHideOption(TowerOption.APPRENTICE);
		showOrHideOption(TowerOption.POISON);
	}
	
	private void showOrHideOption(int type) {
		TowerOption towerTtpe = getParent().findActor(getName() + "_" + type);
		if(towerTtpe != null) {
			towerTtpe.setVisible(showAction);
		}
	}
	
	
}
