package main.game.core;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Calculations {
    public static double RadToDeg(double rad) {
        return rad * 180 / Math.PI;
    }

    public static double DegToRad(int deg) {
        return deg * Math.PI / 180;
    }

    public static double V2Magnitude(Vector2 vector) {
        return Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2));
    }

    public static Vector2 SpriteDynamicCenter(Sprite sprite) {
        return sprite.getBoundingRectangle().getCenter(new Vector2(0,0));
    }

    public static Vector2 SpriteCenter(Sprite sprite) {
        return new Vector2(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
    }
}
