package main.game.core;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.*;

import main.game.world.content.College;
import main.game.world.content.NPC;

public class XMLLoader {
    private File xmlFile;
    private Set<College> colleges;
    private Set<NPC> npcs;

    public XMLLoader(String filePath) {
        this.xmlFile = new File(filePath);
        this.npcs = new HashSet<>();
        this.colleges = new HashSet<>();
    }

    public void load() throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

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

    public Set<College> getColleges() {
        return colleges;
    }

    public Set<NPC> getNpcs() {
        return npcs;
    }
}
