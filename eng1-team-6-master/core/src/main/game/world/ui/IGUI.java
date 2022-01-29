package main.game.world.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class IGUI {
    private BitmapFont font;

    public IGUI() {
        font = new BitmapFont();
    }

    public void draw(SpriteBatch batch, Vector2 playerPosition) {
        font.draw(batch, String.format("Player Position x:%s, y:%s", playerPosition.x, playerPosition.y), 10, 20);
    }

    public void dispose() {

    }
}
