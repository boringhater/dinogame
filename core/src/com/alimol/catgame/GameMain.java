package com.alimol.catgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameMain extends ApplicationAdapter {
	public static final int WIDTH = 800;
	public static final int HEIGHT = 480;

	private GameStateManager gsm;
	private SpriteBatch batch;

	@Override
	public void create () {
		gsm = new GameStateManager();
		batch = new SpriteBatch();
		ScreenUtils.clear((float) 247/255, (float) 247/255, (float) 247/255, 0.9f );
		gsm.push(new MenuState(gsm));
		super.resize(WIDTH, HEIGHT);
	}

	@Override
	public void render () {
		ScreenUtils.clear((float) 247/255, (float) 247/255, (float) 247/255, 0.9f );
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
