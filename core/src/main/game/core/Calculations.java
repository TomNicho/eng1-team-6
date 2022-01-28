package main.game.core;

import com.badlogic.gdx.math.Vector2;

public class Calculations {
    public static double RadToDeg(double rad) {
        return rad * 180 / Math.PI;
    }

    public static double V2Magnitude(Vector2 vector) {
        return Math.sqrt(Math.pow(vector.x, 2) + Math.pow(vector.y, 2));
    }
}
