package com.dferens.core.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.reflect.ClassReflection;

public abstract class TmxLevel extends Level {
    protected static final TmxMapLoader loader = new TmxMapLoader();

    protected final TiledMap tiledMap;

    public TmxLevel(String levelFilePath) {
        super(levelFilePath);
        Texture.setEnforcePotImages(false);
        this.tiledMap = loader.load(this.levelPath);
    }

    public TiledMap getTiledMap() { return this.tiledMap; }

    protected MapLayer getMapLayer(String layerName) {
        return this.tiledMap.getLayers().get(layerName);
    }
    protected <T extends MapLayer> T getMapLayer(String layerName, Class<T> layerClass) {
        MapLayer layer = this.getMapLayer(layerName);
        if (ClassReflection.isInstance(layerClass, layer)) return (T)layer;
        else return null;
    }
    protected abstract TiledMapTileLayer getMainLayer();

    @Override
    public float getWidth() { return this.getMainLayer().getWidth(); }

    @Override
    public float getHeight() { return this.getMainLayer().getHeight(); }

    @Override
    public void dispose() { tiledMap.dispose(); }
}
