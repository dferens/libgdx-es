package com.dferens.core.levels;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.utils.Disposable;
import com.dferens.core.EntityManager;

public abstract class Level implements Disposable{
    protected final String levelPath;

    public Level(String levelPath) {
        this.levelPath = levelPath;
    }

    /**
     * Reads level data from path.
     *
     * @throws LevelParseException on parse errors.
     */
    public abstract void parse() throws LevelParseException;

    /**
     * Creates entities within game with given entity manager;
     *
     * @param entityManager given entity manager
     */
    public abstract void loadEntities(EntityManager entityManager);

    /**
     * Returns level width.
     *
     * @return width in units.
     */
    public abstract float getWidth();

    /**
     * Returns level height.
     *
     * @return height in units.
     */
    public abstract float getHeight();

    public abstract MapLayer getMainLayer();

    @Override
    public void dispose() { }
}
