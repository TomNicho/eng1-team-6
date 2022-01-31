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

import main.game.core.Calculations;
import main.game.core.Constants;
import main.game.core.Constants.*;
import main.game.world.content.*;
import main.game.world.player.Player;
import main.game.world.ui.IGUI;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;  
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
        File xmlfile = new File("core/assets/entities.xml");

        player = new Player(100, 100, new Vector2(0,0), 0);
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

        //Generate npc and college objects using XML data
        try{
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlfile);
            NodeList xmlcolleges = document.getElementsByTagName("college");
            NodeList xmlnpcs = document.getElementsByTagName("npc");
            for (int i = 0; i < xmlnpcs.getLength(); i++){
                Node npcNode = xmlnpcs.item(i);
                Element npcElement = (Element) npcNode;
                int npcElementHealth = Integer.parseInt(npcElement.getElementsByTagName("health").item(0).getTextContent());
                int npcElementX = Integer.parseInt(npcElement.getElementsByTagName("x").item(0).getTextContent());
                int npcElementY = Integer.parseInt(npcElement.getElementsByTagName("y").item(0).getTextContent());
                int npcElementRotation = Integer.parseInt(npcElement.getElementsByTagName("rotation").item(0).getTextContent());
                npcs.add(new NPC(npcElementHealth, new Vector2(npcElementX, npcElementY), npcElementRotation));
            }
            for (int i = 0; i < xmlcolleges.getLength(); i++){
                Node collegeNode = xmlcolleges.item(i);
                Element collegeElement = (Element) collegeNode;
                int collegeElementHealth = Integer.parseInt(collegeElement.getElementsByTagName("health").item(0).getTextContent());
                int collegeElementX = Integer.parseInt(collegeElement.getElementsByTagName("x").item(0).getTextContent());
                int collegeElementY = Integer.parseInt(collegeElement.getElementsByTagName("y").item(0).getTextContent());
                String collegeElementName = collegeElement.getElementsByTagName("name").item(0).getTextContent();
                colleges.add(new College(collegeElementHealth, collegeElementName, new Vector2(collegeElementX, collegeElementY)));
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        gameCamera.setToOrtho(false);
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
        // if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
        //     GameRunner.IS_MENU = true;
        //     return;
        // } 

        float deltaTime = Gdx.graphics.getDeltaTime();

        //Update Player
        int bSPawn = player.update(deltaTime);
        Vector2 playerCenter = player.getCenter();

        if (bSPawn != -1) {
            pBullets.add(new Bullet(playerCenter.add(BulletConstants.BULLET_OFFET), (float) Calculations.DegToRad(bSPawn), PlayerConstants.BULLET_SPEED, player.getDamage()));
        }

        Iterator<Bullet> beIterator = eBullets.iterator();
        while (beIterator.hasNext()) {
            Bullet bullet = beIterator.next();

            if (bullet.update(deltaTime) == 0) {
                bullet.dispose();
                beIterator.remove();
                continue;
            }
        }

        Iterator<Bullet> bpIterator = pBullets.iterator();
        while (bpIterator.hasNext()) {
            Bullet bullet = bpIterator.next();

            if (bullet.update(deltaTime) == 0) {
                bullet.dispose();
                bpIterator.remove();
            }
        }

        Iterator<College> cIterable = colleges.iterator();
        while (cIterable.hasNext()) {
            College college = cIterable.next();

            if (college.inProcess(playerCenter)) {
                int cRet = college.update(deltaTime);

                if (cRet == 0) {
                    player.collectScore(CollegeConstants.SCORE_DEATH);
                    player.collectGold(CollegeConstants.GOLD);
                    player.collectXP(CollegeConstants.XP);

                    college.dispose();
                    cIterable.remove();
                } else if (cRet == 2 && college.inRange(playerCenter)) {
                    double angle = -Math.atan2(college.getSprite().getY() - playerCenter.y - BulletConstants.BULLET_OFFET.y, college.getSprite().getX() - playerCenter.x - BulletConstants.BULLET_OFFET.x) - Math.PI / 2;
                    eBullets.add(new Bullet(college.getPosition(), (float) angle, CollegeConstants.BULLET_SPEED, 10));
                }
            }
        }

        Iterator<NPC> nIterator = npcs.iterator();
        while (nIterator.hasNext()) {
            NPC npc = nIterator.next();

            if (npc.update(deltaTime) == 0) {
                player.collectScore(NPCConstants.SCORE_DEATH);
                player.collectGold(NPCConstants.GOLD);
                player.collectXP(NPCConstants.XP);

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

            for (College c : colleges) {
                if (c.inProcess(playerCenter)) {
                    if (c.getBounds().overlaps(pb.getBounds())) {
                        pb.hit();
                        c.takeDamage(pb.getDamage());
                        player.collectScore(CollegeConstants.SCORE_HIT);
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
                        player.collectScore(NPCConstants.SCORE_HIT);
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

        for (Bullet bullet : eBullets) {
            bullet.render(batch);
        }

        for (Bullet bullet : pBullets) {
            bullet.render(batch);
        }

        for (NPC npcs : npcs) {
            npcs.render(batch);
        }

        for (College college : colleges) {
            college.render(batch);
        }

        player.render(batch);
        batch.end();

        //RenderUI
        uiBatch.begin();
        inGameUI.draw(uiBatch, player);
        uiBatch.end();
    }

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
        worldMap.dispose();
        inGameUI.dispose();
    }
}
