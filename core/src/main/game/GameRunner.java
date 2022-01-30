package main.game;

import com.badlogic.gdx.ApplicationAdapter;

import main.game.menu.MenuUI;
import main.game.world.World;

public class GameRunner extends ApplicationAdapter {   
    //Temp H & W until fullscreen cals
    public static final float SCREEN_WIDTH = 1080;
    public static final float SCREEN_HEIGHT = 720;
    public static boolean IS_MENU = true;
    
    private World world;
    private MenuUI menu;

    @Override
    public void create() {
        world = null;
        menu = new MenuUI();
    }

    @Override 
    public void render() {
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
        world.dispose();
        menu.dispose();
    }

    @Override
    public void pause() {
        //super.pause();
    }

    @Override
    public void resume() {
        //super.resume();
    }

    @Override
    public void resize(int width, int height) {

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
