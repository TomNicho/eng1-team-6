package main.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class MainRunner extends ApplicationAdapter {    
    private static float moveSpeed = 250f;
    
    private OrthographicCamera camera;
    //private SpriteBatch batch;
    private TiledMap worldMap;
    private TiledMapRenderer mapRenderer;
    private Texture boat;
    private Sprite player;
    private SpriteBatch batch;

    @Override
    public void create() {
        worldMap = new TmxMapLoader().load("core/assets/tiles/libmpTest.tmx");
        boat = new Texture(Gdx.files.internal("core/assets/textures/boat.png"));

        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        player = new Sprite(boat, 32, 64);
        mapRenderer = new OrthogonalTiledMapRenderer(worldMap);

        camera.setToOrtho(false, 1080, 720);
    }

    @Override 
    public void render() {
        //Clear the screen (1)
        Gdx.gl.glClearColor(0.8f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Vector2 pos = new Vector2(0, 0);

        if (Gdx.input.isKeyPressed(Input.Keys.A)) pos.x = -moveSpeed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) pos.x = moveSpeed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) pos.y = -moveSpeed * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) pos.y = moveSpeed * Gdx.graphics.getDeltaTime();

        // batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        camera.translate(pos.x, pos.y);

        mapRenderer.render();

        batch.begin();
        batch.draw(player, Gdx.graphics.getWidth() / 2 - player.getWidth() / 2, Gdx.graphics.getHeight() / 2 - player.getHeight() / 2);
        batch.end();

        camera.update();
    }

    @Override
    public void dispose() {
        worldMap.dispose();
        boat.dispose();
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
