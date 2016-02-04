package com.tower.defense.one.game.actor.player;

import static com.tower.defense.one.game.Assets.chinese;
import static com.tower.defense.one.game.Assets.shapeRenderer;
import static com.tower.defense.one.game.Const.TOWERTYPE_BUTTON_RADIUS;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.tower.defense.one.game.Const;
import com.tower.defense.one.game.actor.BasicActor;
import com.tower.defense.one.game.actor.bg.BGPanel;

public class TowerTypeOption extends BasicActor {

	public static final int CANNON = 1;
	public static final int CRYPT = 2;
	public static final int CROSSBOW = 3;
	public static final int APPRENTICE = 4;
	public static final int POISON = 5;

	// private TextureRegion region;
	private static final int MAXCOST = 10000;
	private int type = 1;
	private int COST = MAXCOST;

	private Ellipse tempATKEllipse;
	private Color tempATKBoundColor;
	private Color towerColor;
	private String chineseStr;
	private Circle optionCircle;

	public TowerTypeOption(float x, float y, int type) {
		this(x, y, type, false);
	}

	public TowerTypeOption(float x, float y, int type, boolean locked) {
		super(x, y, 0, 0);
		optionCircle = new Circle(getX(), getY(), TOWERTYPE_BUTTON_RADIUS);
		tempATKEllipse = new Ellipse(0, 0, 0, 0);
		tempATKBoundColor = new Color();
		towerColor = Const.LockedTowerColor;
		
		this.type = type;
		if (locked) {
			chineseStr = "迷";
		} else {
			switch (type) {
			case CANNON:
				chineseStr = "炮";
				COST = OrcCannon.COST;
				towerColor = Const.CannonColor;
				break;
			case CRYPT:
				chineseStr = "兵";
				break;
			case CROSSBOW:
				chineseStr = "箭";
				break;
			case APPRENTICE:
				chineseStr = "法";
				break;
			case POISON:
				chineseStr = "毒";
				break;
			}
		}
		// if (locked) {
		// region = lockingIcon;
		// } else {
		// switch (type) {
		// case CANNON:
		// region = cannonIcon;
		// COST = OrcCannon.COST;
		// break;
		// case CRYPT:
		// region = cryptIcon;
		// break;
		// case CROSSBOW:
		// region = crossbowIcon;
		// break;
		// case APPRENTICE:
		// region = apprenticeIcon;
		// break;
		// case POISON:
		// region = poisonIcon;
		// break;
		// }
		// }
		// setBounds(x - region.getRegionWidth() / 2, y -
		// region.getRegionHeight()
		// / 2, region.getRegionWidth(), region.getRegionHeight());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);

		batch.end();
		
		if (lastTouchActorName == getName()) {
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(tempATKBoundColor.r, tempATKBoundColor.g,
					tempATKBoundColor.b, tempATKBoundColor.a);
			shapeRenderer.ellipse(tempATKEllipse.x - tempATKEllipse.width / 2,
					tempATKEllipse.y - tempATKEllipse.height / 2,
					tempATKEllipse.width, tempATKEllipse.height);
			shapeRenderer.end();
		}

		
//		batch.draw(region, getX(), getY(), getWidth(), getHeight());
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(towerColor);
		shapeRenderer.circle(optionCircle.x, optionCircle.y, optionCircle.radius + 2);
		
		
		if (COST <= BGPanel.TreasureCur) {
			shapeRenderer.setColor(Const.ButtonAvailableColor);
		} else {
			shapeRenderer.setColor(Const.CoverColor);
		}
		
		shapeRenderer.circle(optionCircle.x, optionCircle.y, optionCircle.radius);
		shapeRenderer.end();
		
		batch.begin();
		if (lastTouchActorName == getName() && COST < MAXCOST) {
			chinese.draw(batch, "√", getX() - optionCircle.radius, getY() + chinese.getCapHeight()/2, optionCircle.radius * 2, Align.center, false);
		} else {
			chinese.draw(batch, chineseStr, getX() - optionCircle.radius, getY() + chinese.getCapHeight()/2, optionCircle.radius * 2, Align.center, false);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public void onClick() {
		Gdx.app.debug("TowerType", type + " ");
		if (lastTouchActorName != getName()) {
			Tower tower = getParent().findActor(getName().split("_")[0]);
			if (tower != null) {
				switch (type) {
				case CANNON:
					tempATKEllipse.set(tower.offsetX, tower.offsetY,
							OrcCannon.getATKWidth(OrcCannon.Level1),
							OrcCannon.getATKHeight(OrcCannon.Level1));
					tempATKBoundColor.set(Const.CannonColor);
					break;
				case CRYPT:
					return;
				case CROSSBOW:
					return;
				case APPRENTICE:
					return;
				case POISON:
					return;
				}
			}

		} else if (COST <= BGPanel.TreasureCur) {
			tempATKEllipse.set(0, 0, 0, 0);
			Tower tower = getParent().findActor(getName().split("_")[0]);
			if (tower != null) {
				switch (type) {
				case CANNON:
					Tower newTower = new OrcCannon(tower.offsetX, tower.offsetY);
					BGPanel.addTreasure(-COST);
					newTower.setName(tower.getName());
					getParent().addActorAfter(tower, newTower);
					break;
				case CRYPT:
					return;
				case CROSSBOW:
					return;
				case APPRENTICE:
					return;
				case POISON:
					return;
				}
				tower.hideWhenClickOthers();
				tower.setVisible(false);

				for (int i = 1; i <= 5; i++) {
					if (i == type) {
						continue;
					}
					TowerTypeOption type = getParent().findActor(
							getName().split("_")[0] + "_" + i);
					type.remove();
				}
				tower.remove();
				remove();
			}
		}
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && !isTouchable())
			return null;
		return optionCircle.contains(getX() + x, getY() + y) ? this : null;
	}
	

}
