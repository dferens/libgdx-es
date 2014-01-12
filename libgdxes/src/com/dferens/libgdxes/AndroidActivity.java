package com.dferens.libgdxes;

import android.os.Bundle;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public abstract class AndroidActivity extends AndroidApplication {
    class LibgdxGame extends Game {
        private final AndroidActivity activity;
        private Settings gameSettings;

        public LibgdxGame(AndroidActivity activity) {
            this.activity = activity;
        }

        @Override
        public final void create() {
            this.gameSettings = activity.createGameConfiguration();
            this.activity.onGameCreate(this.gameSettings);
        }
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        this.setupAndroidConfiguration(cfg);
        initialize(new LibgdxGame(this), cfg);
    }

    public void setGameManager(GameManager gameManager) {
        ((LibgdxGame)this.listener).setScreen(gameManager);
    }

    protected abstract void setupAndroidConfiguration(AndroidApplicationConfiguration cfg);
    protected abstract Settings createGameConfiguration();
    public abstract void onGameCreate(Settings gameSettings);
}
