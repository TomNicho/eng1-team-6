package main.game.core;

import com.badlogic.gdx.math.Vector2;

public class Constants {
    public static class CollegeConstants {
        public static final float FIRE_RATE = 1000f;
        public static final float RANGE = 500f;
        public static final float PROCESS_RANGE = 1000f;
        public static final float BULLET_SPEED = 250f;
        public static final int GOLD = 500;
        public static final int XP = 10;
        public static final int SCORE_HIT = 20;
        public static final int SCORE_DEATH = 200;
    }

    public static class PlayerConstants {
        public static final float SPEED = 100f;
        public static final float FIRE_RATE = 500f;
        public static final float BULLET_SPEED = 250f;
        public static final int SCORE_HIT = 5;
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
