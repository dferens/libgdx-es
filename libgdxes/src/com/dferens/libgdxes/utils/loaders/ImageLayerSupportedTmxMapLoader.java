package com.dferens.libgdxes.utils.loaders;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import com.dferens.libgdxes.render.TiledMapImageLayer;

import java.io.IOError;

public class ImageLayerSupportedTmxMapLoader extends ExtendableTmxMapLoader {

    protected void loadImageLayer(TiledMap map, XmlReader.Element element, FileHandle tmxFile, ImageResolver imageResolver) {
        String name = element.getAttribute("name", null);
        XmlReader.Element imageElement = element.getChildByName("image");
        if (imageElement != null) {
            String imagePath = imageElement.getAttribute("source");
            try {
                FileHandle image = getRelativeFileHandle(tmxFile, imagePath);
                TextureRegion texture = imageResolver.getImage(image.path());
                TiledMapImageLayer layer = new TiledMapImageLayer(texture);
                map.getLayers().add(layer);

                XmlReader.Element properties = element.getChildByName("properties");
                if (properties != null) {
                    loadProperties(layer.getProperties(), properties);
                }
            }
            catch (IOError e) {
                throw new GdxRuntimeException("Error parsing image layer.");
            }
        }
    }

    @Override
    protected void loadTmxObject(TiledMap map, XmlReader.Element element, FileHandle tmxFile, ImageResolver imageResolver) {
        super.loadTmxObject(map, element, tmxFile, imageResolver);
        String name = element.getName();

        if (name.equals("imagelayer")) {
            loadImageLayer(map, element, tmxFile, imageResolver);
            return;
        }
    }
}
