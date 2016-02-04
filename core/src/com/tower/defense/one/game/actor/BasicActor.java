package com.tower.defense.one.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class BasicActor extends Actor {
	
	private boolean record;
	protected static String lastTouchActorName = "";
	
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
		Gdx.app.debug(getName(), "OnClick " + getName() + " record=" + record + " lastTouchActorName=" + lastTouchActorName);
		if(record && lastTouchActorName != null && lastTouchActorName.length() > 0) {
			Group parent = getTopParent(getParent());
			BasicActor actor = parent.findActor(lastTouchActorName.split("_")[0]);
			if(actor != null) {
				actor.hideWhenClickOthers();
			} else {
				lastTouchActorName = "";
			}
		}
	}
	
	public Group getTopParent(Group parent){
		Group preParent = parent.getParent();
		if(preParent == null) {
			return parent;
		}
		return getTopParent(preParent);
	}
	
	public void hideWhenClickOthers() {
		
	}
}