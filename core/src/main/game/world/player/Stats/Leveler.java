package main.game.world.player.Stats;

public class Leveler {
    public static final int MAX_XP = 100000;
    public static final int LEVEL_CONSTANT = 1;

    public static int LevelFunction(int xp) {
        return (int) Math.sqrt((xp + LEVEL_CONSTANT) / LEVEL_CONSTANT);
    }

    public static int XPFunction(int level) {
        return (int) Math.pow(level, 2) * LEVEL_CONSTANT - LEVEL_CONSTANT;
    }

    private int xp;

    public Leveler(int xp) {
        this.xp = xp;
    }

    public boolean increase(int amount) {
        int currentLevel = LevelFunction(xp);

        if (xp + amount > MAX_XP) xp = MAX_XP;
        else xp += amount;

        if (currentLevel < LevelFunction(xp)) return true;
        else return false;
    }

    public int getLevel() {
        return LevelFunction(xp);
    }

    public int[] getLevelNXP() {
        int currentLevel = LevelFunction(xp);
        int nextLevelXP = XPFunction(currentLevel + 1);
        return new int[] {currentLevel, xp, nextLevelXP};
    }

    public int getXp() {
        return xp;
    }
}
