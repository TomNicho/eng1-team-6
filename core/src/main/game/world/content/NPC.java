package main.game.world.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import main.game.core.Calculations;

public class NPC extends Entity {
    public static final float FIRE_RATE = 1000f;
    public static final float RANGE = 500f;
    public static final float BULLET_SPEED = 250f;
    
    private Texture texture;
    private Sprite sprite;
    private int health;
   
    
    public NPC(int health, Vector2 position) {
        this.health = health;
        this.texture = new Texture(Gdx.files.internal("textures/NPC.png"));
        this.sprite = new Sprite(texture);

        sprite.setPosition(position.x, position.y);
    }
    
    public void update() {
    }

    @Override
    public void render(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
        this.texture.dispose();
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
}
