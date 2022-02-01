package main.game.world.player.Stats;

public class Gold {
    public static final int GOLD_MAX = 10000;

    private int gold, totalGold;

    public Gold(int gold) {
        this.gold = gold;
        this.totalGold = gold;
    }

    public int getGold() {
        return gold;
    }

    public int getTotalGold() {
        return totalGold;
    }

    /**
     * Increase the gold value by an amount.
     * @param amount of gold.
     */
    public void collect(int amount) {
        this.totalGold += amount;
        if (this.gold + amount > GOLD_MAX) this.gold = GOLD_MAX;
        else this.gold += amount;
    }

    // public boolean spend(int amount) {
    //     if (this.gold - amount >= 0) return true;
    //     else return false; 
    // }
}
