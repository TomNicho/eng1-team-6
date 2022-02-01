package main.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import main.game.MainRunner;

public class MenuUI {
    private Stage stage;
    private BitmapFont font;
    private Label label;

    public MenuUI() {
        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        label = new Label("PRESS ENTER TO START!", new Label.LabelStyle(font, Color.BLACK));
        
        label.setFontScale(2f);
        label.setWidth(label.getWidth() * 2);
        label.setPosition(Gdx.graphics.getWidth() / 2 - label.getWidth() / 2, 100);
        stage.addActor(label);
    }

    public void menuCycle() {
        update();
        render();
    }

    /**
     * Updates all instances within the {@link MenuUI} and act the {@link Stage}.
     * @see 
     * {@link Label},
     * {@link BitmapFont},
     * {@link Stage}.
     */
    private void update() {
        float delta = Gdx.graphics.getDeltaTime();

        //Check if the menu needs to switch to the world or close the program
        if (Gdx.input.isKeyPressed(Keys.ENTER)) {
            MainRunner.IS_MENU = false;
        } else if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
            MainRunner.CLOSING = true;
        }

        stage.act(delta);
    }
    
    /**
     * Renders the {@link Stage} within the {@link MenuUI}.
     * @see
     * {@link Stage}.
     */
    private void render() {
        Gdx.gl.glClearColor(0.3f, 0.6f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
        font.dispose();
    }
}
