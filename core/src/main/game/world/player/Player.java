package main.game.world.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import main.game.MainRunner;
import main.game.core.Calculations;
import main.game.core.Constants;
import main.game.core.Constants.PlayerConstants;
import main.game.world.content.Entity;
import main.game.world.player.Objectives.Objective;
import main.game.world.player.Objectives.ObjectiveManager;
import main.game.world.player.Stats.PlayerStats;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends Entity {
    private PlayerStats stats;
    private ObjectiveManager objectives;
    private Texture boat;
    private Sprite sprite;

    private boolean immune, disabled;

    private float disabledAngle;
    private long lastShot;
    private long lastHit;

    public Player(int health, int damage, Vector2 initialPosition, float initialRotation, List<Objective> objectives){
        long currentTime = TimeUtils.millis();
        this.lastShot = currentTime;
        this.lastHit = currentTime;
        this.immune = false;
        this.disabled = false;
        this.disabledAngle = 0f;

        this.stats = new PlayerStats(health, damage, 0, 0);
        this.objectives = new ObjectiveManager(objectives);
        this.boat = new Texture(Gdx.files.internal("textures/boat.png"));
        this.sprite = new Sprite(boat, 32, 64);

        sprite.setPosition(initialPosition.x, initialPosition.y);
        sprite.setRotation(initialRotation);
    }

    public int update(float deltaTime) {
        if (disabled) {
            if (lastHit + PlayerConstants.DISABLED_LENGTH > TimeUtils.millis()) {
                sprite.translate((float) Math.cos(disabledAngle) * Constants.COLLIDE_PUSHBACK * deltaTime, (float) Math.sin(disabledAngle) * Constants.COLLIDE_PUSHBACK * deltaTime);
                return -1;
            } else disabled = false;
        }

        Vector2 position = new Vector2(0,0);
        double rotX = 0, rotY = 0;
        boolean input = false;

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            input = true;
            position.x = -PlayerConstants.SPEED * deltaTime;
            rotX = Math.PI / 2;
            rotY = Math.PI / 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            input = true;
            position.x = PlayerConstants.SPEED * deltaTime;
            rotX = 3 * Math.PI / 2;
            rotY = 3 * Math.PI / 2;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            input = true;
            position.y = PlayerConstants.SPEED * deltaTime;
            if (rotX == 3 * Math.PI / 2) rotX = 2 * Math.PI;
            else rotX = 0;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            input = true;
            position.y = -PlayerConstants.SPEED * deltaTime;
            rotY = Math.PI;
            if (rotX == 0) rotX = Math.PI;
        }

        double rotation = (rotX + rotY) / 2;

        sprite.translate(position.x, position.y);
        if (input) sprite.setRotation((float) Calculations.RadToDeg(rotation));

        if (immune) {
            if (lastHit + PlayerConstants.IMMUNE_LENGTH > TimeUtils.millis()) return -1;
            else collideFinish();
        }

        if (TimeUtils.timeSinceMillis(lastShot) <= PlayerConstants.FIRE_RATE) {
            return -1;
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
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

    private void collideFinish() {
        immune = false;
    }

    private void collided(Vector2 origin) {
        immune = true;
        disabled = true;
        lastHit = TimeUtils.millis();


        Vector2 center = getCenter();
        disabledAngle = (float) Math.atan2(center.y - origin.y, center.x - origin.x);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void dispose() {
        boat.dispose();
    }

    public void takeDamage(int damage, Vector2 origin) {
        if (!immune) {
            if (stats.takeDamage(damage)) MainRunner.IS_MENU = true;
            else collided(origin);
        }
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

    public void updateObjective(String update, int amount){
        objectives.updateObjective(update, amount);
    }

    public Objective getCurrentObjective(){
        return objectives.getCurrentObjective();
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
