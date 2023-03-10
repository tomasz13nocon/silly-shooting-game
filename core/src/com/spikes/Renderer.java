package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Renderer {

	private SpriteBatch batch;
	private Texture bg;
	private Camera camera;

	public Renderer(SpriteBatch batch, Camera camera) {
		this.batch = batch;
		this.camera = camera;
		Gdx.gl.glClearColor(0.37f, 0.57f, 0.87f, 1);
	}

	public void setBackground(Texture bg) {
		this.bg = bg;
	}

	public Texture getBackground() {
		return bg;
	}

	// Renderer isn't exactly intuitive class to have it but it's how it should be.
	public void update(Entity entity) {
		entity.update(Gdx.graphics.getDeltaTime());
		for (Entity child : entity.getChildren())
			update(child);
	}

	public void begin() {
		camera.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		if (bg != null) batch.draw(bg, 0, 0);
	}

	public void render(Entity entity) {
		entity.render(batch);
		for (Entity child : entity.getChildren())
			render(child);
	}

	public void end() {
		batch.end();
	}

	public void dispose() {
		batch.dispose();
	}

}
