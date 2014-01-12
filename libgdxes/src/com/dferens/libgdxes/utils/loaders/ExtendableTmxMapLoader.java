package com.dferens.libgdxes.utils.loaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.utils.XmlReader.Element;

/**
 * Knows how to parse image layers.
 */
public class ExtendableTmxMapLoader extends TmxMapLoader {
    @Override
    protected TiledMap loadTilemap(Element root, FileHandle tmxFile, ImageResolver imageResolver) {
        TiledMap map = new TiledMap();

        String mapOrientation = root.getAttribute("orientation", null);
        int mapWidth = root.getIntAttribute("width", 0);
        int mapHeight = root.getIntAttribute("height", 0);
        int tileWidth = root.getIntAttribute("tilewidth", 0);
        int tileHeight = root.getIntAttribute("tileheight", 0);
        String mapBackgroundColor = root.getAttribute("backgroundcolor", null);

        MapProperties mapProperties = map.getProperties();
        if (mapOrientation != null) {
            mapProperties.put("orientation", mapOrientation);
        }
        mapProperties.put("width", mapWidth);
        mapProperties.put("height", mapHeight);
        mapProperties.put("tilewidth", tileWidth);
        mapProperties.put("tileheight", tileHeight);
        if (mapBackgroundColor != null) {
            mapProperties.put("backgroundcolor", mapBackgroundColor);
        }
        mapWidthInPixels = mapWidth * tileWidth;
        mapHeightInPixels = mapHeight * tileHeight;

        Element properties = root.getChildByName("properties");
        if (properties != null) {
            loadProperties(map.getProperties(), properties);
        }
        Array<Element> tilesets = root.getChildrenByName("tileset");
        for (Element element : tilesets) {
            loadTileSet(map, element, tmxFile, imageResolver);
            root.removeChild(element);
        }
        for (int i = 0, j = root.getChildCount(); i < j; i++) {
            Element element = root.getChild(i);
            this.loadTmxObject(map, element, tmxFile, imageResolver);
        }
        return map;
    }

    protected void loadTmxObject(TiledMap map, Element element, FileHandle tmxFile, ImageResolver imageResolver) {
        String name = element.getName();

        if (name.equals("layer")) {
            loadTileLayer(map, element);
            return;
        } else if (name.equals("objectgroup")) {
            loadObjectGroup(map, element);
            return;
        }
    }
}
