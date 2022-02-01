package main.game.world.player.Objectives;

import java.util.List;

public class ObjectiveManager {
    private List<Objective> objectives;

    public ObjectiveManager(List<Objective> objectives) {
        this.objectives = objectives;
    }

    /**
     * Updates an {@link Objective} with a given key, if there is an {@link Objective} to modify.
     * @param update the {@link Objective} key.
     * @param amount to increase the {@link Objective} count.
     */
    public void updateObjective(String update, int amount) {
        if (objectives.size() != 0) {
            if (objectives.get(0).updateObjective(update, amount)) changeObjectives();
        }
    }

    /**
     * When an objective is completed remove it from the list.
     */
    private void changeObjectives() {
        objectives.remove(0);
    }

    public Objective getCurrentObjective() {
        if (objectives.size() != 0) return objectives.get(0);
        else return null;
    }
}
