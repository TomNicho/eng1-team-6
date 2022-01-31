package main.game.world.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import main.game.GameRunner;
import main.game.core.Calculations;
import main.game.core.Constants.PlayerConstants;
import main.game.world.content.Entity;
import main.game.world.player.Stats.PlayerStats;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends Entity {
    private PlayerStats stats;
    private Texture boat;
    private Sprite sprite;

    private long lastShot;

    public Player(int health, int damage, Vector2 initialPosition, float initialRotation){
        this.lastShot = TimeUtils.millis();
        this.stats = new PlayerStats(health, damage, 0, 0);
        this.boat = new Texture(Gdx.files.internal("textures/boat.png"));
        this.sprite = new Sprite(boat, 32, 64);

        sprite.setPosition(initialPosition.x, initialPosition.y);
        sprite.setRotation(initialRotation);
    }

    public int update() {
        Vector2 position = new Vector2(0,0);
        double rotX = 0, rotY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x = -PlayerConstants.SPEED * Gdx.graphics.getDeltaTime();
            rotX = Math.PI / 2;
            rotY = Math.PI / 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x = PlayerConstants.SPEED * Gdx.graphics.getDeltaTime();
            rotX = 3 * Math.PI / 2;
            rotY = 3 * Math.PI / 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y = PlayerConstants.SPEED * Gdx.graphics.getDeltaTime();
            if (rotX == 3 * Math.PI / 2) rotX = 2 * Math.PI;
            else rotX = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y = -PlayerConstants.SPEED * Gdx.graphics.getDeltaTime();
            rotY = Math.PI;
            if (rotX == 0) rotX = Math.PI;
        }

        double rotation = (rotX + rotY) / 2;

        sprite.translate(position.x, position.y);
        sprite.setRotation((float) Calculations.RadToDeg(rotation));

        if (TimeUtils.timeSinceMillis(lastShot) <= PlayerConstants.FIRE_RATE) {
            return -1;
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                System.out.println(sprite.getBoundingRectangle().getWidth() + " " + sprite.getBoundingRectangle().getHeight());
                lastShot = TimeUtils.millis();
                return 0;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                lastShot = TimeUtils.millis();
                return 90;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                lastShot = TimeUtils.millis();
                return 180;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                lastShot = TimeUtils.millis();
                return 270;
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
        if (stats.takeDamage(damage)) GameRunner.IS_MENU = true;
    }

    public void collectGold(int amount) {
        stats.increaseGold(amount);
    }

    public void collectXP(int amount) {
        stats.increaseXP(amount);
    }

    public void collectScore(int amount) {
        stats.increaseScore(amount);
    }

    public int getGold() {
        return stats.getGold();
    }

    public int[] getLevelNXP() {
        return stats.getLevelNXP();
    }

    public int getDamage() {
        return stats.getDamage();
    }

    public int getHealth() {
        return stats.getHealth();
    }

    public int getScore() {
        return stats.getScore();
    }

    public float getRotation() {
        return sprite.getRotation();
    }

    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public Vector2 getCenter() {
        return Calculations.SpriteDynamicCenter(sprite);
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }
}
