package main.game.world.content;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class College extends Entity {
    public static final float FIRE_RATE = 1000f;
    public static final float RANGE = 500f;
    public static final float BULLET_SPEED = 250f;

    private Texture collegeTexture;
    private Sprite collegeSprite;
    private float health;
    private String name;

    private long lastShot;

    public College(float health, String name, Vector2 position) {
        this.health = health;
        this.name = name;

        collegeTexture = new Texture("core/assets/textures/college.png");
        collegeSprite = new Sprite(collegeTexture);

        collegeSprite.setPosition(position.x, position.y);
        collegeSprite.setRotation(0);
        lastShot = TimeUtils.millis();
    }

    public boolean update() {
        if (TimeUtils.timeSinceMillis(lastShot) > FIRE_RATE) {
            lastShot = TimeUtils.millis();
            return true;
        }

        return false;
    }

    @Override
    public void render(SpriteBatch batch) {
        collegeSprite.draw(batch);
    }

    @Override
    public void dispose() {
        collegeTexture.dispose();
    }

    public void takeDamage(float damage) {
        health -= damage;

        if (health <= 0) {
            //DO SOMETHING
        }
    }

    public void shoot() {

    }

    public float getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public Vector2 getPosition() {
        return new Vector2(collegeSprite.getX(), collegeSprite.getY());
    }

    public Sprite getSprite() {
        return collegeSprite;
    }
}
