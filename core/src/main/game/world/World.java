package main.game.world;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import main.game.core.Calculations;
import main.game.core.Constants;
import main.game.core.XMLLoader;
import main.game.core.Constants.*;
import main.game.world.content.*;
import main.game.world.player.Player;
import main.game.world.ui.IGUI;

public class World {
    private Player player;
    private IGUI inGameUI;
    private Set<NPC> npcs;
    private Set<College> colleges;
    private Set<Bullet> eBullets;
    private Set<Bullet> pBullets;

    private OrthographicCamera gameCamera, uiCamera;
    private SpriteBatch batch, uiBatch;

    public World() {
        // Create XMLLoader to load input files
        XMLLoader loader = new XMLLoader("core/assets/entities.xml");

        try {
            //Read input file and initalize variables
            loader.load();
        } catch (Exception e) {
            System.out.println(e);
        }

        //Initialise all world objects
        player = new Player(100, 100, new Vector2(0,0), 0, loader.getObjectives());
        npcs = loader.getNpcs();
        colleges = loader.getColleges();
        eBullets = new HashSet<>();
        pBullets = new HashSet<>();
        inGameUI = new IGUI();

        //Generate npc and college objects using XML data
        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        batch = new SpriteBatch();
        uiBatch = new SpriteBatch();

        gameCamera.setToOrtho(false);
        uiCamera.setToOrtho(false);
    }

    /**
     * The update, process, render loop for the {@link World}.
     */
    public void worldCycle() {
        //Update All Instances of World
        //Process All Information Returned from update
        //Render All Istances
        update();
        process();
        render();
    }

    /**
     * Updates all assets within the {@link World} 
     * @see 
     * {@link Entity},
     */
    private void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        //Update Player
        int bSPawn = player.update(deltaTime);
        Vector2 playerCenter = player.getCenter();

        if (bSPawn != -1) {
            //Check if there is a shooting objective
            if (player.getCurrentObjective() != null && player.getCurrentObjective().getuKey().equals("shoot")) player.updateObjective("shoot", 1);

            //Spawn player bullet based on player update response
            pBullets.add(new Bullet(playerCenter.add(BulletConstants.BULLET_OFFET), (float) Calculations.DegToRad(bSPawn), PlayerConstants.BULLET_SPEED, player.getDamage()));
        }

        //Update all enemy bullets
        Iterator<Bullet> beIterator = eBullets.iterator();
        while (beIterator.hasNext()) {
            Bullet bullet = beIterator.next();

            //If bullet has expired then dispose and delete it
            if (bullet.update(deltaTime) == 0) {
                bullet.dispose();
                beIterator.remove();
                continue;
            }
        }

        //Update all player bullets
        Iterator<Bullet> bpIterator = pBullets.iterator();
        while (bpIterator.hasNext()) {
            Bullet bullet = bpIterator.next();

            //If bullet has expired then dispose and delete it
            if (bullet.update(deltaTime) == 0) {
                bullet.dispose();
                bpIterator.remove();
            }
        }

        //Update all colleges
        Iterator<College> cIterable = colleges.iterator();
        while (cIterable.hasNext()) {
            College college = cIterable.next();

            //Update college when in process range
            if (college.inProcess(playerCenter)) {
                int cRet = college.update(deltaTime);

                //If college has been destroyed update player stats, and remove the college
                if (cRet == 0) {
                    player.collectScore(CollegeConstants.SCORE_DEATH);
                    player.collectGold(CollegeConstants.GOLD);
                    player.collectXP(CollegeConstants.XP);

                    if (player.getCurrentObjective() != null) {
                        if (player.getCurrentObjective().getuKey().equals(college.getUkey())) player.updateObjective(college.getUkey(), 1);
                    }
                    college.capture();
                } else if (cRet == 2 && college.inRange(playerCenter) && college.getName() != "captured") {

                    //Create a bullet between the player and college
                    double angle = -Math.atan2(college.getSprite().getY() - playerCenter.y - BulletConstants.BULLET_OFFET.y, college.getSprite().getX() - playerCenter.x - BulletConstants.BULLET_OFFET.x) - Math.PI / 2;
                    eBullets.add(new Bullet(college.getPosition(), (float) angle, CollegeConstants.BULLET_SPEED, 10));
                }
            }
        }

