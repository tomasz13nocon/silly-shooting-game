package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;

public class Text extends Rectangle {

	private String text;
	private BitmapFont font;

	public Text(BitmapFont font, String text) {
		super();
		this.text = text;
		this.font = font;
		GlyphLayout layout = new GlyphLayout(font, text);
		width = layout.width;
		height = layout.height;
	}

	public Text(BitmapFont font, String text, float x, float y) {
		super();
		this.text = text;
		this.font = font;
		GlyphLayout layout = new GlyphLayout(font, text);
		this.x = x;
		this.y = y;
		this.width = layout.width;
		this.height = layout.height;
	}

	public void setText(String text) {
		this.text = text;
		GlyphLayout layout = new GlyphLayout(font, text);
		width = layout.width;
		height = layout.height;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
		GlyphLayout layout = new GlyphLayout(font, text);
		width = layout.width;
		height = layout.height;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void draw(SpriteBatch batch) {
		Matrix4 temp = new Matrix4(batch.getProjectionMatrix());
		batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), -0.1f, 1));
		font.draw(batch, text, x, y + height);
		batch.setProjectionMatrix(temp);
	}

	// Unproject before rendering.
	/*public void draw(SpriteBatch batch, Camera camera) {
		Vector3 unprojected = camera.unproject(new Vector3(x, Gdx.graphics.getHeight() - y, 0.0f));
		font.draw(batch, text, unprojected.x, unprojected.y + height);
	}*/

}
