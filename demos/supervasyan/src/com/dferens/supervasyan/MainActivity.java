package com.dferens.supervasyan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dferens.libgdxes.AndroidActivity;
import com.dferens.libgdxes.Settings;
import com.dferens.libgdxes.levels.LevelParseException;

public class MainActivity extends AndroidActivity {

    @Override
    public void onGameCreate(Settings gameSettings) {
        SVGameManager gameManager = new SVGameManager(gameSettings);
        try {
            gameManager.load();
            this.setGameManager(gameManager);
        } catch (LevelParseException e) {
            Gdx.app.error("Super Vasyan", "Load map error", e);
        }
    }

    @Override
    protected Settings createGameConfiguration() {
        Settings settings = new Settings(false);
        settings.renderVisibleUnits = 30;
        settings.worldGravity.set(0, -10);
        settings.worldPositionIterations = 10;
        settings.worldVelocityIterations = 2;
        settings.systemFont = new BitmapFont(Gdx.files.internal("data/fonts/arial-15.fnt"));
        return settings;
    }

    @Override
    protected void setupAndroidConfiguration(AndroidApplicationConfiguration cfg) {
        cfg.useCompass = false;
        cfg.useGL20 = true;
    }
}