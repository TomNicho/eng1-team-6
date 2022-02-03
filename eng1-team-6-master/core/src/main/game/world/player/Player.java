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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class Player extends Entity {
    private PlayerStats stats;
    private ObjectiveManager objectives;
    private Texture boat;

    private boolean immune, disabled, won;

    private float disabledAngle;
    private long lastShot, lastHit; //lastScore;

    public Player(int health, int damage, Vector2 position, float rotation, List<Objective> objectives){
        long currentTime = TimeUtils.millis();
        this.lastShot = currentTime;
        this.lastHit = currentTime;
        // this.lastScore = currentTime;

        this.immune = false;
        this.disabled = false;
        this.won = false;
        this.disabledAngle = 0f;

        this.stats = new PlayerStats(health, damage, 0, 0);
        this.objectives = new ObjectiveManager(objectives);
        this.boat = new Texture(Gdx.files.internal("textures/player.png"));
        this.sprite = new Sprite(boat);

        //Set inital player transfrom
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
    }

    @Override
    public int update(float deltaTime) {
        //If the game is Won and the player presses escape return to the menu
        if (won) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) MainRunner.IS_MENU = true;
        } else {  
            // if (lastScore + 1000 < TimeUtils.millis()) {
            //     lastScore = TimeUtils.millis();
            //     collectScore(1);
            // }
        }

        //Check if the player is disabled, if so it can't shoot and it cannot move.
        if (disabled) {
            if (lastHit + PlayerConstants.DISABLED_LENGTH > TimeUtils.millis()) {

                //Translate the player based on it's forced movement
                sprite.translate((float) Math.cos(disabledAngle) * Constants.COLLIDE_PUSHBACK * deltaTime, (float) Math.sin(disabledAngle) * Constants.COLLIDE_PUSHBACK * deltaTime);
                return -1;
            } else disabled = false;
        }

        Vector2 position = new Vector2(0,0);
        double rotX = 0, rotY = 0;
        boolean input = false;

        //Get the player input keys and determine the x axis of movement
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

        //Get the player input keys and determine the y axis of movement
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

        //Combine the rotation's gathered to create and average for the player to be rotated on.
        double rotation = (rotX + rotY) / 2;
       
        //Clamp Player position if it exceeds bounds
        // Vector2 exceeds = Calculations.ExceedsBounds(getPosition(), Constants.WOLD_BOUND_MIN, Constants.WOLD_BOUND_MAX);

        sprite.translate(position.x, position.y);
        if (input) sprite.setRotation((float) Calculations.RadToDeg(rotation));

        //If there is a movement objective then check keys
        if (getCurrentObjective() != null && getCurrentObjective().getuKey().equals("move")) updateObjective("move", (int) Calculations.V2Magnitude(position));

        //If the player is immune but not disabled then prevent the player from shooting
        if (immune) {
            if (lastHit + PlayerConstants.IMMUNE_LENGTH > TimeUtils.millis()) return -1;
            else immune = false;
        }

        //Find if any shooting keys are pressed and that the player can shoot to return the bullet angle.
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

    /**
     * When the player is hit, either by a bullet and collision, set the player to disabled and immune.
     * Also begin forced movement between the player and the origin provided.
     * @param origin a {@link Vector2} origin to determine where the player gets moved.
     */
    private void collided(Vector2 origin) {
        immune = true;
        disabled = true;
        lastHit = TimeUtils.millis();

        //Use the center of the player and the origin to find out the forced disabled movement.
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

    /**
     * Make the player take an amount of damage based on an origin, and check if the player is dead to then return to the menu.
     * @param damage the amount of damage the player takes.
     * @param origin the origin for which the collision came from.
     */
    public void takeDamage(int damage, Vector2 origin) {
        if (!immune) {
            if (stats.takeDamage(damage)) MainRunner.IS_MENU = true;
            else collided(origin);
        }
    }

    /**
     * Call the player stats to increase the amount of {@link Gold} the player has.
     * @param amount The amount of {@link Gold} to increase by.
     */
    public void collectGold(int amount) {
        stats.increaseGold(amount);
    }

    /**
     * Call the player stats to increase the amount of xp the player has. Stored by the {@link Leveler}.
     * @param amount The amount of xp to increase by.
     */
    public void collectXP(int amount) {
        stats.increaseXP(amount);
    }

    /**
     * Call the player stats to increase the amount of score the player has.
     * @param amount The amount of score to increase by.
     */
    public void collectScore(int amount) {
        stats.increaseScore(amount);
    }

    /**
     * Call the {@link ObjectiveManager} to potentially update an {@link Objective} based on a objective key.
     * @param update The {@link Objective} key to check if this update matches the current objective.
     * @param amount The amount in which to increase the {@link Objective} by.
     */
    public void updateObjective(String update, int amount){
        int xpGain = objectives.updateObjective(update, amount);

        if (xpGain != 0) collectXP(xpGain);
        if (getCurrentObjective() == null) won = true;
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

    public boolean getWon() {
        return won;
    }
}
