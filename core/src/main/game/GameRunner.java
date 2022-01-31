package main.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.TimeUtils;

import main.game.menu.MenuUI;
import main.game.world.World;

public class GameRunner extends ApplicationAdapter {   
    //Temp H & W until fullscreen cals
    public static boolean IS_MENU = true, CLOSING = false;
    
    private long fullscreenCooldown;

    private World world;
    private MenuUI menu;

    @Override
    public void create() {
        world = null;
        menu = new MenuUI();
        fullscreenCooldown = TimeUtils.millis();
    }

    @Override 
    public void render() {
        if (CLOSING) {
            Gdx.app.exit();
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            if (fullscreenCooldown + 1000 < TimeUtils.millis()) {
                fullscreenCooldown = TimeUtils.millis();
                if (Gdx.graphics.isFullscreen()) Gdx.graphics.setWindowedMode(1280, 720);
                else Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }

        if (IS_MENU) {
            if (menu == null) generateMenu();
            menu.menuCycle();
        } else {
            if (world == null) generateWorld();
            world.worldCycle();
        }
    }

    @Override
    public void dispose() {
        if (world != null) world.dispose();
        if (menu != null) menu.dispose();
    }

    public void generateWorld() {
        menu.dispose();
        menu = null;
        world = new World();
    }

    public void generateMenu() {
        world.dispose();
        world = null;
        menu = new MenuUI();
    }
}
