package main.game.world;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;

import main.game.core.Calculations;
import main.game.world.content.Bullet;
import main.game.world.content.College;
import main.game.world.content.NPC;
import main.game.world.player.Player;
import main.game.world.ui.IGUI;

public class World {
    private Player player;
    private IGUI inGameUI;
    private Set<NPC> npcs;
    private Set<College> colleges;
    private Set<Bullet> bullets;

    private TiledMap worldMap;
    // private TiledMapRenderer mapRenderer;
    private OrthographicCamera gameCamera, uiCamera;
    private SpriteBatch batch, uiBatch;

    public World() {
        //Read Input Files To::
        worldMap = new TmxMapLoader().load("core/assets/tiles/libmpTest.tmx");

        player = new Player(100, new Vector2(0,0), 0);
        npcs = new HashSet<>();
        colleges = new HashSet<>();
        bullets = new HashSet<>();
        inGameUI = new IGUI();
        // mapRenderer = new OrthogonalTiledMapRenderer(worldMap);
        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        batch = new SpriteBatch();
        uiBatch = new SpriteBatch();

        colleges.add(new College(1000, "James", new Vector2(100,100)));

        gameCamera.setToOrtho(false, 1080, 720);
        uiCamera.setToOrtho(false);
    }

    public void update() {
        //Update All Instances of World
        //Process All Information Returned from update
        //Render All Istances
        gather();
        process();
        render();
    }

    private void gather() {

        //Update Player
        float bSPawn = player.update();
        Vector2 playerCenter = player.getCenter();

        if (bSPawn != -1) {
            bullets.add(new Bullet(player.getPosition(), bSPawn, Player.BULLET_SPEED, true));
        }

        

        //check for bullet collision
        for(Bullet b : bullets){
            if(Intersector.intersectRectangles(b.getBounds(), player.getBounds(),b.getBounds())){
                player.takeDamage(Bullet.damage);
                bullets.remove(b);
            }
        }
        Iterator<Bullet> bIterator = bullets.iterator();
        while (bIterator.hasNext()) {
            Bullet bullet = bIterator.next();

            if (Math.abs(Calculations.V2Magnitude(bullet.getOrigin()) - Calculations.V2Magnitude(bullet.getPosition())) > Bullet.RANGE) {
                bullet.dispose();
                bIterator.remove();
            } else {
                bullet.update();
            }
        }

        Iterator<College> cIterator = colleges.iterator();

        while (cIterator.hasNext()) {
            College college = cIterator.next();

            if (Math.abs(Calculations.V2Magnitude(player.getPosition()) - Calculations.V2Magnitude(college.getPosition())) <= College.RANGE) {
                if (college.update()) {

                    double angle = -Math.atan2(college.getSprite().getY() - playerCenter.y, college.getSprite().getX() - playerCenter.x) - Math.PI / 2;
                    bullets.add(new Bullet(college.getPosition(), (float) angle, College.BULLET_SPEED, false));
                }
            }
        }
    }

    private void process() {
        // mapRenderer.setView(gameCamera);
        batch.setProjectionMatrix(gameCamera.combined);
        uiBatch.setProjectionMatrix(uiCamera.combined);
        gameCamera.position.set(player.getPosition(), gameCamera.position.z);
    }

    private void render() {
        Gdx.gl.glClearColor(0.3f, 0.6f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // mapRenderer.render();

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

        //RenderUI
        uiBatch.begin();
        inGameUI.draw(uiBatch, player.getPosition());
        uiBatch.end();

        gameCamera.update();
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
        inGameUI.dispose();
    }
}
