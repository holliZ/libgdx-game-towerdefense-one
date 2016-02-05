package com.tower.defense.one.game.actor.player;

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
import com.tower.defense.one.game.ShaperRendererUtils;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.actor.bg.BGPanel;
import com.tower.defense.one.game.actor.button.PlaySpeedButton;
import com.tower.defense.one.game.actor.enemy.Enemy;
import com.tower.defense.one.game.map.Point;

public class Tower extends BasicActor {

	int ATK;// 攻击力
	int ATKNum = 1;// 最多可以同时攻击多少个敌人;为0时指范围攻击
	float ATKSpeed;// 攻击速度, 数值越大越慢
	Array<Point> enemyPoints = new Array<Point>(0);//同時攻擊的敵人
	float offsetX, offsetY;
	int ATKCount = 0;// 本次已经攻击的敌人数目
	long lastATKTime = 0;// 用来判定该塔是否可以攻击
	boolean showAction = false;
	int Level = 0;
	
	Color towerColor;
	Color ATKBoundColor;
	
	private final float optionDistance = 90f;
	
	private Ellipse towerEllipse;
	private TowerType towerType = null;
	private Actor[] types;
	private TowerActionOption[] actions;
	private float oldEllipseWidth, oldEllipseHeight;
	private Ellipse ATKBoundEllipse;

	public Tower(float x, float y) {//传入中心位置
		super(x - Const.TOWER_WIDTH/2, y - Const.TOWER_HEIGHT/2, Const.TOWER_WIDTH, Const.TOWER_HEIGHT);
		offsetX = x;
		offsetY = y;
		oldEllipseWidth = -1;
		towerColor = new Color();
		ATKBoundEllipse = new Ellipse(x, y, 0, 0);//椭圆为了判定hit需要传入的是中心位置已经宽高，但是绘制的时候需要传入的是顶点位置，这一点需要注意
		towerEllipse = new Ellipse(x , y, Const.TOWER_WIDTH, Const.TOWER_HEIGHT);
		types = new Actor[5];
		actions = new TowerActionOption[2];
	}
	
	public void init() {
		Array<Point> pointsTowerType = generateTowerTypeOptionPosition(offsetX, offsetY);
		
		types[0] = newTowerTypeOption(pointsTowerType.get(0).getX(), pointsTowerType.get(0).getY(), TowerTypeOption.CANNON, getName(), false);
		types[1] = newTowerTypeOption(pointsTowerType.get(1).getX(), pointsTowerType.get(1).getY(), TowerTypeOption.CRYPT, getName(), true);
		types[2] = newTowerTypeOption(pointsTowerType.get(2).getX(), pointsTowerType.get(2).getY(), TowerTypeOption.CROSSBOW, getName(), true);
		types[3] = newTowerTypeOption(pointsTowerType.get(3).getX(), pointsTowerType.get(3).getY(), TowerTypeOption.APPRENTICE, getName(), true);
		types[4] = newTowerTypeOption(pointsTowerType.get(4).getX(), pointsTowerType.get(4).getY(), TowerTypeOption.POISON, getName(), true);
		
		Array<Point> pointsTowerAction = generateTowerActionOptionPosition(offsetX, offsetY);
		actions[0] = newTowerActionOption(pointsTowerAction.get(0).getX(), pointsTowerAction.get(0).getY(), TowerActionOption.SELL, getName());
		actions[1] = newTowerActionOption(pointsTowerAction.get(1).getX(), pointsTowerAction.get(1).getY(), TowerActionOption.UPGRADE, getName());
		
		setTowerType(null);
	}
	
	private Array<Point> generateTowerTypeOptionPosition(float x, float y){
		Array<Point> points = new Array<Point>(5);
		points.add(new Point(x, y + optionDistance));
		points.add(new Point(x - MathUtils.sinDeg(72) * optionDistance, y + MathUtils.cosDeg(72) * optionDistance));
		points.add(new Point(x - MathUtils.sinDeg(36) * optionDistance, y - MathUtils.cosDeg(36) * optionDistance));
		points.add(new Point(x + MathUtils.sinDeg(72) * optionDistance, y + MathUtils.cosDeg(72) * optionDistance));
		points.add(new Point(x + MathUtils.sinDeg(36) * optionDistance, y - MathUtils.cosDeg(36) * optionDistance));
		return points;
	}
	
	private Actor newTowerTypeOption(float x, float y, int type, String towerName, boolean locked){
		TowerTypeOption option= new TowerTypeOption(this, x, y , type, locked);
		option.setName(towerName + "_" + type);
		option.setVisible(false);
		getParent().addActorAfter(this, option);
		return option;
	}
	
	private Array<Point> generateTowerActionOptionPosition(float x, float y){
		Array<Point> points = new Array<Point>(2);
		points.add(new Point(x - optionDistance, y));
		points.add(new Point(x + optionDistance, y));
		return points;
	}
	
