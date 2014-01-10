package com.dferens.rocketgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dferens.libgdxes.Settings;
import com.dferens.libgdxes.levels.LevelParseException;

public class GdxGame extends Game {
    @Override
    public void create() {
        Settings settings = new Settings(true);
        settings.renderVisibleUnits = 90;
        settings.worldGravity.set(0, -10);
        settings.worldPositionIterations = 10;
        settings.worldVelocityIterations = 2;
        settings.systemFont = new BitmapFont(Gdx.files.internal("data/fonts/arial-15.fnt"));

        RocketGameManager gameManager = new RocketGameManager(settings);
        try {
            gameManager.load();
            this.setScreen(gameManager);

        } catch (LevelParseException e) {
            Gdx.app.error("RocketGame", "Load map error", e);
        }
    }
}
