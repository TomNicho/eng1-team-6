package main.game.core;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Calculations {

    /**
     * Converts a Radian value to Degree value.
     * @param rad value to convert.
     * @return the degree value.
     */
    public static double RadToDeg(double rad) {
        return rad * 180 / Math.PI;
    }

    /**
     * Converts a Degree value to Radian value.
     * @param deg value to convert.
     * @return the radian value.
     */
    public static double DegToRad(int deg) {
        return deg * Math.PI / 180;
    }

    /**
     * Calculates the magnitude of a {@link Vector2}.
     * @param vector the {@link Vector2} to calculate.
     * @return the magnitude of the {@link Vector2}.
     */
    public static double V2Magnitude(Vector2 vector) {
        return Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2));
    }

    /**
     * Calculates the dynamic center of a moving {@link Sprite}.
     * @param sprite to calculate the center from.
     * @return calculated {@link Vector2} center.  
     */
    public static Vector2 SpriteDynamicCenter(Sprite sprite) {
        return sprite.getBoundingRectangle().getCenter(new Vector2(0,0));
    }

    /**
     * Calculate the center of a static {@link Sprite}
     * @param sprite to calculate the center from.
     * @return calculated {@link Vector2} center.
     */
    public static Vector2 SpriteCenter(Sprite sprite) {
        return new Vector2(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
    }
}
