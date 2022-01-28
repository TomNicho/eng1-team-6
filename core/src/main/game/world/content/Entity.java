package main.game.world.content;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Transform;

public abstract class Entity {
    public Transform transform;

    public abstract void update();
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();
}