	private TowerActionOption newTowerActionOption(float x, float y, int type, String towerName){
		TowerActionOption option= new TowerActionOption(this, x, y , type);
		option.setName(towerName + "_" + type);
		option.setVisible(false);
		getParent().addActorAfter(this, option);
		return option;
	}
	
	void updateATKBound(float atkWidth, float atkHeight){
		ATKBoundEllipse.setSize(atkWidth, atkHeight);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.end();
		
		ShaperRendererUtils.DrawEllipse(towerEllipse, towerColor, ShapeType.Filled);
		
		if(ATKBoundColor != null) {
			ShaperRendererUtils.EnableAlpha();
			ShaperRendererUtils.DrawEllipse(ATKBoundEllipse, ATKBoundColor, ShapeType.Filled);
			ShaperRendererUtils.DrawEllipse(ATKBoundEllipse, ATKBoundColor, ShapeType.Line);
		}
		
		/*shapeRenderer.begin(ShapeType.Line);
		if(ATKColor != null) {
			shapeRenderer.setColor(ATKColor);
			Iterator<Point> it = enemyPoints.iterator();
			while (it.hasNext()) {
				Point point = it.next();
				shapeRenderer.line(ATKBoundEllipse.x, ATKBoundEllipse.y, point.getX(),
						point.getY());
			}
		}
		shapeRenderer.end();*/
		
		if(showAction) {
			ShaperRendererUtils.DrawCircle(offsetX, offsetY, optionDistance, Const.SplitLinesColor, ShapeType.Line);
		}

		batch.begin();
		if(towerType != null) {
			towerType.towerDraw(batch, parentAlpha);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		enemyPoints.clear();
		if(towerType != null) {
			towerType.towerAct(this, delta);
		}
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
		if (ATKBoundEllipse.contains(enemy.getX(), enemy.getY())
				|| ATKBoundEllipse.contains(enemy.getX() + enemy.getWidth(),
						enemy.getY())
				|| ATKBoundEllipse.contains(enemy.getX(),
						enemy.getY() + enemy.getHeight())
				|| ATKBoundEllipse.contains(enemy.getX() + enemy.getWidth(),
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
		return towerEllipse.contains(getX() + x, getY() + y) ? this : null;
	}
	
	public void hideWhenClickOthers(){
		tempATKBoundSize(-1, -1);
		if(showAction) {
			showAction = !showAction;
			showOrHideActions();
			showOrHideTypes();
			ATKBoundColor = null;
		}
	}
	
	public void onClick(){
		Gdx.app.debug("Tower", "OnClick  lastTouchActorName =" + lastTouchActorName + " getName()=" + getName());
		if(lastTouchActorName != null && lastTouchActorName != getName() && !lastTouchActorName.startsWith(getName())) {
			super.onClick();
		}
		showOrHideOption();
		if(showAction) {
			ATKBoundColor = Const.ATKColor;
		} else {
			ATKBoundColor = null;
		}
	}
	
	void showOrHideOption(){
		showAction = !showAction;
		if(towerType != null) {
			showOrHideActions();
		} else {
			showOrHideTypes();
		}
	}

	public TowerType getTowerType() {
		return towerType;
	}

	public void setTowerType(TowerType towerType) {
		this.towerType = towerType;
		if(towerType != null) {
			Level = 1;
			ATK = towerType.getATK(Level);
			ATKSpeed = towerType.getATKSpeed();
			towerColor.set(towerType.getTowerColor());
			updateATKBound(towerType.getATKWidth(Level), towerType.getATKHeight(Level));
		} else {
			Level = 0;
			ATK = 20;
			ATKSpeed = 1f;
			towerColor.set(Const.DefaultTowerColor);
			updateATKBound(0,0);
		}
		actions[0].update();
		actions[1].update();
	}
	
	public void upgrade(){
		if(towerType != null) {
			Level++;
			ATK = towerType.getATK(Level);
			updateATKBound(towerType.getATKWidth(Level), towerType.getATKHeight(Level));
		}
	}
	
	void showOrHideActions() {
		actions[0].setVisible(showAction);
		actions[1].setVisible(showAction);
	}
	
	void showOrHideTypes(){
		types[0].setVisible(showAction);
		types[1].setVisible(showAction);
		types[2].setVisible(showAction);
		types[3].setVisible(showAction);
		types[4].setVisible(showAction);
	}
	
	void tempATKBoundSize(float width, float height){
		Gdx.app.debug(getName(), "tempATKBoundSize(" + width +"," + height +")");
		if(width == -1) {
			if(oldEllipseWidth != -1) {
				updateATKBound(oldEllipseWidth, oldEllipseHeight);
			}
		} else {
			oldEllipseWidth = ATKBoundEllipse.width;
			oldEllipseHeight = ATKBoundEllipse.height;
			updateATKBound(width, height);
		}
	}

}
