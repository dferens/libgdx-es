package com.dferens.core;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Disposable;

public abstract class TmxLevel implements Level, Disposable {
    protected static final TmxMapLoader loader = new TmxMapLoader();

    protected final TiledMap tiledMap;
    protected final String levelFilePath;

    public TmxLevel(String levelFilePath) {
        this.levelFilePath = levelFilePath;
        this.tiledMap = loader.load(this.levelFilePath);
    }

    public TiledMap getTiledMap() { return this.tiledMap; }

    @Override
    public void dispose() {
        tiledMap.dispose();
    }
}
