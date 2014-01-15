package com.dferens.libgdxes.render.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.render.AssetContainer;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.render.SeparateLayerRenderer;

public class TileLayerRendererEntity extends LevelLayerRendererEntity<TiledMapTileLayer>{

    protected final SeparateLayerRenderer mapRenderer;

    public TileLayerRendererEntity(TiledMapTileLayer layer, int renderPriority, SeparateLayerRenderer mapRenderer) {
        // TODO: move mapRenderer to render scope
        super(layer, renderPriority);
        this.mapRenderer = mapRenderer;
    }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderer) {
        renderer.synchronise(this.mapRenderer);
        renderer.commitDraw(false);
        this.mapRenderer.render(this.layer);
        renderer.beginDraw();
    }

    @Override
    public void loadAssets(AssetContainer assetContainer) { }
}
