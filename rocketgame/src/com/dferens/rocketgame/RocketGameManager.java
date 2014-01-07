package com.dferens.rocketgame;

import com.dferens.core.*;
import com.dferens.core.levels.LevelParseException;

public class RocketGameManager extends GameManager {

    @Override
    public RocketGameEntityManager getEntityManager() {
        return (RocketGameEntityManager) this.entityManager;
    }

    public RocketGameManager() { }

    public void load() throws LevelParseException {
        this.getEntityManager().switchLevel(new RocketGameLevel("levels/demo.tmx"));
    }

    @Override
    protected Settings createSettings() {
        Settings settings = new Settings();
        settings.renderVisibleUnits = 20;
        settings.worldGravity.set(0, -10);
        settings.worldPositionIterations = 10;
        settings.worldVelocityIterations = 2;
        return settings;
    }

    @Override
    public InputScope createUIManager() {
        return new RockeGameInputScope();
    }

    @Override
    public EntityManager createEntityManager(SettingsProvider configProvider, GameWorld world) {
        return new RocketGameEntityManager(this, world);
    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#pause()
     */
    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * @see com.badlogic.gdx.ApplicationListener#resume()
     */
    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
