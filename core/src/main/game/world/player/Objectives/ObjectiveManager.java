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
     * @return the xp from the current {@link Objective}, if this {@link Objective} was completed.
     */
    public int updateObjective(String update, int amount) {
        if (objectives.size() != 0) {
            int potentialXp = objectives.get(0).getXp();
            if (objectives.get(0).updateObjective(update, amount)) {
                changeObjectives();
                return potentialXp;
            }
        }

        return 0;
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
