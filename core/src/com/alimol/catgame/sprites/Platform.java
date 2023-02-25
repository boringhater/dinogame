package com.alimol.catgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Platform {
    public static final int SPEED = 250;
    public static final int HEIGHTDISPERSION = 300;
    public static final int WIDTHDISPERTION = 200;

    public static final int HEIGHT = 27;


    private static final Texture platformsSheet = new Texture("platforms.png");
    private static final TextureRegion[] platformsSprites = {
            new TextureRegion(platformsSheet, 0, 0, 79, HEIGHT),
            new TextureRegion(platformsSheet, 79, 0, 133, HEIGHT),
            new TextureRegion(platformsSheet, 212, 0, 181, HEIGHT)
    };

    private Vector2 position;
    private Rectangle platBox;
    private int type;

    public Platform(int x, int y) {
        position = new Vector2(x,y);
        Random rand = new Random();
        type = rand.nextInt(platformsSprites.length);
        platBox = new Rectangle(x,y, platformsSprites[type].getRegionWidth(), platformsSprites[type].getRegionHeight());
    }
    public Platform(int x, int y, int type) {
        position = new Vector2(x,y);
        this.type = type;
        platBox = new Rectangle(x,y, platformsSprites[type].getRegionWidth(), platformsSprites[type].getRegionHeight());
    }

    public void update(float dt, float stateTime) {
        position.x -= (SPEED + stateTime*3) * dt;
        platBox.x = position.x;
    }

    public Vector2 getPosition() {
        return position;
    }

    public TextureRegion getPlatformTexture() {
        return platformsSprites[this.type];
    }

    public Rectangle getPlatBox() {
        return platBox;
    }

    //type in {0,1,2}
    public static TextureRegion getPlatformTex(int type) {
        return  platformsSprites[type];
    }
}
