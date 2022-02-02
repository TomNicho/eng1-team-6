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

    private int health, damage;
    private String name, ukey;
    private boolean allied;
    private long lastShot;

    public College(int health, int damage, String name, String ukey, Vector2 position, boolean allied) {
        this.health = health;
        this.damage = damage;
        this.name = name;
        this.ukey = ukey;
        this.allied = allied;

        try {
            String texturePath = "textures/" + this.name.toLowerCase() + ".png";
            collegeTexture = new Texture(Gdx.files.internal(texturePath));
        } catch (Exception fileNotFoundException) {
            collegeTexture = new Texture(Gdx.files.internal("textures/captured.png"));
        }
        
        sprite = new Sprite(collegeTexture);

        //Set inital college transform.
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(0);
        lastShot = TimeUtils.millis();
    }

    public void setAllied() {
        allied = true;
        collegeTexture.dispose();
        collegeTexture = new Texture(Gdx.files.internal("textures/captured.png"));
        sprite.setTexture(collegeTexture);
    }

    @Override
    public int update(float deltaTime) {
        if (allied) return -1;

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
        if (!allied) health -= damage;
    }

    public void shoot() {

    }

    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public String getName() {
        return name;
    }

    public String getUkey() {
        return ukey;
    }

    public boolean getAllied() {
        return allied;
    }
    
    public boolean inRange(Vector2 pos) {
        if (pos.dst(this.getPosition()) <= CollegeConstants.RANGE) return true;
        else return false;
    }

    public boolean inProcess(Vector2 pos) {
        if (pos.dst(this.getPosition()) <= CollegeConstants.PROCESS_RANGE) return true;
        else return false;
    }
    public void capture(){
        Vector2 replacePos = new Vector2(this.getPosition());
        this.name = "captured";
        this.collegeTexture = new Texture(Gdx.files.internal("textures/captured.png"));
        this.sprite = new Sprite(this.collegeTexture);
        this.sprite.setPosition(replacePos.x, replacePos.y);
    }
}
