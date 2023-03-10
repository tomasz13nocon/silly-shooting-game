package com.spikes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

// This class should have constructor with Sprite parameter to establish contract, that the sprite is necessary for this class to work
// However I would have to play with factory classes then, and f*ck that.
public abstract class SpriteEntity extends Entity {

	protected Sprite sprite;

	// Position
	public float getCenterX() {
		return sprite.getX() + sprite.getWidth()/2;
	}
	public float getCenterY() {
		return sprite.getY() + sprite.getHeight()/2;
	}
	public Vector2 getCenter() {
		return new Vector2(getCenterX(), getCenterY());
	}

	public void setCenterX(float x) { sprite.setCenterX(x); }
	public void setCenterY(float y) { sprite.setCenterY(y); }
	public void setCenter(float x, float y) {
		sprite.setCenter(x, y);
	}

	public float getX() {
		return sprite.getX() + sprite.getWidth()/2 * (1-sprite.getScaleX());
	}
	public float getY() {
		return sprite.getY() + sprite.getHeight()/2 * (1-sprite.getScaleY());
	}
	public Vector2 getPosition() {
		return new Vector2(getX(), getY());
	}

	public void setX(float x) {
		sprite.setX(x - sprite.getWidth()/2 * (1-sprite.getScaleX()));
	}
	public void setY(float y) {
		sprite.setY(y - sprite.getHeight()/2 * (1-sprite.getScaleY()));
	}
	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
	}

	public void move(float x, float y) {
		sprite.translate(x, y);
	}

	// Size
	public float getWidth() {
		return sprite.getWidth() * sprite.getScaleX();
	}
	public float getHeight() {
		return sprite.getHeight() * sprite.getScaleY();
	}

	// Scale
	public float getScaleX() {
		return sprite.getScaleX();
	}
	public float getScaleY() {
		return sprite.getScaleY();
	}

	// Rotation
	public void setRotation(float degrees) {
		sprite.setRotation(degrees);
	}

	// Collision
	public boolean intersects(SpriteEntity other) {
		return sprite.getBoundingRectangle().overlaps(other.sprite.getBoundingRectangle());
	}


	@Override
	public void render(SpriteBatch batch) {
		sprite.draw(batch);
	}

}
