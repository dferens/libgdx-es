package com.dferens.libgdxes.render.entities;

import com.badlogic.gdx.maps.MapLayer;
import com.dferens.libgdxes.entities.Renderable;

public abstract class LevelLayerRendererEntity<T extends MapLayer> implements Renderable {
    protected final T layer;
    protected final int renderPriority;

    public LevelLayerRendererEntity(T layer, int renderPriority) {
        this.layer = layer;
        this.renderPriority = renderPriority;
    }

    @Override
    public int getRenderPriority() { return this.renderPriority; }
}
