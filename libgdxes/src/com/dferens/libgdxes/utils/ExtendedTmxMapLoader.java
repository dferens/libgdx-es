package com.dferens.libgdxes.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOError;

import static com.badlogic.gdx.utils.XmlReader.Element;

/**
 * Knows how to parse image layers.
 */
public class ExtendedTmxMapLoader extends TmxMapLoader {
    @Override
    protected TiledMap loadTilemap(Element root, FileHandle tmxFile, ImageResolver imageResolver) {
        TiledMap map =  super.loadTilemap(root, tmxFile, imageResolver);
        Array<Element> elements = root.getChildrenByName("imagelayer");
        for (Element element : elements) loadImageLayer(map, element, tmxFile, imageResolver);
        return map;
    }

    protected void loadImageLayer(TiledMap map, Element element, FileHandle tmxFile, ImageResolver imageResolver) {
        String name = element.getAttribute("name", null);
        Element imageElement = element.getChildByName("image");
        if (imageElement != null) {
            String imagePath = imageElement.getAttribute("source");
            try {
                FileHandle image = getRelativeFileHandle(tmxFile, imagePath);
                TextureRegion texture = imageResolver.getImage(image.path());
                TiledMapTImageLayer layer = new TiledMapTImageLayer(texture);
                map.getLayers().add(layer);
            }
            catch (IOError e) {
                throw new GdxRuntimeException("Error parsing image layer.");
            }
        }
    }
}
