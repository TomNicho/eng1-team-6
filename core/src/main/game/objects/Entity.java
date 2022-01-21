package main.game.objects;

import com.badlogic.gdx.physics.box2d.Transform;

public abstract class Entity {
    public Transform transform;

    public abstract void Update();
    public abstract void Render();
    public abstract void Dispose();
}
