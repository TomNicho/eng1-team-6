package main.game.world.content;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import main.game.core.Calculations;

public abstract class Entity {
    protected Sprite sprite;

    /**
     * {@link Entity} update function, to run before rendering the {@link Entity}.
     * @param deltaTime The time difference between this frame and the last.
     * @return An integer value to determine the output of this function.
     * @see
     * {@link Entity}
     */
    public abstract int update(float deltaTime);

    /**
     * {@link Entity} render function, run to render the {@link Entity}.
     * @param batch the {@link SpriteBatch} to render this entity under.
     */
    public abstract void render(SpriteBatch batch);

    /**
     * {@link Entity} dispose function, run to dispose the {@link Entity}.
     */
    public abstract void dispose();

    /**
     * Gets the position of the {@link Entity}'s {@link Sprite}.
     * @return A {@link Vector2} with the x and y co-ordinate of the sprite.
     */
    public Vector2 getPosition() {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    /**
     * Gets the center of the {@link Entity}'s {@link Sprite}.
     * @return A {@link Vector2} with the x and y center co-ordinates of the sprite.
     */
    public Vector2 getCenter() {
        return Calculations.SpriteDynamicCenter(sprite);
    }

    /**
     * Gets the bounding {@link Rectangle} of the {@link Entity}'s {@link Sprite}.
     * @return The bounding {@link Rectangle} of the sprite.
     */
    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }

    /**
     * Gets the rotation of the {@link Entity}'s {@link Sprite}.
     * @return The rotation of the sprite.
     */
    public float getRotation() {
        return sprite.getRotation();
    }

    /**
     * Get the sprite of the {@link Entity}.
     * @return The {@link Entity}'s {@link Sprite}.
     */
    public Sprite getSprite() {
        return sprite;
    }
}

