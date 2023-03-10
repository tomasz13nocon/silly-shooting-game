package com.spikes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Container Entity for other entities.
// You should always have root Node at the top of the hierarchy because Input Processors only work on entities which are attached to something.
public class Node extends Entity {

	@Override
	public void render(SpriteBatch batch) {}

}
