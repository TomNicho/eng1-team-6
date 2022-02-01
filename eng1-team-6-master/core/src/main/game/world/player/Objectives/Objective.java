package main.game.world.player.Objectives;

import com.badlogic.gdx.math.Vector2;

public class Objective {
    private String name, uKey;
    private Vector2 location;
    private int count, amount, xp;

    public Objective(String name, String uKey, int amount, int xp, Vector2 location) {
        this.name = name;
        this.uKey = uKey;
        this.location = location;
        this.amount = amount;
        this.xp = xp;
        this.count = 0;
    }

    /**
     * Updates the current {@link Objective} based of a key and increases the {@link Objective} count.
     * @param update the {@link Objective} key.
     * @param a An amount to increase the {@link Objective} count by.
     * @return if the {@link Objective} has been completed.
     */
    public boolean updateObjective(String update, int a) {
        if (update.equals(uKey)) count += a;
        if (count >= amount) return true;
        else return false;
    }

    public int getCount() {
        return count;
    }

    public Vector2 getLocation() {
        return location;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getuKey() {
        return uKey;
    }

    public int getXp() {
        return xp;
    }
}
