package com.alimol.catgame;

import com.alimol.catgame.GameMain;
import com.alimol.catgame.GameStateManager;
import com.alimol.catgame.PlayState;
import com.alimol.catgame.State;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameOverState extends State {
    public static final int NUMBERWIDTH = 20;

    private Texture gameoverTex;
    private Texture playBtn;
    private int finalScore;

    Texture numbersSheet;
    TextureRegion[] numbersTex; //ширина одной цифры: 20 пикселей

    public GameOverState(GameStateManager gsm, int finalScore) {
        super(gsm);
        camera.setToOrtho(false, GameMain.WIDTH, GameMain.HEIGHT);
        gameoverTex = new Texture("gameover.png");
        playBtn = new Texture("playbtn.png");
        this.finalScore = finalScore;

        numbersSheet = new Texture("numbers.png");
        numbersTex = new TextureRegion[10];
        for (int i = 0; i < 10; i++) {
            numbersTex[i] = new TextureRegion(numbersSheet, NUMBERWIDTH*i, 0, NUMBERWIDTH, numbersSheet.getHeight());
        }
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
        spriteBatch.begin();
        spriteBatch.draw(gameoverTex, (GameMain.WIDTH/2) - (gameoverTex.getWidth()/2), (GameMain.HEIGHT/2) + (playBtn.getHeight()/2) + 16);
        spriteBatch.draw(playBtn, (GameMain.WIDTH/2) - (playBtn.getWidth()/2), (GameMain.HEIGHT/2) - (playBtn.getHeight()/2) - 16);
        renderScore(spriteBatch, finalScore);
        spriteBatch.end();
    }

    void renderScore(SpriteBatch spriteBatch, int score) {
        for(int i = 0; score> 0; score/= 10, i++) {
            spriteBatch.draw(numbersTex[score%10], (int)(GameMain.WIDTH * 0.95) - NUMBERWIDTH*i, (int)(GameMain.HEIGHT*0.94));
        }
    }

    @Override
    public void dispose() {
        gameoverTex.dispose();
        playBtn.dispose();
    }
}
