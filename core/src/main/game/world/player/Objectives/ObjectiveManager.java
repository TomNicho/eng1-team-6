package main.game.world.player.Objectives;

import java.util.List;

public class ObjectiveManager {
    private List<Objective> objectives;

    public ObjectiveManager(List<Objective> objectives) {
        this.objectives = objectives;
    }

    public Objective getCurrentObjective() {
        if (objectives.size() != 0) return objectives.get(0);
        else return null;
    }

    public void updateObjective(String update, int amount) {
        if (objectives.size() != 0) {
            if (objectives.get(0).updateObjective(update, amount)) changeObjectives();
        }
    }

    private void changeObjectives() {
        objectives.remove(0);
    }
}
