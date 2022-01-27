package main.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import main.game.world.player.Player;

public class MainRunner extends ApplicationAdapter {    
    // private static float moveSpeed = 250f;
    
    private OrthographicCamera camera;
    //private SpriteBatch batch;
    private TiledMap worldMap;
    private TiledMapRenderer mapRenderer;
    private Player player;
    private SpriteBatch batch;
    @Override
    public void create() {
        worldMap = new TmxMapLoader().load("core/assets/tiles/libmpTest.tmx");
        

        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        player = new Player(100,250f);
        mapRenderer = new OrthogonalTiledMapRenderer(worldMap);

        camera.setToOrtho(false, 1080, 720);
    }

    @Override 
    public void render() {
        player.render();
        //Clear the screen (1)
        Gdx.gl.glClearColor(0.8f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            // player.GetPosition().x = -player.GetSpeed() * Gdx.graphics.getDeltaTime();
            player.RotateBy(-2);
            camera.rotate(-2);
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            
            // player.GetPosition().x = player.GetSpeed() * Gdx.graphics.getDeltaTime();
            player.RotateBy(2);
            camera.rotate(2);
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.S)){
            player.GetPosition().y = -player.GetSpeed() * Gdx.graphics.getDeltaTime() * Math.abs(player.GetRotation() / 10);
            player.GetPosition().x = -player.GetSpeed() * Gdx.graphics.getDeltaTime() * player.GetRotation() / 10;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            player.GetPosition().y = player.GetSpeed() * Gdx.graphics.getDeltaTime() * Math.abs(player.GetRotation() /10);
            player.GetPosition().x = player.GetSpeed() * Gdx.graphics.getDeltaTime() * player.GetRotation() / 10;
        }
        
        // batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        camera.translate(player.GetPosition().x, player.GetPosition().y);
        mapRenderer.render();
        
        batch.begin();
        batch.draw(player.GetTextureRegion(), Gdx.graphics.getWidth() / 2 - player.GetSprite().getWidth() / 2, Gdx.graphics.getHeight() / 2 - player.GetSprite().getHeight() / 2,0,0,player.GetSprite().getWidth(),player.GetSprite().getHeight(),2,2,0,false);
        batch.end();
        
        camera.update();
    }

    @Override
    public void dispose() {
        worldMap.dispose();
        player.GetTexture().dispose();
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
