package main.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import main.game.MainRunner;

public class DesktopLauncher {
    public static void main(String[] args) {
        //Setting Default Params
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Testing";
        config.foregroundFPS = 60;
        config.backgroundFPS = 60;
        config.vSyncEnabled = true;
        config.width = 1280;
        config.height = 720;
        config.fullscreen = true;
        config.addIcon("icons/newIcon.png", FileType.Internal);
        new LwjglApplication(new MainRunner(), config);
    }
}
