package main.game.world.player.Stats;

public class Leveler {
    public static final int MAX_XP = 100000;
    public static final int LEVEL_CONSTANT = 1;

    /**
     * Determines the current level based on an amount of xp.
     * @param xp amount.
     * @return the level.
     */
    public static int LevelFunction(int xp) {
        return (int) Math.sqrt((xp + LEVEL_CONSTANT) / LEVEL_CONSTANT);
    }

    /**
     * Determines the amount of xp from a given level. 
     * @param level given.
     * @return the amount of xp.
     */
    public static int XPFunction(int level) {
        return (int) Math.pow(level, 2) * LEVEL_CONSTANT - LEVEL_CONSTANT;
    }

    private int xp;

    public Leveler(int xp) {
        this.xp = xp;
    }

    /**
     * Increase the amount of xp.
     * @param amount of xp.
     * @return if a level up has occured.
     */
    public boolean increase(int amount) {
        int currentLevel = LevelFunction(xp);

        if (xp + amount > MAX_XP) xp = MAX_XP;
        else xp += amount;

        //Checks if the level before is less than the level after the given xp.
        if (currentLevel < LevelFunction(xp)) return true;
        else return false;
    }

    /**
     * Gets the current level, and the current xp, and the amount of xp needed for the next level.
     * @return an int array length 3 : {level, xp, nextLevel}.
     */
    public int[] getLevelNXP() {
        int currentLevel = LevelFunction(xp);
        int nextLevelXP = XPFunction(currentLevel + 1);
        return new int[] {currentLevel, xp, nextLevelXP};
    }

    public int getLevel() {
        return LevelFunction(xp);
    }

    public int getXp() {
        return xp;
    }
}
