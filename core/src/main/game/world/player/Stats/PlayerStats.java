package main.game.world.player.Stats;

public class PlayerStats {
    public static final float HEALTH_MULTI = 0.1f;
    public static final float DAMAGE_MULTI = 0.1f;
    public static final int MAX_SCORE = 1000000;

    private int initialHealth;
    private int initialDamage;

    private int health;
    private int damage;
    private int score;

    private Gold gold;
    private Leveler leveler;

    public PlayerStats(int health, int damage, int xp, int gold) {
        this.initialDamage = damage;
        this.initialHealth = health;
        this.damage = damage;
        this.health = health;

        this.gold = new Gold(gold);
        this.leveler = new Leveler(xp);
    }

    /**
     * Increases the gold value by an amount within the {@link Gold} object;
     * @param amount to increase.
     */
    public void increaseGold(int amount) {
        gold.collect(amount);
    }

     /**
     * Increases the xp value by an amount, within the {@link Leveler} object;
     * @param amount to increase.
     */
    public void increaseXP(int amount) {
        if (leveler.increase(amount)) levelUP();
    }

    /**
     * Modifies the health and damage values when a leveler occures. Determined by the {@link Leveler}.
     */
    private void levelUP() {
        int level = leveler.getLevel();
        health = (int) (HEALTH_MULTI * level * initialHealth + initialHealth);
        damage = (int) (DAMAGE_MULTI * level * initialDamage + initialDamage);
    }

    /**
     * Increase the score value by an amount;
     * @param amount to increase.
     */
    public void increaseScore(int amount) {
        if (this.score + amount > MAX_SCORE) this.score = MAX_SCORE;
        else this.score += amount;
    }

    /**
     * Gets the max health based on the current level from the {@link Leveler}.
     * @return the max health.
     */
    public int getMaxHealth() {
        return (int) HEALTH_MULTI * leveler.getLevel() * initialHealth;
    }

    /**
     * Take damage by an amount.
     * @param damage amount.
     * @return whether the health is < 0.
     */
    public boolean takeDamage(int damage) {
        this.health -= damage;
        if (health <= 0) return true;
        else return false;
    }

    public int getDamage() {
        return damage;
    }

    public int getHealth() {
        return health;
    }

    public int getGold() {
        return gold.getGold();
    }

    public int getScore() {
        return score;
    }

    public int[] getLevelNXP() {
        return leveler.getLevelNXP();
    }
}
