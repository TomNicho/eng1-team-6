package main.game.core;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static final float COLLIDE_DAMAGE = 10f;
    public static final float COLLIDE_PUSHBACK = 200;
    public static final float WOLD_BOUND_MIN = -100f;
    public static final float WOLD_BOUND_MAX = 500f;

    public static class CollegeConstants {
        public static final float FIRE_RATE = 2000f;
        public static final float RANGE = 600f;
        public static final float PROCESS_RANGE = 1000f;
        public static final float BULLET_SPEED = 250f;
        public static final int GOLD = 500;
        public static final int XP = 10;
        public static final int SCORE_HIT = 20;
        public static final int SCORE_DEATH = 200;
    }

    public static class PlayerConstants {
        public static final float SPEED = 125f;
        public static final float FIRE_RATE = 500f;
        public static final float BULLET_SPEED = 250f;
        public static final int SCORE_HIT = 5;
        public static final int IMMUNE_LENGTH = 1000;
        public static final int DISABLED_LENGTH = 250;
    }

    public static class NPCConstants {
        public static final float MOVE_SPEED = 100f;
        public static final float PROCESS_RANGE = 1000f;
        public static final int GOLD = 50;
        public static final int XP = 1;
        public static final int SCORE_HIT = 10;
        public static final int SCORE_DEATH = 50; 
    }

    public static class BulletConstants {
        public static final float RANGE = 500f;
        public static final Vector2 BULLET_OFFET = new Vector2(-8,-8);
    }
}
