package com.tower.defense.one.game.actor;

import static com.tower.defense.one.game.screen.GameScreen.lastTouchActorName;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BasicActor extends Actor {
	
	private boolean record;
	
	public BasicActor(float x, float y, float width, float height){
		this(x, y, width, height, true);
	}
	
	public BasicActor(float x, float y, float width, float height, final boolean record){
		setBounds(x, y, width, height);
		this.record = record;
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				BasicActor.this.onClick();
				if(record) {
					lastTouchActorName = getName();
				}
				return super.touchDown(event, x, y, pointer, button);
			}
		});
		setName(this.getClass().getName());
	}
	
	public void onClick(){
		Gdx.app.debug("BasicActor", "OnClick");
		if(record && lastTouchActorName != null && lastTouchActorName.length() > 0) {
			BasicActor actor = getParent().findActor(lastTouchActorName.split("_")[0]);
			if(actor != null) {
				actor.hideWhenClickOthers();
			} else {
				lastTouchActorName = "";
			}
		}
	}
	
	public void hideWhenClickOthers() {
		
	}
}