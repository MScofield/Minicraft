package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

public class GDXMinicraft extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion stand, standLeft, up, moveUp, down, moveDown, left, right, zip;
	TextureRegion zStand, zUp, zDown, zLeft, zRight;
	boolean canZip;
	Animation walkUp, walkDown, walkLeft, walkRight;
	float time;

	float x, y, xv, yv;
	static final float MAX_VELOCITY = 250;
	static final float ZIP_VELOCITY = MAX_VELOCITY *2;

	static final int WIDTH = 43;
	static final int HEIGHT = 32;

	float zx, zy, zxv, zyv;
	static final float ZOMBIE_VELOCITY = 175;
	static final float ZOMBIE_ZIP_VELOCITY = 350;

	static final int ZWIDTH = 43;
	static final int ZHEIGHT = 32;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion [][] grid = TextureRegion.split(tiles, 16,16);
		zip = grid [1][3];
		down = grid[6][0];
		moveDown = grid [7][0];
		up = grid[6][1];
		moveUp = grid [7][1];
		stand = grid[6][2];
		standLeft = new TextureRegion(stand);
		standLeft.flip(true, false);
		right = grid [6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
		walkRight = new Animation(0.1f, stand, right);
		walkLeft = new Animation(0.1f, standLeft, left);
		walkUp = new Animation(0.1f, up, moveUp);
		walkDown = new Animation(0.1f, down, moveDown);
		//zImages = zombie images
		zStand = grid [6][6];
		zRight = grid [6][7];
		zUp = grid [6][5];
		zDown = grid [6][4];
		zLeft = new TextureRegion(zRight);
		zLeft.flip(true, false);

	}

	@Override
	public void render () {
		time += Gdx.graphics.getDeltaTime();
		move();
		TextureRegion img;
		TextureRegion zImg = null;
		zImg = zStand;

		if (xv > 10) {
			img = walkRight.getKeyFrame(time, true);
			if (xv > 400){
				img = zip;
			}
		}
		else if (xv < -10) {
			img = walkLeft.getKeyFrame(time, true);
			if (xv < -400) {
				img = zip;
			}
		}
		else if(yv > 10){
			img = walkUp.getKeyFrame(time, true);
			if (yv > 400) {
				img = zip;
			}
		}
		else if(yv < -10){
			img = walkDown.getKeyFrame(time, true);
			if (yv < -400) {
				img = zip;
			}
		}
		else	{
			img = stand;
		}

		Gdx.gl.glClearColor(0.3f, 0.3f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y, HEIGHT, WIDTH);
		batch.draw(zImg, zx, zy, ZHEIGHT, ZWIDTH);
		batch.end();
	}

	public void move(){

		if(Gdx.input.isKeyPressed(Input.Keys.UP))	{
			yv = MAX_VELOCITY;
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				yv = ZIP_VELOCITY;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = MAX_VELOCITY * -1;
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				yv = ZIP_VELOCITY * -1;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				xv = ZIP_VELOCITY;
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = MAX_VELOCITY * -1;
			if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
				xv = ZIP_VELOCITY * -1;
			}
		}

		y = y + (yv * Gdx.graphics.getDeltaTime());
		x = x + (xv * Gdx.graphics.getDeltaTime());


		yv = decelerate(yv);
		xv = decelerate(xv);

		if (y < 0){
			y = Gdx.graphics.getHeight();
		}
		if (x < 0){
			x = Gdx.graphics.getWidth();
		}
		if (y > Gdx.graphics.getHeight()){
			y = 0;
		}
		if (x > Gdx.graphics.getWidth()){
			x = 0;
		}
	}

	public float decelerate(float velocity) {
		float deceleration = 0.832f;
		velocity *= deceleration;
		if(Math.abs(velocity) < 1){
			velocity = 0;
		}
		return velocity;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
