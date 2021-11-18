package main.java.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import main.java.game.MainRunner;

public class DesktopLauncher {
    public static Integer width, height, fps;

    public static void main(String[] args) {
        //Setting Default Params
        width = 1200;
        height = 720;
        fps = 60;

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = width;
        config.height = height;
        config.title = "Testing";
        config.foregroundFPS = fps;
        config.backgroundFPS = fps;
        new LwjglApplication(new MainRunner(), config);
    }
}
