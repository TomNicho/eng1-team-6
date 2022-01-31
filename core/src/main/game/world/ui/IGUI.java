package main.game.world.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;

import main.game.world.player.Player;
import main.game.world.player.Objectives.Objective;

public class IGUI {
    private static final int PAGE_OFFSET_X = 10;
    private static final int PAGE_OFFSET_Y = 10;

    private BitmapFont font;
    private LabelStyle basicStyle;
    private Label position, xp, level, gold, score, health, objectiveName, objectiveValue;

    public IGUI() {
        font = new BitmapFont();
        basicStyle = new LabelStyle(font, Color.BLACK);
        position = new Label("POSITION - X:0, Y:0", basicStyle);
        score = new Label("SCORE - 0", basicStyle);
        xp = new Label("XP - 0", basicStyle);
        level = new Label("LEVEL - 0", basicStyle);
        gold = new Label("GOLD - 0", basicStyle);
        health = new Label("HEALTH - 0", basicStyle);
        objectiveName = new Label("CURRENT OBJECTIVE", basicStyle);
        objectiveValue = new Label("CURRENT OBJECTIVE", basicStyle);

        score.setPosition(PAGE_OFFSET_X, Gdx.graphics.getHeight() - PAGE_OFFSET_Y - xp.getHeight());
        xp.setPosition(PAGE_OFFSET_X, Gdx.graphics.getHeight() - PAGE_OFFSET_Y * 2 - xp.getHeight() - score.getHeight());
        level.setPosition(PAGE_OFFSET_X, Gdx.graphics.getHeight() - PAGE_OFFSET_Y * 3 - xp.getHeight() - score.getHeight() - level.getHeight());
        gold.setPosition(PAGE_OFFSET_X, Gdx.graphics.getHeight() - PAGE_OFFSET_Y * 4 - xp.getHeight() - score.getHeight() - level.getHeight() - gold.getHeight());
        
        objectiveValue.setPosition(Gdx.graphics.getWidth() - objectiveValue.getWidth() - PAGE_OFFSET_X, Gdx.graphics.getHeight() * 0.75f);
        objectiveName.setPosition(Gdx.graphics.getWidth() - objectiveName.getWidth() - PAGE_OFFSET_X, Gdx.graphics.getHeight() * 0.75f - PAGE_OFFSET_Y - objectiveValue.getHeight());

        position.setPosition(PAGE_OFFSET_X, PAGE_OFFSET_Y);
        health.setPosition(PAGE_OFFSET_X, PAGE_OFFSET_Y * 2 + position.getHeight());

        objectiveName.setWrap(true);
        objectiveName.setAlignment(Align.center);
        objectiveValue.setAlignment(Align.center);
    }

    public void draw(SpriteBatch batch, Player player) {
        float parentAlpha = 1f;

        setTexts(player);

        position.draw(batch, parentAlpha);
        xp.draw(batch, parentAlpha);
        score.draw(batch, parentAlpha);
        level.draw(batch, parentAlpha);
        gold.draw(batch, parentAlpha);
        health.draw(batch, parentAlpha);
        objectiveName.draw(batch, parentAlpha);
        objectiveValue.draw(batch, parentAlpha);
    }

    private void setTexts(Player player) {
        Vector2 playerPosition = player.getPosition();
        position.setText(String.format("POSITION - X:%s, Y:%s", playerPosition.x, playerPosition.y));

        int[] playerLnX = player.getLevelNXP();
        score.setText(String.format("SCORE - %s", player.getScore()));
        xp.setText(String.format("XP - %s/%s", playerLnX[1], playerLnX[2]));
        level.setText(String.format("LEVEL - %s", playerLnX[0]));
        gold.setText(String.format("GOLD - %s", player.getGold()));
        health.setText(String.format("HEALTH - %s", player.getHealth()));

        Objective cObjective = player.getCurrentObjective();
        if (cObjective != null) {
            objectiveValue.setText(String.format("%s / %s - %sXP", cObjective.getCount(), cObjective.getAmount(), cObjective.getXp()));
            objectiveName.setText(String.format("%s", cObjective.getName()));
        } else {
            objectiveValue.setText("1 / 1 - 0XP");
            objectiveName.setText("NO OBJECTIVE");
        }
    }

    public void dispose() {
        font.dispose();
    }
}
