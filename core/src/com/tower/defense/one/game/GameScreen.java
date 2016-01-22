package com.tower.defense.one.game;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class GameScreen implements Screen{
	
	final TowerDefense game;
	
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	private OrthographicCamera camera;
	private Rectangle bucket;
	private Array<Rectangle> raindrops;
	private long lastDropTime;
	private int dropsGathered;
	
	public GameScreen(final TowerDefense gam) {
		game = gam;
		
		dropImage = new Texture(Gdx.files.internal("2.jpg"));
		bucketImage = new Texture(Gdx.files.internal("1.jpg"));
		
		dropSound = Gdx.audio.newSound(Gdx.files.internal("record.mp3"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		
		rainMusic.setLooping(true);
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		
		bucket = new Rectangle();
		bucket.x = 800/2 - 64/2;
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;
		
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}
	
	private void spawnRaindrop(){
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0,800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void show() {
		rainMusic.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		
		game.batch.setProjectionMatrix(camera.combined);
		
		game.batch.begin();
		game.font.draw(game.batch, "Drop collected: " + dropsGathered , 0, 480);
		game.batch.draw(bucketImage, bucket.x, bucket.y);
		
		for(Rectangle raindrop:raindrops){
			game.batch.draw(dropImage,raindrop.x,raindrop.y);
		}
		game.batch.end();
		
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64/2;
		}
		
		//Ëæ×ÅÊó±êÒÆ¶¯
//		Vector3 touchPos = new Vector3();
//		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//		camera.unproject(touchPos);
//		
//		bucket.x = touchPos.x - 64/2;
//		bucket.y = touchPos.y - 64/2;
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
		
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 -64) bucket.x = 800 -64;
		
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
		
		Iterator<Rectangle> iter = raindrops.iterator();
		while(iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200*Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) iter.remove();
			if(raindrop.overlaps(bucket)) {
				dropsGathered++;
				dropSound.play();
				iter.remove();
			}
		}
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}

}
