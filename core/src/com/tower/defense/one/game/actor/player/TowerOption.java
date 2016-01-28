package com.tower.defense.one.game.actor.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.tower.defense.one.game.Assets;
import com.tower.defense.one.game.StaticVariable;
import com.tower.defense.one.game.actor.MyActor;

public class TowerOption extends MyActor {

	public static final int CANNON = 1;
	public static final int CRYPT = 2;
	public static final int CROSSBOW = 3;
	public static final int APPRENTICE = 4;
	public static final int POISON = 5;

	private TextureRegion region;
	private int type = 1;
	
	private Ellipse tempATKEllipse;
	private Color tempATKBoundColor;
	
	public TowerOption(float x, float y, int type) {
		this(x, y, type, false);
		tempATKEllipse = new Ellipse(0,0,0,0);
		tempATKBoundColor = new Color();
	}

	public TowerOption(float x, float y, int type, boolean locked) {
		this.type = type;
		if(locked) {
			region = Assets.lockingIcon;
			setTouchable(Touchable.disabled);
		} else {
			switch (type) {
			case CANNON:
				region = Assets.cannonIcon;
				break;
			case CRYPT:
				region = Assets.cryptIcon;
				break;
			case CROSSBOW:
				region = Assets.crossbowIcon;
				break;
			case APPRENTICE:
				region = Assets.apprenticeIcon;
				break;
			case POISON:
				region = Assets.poisonIcon;
				break;
			default:
				region = Assets.lockingIcon;
				setTouchable(Touchable.disabled);
				break;
			}
		}
		
		setBounds(x - region.getRegionWidth() / 2, y - region.getRegionHeight()
				/ 2, region.getRegionWidth(), region.getRegionHeight());

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.draw(region, getX(), getY(), getWidth(), getHeight());
		
		if(StaticVariable.lastTouchActorName == getName()) {
			batch.end();
			StaticVariable.shapeRenderer.begin(ShapeType.Line);
			StaticVariable.shapeRenderer.setColor(1, 0, 0, 1);
			StaticVariable.shapeRenderer.rect(getX(), getY(), getWidth(), getHeight());
			StaticVariable.shapeRenderer.setColor(tempATKBoundColor.r, tempATKBoundColor.g, tempATKBoundColor.b, tempATKBoundColor.a);
			StaticVariable.shapeRenderer.ellipse(tempATKEllipse.x - tempATKEllipse.width/2, tempATKEllipse.y
					- tempATKEllipse.height / 2, tempATKEllipse.width, tempATKEllipse.height);
			StaticVariable.shapeRenderer.end();
			
			batch.begin();
		}
		
	}

	@Override
	public void act(float delta) {
		super.act(delta);
	}

	public void onClick() {
		Gdx.app.debug("TowerType", type + " ");
		
		if(StaticVariable.lastTouchActorName != getName()) {
			Tower tower = getParent().findActor(getName().split("_")[0]);
			if (tower != null) {
				switch (type) {
				case CANNON:
					tempATKEllipse.set(tower.offsetX, tower.offsetY, OrcCannon.getATKWidth(OrcCannon.Level1), OrcCannon.getATKHeight(OrcCannon.Level1));
					tempATKBoundColor.set(OrcCannon.ATKBoundColor);
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
			
		} else {
			tempATKEllipse.set(0,0,0,0);
			Tower tower = getParent().findActor(getName().split("_")[0]);
			if (tower != null) {
				switch (type) {
				case CANNON:
					Tower newTower = new OrcCannon(tower.offsetX, tower.offsetY);
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
					TowerOption type = getParent().findActor(
							getName().split("_")[0] + "_" + i);
					type.remove();
				}
				tower.remove();
				this.remove();
			}
		}
	}

}
