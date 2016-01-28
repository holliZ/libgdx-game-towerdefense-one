package com.tower.defense.one.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.tower.defense.one.game.StaticVariable;
public class MyActor extends Actor {
	
	public MyActor(){
		addListener(new InputListener() {
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				MyActor.this.onClick();
				StaticVariable.lastTouchActorName = getName();
				return super.touchDown(event, x, y, pointer, button);
			}
		});
	}
	
	public void onClick(){
		Gdx.app.debug("MyActor", "OnClick");
		if(StaticVariable.lastTouchActorName != null && StaticVariable.lastTouchActorName.length() > 0) {
			MyActor actor = getParent().findActor(StaticVariable.lastTouchActorName.split("_")[0]);
			if(actor != null) {
				actor.hideWhenClickOthers();
			} else {
				StaticVariable.lastTouchActorName = "";
			}
		}
	}
	
	public void hideWhenClickOthers() {
		
	}
}