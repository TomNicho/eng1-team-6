package main.game.world.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import main.game.core.Calculations;

public class NPC extends Entity {
    public static final float MOVE_SPEED = 100f;
    public static final float PROCESS_RANGE = 1000f;
    
    private Texture texture;
    private Sprite sprite;
    private int health;
    
    public NPC(int health, Vector2 position, float rotation) {
        this.health = health;
        this.texture = new Texture(Gdx.files.internal("textures/NPC.png"));
        this.sprite = new Sprite(texture);

        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
    }
    
    public int update() {
        if (this.health < 0) return 0;
        return 1;
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void dispose() {
        this.texture.dispose();
    }

    public void takeDamage(int damage) {
        health -= damage;
    }
    
    public int getHealth() {
        return health;
    }
    
    public Vector2 getPosition() {
        return new Vector2(this.sprite.getX(), this.sprite.getY());
    }
    
    public Vector2 getCenter() {
        return Calculations.SpriteCenter(sprite);
    }

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }
    
    public boolean inProcess(Vector2 pos) {
        if (pos.dst(this.getPosition()) <= PROCESS_RANGE) return true;
        else return false;
    }
}
