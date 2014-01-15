package com.dferens.supervasyan;

import com.dferens.libgdxes.GameManager;
import com.dferens.libgdxes.GameWorld;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.Settings;
import com.dferens.libgdxes.levels.LevelParseException;

public class SVGameManager extends GameManager<Settings,
        SVEntityManager,
                                                   RenderScope,
        SVInputScope> {

    public SVGameManager(Settings settings) { super(settings); }

    @Override
    protected void setupComponents(GameWorld world) {
        this.entities = new SVEntityManager(this, world);
        this.renderScope = new RenderScope(this);
        this.inputScope = new SVInputScope();

        this.entities.initialize();
        this.renderScope.initialize();
        this.inputScope.initialize();
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void load() throws LevelParseException {
        this.getEntities().switchLevel(new SVLevel("data/levels/level1.tmx"));
    }
}