        //Update all NPCs
        Iterator<NPC> nIterator = npcs.iterator();
        while (nIterator.hasNext()) {
            NPC npc = nIterator.next();

            //When the NPC is dead update player stats and remove the npc
            if (npc.update(deltaTime) == 0) {
                player.collectScore(NPCConstants.SCORE_DEATH);
                player.collectGold(NPCConstants.GOLD);
                player.collectXP(NPCConstants.XP);

                if (player.getCurrentObjective() != null) {
                    if (player.getCurrentObjective().getuKey().equals("npc")) player.updateObjective("npc", 1);
                }

                npc.dispose();
                nIterator.remove();
            }
        }
    }

    /**
     * Processes all assets within the {@link World}, this includes collisions as well as updating {@link OrthographicCamera}, and {@link SpriteBatch} matrixes.
     * @see 
     * {@link OrthographicCamera},
     * {@link SpriteBatch}.
     */
    private void process() {
        collisions();

        // Update Both Cameras
        batch.setProjectionMatrix(gameCamera.combined);
        uiBatch.setProjectionMatrix(uiCamera.combined);
        gameCamera.position.set(player.getCenter(), gameCamera.position.z);
        gameCamera.update();
    }

    /**
     * Processes all collisions within the {@link World}.
     * @see 
     * {@link Player},
     * {@link College},
     * {@link NPC},
     * {@link Bullet}.
     */
    private void collisions() {
        Vector2 playerCenter = player.getCenter();
        boolean collided = false;

        //Process Player Collisions With A College
        for (College c : colleges) {
            if (c.inProcess(playerCenter)) {
                if (c.getBounds().overlaps(player.getBounds())) {
                    player.takeDamage((int) Constants.COLLIDE_DAMAGE, c.getCenter());
                    collided = true;
                    break;
                }
            }
        }

        //Process Player Collisions With A NPC
        if (!collided) {
            for (NPC n : npcs) {
                if (n.inProcess(playerCenter)) {
                    if (n.getBounds().overlaps(player.getBounds())) {
                        player.takeDamage((int) Constants.COLLIDE_DAMAGE, n.getCenter());
                        collided = true;
                        break;
                    }
                }
            }
        }


        //Process enemy bullets towards the Player
        if (!collided) {
            for (Bullet eb : eBullets) {
                if (player.getBounds().overlaps(eb.getBounds())) {
                    eb.hit();
                    player.takeDamage(eb.getDamage(), eb.getCenter());
                    break;
                }
            }
        }

        if (collided) player.collectScore(PlayerConstants.SCORE_HIT);

        //Process player bullets for NPCs and Colleges
        for (Bullet pb : pBullets) {
            collided = false;

            //Process College collisions
            for (College c : colleges) {
                if (c.inProcess(playerCenter)) {
                    if (c.getBounds().overlaps(pb.getBounds())) {

                        //if hit queue removing the bullet and update health and score
                        pb.hit();
                        c.takeDamage(pb.getDamage());
                        player.collectScore(CollegeConstants.SCORE_HIT);
                        collided = true;
                        break;
                    }
                }
            }

            if (collided) continue;

            //Process NPC collisions
            for (NPC n : npcs) {
                if (n.inProcess(playerCenter)) {
                    if (n.getBounds().overlaps(pb.getBounds())) {

                        //if hit queue removing the bullet and update health and score
                        pb.hit();
                        n.takeDamage(pb.getDamage());
                        player.collectScore(NPCConstants.SCORE_HIT);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Renders all entities within {@link World}. 
     * @see 
     * {@link Entity},
     */
    private void render() {
        Gdx.gl.glClearColor(0.3f, 0.6f, 0.9f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // mapRenderer.render();
        batch.begin();

        // Render enemy bullets
        for (Bullet bullet : eBullets) {
            bullet.render(batch);
        }

        //Render player bullets
        for (Bullet bullet : pBullets) {
            bullet.render(batch);
        }

        //Render npcs
        for (NPC npcs : npcs) {
            npcs.render(batch);
        }

        //Render colleges
        for (College college : colleges) {
            college.render(batch);
        }

        player.render(batch);
        batch.end();

        //Render the in game ui
        uiBatch.begin();
        inGameUI.draw(uiBatch, player);
        uiBatch.end();
    }

    /**
     * Disposes the {@link World}.
     * @see 
     * {@link Entity},k
     */
    public void dispose() {

        //Remove All Entities
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

        player.dispose();
        batch.dispose();
        uiBatch.dispose();
        inGameUI.dispose();
    }
}
