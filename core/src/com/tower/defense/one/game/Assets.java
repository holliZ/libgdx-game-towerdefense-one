package com.tower.defense.one.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	public static Texture dropImage;
	public static Texture bucketImage;
	public static Sound dropSound;
	public static Music rainMusic;
	
	public static void load () {
		dropImage = loadTexture("2.jpg");
		bucketImage = loadTexture("1.jpg");
		
		dropSound = loadSound("record.mp3");
		rainMusic = loadMusic("music.mp3");
		
		rainMusic.setLooping(true);
	}
	
	public static void dispose () {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}
	
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public static Sound loadSound(String file) {
		return Gdx.audio.newSound(Gdx.files.internal(file));
	}
	
	public static Music loadMusic(String file){
		return Gdx.audio.newMusic(Gdx.files.internal(file));
		
	}
}
