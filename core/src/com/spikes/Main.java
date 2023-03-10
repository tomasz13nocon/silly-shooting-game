package com.spikes;

import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Main extends ApplicationAdapter {

	private OrthographicCamera camera;
	private Renderer renderer;
	private Entity root;
	private MainMenu mainMenu;
	private IngameMenu ingameMenu;
	private Game game;
	private Music imperialMarch;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
		Texture background = new Texture("bg2.jpg");
		camera.position.x = background.getWidth()/2.0f;
		camera.position.y = background.getHeight()/2.0f;

		renderer = new Renderer(new SpriteBatch(), camera);
		renderer.setBackground(background);

		root = new Node();
		mainMenu = new MainMenu(this);
		root.attach(mainMenu);

		ingameMenu = new IngameMenu(this);

		imperialMarch = Gdx.audio.newMusic(Gdx.files.internal("Imperial March.ogg"));
		imperialMarch.setLooping(true);
		imperialMarch.setVolume(0.5f);
		imperialMarch.play();
	}

	public void newGame() {
		root.detach(mainMenu);
		game = new Game(this, camera, renderer.getBackground().getWidth(), renderer.getBackground().getHeight());
		root.attach(game);
	}

	public void continueGame() {
		root.detach(ingameMenu);
		root.attach(game);
	}

	public void showIngameMenu() {
		root.detach(game);
		root.attach(ingameMenu);
	}

	public void showMainMenu() {
		root.detachAll();
		root.attach(mainMenu);
	}

	@Override
	public void pause() {
		if (root.getChildren().contains(game))
			showIngameMenu();
	}

	@Override
	public void render() {
		if (Gdx.graphics.getDeltaTime() > 0.1f) return; // Little dirty hack for dragging window.

		renderer.update(root);
		renderer.begin();
		renderer.render(root);
		renderer.end();
	}

	@Override
	public void dispose() {
		FontManager.starFont.dispose();
		FontManager.font.dispose();
		imperialMarch.dispose();
		renderer.dispose();
	}
}
