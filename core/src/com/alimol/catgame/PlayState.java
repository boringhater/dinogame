package com.alimol.catgame;

import com.alimol.catgame.sprites.Bird;
import com.alimol.catgame.sprites.Cactus;
import com.alimol.catgame.sprites.Platform;
import com.alimol.catgame.sprites.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

public class PlayState extends State {
    Player player;
    Queue<Platform> platforms;
    Queue<Bird> birds;
    Queue<Cactus> cacti;
    float stateTime;
    Random random;
    float nextPlatformTime;
    float nextBirdTime;
    float nextCactusTime;

    Texture numbersSheet;
    TextureRegion[] numbersTex; //ширина одной цифры: 20 пикселей
    Texture arrowTex;

    public static final int PLATFORMSUPDTIME = 1000000000;
    public static final float BIRDSUPDTIME = PLATFORMSUPDTIME*13f;
    public static final float CACTUSUPDTIME = PLATFORMSUPDTIME*10f;
    public static final int NUMBERWIDTH = 20;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        camera.setToOrtho(false, GameMain.WIDTH, GameMain.HEIGHT);
        player = new Player(50,240);
        random = new Random();

        platforms = new Queue<Platform>();
        platforms.addLast(new Platform(50,240 - Platform.getPlatformTex(2).getRegionHeight(),2));
        int tmp = random.nextInt(4) + 2;
        for(int i =0; i < tmp; i++) {
            platforms.addLast(new Platform((int)(platforms.last().getPosition().x + random.nextInt(Platform.WIDTHDISPERTION) + 150), random.nextInt(Platform.HEIGHTDISPERSION)
                    % (GameMain.HEIGHT - player.getPlayerTex().getHeight())));
        }

        birds = new Queue<Bird>();
        birds.addLast(new Bird(GameMain.WIDTH * 3, random.nextInt(GameMain.HEIGHT )));

        cacti = new Queue<Cactus>();
        cacti.addLast(new Cactus((int) platforms.last().getPosition().x, (int) platforms.last().getPosition().y + Platform.HEIGHT));

        numbersSheet = new Texture("numbers.png");
        numbersTex = new TextureRegion[10];
        for (int i = 0; i < 10; i++) {
            numbersTex[i] = new TextureRegion(numbersSheet, NUMBERWIDTH*i, 0, NUMBERWIDTH, numbersSheet.getHeight());
        }

        arrowTex = new Texture("arrow.png");

        stateTime = 0f;
        nextPlatformTime = TimeUtils.nanoTime() + PLATFORMSUPDTIME/2 + random.nextInt(PLATFORMSUPDTIME) ;
        nextBirdTime = TimeUtils.nanoTime() + BIRDSUPDTIME/2 + random.nextFloat()%BIRDSUPDTIME;
        nextCactusTime = TimeUtils.nanoTime() + CACTUSUPDTIME/2 + random.nextFloat()%CACTUSUPDTIME;
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            if(player.getJumps() > 0) player.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        player.update(dt);
        for(Platform platform : platforms) {
            platform.update(dt, stateTime);
            if(platform.getPlatBox().overlaps(player.getBoxLow()) && player.getVelocity().y < 1) {
                player.land(platform);
            }
        }

        for(Cactus cactus : cacti) {
            cactus.update(dt, stateTime);
            if(cactus.getCactusBox().overlaps(player.getBoxFull())) {
                die();
            }
        }

        for(Bird bird : birds) {
            bird.update(dt, stateTime);
            if(bird.getBirdBox().overlaps(player.getBoxFull())) {
                die();
            }
        }
        //добавление платформы и кактуса
        if(TimeUtils.nanoTime() > nextPlatformTime) {
            platforms.addLast(new Platform(GameMain.WIDTH, (int) (platforms.last().getPosition().y +  (random.nextInt(Platform.HEIGHTDISPERSION)+ Platform.HEIGHT)) % (int)(GameMain.HEIGHT - player.getBoxFull().height)));
            nextPlatformTime = TimeUtils.nanoTime() + PLATFORMSUPDTIME/2 + random.nextInt(PLATFORMSUPDTIME) + stateTime*50;

            if(TimeUtils.nanoTime() > nextCactusTime) {
                cacti.addLast(new Cactus(GameMain.WIDTH + random.nextInt((int) platforms.last().getPlatBox().width - Cactus.WIDTH), (int) (platforms.last().getPosition().y + Platform.HEIGHT)));
                nextCactusTime = TimeUtils.nanoTime() + CACTUSUPDTIME/2 + random.nextInt((int) CACTUSUPDTIME) - stateTime*6;
            }
        }
        //добавление птицы
        if(TimeUtils.nanoTime() > nextBirdTime) {
            birds.addLast(new Bird(GameMain.WIDTH, random.nextInt(GameMain.HEIGHT - 30)));
            nextBirdTime = TimeUtils.nanoTime() + BIRDSUPDTIME/2 + random.nextFloat()%BIRDSUPDTIME - stateTime*10;
        }

        //Если самая левая платформа ушла за край экрана, удаляем.
        if((platforms.size > 1) & (platforms.first().getPosition().x < (-1 * platforms.first().getPlatBox().getWidth()))) {
            platforms.removeFirst();
        }
        if((birds.size > 1) & (birds.first().getPosition().x < (-1 * birds.first().getBirdBox().getWidth()))) {
            birds.first().dispose();
            birds.removeFirst();
        }
        if((cacti.size > 1) & (cacti.first().getPosition().x < (-1 * cacti.first().getCactusBox().getWidth()))) {
            cacti.removeFirst();
        }

        //смерть от падения
        if(player.getPosition().y < (-1 * player.getPlayerTex().getHeight())) {
            die();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        stateTime +=Gdx.graphics.getDeltaTime();
        if(player.getPosition().y > GameMain.HEIGHT) {
            spriteBatch.draw(arrowTex, player.getPosition().x + arrowTex.getWidth()/2, GameMain.HEIGHT - arrowTex.getHeight());
        } else spriteBatch.draw((TextureRegion) player.getWalkAnimation().getKeyFrame(stateTime,true), player.getPosition().x, player.getPosition().y-2);

        for(Platform platform : platforms) {
            spriteBatch.draw(platform.getPlatformTexture(), platform.getPosition().x, platform.getPosition().y);
        }

        for(Cactus cactus : cacti) {
            spriteBatch.draw(cactus.getTex(), cactus.getPosition().x, cactus.getPosition().y);
        }

        for (Bird bird : birds) {
            spriteBatch.draw((TextureRegion) bird.getFlyAnimation().getKeyFrame(stateTime, true), bird.getPosition().x, bird.getPosition().y);
        }

        renderScore(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        player.dispose();
    }

    void renderScore(SpriteBatch spriteBatch) {
        int currentScore = currentScore();
        for(int i = 0; currentScore > 0; currentScore /= 10, i++) {
            spriteBatch.draw(numbersTex[currentScore%10], (int)(GameMain.WIDTH * 0.95) - NUMBERWIDTH*i, (int)(GameMain.HEIGHT*0.94));
        }
    }

    void die() {
        gsm.set(new GameOverState(gsm, currentScore()));
    }

    int currentScore() {
        return (int) (10 * stateTime);
    }
}
