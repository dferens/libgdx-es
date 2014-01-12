package com.dferens.libgdxes.levels;

import com.dferens.libgdxes.EntityManager;
import com.dferens.libgdxes.GameManager;
import com.dferens.libgdxes.GameWorld;

public abstract class LevelEntityManager extends EntityManager {
    protected Level currentLevel;

    public final Level getLevel() { return this.currentLevel; }

    public LevelEntityManager(GameManager gameManager, GameWorld world) {
        super(gameManager, world);
    }

    public final void switchLevel(Level newLevel) throws LevelParseException {
        this.unloadCurrentLevel();
        this.loadLevel(newLevel);
    }
    protected final void unloadCurrentLevel() {
        if (this.currentLevel != null) {
            this.beforeLevelEntitiesClear();
            this.clear();
            this.beforeCurrentLevelUtilize();
            this.currentLevel.dispose();
            this.currentLevel = null;
        }
    }
    protected final void loadLevel(Level level) throws LevelParseException {
        this.beforeNewLevelEntitiesLoad(level);
        this.currentLevel = level;
        this.currentLevel.loadSettings(gameManager);
        this.currentLevel.loadEntities(this);
        this.afterNewLevelEntitiesLoad();
    }

    protected void beforeNewLevelEntitiesLoad(Level newLevel) { }
    protected void afterNewLevelEntitiesLoad() { }
    protected void beforeLevelEntitiesClear() { }
    protected void beforeCurrentLevelUtilize() { }
}
