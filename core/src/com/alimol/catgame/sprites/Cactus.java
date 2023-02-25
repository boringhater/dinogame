package com.alimol.catgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

//frame size: 34x70
public class Cactus {
    public static final int FRAMESNUM = 6;
    public static final int WIDTH = 34;
    public static final int HEIGHT = 70;

    private static final Texture cactiSheet = new Texture("cacti.png");
    private static final TextureRegion[] cactiSprites = {
            new TextureRegion(cactiSheet, 0,0, WIDTH, HEIGHT),
            new TextureRegion(cactiSheet,WIDTH*1, 0, WIDTH, HEIGHT),
            new TextureRegion(cactiSheet,WIDTH*2, 0, WIDTH, HEIGHT),
            new TextureRegion(cactiSheet,WIDTH*3, 0, WIDTH, HEIGHT),
            new TextureRegion(cactiSheet,WIDTH*4, 0, WIDTH, HEIGHT),
            new TextureRegion(cactiSheet,WIDTH*5, 0, WIDTH, HEIGHT),
    };

    private Vector2 position;
    private Rectangle cactusBox;
    private int type;
    private Random random;

    public Cactus(int x, int y) {
        position = new Vector2(x,y);
        random = new Random();
        type = random.nextInt(FRAMESNUM);
        cactusBox = new Rectangle(x,y, cactiSprites[type].getRegionWidth(), cactiSprites[type].getRegionHeight());
    }

    public void update(float dt, float stateTime) {
        position.x -= (Platform.SPEED + stateTime*3) * dt;
        cactusBox.x = position.x;
    }

    public TextureRegion getTex(){
        return cactiSprites[type];
    }

    public Rectangle getCactusBox() {
        return cactusBox;
    }

    public Vector2 getPosition() {
        return position;
    }
}
