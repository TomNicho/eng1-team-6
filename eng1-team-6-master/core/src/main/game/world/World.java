package main.game.world;

import java.util.Set;

import main.game.world.content.Bullet;
import main.game.world.content.College;
import main.game.world.content.NPC;
import main.game.world.player.Player;

public class World {
    private Player player;
    private Set<NPC> npcs;
    private Set<College> colleges;
    private Set<Bullet> bullets;

    public World() {
        //Read Input Files To::
        //Initialize Objects
        //Generate Map
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

    }

    private void render() {
        player.render();

        for (NPC npcs : npcs) {
            npcs.render();
        }

        for (College college : colleges) {
            college.render();
        }

        for (Bullet bullet : bullets) {
            bullet.render();
        }
    }
}
