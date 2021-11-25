package main.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

import main.game.enums.GameState;

public class MainRunner extends ApplicationAdapter {
    
    private GameState state;
    private Texture priate, background;
    private Color backgroundColour;
    private Batch batch;

    private OrthographicCamera camera;
    private Vector2 piratePosition, pirateSize;

    @Override
    public void create() {
        backgroundColour = new Color(1f, 1f, 1f, 1f);
        background = new Texture(Gdx.files.internal("core/assets/textures/background.jpg"));
        priate = new Texture(Gdx.files.internal("core/assets/textures/pirate_hat.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        batch = new SpriteBatch();
        piratePosition = new Vector2(200, 200);
        pirateSize = new Vector2(256,256);

        state = GameState.RUNNING;
    }

    @Override 
    public void render() {
        if (state == GameState.RUNNING) {
            ScreenUtils.clear(backgroundColour);

            camera.update();
            batch.setProjectionMatrix(camera.combined);

            batch.begin();
            batch.draw(background, 0, 0, 1200, 720);
            batch.draw(priate, piratePosition.x - pirateSize.x / 2, piratePosition.y - pirateSize.y / 2, pirateSize.x, pirateSize.y);
            batch.end();

            if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
                pause();
                return;
            }

            if (Gdx.input.isKeyPressed(Keys.LEFT)) piratePosition.x -= 200 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Keys.RIGHT)) piratePosition.x += 200 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Keys.UP)) piratePosition.y += 200 * Gdx.graphics.getDeltaTime();
            if (Gdx.input.isKeyPressed(Keys.DOWN)) piratePosition.y -= 200 * Gdx.graphics.getDeltaTime();

        } else {
            if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
                resume();
                return;
            }
        }
    }

    @Override
    public void resume() {
        if (this.state == GameState.PAUSED) this.state = GameState.RUNNING;
    }

    @Override
    public void pause() {
        if (this.state == GameState.RUNNING) this.state = GameState.PAUSED;        
    }

    @Override
    public void resize(int width, int height) {
        
    }

    @Override
    public void dispose() {
        priate.dispose();
        background.dispose();
        batch.dispose();  
    }
}
