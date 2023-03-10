package com.spikes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class Entity implements InputProcessor {

	private static InputMultiplexer input;
	private List<Entity> children = new ArrayList<>();

	static {
		setInput(new InputMultiplexer());
	}

	public static void setInput(InputMultiplexer input) {
		Entity.input = input;
		Gdx.input.setInputProcessor(input);
	}

	public void attach(Entity entity) {
		attachProccessors(entity);
		children.add(entity);
	}

	private void attachProccessors(Entity entity) {
		input.addProcessor(entity);
		for (Entity child : entity.getChildren())
			attachProccessors(child);
	}

	public void detach(Entity entity) {
		if (children.remove(entity))
			detachProccessors(entity);
	}

	private void detachProccessors(Entity entity) {
		input.removeProcessor(entity);
		for (Entity child : entity.getChildren())
			detachProccessors(child);
	}

	public void detachAll() {
		for (Entity child : getChildren())
			detachProccessors(child);
		children.clear();
	}

	public List<? extends Entity> getChildren() {
		return children;
	}

	public void dispose() {
		children.forEach(Entity::dispose);
	}

	public void update(float dt) {}

	public abstract void render(SpriteBatch batch);

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
