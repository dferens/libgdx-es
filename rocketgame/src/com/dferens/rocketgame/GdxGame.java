package com.dferens.rocketgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.dferens.core.levels.LevelParseException;

public class GdxGame extends Game {
    @Override
    public void create() {
        RocketGameManager gameManager = new RocketGameManager();
        try {
            gameManager.load();
            this.setScreen(gameManager);
        } catch (LevelParseException e) {
            Gdx.app.error("RocketGame", "Load map error", e);
        }
    }
}
