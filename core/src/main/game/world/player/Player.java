package main.game.world.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import main.game.core.Calculations;
import main.game.world.content.Bullet;
import main.game.world.content.Entity;

import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;



public class Player extends Entity {
    public static final float SPEED = 100f;
    public static final float FIRE_RATE = 500f;
    public static final float BULLET_SPEED = 250f;

    private Texture boat;
    private Sprite sprite;
    private Set<Bullet> playerBullets;

    private int health;
    private long lastShot;

    public Player(int health, Vector2 initialPosition, float initialRotation){
        this.health = health;
        this.lastShot = TimeUtils.millis();

        boat = new Texture(Gdx.files.internal("core/assets/textures/boat.png"));
        sprite = new Sprite(boat, 32, 64);

        sprite.setPosition(initialPosition.x, initialPosition.y);
        sprite.setRotation(initialRotation);
    }

    public float update() {
        Vector2 position = new Vector2(0,0);
        double rotX = 0, rotY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x = -SPEED * Gdx.graphics.getDeltaTime();
            rotX = Math.PI / 2;
            rotY = Math.PI / 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x = SPEED * Gdx.graphics.getDeltaTime();
            rotX = 3 * Math.PI / 2;
            rotY = 3 * Math.PI / 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y = SPEED * Gdx.graphics.getDeltaTime();
            if (rotX == 3 * Math.PI / 2) rotX = 2 * Math.PI;
            else rotX = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y = -SPEED * Gdx.graphics.getDeltaTime();
            rotY = Math.PI;
            if (rotX == 0) rotX = Math.PI;
        }

        double rotation = (rotX + rotY) / 2;

        sprite.translate(position.x, position.y);
        sprite.setRotation((float) Calculations.RadToDeg(rotation));

        if (TimeUtils.timeSinceMillis(lastShot) <= FIRE_RATE) {
            return -1;
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                lastShot = TimeUtils.millis();
                return 0;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                lastShot = TimeUtils.millis();
                return (float) Math.PI / 2;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                lastShot = TimeUtils.millis();
                return (float) Math.PI;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                lastShot = TimeUtils.millis();
                return 3 * (float) Math.PI / 2;
            }

            return -1;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void dispose() {
        boat.dispose();
    }

    public void takeDamage(int damage) {
        this.health -= damage;
        if(this.health <= 0) {
        }
    }

    public int getHealth() {
        return health;
    }

    public float getRotation() {
        return sprite.getRotation();
    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public Vector2 getCenter() {
        return Calculations.SpriteCenter(sprite);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }

    public Set<Bullet> getBullets() {
        return playerBullets;
    }
}
