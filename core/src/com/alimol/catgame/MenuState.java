package com.alimol.catgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuState extends State {
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        playBtn = new Texture("playbtn.png");
        camera.setToOrtho(false, GameMain.WIDTH, GameMain.HEIGHT);
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(playBtn, (GameMain.WIDTH/2) - (playBtn.getWidth()/2), (GameMain.HEIGHT/2) - (playBtn.getHeight()/2));
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        playBtn.dispose();
    }
}
