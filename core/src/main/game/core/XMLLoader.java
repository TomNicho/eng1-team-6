package main.game.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import main.game.world.content.College;
import main.game.world.content.NPC;
import main.game.world.player.Objectives.Objective;

public class XMLLoader {
    private FileHandle handle;
    private Set<College> colleges;
    private Set<NPC> npcs;
    private List<Objective> objectives;

    public XMLLoader(FileHandle handle) {
        this.handle = handle;
        this.npcs = new HashSet<>();
        this.colleges = new HashSet<>();
        this.objectives = new ArrayList<>();
    }

    /**
     * Loads all the objects from the XML File given and initalises all {@link Entity}, and {@link Objective} instances present.
     * @see
     * {@link XmlReader}
     */
    public void load() {
        XmlReader reader = new XmlReader();
        Element root = reader.parse(this.handle);

        Array<Element> xmlNpcs = root.getChildByName("npcs").getChildrenByName("npc");
        Array<Element> xmlColleges = root.getChildByName("colleges").getChildrenByName("college");
        Array<Element> xmlObjectives = root.getChildByName("objectives").getChildrenByName("objective");

        for (Element npc : xmlNpcs) {
            int health = Integer.parseInt(npc.get("health"));
            int npcx = Integer.parseInt(npc.get("x"));
            int npxy = Integer.parseInt(npc.get("y"));
            int rotation = Integer.parseInt(npc.get("rotation"));
            npcs.add(new NPC(health, new Vector2(npcx, npxy), rotation));
        }

        for (Element objective : xmlObjectives) {
            String name = objective.get("name");
            String uKey = objective.get("ukey");
            int amount = Integer.parseInt(objective.get("amount"));
            int xp = Integer.parseInt(objective.get("xp"));
            int objX = Integer.parseInt(objective.get("x"));
            int objY = Integer.parseInt(objective.get("y"));
            objectives.add(new Objective(name, uKey, amount, xp, new Vector2(objX, objY)));
        }

        for (Element college : xmlColleges) {
            int health = Integer.parseInt(college.get("health"));
            int damage = Integer.parseInt(college.get("damage"));
            int objx = Integer.parseInt(college.get("x"));
            int objy = Integer.parseInt(college.get("y"));
            String name = college.get("name");
            String key = college.get("ukey");

            if (key.equals("college-goodricke")) {
                colleges.add(new College(health, damage, name, key, new Vector2(objx, objy), true));
            } else {
                colleges.add(new College(health, damage, name, key, new Vector2(objx, objy), false));
            }
        }
    }

    public Set<College> getColleges() {
        return colleges;
    }

    public Set<NPC> getNpcs() {
        return npcs;
    }

    public List<Objective> getObjectives() {
        return objectives;
    }
}
