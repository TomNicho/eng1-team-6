package main.game.world;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import main.game.world.content.Bullet;
import main.game.world.content.College;
import main.game.world.content.NPC;
import main.game.world.player.Player;

public class World {
    private Player player;
    private Set<NPC> npcs;
    private Set<College> colleges;
    private Set<Bullet> bullets;

    private TiledMap worldMap;
    private TiledMapRenderer mapRenderer;
    private OrthographicCamera gameCamera;
    private SpriteBatch batch;

    public World() {
        //Read Input Files To::
        worldMap = new TmxMapLoader().load("./tiles/libmpTest.tmx");

        //Initialize Objects
        player = new Player();
        npcs = new HashSet<>();
        colleges = new HashSet<>();
        bullets = new HashSet<>();
        mapRenderer = new OrthogonalTiledMapRenderer(worldMap);
        gameCamera = new OrthographicCamera();
        batch = new SpriteBatch();

        //Testing
        Bullet bullet = new Bullet(new Vector2(50,50), 0);
        bullets.add(bullet);

        gameCamera.setToOrtho(false, 1080, 720);
    }

    public void update() {
        //Update All Instances of World
        //Process All Information Returned from update
        //Render All Istances
        gather();
        process();
        render();        
    }

    public void dispose() {
        //Remove All Entities
        player.dispose();

        for (NPC npc : npcs) {
            npc.dispose();
        }

        //Update Colleges
        for (College college : colleges) {
            college.dispose();
        }

        for (Bullet bullet : bullets) {
            bullet.dispose();
        }

        batch.dispose();
        worldMap.dispose();
    }

    private void gather() {

        //Update Player
        player.update();

        //Update NPCS
        for (NPC npc : npcs) {
            npc.update();
        }

        //Update Colleges
        for (College college : colleges) {
            college.update();
        }

        for (Bullet bullet : bullets) {
            bullet.update();
        }
    }

    private void process() {
        Vector2 pos = new Vector2(0, 0);

        // mapRenderer.setView(gameCamera);
        batch.setProjectionMatrix(gameCamera.combined);
        gameCamera.translate(pos);
    }

    private void render() {
        Gdx.gl.glClearColor(0.8f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // mapRenderer.render();
        gameCamera.update();

        batch.begin();
        player.render(batch);

        for (NPC npcs : npcs) {
            npcs.render(batch);
        }

        for (College college : colleges) {
            college.render(batch);
        }

        for (Bullet bullet : bullets) {
            bullet.render(batch);
        }
        batch.end();
    }
}
