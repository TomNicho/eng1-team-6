package main.game.world.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import com.badlogic.gdx.math.Rectangle;

public class College extends Entity {
    public static final float FIRE_RATE = 1000f;
    public static final float RANGE = 500f;
    public static final float PROCESS_RANGE = 1000f;
    public static final float BULLET_SPEED = 250f;

    private Texture collegeTexture;
    private Sprite collegeSprite;
    private int health;
    private String name;

    private long lastShot;

    public College(int health, String name, Vector2 position) {
        this.health = health;
        this.name = name;

        collegeTexture = new Texture(Gdx.files.internal("/textures/college.png"));
        collegeSprite = new Sprite(collegeTexture);

        collegeSprite.setPosition(position.x, position.y);
        collegeSprite.setRotation(0);
        lastShot = TimeUtils.millis();
    }

    public int update() {
        if (this.health < 0) return 0;
        if (TimeUtils.timeSinceMillis(lastShot) > FIRE_RATE) {
            lastShot = TimeUtils.millis();
            return 2;
        }
        return 1;
    }

    @Override
    public void render(SpriteBatch batch) {
        collegeSprite.draw(batch);
    }

    @Override
    public void dispose() {
        collegeTexture.dispose();
    }

    public void takeDamage(int damage) {
        health -= damage;
    }

    public void shoot() {

    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public Vector2 getPosition() {
        return new Vector2(collegeSprite.getX(), collegeSprite.getY());
    }
    
    public Rectangle getBounds() {
        return collegeSprite.getBoundingRectangle();
    }

    public Sprite getSprite() {
        return collegeSprite;
    }

    public boolean inRange(Vector2 pos) {
        if (pos.dst(this.getPosition()) <= RANGE) return true;
        else return false;
    }

    public boolean inProcess(Vector2 pos) {
        if (pos.dst(this.getPosition()) <= PROCESS_RANGE) return true;
        else return false;
    }
}
