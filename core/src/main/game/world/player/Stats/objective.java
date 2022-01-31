package main.game.world.player.Stats;

public class objective {
    private int objectiveCount = 1; 
    public String currentObjective = "kill an npc";

    public String getObjective() {
        return currentObjective;
    }

    public void check(String update){
        if(update.equals("npc") && objectiveCount == 1){
            currentObjective = "kill a college";
            objectiveCount++;
        }
        if(update.equals("college") && objectiveCount == 2){
            currentObjective = "you win";
            objectiveCount++;
        }
    }
}
