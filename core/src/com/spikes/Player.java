package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends SpriteEntity {

	private int maxHp = 100;
	private int hp = 100;
	private int dmg = 50;
	private int money = 0;
	private Vector2 velocity = new Vector2();
	private Vector2 acceleration = new Vector2();
	private float mapX, mapY;
	private float speed = 340;
	private float accel = 1000 + speed*6;
	private final float approximationError = 30.0f; // TODO: Fix this.
	private float startRotation = 0, targetRotation = 0, deltaRotation = 0;
	private boolean rotationNeedsChange = false;
	private Clock clock = new Clock(), clock2 = new Clock();
	private static Sound shootingSound = Gdx.audio.newSound(Gdx.files.internal("Laser.ogg"));

	public Player(float mapX, float mapY) {
		this.mapX = mapX;
		this.mapY = mapY;
		Texture tex = new Texture("falcon2.png");
		tex.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		sprite = new Sprite(tex);
		sprite.setScale(0.5f);
		sprite.setOrigin(86, 120);
		sprite.setCenter(mapX/2.0f, mapY/2.0f);
	}

	@Override
	public boolean intersects(SpriteEntity other) {
		Vector2 scale = new Vector2(sprite.getScaleX(), sprite.getScaleY());
		sprite.setScale(scale.x * 0.7f, scale.y * 0.7f);
		Rectangle rect = sprite.getBoundingRectangle();
		sprite.setScale(scale.x, scale.y);
		return rect.overlaps(other.sprite.getBoundingRectangle());
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public void addHp(int hp) {
		this.hp += hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void addMaxHp(int maxHp) {
		this.maxHp += maxHp;
	}

	public int getDmg() {
		return dmg;
	}

	public void addDmg(int dmg) {
		this.dmg += dmg;
	}

	public int getMoney() {
		return money;
	}

	public void addMoney(int money) {
		this.money += money;
	}

	public float getSpeed() {
		return speed;
	}

	public void addSpeed(int speed) { this.speed += speed; }

	public Bullet shoot(float x, float y) {
		Vector2 direction = new Vector2(x, y).sub(sprite.getX()+sprite.getOriginX(), sprite.getY()+sprite.getOriginY()).nor();
		float rotation = -(float)Math.toDegrees(Math.atan2(direction.x, direction.y));

		Bullet bullet = new Bullet(sprite.getX()+sprite.getOriginX() + direction.x*30, sprite.getY()+sprite.getOriginY() + direction.y*30, direction, dmg);
		bullet.setRotation(rotation);

		long id = shootingSound.play();
		shootingSound.setVolume(id, 0.12f);

		setTargetRotation(rotation);
		rotationNeedsChange = false;
		return bullet;
	}

	public void setTargetRotation(float degrees) {
		startRotation = sprite.getRotation();
		if (startRotation >= 360) startRotation -= 360;
		else if (startRotation <= -360) startRotation += 360;
		targetRotation = degrees;
		deltaRotation = targetRotation - startRotation;
		if (deltaRotation > 180) {
			startRotation += 360;
			deltaRotation = targetRotation - startRotation;
		}
		else if (deltaRotation < -180) {
			targetRotation += 360;
			deltaRotation = targetRotation - startRotation;
		}
		clock.restart();
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.W || keycode == Input.Keys.S || keycode == Input.Keys.D || keycode == Input.Keys.A)
			rotationNeedsChange = true;
		return false;
	}

	@Override
	public void update(float dt) {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (velocity.y < speed)
				acceleration.y = accel;
			else
				acceleration.y = 0;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (velocity.y > -speed)
				acceleration.y = -accel;
			else
				acceleration.y = 0;
		}
		else if (velocity.y < 0 - approximationError) {
			acceleration.y = accel;
		}
		else if (velocity.y > 0 + approximationError) {
			acceleration.y = -accel;
		}
		else {
			acceleration.y = 0;
			velocity.y = 0;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (velocity.x < speed)
				acceleration.x = accel;
			else
				acceleration.x = 0;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (velocity.x > -speed)
				acceleration.x = -accel;
			else
				acceleration.x = 0;
		}
		else if (velocity.x < 0 - approximationError) {
			acceleration.x = accel;
		}
		else if (velocity.x > 0 + approximationError) {
			acceleration.x = -accel;
		}
		else {
			acceleration.x = 0;
			velocity.x = 0;
		}


		Vector2 deltaVelocity = new Vector2(acceleration).scl(dt);
		velocity.add(deltaVelocity);
		Vector2 deltaPosition = new Vector2(velocity).scl(dt);
		Vector2 originalDeltaPosition = new Vector2(deltaPosition); // Used in out of bounds check below.
		if (deltaPosition.len() > speed * dt)
			deltaPosition.nor().scl(speed * dt);
		sprite.translate(deltaPosition.x, deltaPosition.y);

		// Rotations
		if (sprite.getRotation() != targetRotation) {
			sprite.setRotation(startRotation + deltaRotation * (float)clock.getTimeInSeconds() * 10);
			if (clock.getTimeInSeconds() > 0.1f)
				sprite.setRotation(targetRotation); // So that getRotation() return exactly targetRotation.
			clock2.restart();
		}
		if (rotationNeedsChange && (
				(velocity.x > approximationError || velocity.x < -approximationError) ||
				(velocity.y > approximationError || velocity.y < -approximationError)))
			setTargetRotation(-(float)Math.toDegrees(Math.atan2(velocity.x, velocity.y)));
		if (clock2.getTimeInSeconds() > 0.3f)
			rotationNeedsChange = true;

		// Player out of bounds
		float playerX = getX();
		float playerY = getY();
		if (playerX < 5.0f) {
			setX(5.0f);
			sprite.translateY(originalDeltaPosition.y - deltaPosition.y);
		}
		else if (playerX > mapX - getWidth() + 10.0f) {
			setX(mapX - getWidth() + 10.0f);
			sprite.translateY(originalDeltaPosition.y - deltaPosition.y);
		}
		if (playerY < -10.0f) {
			setY(-10.0f);
			sprite.translateX(originalDeltaPosition.x - deltaPosition.x);
		}
		else if (playerY > mapY - getHeight() + 36.0f) {
			setY(mapY - getHeight() + 36.0f);
			sprite.translateX(originalDeltaPosition.x - deltaPosition.x);
		}
	}

}
