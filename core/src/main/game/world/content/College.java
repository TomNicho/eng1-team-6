package main.game.world.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import main.game.core.Constants.CollegeConstants;

public class College extends Entity {
    private Texture collegeTexture;
    private int health;
    private String name, ukey;

    private long lastShot;

    public College(int health, String name, String ukey, Vector2 position) {
        this.health = health;
        this.name = name;
        this.ukey = ukey;

        collegeTexture = new Texture(Gdx.files.internal("textures/college.png"));
        sprite = new Sprite(collegeTexture);

        //Set inital college transform.
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(0);
        lastShot = TimeUtils.millis();
    }

    public int update(float deltaTime) {
        
        // Check if the college has no health and if the college and shoot again
        if (this.health < 0) return 0;
        if (TimeUtils.timeSinceMillis(lastShot) > CollegeConstants.FIRE_RATE) {
            lastShot = TimeUtils.millis();
            return 2;
        }
        return 1;
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
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

    public String getUkey() {
        return ukey;
    }
    
    public boolean inRange(Vector2 pos) {
        if (pos.dst(this.getPosition()) <= CollegeConstants.RANGE) return true;
        else return false;
    }

    public boolean inProcess(Vector2 pos) {
        if (pos.dst(this.getPosition()) <= CollegeConstants.PROCESS_RANGE) return true;
        else return false;
    }
}
