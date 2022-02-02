package main.game.world.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Align;

import main.game.core.Calculations;
import main.game.world.player.Player;
import main.game.world.player.Objectives.Objective;

public class IGUI {
    private static final int PAGE_OFFSET_X = 10;
    private static final int PAGE_OFFSET_Y = 10;

    private Texture compassTexture, needlTexture;
    private Sprite compass, needle;
    private BitmapFont font;
    private LabelStyle basicStyle;
    private Label position, xp, level, gold, score, health, objectiveName, objectiveValue, winner, winnerButton;

    public IGUI() {
        font = new BitmapFont();
        basicStyle = new LabelStyle(font, Color.BLACK);
        position = new Label("POSITION - X:0, Y:0", basicStyle);
        score = new Label("SCORE - 0", basicStyle);
        xp = new Label("XP - 0", basicStyle);
        level = new Label("LEVEL - 0", basicStyle);
        gold = new Label("GOLD - 0", basicStyle);
        health = new Label("HEALTH - 0", basicStyle);
        objectiveName = new Label("CURRENT OBJECTIVE PLACEHOLDER", basicStyle);
        objectiveValue = new Label("CURRENT OBJECTIVE PLACEHOLDER", basicStyle);
        winner = new Label("YOU WIN!", basicStyle);
        winnerButton = new Label("PRESS ESCAPE TO FINISH", basicStyle);

        compassTexture = new Texture(Gdx.files.internal("textures/compass.png"));
        needlTexture = new Texture(Gdx.files.internal("textures/needle.png"));
        compass = new Sprite(compassTexture);
        needle = new Sprite(needlTexture);

        //Set the inital positions of all labels within the in game UI.
        score.setPosition(PAGE_OFFSET_X, Gdx.graphics.getHeight() - PAGE_OFFSET_Y - xp.getHeight());
        xp.setPosition(PAGE_OFFSET_X, Gdx.graphics.getHeight() - PAGE_OFFSET_Y * 2 - xp.getHeight() - score.getHeight());
        level.setPosition(PAGE_OFFSET_X, Gdx.graphics.getHeight() - PAGE_OFFSET_Y * 3 - xp.getHeight() - score.getHeight() - level.getHeight());
        gold.setPosition(PAGE_OFFSET_X, Gdx.graphics.getHeight() - PAGE_OFFSET_Y * 4 - xp.getHeight() - score.getHeight() - level.getHeight() - gold.getHeight());
        
        objectiveName.setHeight(500);

        //Set the compass size and inital positions.
        compass.setSize(160, 160);
        compass.setPosition(Gdx.graphics.getWidth() - compass.getWidth() - PAGE_OFFSET_X, Gdx.graphics.getHeight() - compass.getHeight() - PAGE_OFFSET_Y);
        needle.setPosition(Gdx.graphics.getWidth() - compass.getWidth() - PAGE_OFFSET_X, Gdx.graphics.getHeight() - compass.getHeight() - PAGE_OFFSET_Y);
        
        objectiveValue.setPosition(Gdx.graphics.getWidth() - objectiveValue.getWidth() - compass.getWidth() - PAGE_OFFSET_X * 3, Gdx.graphics.getHeight() - PAGE_OFFSET_Y * 2 - objectiveValue.getHeight());
        objectiveName.setPosition(Gdx.graphics.getWidth()  - objectiveName.getWidth() - compass.getWidth() - PAGE_OFFSET_X * 3, Gdx.graphics.getHeight() - PAGE_OFFSET_Y * 3 - objectiveValue.getHeight() - objectiveName.getHeight());

        position.setPosition(PAGE_OFFSET_X, PAGE_OFFSET_Y);
        health.setPosition(Gdx.graphics.getWidth() / 2 - health.getWidth() / 2, Gdx.graphics.getHeight() / 2 - PAGE_OFFSET_Y * 4 - health.getHeight());

        winner.setFontScale(2f);
        winner.setPosition(Gdx.graphics.getWidth() * 0.5f - winner.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.75f - winner.getHeight() / 2);
        winnerButton.setPosition(Gdx.graphics.getWidth() * 0.5f - winnerButton.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.75f - winner.getHeight() / 2 - PAGE_OFFSET_Y * 2 - winnerButton.getHeight() / 2);

        //Set some label features, like text alignment and wrapping for multiline text.
        objectiveName.setWrap(true);
        objectiveName.setAlignment(Align.top);
        objectiveValue.setAlignment(Align.center);
        winner.setAlignment(Align.center);
        winnerButton.setAlignment(Align.center);
    }

    /**
     * Sets the updated text values and draws each label within the {@link IGUI}.
     * This is based on {@link Player} stats and a {@link SpriteBatch} to draw from.
     * @param batch to render the {@link Label}s to.
     * @param player to update the {@link Label} values.
     */
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
        compass.draw(batch);
        needle.draw(batch);

        if (player.getWon()) {
            winner.draw(batch, parentAlpha);
            winnerButton.draw(batch, parentAlpha);
        }
    }

    /**
     * Sets the text values for each {@link Label} based on updated {@link Player} data. 
     * @param player to get the updated data from.
     */
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
            Vector2 objectivePosition = cObjective.getLocation();

            //Set Needle Rotation
            double angle = Math.atan2(playerPosition.y - objectivePosition.y, playerPosition.x - objectivePosition.x) + Math.PI / 2;
            needle.setRotation((float) Calculations.RadToDeg(angle));
            objectiveValue.setText(String.format("%s / %s - %sXP", cObjective.getCount(), cObjective.getAmount(), cObjective.getXp()));
            objectiveName.setText(String.format("%s", cObjective.getName().replaceAll("%n", "\n")));
        } else {
            needle.setRotation(0);
            objectiveValue.setText("1 / 1 - 0XP");
            objectiveName.setText("NO OBJECTIVE");
        }
    }

    /**
     * To dispose the {@link IGUI}. 
     */
    public void dispose() {
        font.dispose();
        compassTexture.dispose();
        needlTexture.dispose();
    }
}
