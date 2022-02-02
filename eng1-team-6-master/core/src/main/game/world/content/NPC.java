package main.game.world.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import main.game.core.Constants.NPCConstants;

public class NPC extends Entity {
    private Texture texture;
    private int health;
    
    public NPC(int health, Vector2 position, float rotation) {
        this.health = health;
        this.texture = new Texture(Gdx.files.internal("textures/npcs.png"));
        this.sprite = new Sprite(texture);

        //Set inital NPC transform.
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
    }
    
    @Override
    public int update(float deltaTime) {
        //Check if the npc is not dead to determine whether it should be removed.
        if (this.health <= 0) return 0;
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
    
    public boolean inProcess(Vector2 pos) {
        if (pos.dst(this.getPosition()) <= NPCConstants.PROCESS_RANGE) return true;
        else return false;
    }
}
