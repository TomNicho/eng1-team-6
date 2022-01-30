package main.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import main.game.GameRunner;

public class DesktopLauncher {
    public static void main(String[] args) {
        //Setting Default Params
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Testing";
        config.width = 1080;
        config.height = 720;
        config.foregroundFPS = 60;
        config.backgroundFPS = 60;
        config.addIcon("icons/pirate_icon.png", FileType.Internal);
        new LwjglApplication(new GameRunner(), config);
    }
}
