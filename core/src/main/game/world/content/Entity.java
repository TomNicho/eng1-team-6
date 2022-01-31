package main.game.world.content;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;

public abstract class Entity {
    public Transform transform;

    public abstract int update(float deltaTime);
    public abstract void render(SpriteBatch batch);
    public abstract void dispose();

    public abstract Vector2 getPosition();
    public abstract Vector2 getCenter();
    public abstract Rectangle getBounds();
}
