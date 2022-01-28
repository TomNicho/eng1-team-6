package main.game.world.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity {
    public static float bulletSpeed = 100f;

    private Texture texture;
    private Sprite sprite;
    private boolean player;

    public Bullet(Vector2 position, float rotation) {
        this.texture = new Texture(Gdx.files.internal("./textures/bullet.png"));
        this.sprite = new Sprite(texture);

        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
        player = false;
    }

    @Override
    public void update() {
        Vector2 movement = new Vector2();
        movement.x = (float) Math.cos(sprite.getRotation()) * bulletSpeed * Gdx.graphics.getDeltaTime();
        movement.y = (float) Math.sin(sprite.getRotation()) * bulletSpeed * Gdx.graphics.getDeltaTime();
        sprite.setPosition(sprite.getX() + movement.x, sprite.getY() + movement.y);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(this.sprite, sprite.getX(), sprite.getY());
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
