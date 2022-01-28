package main.game;

import com.badlogic.gdx.ApplicationAdapter;

import main.game.world.World;

public class MainRunner extends ApplicationAdapter {   
    
    private World world;

    //Temp H & W until fullscreen cals
    public static final float SCREEN_WIDTH = 1080;
    public static final float SCREEN_HEIGHT = 720;

    @Override
    public void create() {
        world = new World();
    }

    @Override 
    public void render() {
        world.update();
    }

    @Override
    public void dispose() {
        world.dispose();
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
}
