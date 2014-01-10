package com.dferens.rocketgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dferens.libgdxes.*;
import com.dferens.libgdxes.levels.LevelParseException;

public class RocketGameManager extends GameManager {

    @Override
    public RocketGameEntityManager getEntities() { return (RocketGameEntityManager) super.getEntities(); }

    public void load() throws LevelParseException {
        this.getEntities().switchLevel(new RocketGameLevel("data/levels/demo.tmx"));
    }

    @Override
    protected Settings createSettings() {
        Settings settings = new Settings();
        settings.renderVisibleUnits = 30;
        settings.worldGravity.set(0, -10);
        settings.worldPositionIterations = 10;
        settings.worldVelocityIterations = 2;
        settings.systemFont = new BitmapFont(Gdx.files.internal("data/fonts/arial-15.fnt"));
        return settings;
    }
    @Override
    public InputScope getUIManager() {
        return new RocketGameInputScope();
    }
    @Override
    public EntityManager createEntityManager(SettingsProvider configProvider, GameWorld world) {
        return new RocketGameEntityManager(this, world);
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
