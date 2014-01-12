package com.dferens.supervasyan;

import com.dferens.libgdxes.GameManager;
import com.dferens.libgdxes.GameWorld;
import com.dferens.libgdxes.levels.LevelEntityManager;
import com.dferens.libgdxes.levels.TmxLevel;
import com.dferens.libgdxes.entities.utils.CameramanEntity;
import com.dferens.supervasyan.entities.VasyanEntity;

public class SVEntityManager extends LevelEntityManager {
    private VasyanEntity player;
    private CameramanEntity cameraman;
    private RocketGameLevelRenderer levelRenderer;

    public VasyanEntity getPlayer() { return this.player; }

    public SVEntityManager(GameManager gameManager, GameWorld world) {
        super(gameManager, world);
        this.cameraman = new CameramanEntity();
    }

    @Override
    public void initialize() {
        super.initialize();
        this.levelRenderer = new RocketGameLevelRenderer(this.gameManager.getRenderScope().createMapRenderer(null, 1));
    }

    @Override
    protected void beforeLevelEntitiesClear() {
        this.player.dispose();
        this.player = null;
    }

    @Override
    protected void afterNewLevelEntitiesLoad() {
        RocketGameLevel level = (RocketGameLevel) this.currentLevel;
        // Creating player
        this.player = new VasyanEntity(level.getSpawnPoint());
        this.createEntity(this.player);
        // Triggering level render
        this.levelRenderer.onLevelChanged((TmxLevel) this.currentLevel);
        this.createEntity(levelRenderer.getForegroundRendererEntity());
        this.createEntity(levelRenderer.getBackgroundRendererEntity());
        this.createEntity(this.cameraman);
        this.cameraman.setEntity(this.player);
    }
}
