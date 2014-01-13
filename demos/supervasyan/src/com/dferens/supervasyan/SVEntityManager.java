package com.dferens.supervasyan;

import com.dferens.libgdxes.GameManager;
import com.dferens.libgdxes.GameWorld;
import com.dferens.libgdxes.entities.utils.CameramanEntity;
import com.dferens.libgdxes.levels.Level;
import com.dferens.libgdxes.levels.LevelEntityManager;
import com.dferens.libgdxes.render.ScaledOrthogonalTiledMapRenderer;
import com.dferens.libgdxes.render.SeparateLayerRenderer;
import com.dferens.supervasyan.entities.VasyanEntity;

public class SVEntityManager extends LevelEntityManager {
    private VasyanEntity player;
    private CameramanEntity cameraman;
    private ScaledOrthogonalTiledMapRenderer mapRenderer;

    public VasyanEntity getPlayer() { return this.player; }
    public SeparateLayerRenderer getMapRenderer() { return this.mapRenderer; }

    public SVEntityManager(GameManager gameManager, GameWorld world) {
        super(gameManager, world);
        this.cameraman = new CameramanEntity();
        this.mapRenderer = new ScaledOrthogonalTiledMapRenderer();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    protected void beforeLevelEntitiesClear() {
        this.player = null;
    }

    @Override
    protected void beforeNewLevelEntitiesLoad(Level newLevel) {
        SVLevel level = (SVLevel) newLevel;
        // Invalidate map mapRenderer
        this.mapRenderer.setMap(level.getTiledMap());
        this.mapRenderer.setUnitScale(level.calculateMapScale());
    }

    @Override
    protected void afterNewLevelEntitiesLoad() {
        // Creating player
        SVLevel level = (SVLevel) this.currentLevel;
        this.player = new VasyanEntity(level.getSpawnPoint());
        this.cameraman.setEntity(this.player);
        this.createEntities(this.player, this.cameraman);
    }
}
