package com.alimol.catgame.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Player {
    public static final int GRAVITY = -13;
    public static final int JUMPHEIGHT = 350;
    public static final int JUMPSNUM = 3;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle boxFull, boxLow;
    private int jumps;

    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    public static final int FRAMES_NUM = 2;
    private Animation walkAnimation;

    private Texture playerTex;

    //РАЗМЕР ФРЕЙМА: 88x94; расстояния слева и справа до ног от начала фрейма: 20 и 32 соответственно
    public Player(int x, int y) {
        this.position = new Vector2(x,y);
        this.velocity = new Vector2(0,0);
        playerTex = new Texture("player.png");
        jumps = JUMPSNUM;

        walkSheet = new Texture("dinowalk.png");
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, playerTex.getWidth(), playerTex.getHeight());

        walkFrames = new TextureRegion[FRAMES_NUM];
        walkFrames[0] = new TextureRegion(walkSheet, 0, 0, playerTex.getWidth(), playerTex.getHeight());
        walkFrames[1] = new TextureRegion(walkSheet, playerTex.getWidth(), 0, playerTex.getWidth(), playerTex.getHeight());

        walkAnimation = new Animation(0.1f, walkFrames);
        boxLow = new Rectangle(position.x + 20, position.y, playerTex.getWidth() - 45, 3);
        boxFull = new Rectangle(position.x, position.y, playerTex.getWidth(), playerTex.getHeight());
    }

    public void dispose() {
        playerTex.dispose();
        walkSheet.dispose();
    }

    public void update(float dt) {
        velocity.add(0,GRAVITY);
        velocity.scl(dt);
        position.add(0,2*velocity.y);
        velocity.scl(1/dt);

        boxLow.setPosition(position.x + 20,position.y);
        boxFull.setPosition(position.x,position.y);
    }

    public void jump() {
        velocity.y = JUMPHEIGHT;
        jumps--;
    }

    public void land(Platform platform){
        velocity.y = 0;
        jumps = JUMPSNUM;
        position.y = platform.getPosition().y + platform.getPlatformTexture().getRegionHeight();
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Texture getPlayerTex() {
        return playerTex;
    }

    public Rectangle getBoxFull() {
        return boxFull;
    }

    public Rectangle getBoxLow() {
        return boxLow;
    }

    public int getJumps() {
        return jumps;
    }

    public Animation getWalkAnimation() {
        return walkAnimation;
    }
}
