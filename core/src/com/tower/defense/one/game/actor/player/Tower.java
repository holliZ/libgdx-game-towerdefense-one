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
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.map.Point;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;

import static com.tower.defense.one.game.Assets.shapeRenderer;

public class Tower extends BasicActor {

	int ATK = 20;// 攻击力
	int ATKNum = 1;// 最多可以同时攻击多少个敌人;为0时指范围攻击
	float ATKSpeed = 1;// 攻击速度, 数值越大越慢
	Array<Point> enemyPoints = new Array<Point>(0);//同時攻擊的敵人
	float offsetX, offsetY;
	Color towerColor;
	Color ATKColor;
	Color ATKBoundColor;

	protected int ATKCount = 0;// 本次已经攻击的敌人数目
	private Ellipse ATKEllipse;
	private Ellipse boundEllipse;
	protected long lastATKTime = 0;// 用来判定该塔是否可以攻击
	protected boolean showAction = false;

	public Tower(float x, float y) {//传入中心位置
		super(x - Const.TOWER_WIDTH/2, y - Const.TOWER_HEIGHT/2, Const.TOWER_WIDTH, Const.TOWER_HEIGHT);
		offsetX = x;
		offsetY = y;
		ATKEllipse = new Ellipse(x, y, 0, 0);//椭圆为了判定hit需要传入的是中心位置已经宽高，但是绘制的时候需要传入的是顶点位置，这一点需要注意
		boundEllipse = new Ellipse(x , y, Const.TOWER_WIDTH, Const.TOWER_HEIGHT);
		towerColor = new Color(Const.DefaultTowerColor);
		ATKColor = new Color(1, 0, 0, 1);
	}
	
	public void init() {
		Array<Point> points = generateTowerOptionPosition(offsetX, offsetY);
		
		newTowerOption(points.get(0).getX(), points.get(0).getY(), TowerTypeOption.CANNON, getName(), false);
		newTowerOption(points.get(1).getX(), points.get(1).getY(), TowerTypeOption.CRYPT, getName(), true);
		newTowerOption(points.get(2).getX(), points.get(2).getY(), TowerTypeOption.CROSSBOW, getName(), true);
		newTowerOption(points.get(3).getX(), points.get(3).getY(), TowerTypeOption.APPRENTICE, getName(), true);
		newTowerOption(points.get(4).getX(), points.get(4).getY(), TowerTypeOption.POISON, getName(), true);
	}
	
	private Array<Point> generateTowerOptionPosition(float x, float y){
		float distance = 70f;
		Array<Point> points = new Array<Point>();
		points.add(new Point(x, y + distance));
		points.add(new Point(x - MathUtils.sinDeg(72) * distance, y + MathUtils.cosDeg(72) * distance));
		points.add(new Point(x - MathUtils.sinDeg(36) * distance, y - MathUtils.cosDeg(36) * distance));
		points.add(new Point(x + MathUtils.sinDeg(72) * distance, y + MathUtils.cosDeg(72) * distance));
		points.add(new Point(x + MathUtils.sinDeg(36) * distance, y - MathUtils.cosDeg(36) * distance));
		return points;
	}
	
	private void newTowerOption(float x, float y, int type, String towerName, boolean locked){
		TowerTypeOption option= new TowerTypeOption(x, y , type, locked);
		option.setName(towerName + "_" + type);
		option.setVisible(false);
		getParent().addActorAfter(this, option);
	}
	
	void updateATKBound(float atkWidth, float atkHeight){
		ATKEllipse.setSize(atkWidth, atkHeight);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.end();

		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(towerColor.r, towerColor.g, towerColor.b, towerColor.a);
		shapeRenderer.ellipse(boundEllipse.x - boundEllipse.width / 2,
				boundEllipse.y - boundEllipse.height / 2, boundEllipse.width,
				boundEllipse.height);
		shapeRenderer.end();

		shapeRenderer.begin(ShapeType.Line);
		
		if(ATKBoundColor != null) {
			shapeRenderer.setColor(ATKBoundColor.r, ATKBoundColor.g, ATKBoundColor.b, ATKBoundColor.a);
			shapeRenderer.ellipse(ATKEllipse.x - ATKEllipse.width / 2, ATKEllipse.y
					- ATKEllipse.height / 2, ATKEllipse.width, ATKEllipse.height);
		}
		
		if(ATKColor != null) {
			shapeRenderer.setColor(ATKColor.r, ATKColor.g, ATKColor.b, ATKColor.a);
			Iterator<Point> it = enemyPoints.iterator();
			while (it.hasNext()) {
				Point point = it.next();
				shapeRenderer.line(ATKEllipse.x, ATKEllipse.y, point.getX(),
						point.getY());
			}
		}
		shapeRenderer.end();

		batch.begin();
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		enemyPoints.clear();
		towerAct(delta);
	}
	
	protected void towerAct(float delta) {
		if (canATK()) {
			for (int i = 0; i < BGPanel.enemyIndex; i++) {
				Enemy enemy = getParent().findActor(Const.ENEMY_ACTOR + i);
				if (enemy != null && hitEnemy(enemy)) {
					if (ATKNum == 0 || ATKCount < ATKNum) {
						continue;
					} else {
						break;
					}
				}
			}
		}
	}
	
	protected boolean findEnemy(Enemy enemy){
		if (ATKEllipse.contains(enemy.getX(), enemy.getY())
				|| ATKEllipse.contains(enemy.getX() + enemy.getWidth(),
						enemy.getY())
				|| ATKEllipse.contains(enemy.getX(),
						enemy.getY() + enemy.getHeight())
				|| ATKEllipse.contains(enemy.getX() + enemy.getWidth(),
						enemy.getY() + enemy.getHeight())) {
			enemyPoints.add(new Point(enemy.getX() + enemy.getWidth() / 2,
					enemy.getY() + enemy.getHeight() / 2));
			return true;
		}
		return false;
	}

	protected boolean hitEnemy(Enemy enemy) {
		if(findEnemy(enemy)) {
			ATKCount++;
			enemy.hited(ATK);
			lastATKTime = TimeUtils.nanoTime();
			return true;
		}
		return false;
	}

	protected boolean canATK() {
		return TimeUtils.nanoTime() - lastATKTime > Const.ONE_SECOND/ PlaySpeedButton.getSpeed() * ATKSpeed;
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
		Gdx.app.debug("Tower", "OnClick  lastTouchActorName =" + lastTouchActorName + " getName()=" + getName());
		if(lastTouchActorName != null && lastTouchActorName != getName() && !lastTouchActorName.startsWith(getName())) {
			super.onClick();
		}
		showOrHideOption();
	}
	
	void showOrHideOption(){
		showAction = !showAction;
		showOrHideOption(TowerTypeOption.CANNON);
		showOrHideOption(TowerTypeOption.CRYPT);
		showOrHideOption(TowerTypeOption.CROSSBOW);
		showOrHideOption(TowerTypeOption.APPRENTICE);
		showOrHideOption(TowerTypeOption.POISON);
	}
	
	private void showOrHideOption(int type) {
		TowerTypeOption towerType = getParent().findActor(getName() + "_" + type);
		if(towerType != null) {
			towerType.setVisible(showAction);
		}
	}

}
