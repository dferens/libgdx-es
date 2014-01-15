package com.dferens.libgdxes.levels;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.utils.Disposable;
import com.dferens.libgdxes.EntityManager;
import com.dferens.libgdxes.GameManager;

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

    public abstract void loadSettings(GameManager gameManager);

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

    public abstract MapLayers getBackgroundLayers();
    public abstract MapLayer getMainLayer();
    public abstract MapLayers getForegroundLayers();
    public abstract MapLayers getLayers();

    @Override
    public void dispose() { }
}
