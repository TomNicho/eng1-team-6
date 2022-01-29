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
import com.badlogic.gdx.math.Vector2;

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
    private Set<Bullet> eBullets;
    private Set<Bullet> pBullets;

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
        eBullets = new HashSet<>();
        pBullets = new HashSet<>();
        inGameUI = new IGUI();

        // mapRenderer = new OrthogonalTiledMapRenderer(worldMap);
        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        batch = new SpriteBatch();
        uiBatch = new SpriteBatch();

        colleges.add(new College(1000, "James", new Vector2(100,100)));
        npcs.add(new NPC(500, new Vector2(-100,-100), 235));

        gameCamera.setToOrtho(false, 1080, 720);
        uiCamera.setToOrtho(false);
    }

    public void worldCycle() {
        //Update All Instances of World
        //Process All Information Returned from update
        //Render All Istances
        update();
        process();
        render();
    }

    private void update() {

        //Update Player
        float bSPawn = player.update();
        Vector2 playerCenter = player.getCenter();

        if (bSPawn != -1) {
            pBullets.add(new Bullet(player.getPosition(), bSPawn, Player.BULLET_SPEED, 50));
        }

        Iterator<Bullet> beIterator = eBullets.iterator();
        while (beIterator.hasNext()) {
            Bullet bullet = beIterator.next();

            if (bullet.update() == 0) {
                bullet.dispose();
                beIterator.remove();
                continue;
            }
        }

        Iterator<Bullet> bpIterator = pBullets.iterator();
        while (bpIterator.hasNext()) {
            Bullet bullet = bpIterator.next();

            if (bullet.update() == 0) {
                bullet.dispose();
                bpIterator.remove();
            }
        }

        Iterator<College> cIterable = colleges.iterator();
        while (cIterable.hasNext()) {
            College college = cIterable.next();

            if (college.inProcess(playerCenter)) {
                int cRet = college.update();

                if (cRet == 0) {
                    college.dispose();
                    cIterable.remove();
                } else if (cRet == 2 && college.inRange(playerCenter)) {
                    double angle = -Math.atan2(college.getSprite().getY() - playerCenter.y, college.getSprite().getX() - playerCenter.x) - Math.PI / 2;
                    eBullets.add(new Bullet(college.getPosition(), (float) angle, College.BULLET_SPEED, 10));
                }
            }
        }

        Iterator<NPC> nIterator = npcs.iterator();
        while (nIterator.hasNext()) {
            NPC npc = nIterator.next();

            if (npc.update() == 0) {
                npc.dispose();
                nIterator.remove();
            }
        }
    }

    private void process() {

        collisions();

        // Update Both Cameras
        batch.setProjectionMatrix(gameCamera.combined);
        uiBatch.setProjectionMatrix(uiCamera.combined);
        gameCamera.position.set(player.getPosition(), gameCamera.position.z);
        gameCamera.update();
    }

    private void collisions() {
        Vector2 playerCenter = player.getCenter();

        //Process enemy bullets towards the Player
        for (Bullet eb : eBullets) {
            if (player.getBounds().overlaps(eb.getBounds())) {
                eb.hit();
                player.takeDamage(eb.getDamage());
            }
        }

        //Process player bullets for NPCs and Colleges
        for (Bullet pb : pBullets) {
            boolean collided = false;

            for (College c : colleges) {
                if (c.inProcess(playerCenter)) {
                    if (c.getBounds().overlaps(pb.getBounds())) {
                        pb.hit();
                        c.takeDamage(pb.getDamage());
                        collided = true;
                        break;
                    }
                }
            }

            if (collided) continue;

            for (NPC n : npcs) {
                if (n.inProcess(playerCenter)) {
                    if (n.getBounds().overlaps(pb.getBounds())) {
                        pb.hit();
                        n.takeDamage(pb.getDamage());
                        break;
                    }
                }
            }
        }
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

        for (Bullet bullet : eBullets) {
            bullet.render(batch);
        }

        for (Bullet bullet : pBullets) {
            bullet.render(batch);
        }

        batch.end();

        //RenderUI
        uiBatch.begin();
        inGameUI.draw(uiBatch, player.getPosition());
        uiBatch.end();
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

        for (Bullet bullet : eBullets) {
            bullet.dispose();
        }

        for (Bullet bullet : pBullets) {
            bullet.dispose();
        }

        batch.dispose();
        worldMap.dispose();
        inGameUI.dispose();
    }
}
