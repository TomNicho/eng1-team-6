package main.game.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import main.game.MainRunner;

public class MenuUI {
    private Stage stage;
    private BitmapFont font;
    private LabelStyle basicStyle;
    private Label enter, exit;
    private Texture logoTexture;
    private Sprite logo;

    public MenuUI() {
        logoTexture = new Texture(Gdx.files.internal("icons/icon128.png"));
        logo = new Sprite(logoTexture);
        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        basicStyle = new LabelStyle(font, Color.BLACK);
        enter = new Label("ENTER TO START!", basicStyle);
        exit = new Label("PRESS ESCAPE TO EXIT", basicStyle);
        
        enter.setFontScale(2f);
        enter.setPosition(Gdx.graphics.getWidth() / 2 - enter.getWidth() / 2, 100);
        enter.setAlignment(Align.center);

        exit.setPosition(Gdx.graphics.getWidth() / 2 - exit.getWidth() / 2, 70);

        logo.setScale(2f);
        logo.setPosition(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2, Gdx.graphics.getHeight() / 2);

        stage.addActor(enter);
        stage.addActor(exit);
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
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            MainRunner.IS_MENU = false;
        } else if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
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

        stage.getBatch().begin();
        logo.draw(stage.getBatch());
        stage.getBatch().end();
    }

    /**
     * Disposes the {@link Stage} and any other disposables within the {@link MenuUI}.
     * @see
     * {@link Stage}.
     */
    public void dispose() {
        stage.dispose();
        font.dispose();
        logoTexture.dispose();
    }
}
