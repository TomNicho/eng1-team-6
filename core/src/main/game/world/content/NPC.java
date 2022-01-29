package main.game.world.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;


public class NPC extends Entity {
    public static final float FIRE_RATE = 1000f;
    public static final float RANGE = 500f;
    public static final float BULLET_SPEED = 250f;
    
    private Texture NPC;
    private Sprite sprite;
    private int health;
   
    
    public NPCActor(int health, Vector2 position,SpriteBatch batch) {
        this.health = health;
        this.NPC= new Texture(Gdx.files.internal("core/assets/textures/NPC.png"));    
        
       
    }
    
    public void update() {
        
         sprite.setPosition(position.x, position.y);
    }

    @Override
    public void render(SpriteBatch batch,float parentAlpha) {
        Vector2 project = ActorEventApplicationAdapter.camera.project(new Vector2(npcX, npcY, 0));
        sprite.draw(npc, project.x, project.y);
        setPosition(project.x, project.y);
    }

    @Override
    public void dispose() {
        NPC.dispose();
        
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
