package com.dferens.libgdxes.render;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.dferens.libgdxes.entities.Renderable;

import java.util.*;

public class AssetStorage implements AssetContainer {
    private class AliasMap {
        private class MultipleAssetsAvailable extends Exception {
            private MultipleAssetsAvailable(String alias) {
                super(alias);
            }
        }

        private Map<String, String[]> alliases;

        public AliasMap() {
            this.alliases = new Hashtable<String, String[]>();
        }

        public <T extends Object> void put(String key, String value, Class<T> type) {
            String[] values = this.getOrCreateValues(key);
            int aliasIndex = this.getAliasIndex(type);

            if (aliasIndex > -1) {
                values[aliasIndex] = value;
            } else {
                throw new UnsupportedOperationException();
            }
        }

        public <T extends Object> String get(String key, Class<T> type) {
            String[] values = this.getOrCreateValues(key);
            int aliasIndex = this.getAliasIndex(type);
            return values[aliasIndex];
        }
        public String get(String key) throws MultipleAssetsAvailable {
            String resultPath = null;
            String[] values = this.getOrCreateValues(key);
            for (String value : values) {
                if (value != null) {
                    if (resultPath == null) {
                        resultPath = value;
                    } else {
                        throw new MultipleAssetsAvailable(key);
                    }
                }
            }
            return resultPath;
        }

        private String[] getOrCreateValues(String key) {
            String[] values = this.alliases.get(key);
            if (values == null) {
                values = new String[4];
                this.alliases.put(key, values);
            }
            return values;
        }
        private <T extends Object> int getAliasIndex(Class<T> type) {
            int aliasIndex = -1;
            if (type == Texture.class) {
                aliasIndex = 0;
            } else if (type == Pixmap.class) {
                aliasIndex = 1;
            } else if (type == BitmapFont.class) {
                aliasIndex = 2;
            } else if (type == ParticleEffect.class) {
                aliasIndex = 3;
            }
            return aliasIndex;
        }
    }
    public class AssetNotFoundException extends Exception {
        private AssetNotFoundException(String key) {
            super(key);
        }
    }

    private AssetManager assetManager;
    private AliasMap aliasMap;
    private LinkedList<String> lastEntitiesAssets;

    public AssetStorage() {
        FileHandleResolver fileHandleResolver = new InternalFileHandleResolver();
        this.assetManager = new AssetManager(fileHandleResolver);
        this.aliasMap = new AliasMap();
        this.lastEntitiesAssets = new LinkedList<String>();
    }

    public Collection<String> loadEntitiesAssets(Renderable entity) {
        this.lastEntitiesAssets.clear();
        entity.loadAssets(this);
        if (this.lastEntitiesAssets.size() > 0) {
            return this.lastEntitiesAssets;
        } else {
            return null;
        }
    }

    public void unloadAsset(String pathOrAlias) {
        try {
            String path = this.aliasMap.get(pathOrAlias);
            if (path == null) path = pathOrAlias;
            this.assetManager.unload(path);
        } catch (AliasMap.MultipleAssetsAvailable e) {
            throw new GdxRuntimeException("Asset manager contains multiple assets with same alias");
        }
    }
    public void unloadAssets(Iterable<String> pathesOrAliases) {
        for (String pathOrAlias : pathesOrAliases) this.unloadAsset(pathOrAlias);
    }
    public void unloadAssets(String[] pathesOrAliases) {
        for (String pathOrAlias : pathesOrAliases) this.unloadAsset(pathOrAlias);
    }
    public void finishLoading() {
        this.assetManager.finishLoading();
    }

    @Override
    public <T extends Object> void load(String alias, String filePath, Class<T> type) {
        this.assetManager.load(filePath, type);
        if (alias != null) this.aliasMap.put(alias, filePath, type);
        this.lastEntitiesAssets.add(filePath);
    }
    @Override
    public <T extends Object> void load(String filePath, Class<T> type) {
        this.load(null, filePath, type);
    }
    @Override
    public <T extends Object>  T get(String pathOrAlias, Class<T> type) {
        String path = this.aliasMap.get(pathOrAlias, type);
        if (path == null) path = pathOrAlias;
        T asset = this.assetManager.get(path, type);
        return asset;
    }
    @Override
    public Object get(String pathOrAlias) throws AssetNotFoundException {
        try {
            String path = this.aliasMap.get(pathOrAlias);
            if (path != null) {
                return this.assetManager.get(path);
            } else {
                throw new AssetNotFoundException(pathOrAlias);
            }

        } catch (AliasMap.MultipleAssetsAvailable e) {
            throw new GdxRuntimeException("Asset manager contains multiple assets with same alias");
        }
    }
}
