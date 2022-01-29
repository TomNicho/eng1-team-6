package main.game.world.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import main.game.core.Calculations;

public class Bullet extends Entity {
    public static final float RANGE = 500f;

    private Texture texture;
    private Sprite sprite;
    private float bulletSpeed;
    private Vector2 origin;
    private boolean hitTarget;
    private int damage;

    public Bullet(Vector2 position, float rotation, float bulletSpeed, int damage) {
        this.texture = new Texture(Gdx.files.internal("core/assets/textures/bullet.png"));
        this.sprite = new Sprite(texture);
        this.bulletSpeed = bulletSpeed;
        this.origin = position;
        this.damage = damage;

        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
        hitTarget = false;
    }

    public int update() {
        if (Math.abs(Calculations.V2Magnitude(this.getOrigin()) - Calculations.V2Magnitude(this.getPosition())) > Bullet.RANGE || hitTarget) return 0;

        Vector2 movement = new Vector2();
        movement.x = (float) Math.sin(sprite.getRotation()) * bulletSpeed * Gdx.graphics.getDeltaTime();
        movement.y = (float) Math.cos(sprite.getRotation()) * bulletSpeed * Gdx.graphics.getDeltaTime();
        sprite.setPosition(sprite.getX() + movement.x, sprite.getY() + movement.y);
        return 1;
    }

    public void hit() {
        hitTarget = true;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(this.sprite, sprite.getX(), sprite.getY());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public Vector2 getPosition() {
        return new Vector2(this.sprite.getX(), this.sprite.getY());
    }

    public Rectangle getBounds() {
        return sprite.getBoundingRectangle();
    }

    public Vector2 getCenter() {
        return Calculations.SpriteCenter(sprite);
    }

    public int getDamage() {
        return damage;
    }
}
