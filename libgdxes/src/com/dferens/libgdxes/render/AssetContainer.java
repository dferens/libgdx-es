package com.dferens.libgdxes.render;

/**
 * Allows to load & get assets only
 */
public interface AssetContainer {
    <T extends Object> void load(String alias, String filePath, Class<T> type);

    <T extends Object> void load(String filePath, Class<T> type);

    <T extends Object>  T get(String pathOrAlias, Class<T> type);

    Object get(String pathOrAlias) throws AssetStorage.AssetNotFoundException;
}
