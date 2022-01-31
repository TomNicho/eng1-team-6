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

    public void increaseGold(int amount) {
        gold.collect(amount);
    }

    public void increaseXP(int amount) {
        if (leveler.increase(amount)) levelUP();
    }

    private void levelUP() {
        int level = leveler.getLevel();
        health = (int) (HEALTH_MULTI * level * initialHealth + initialHealth);
        damage = (int) (DAMAGE_MULTI * level * initialDamage + initialDamage);
    }

    public void increaseScore(int amount) {
        if (this.score + amount > MAX_SCORE) this.score = MAX_SCORE;
        else this.score += amount;
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

    public int getMaxHealth() {
        return (int) HEALTH_MULTI * leveler.getLevel() * initialHealth;
    }

    public boolean takeDamage(int damage) {
        this.health -= damage;
        if (health <= 0) return true;
        else return false;
    }
}
