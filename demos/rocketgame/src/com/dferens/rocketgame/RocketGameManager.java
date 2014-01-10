package com.dferens.rocketgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dferens.libgdxes.*;
import com.dferens.libgdxes.levels.LevelParseException;

public class RocketGameManager extends GameManager<Settings,
                                                   RocketGameEntityManager,
                                                   RenderScope,
                                                   RocketGameInputScope > {

    public void load() throws LevelParseException {
        this.getEntities().switchLevel(new RocketGameLevel("data/levels/demo.tmx"));
    }


    @Override
    protected void setupComponents(GameWorld world) {
        this.settings = new Settings();
        this.settings.renderVisibleUnits = 30;
        this.settings.worldGravity.set(0, -10);
        this.settings.worldPositionIterations = 10;
        this.settings.worldVelocityIterations = 2;
        this.settings.systemFont = new BitmapFont(Gdx.files.internal("data/fonts/arial-15.fnt"));
        this.entities = new RocketGameEntityManager(this, world);
        this.renderScope = new RenderScope(this);
        this.inputScope = new RocketGameInputScope();
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
