package com.alimol.catgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bird {
    static final int FRAMES_NUM = 2;
    public static final int SPEED = 400;

    private  Animation flyAnimation;
    private Texture birdSheet;
    private TextureRegion[] birdFrames;

    private Vector2 position;
    private Rectangle birdBox;

    public Bird(int x, int y) {
        birdSheet = new Texture("bird.png");
        TextureRegion[][] tmp = TextureRegion.split(birdSheet, birdSheet.getWidth()/FRAMES_NUM, birdSheet.getHeight());
        birdFrames = new TextureRegion[FRAMES_NUM];
        for(int i = 0; i < FRAMES_NUM; i++) {
            birdFrames[i] = tmp[0][i];
        }
        flyAnimation = new Animation(0.2f, birdFrames);

        position = new Vector2(x,y);
        birdBox = new Rectangle(x,y, birdFrames[0].getRegionWidth(), birdFrames[0].getRegionHeight());
    }

    public void update(float dt, float stateTime) {
        position.x -= (SPEED + stateTime*4) * dt;
        birdBox.x = position.x;
    }

    public void dispose() {
        birdSheet.dispose();
    }

    public Animation getFlyAnimation() {
        return flyAnimation;
    }

    public Rectangle getBirdBox() {
        return birdBox;
    }

    public Vector2 getPosition() {
        return position;
    }
}
