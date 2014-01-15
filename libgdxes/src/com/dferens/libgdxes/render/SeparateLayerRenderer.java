package com.dferens.libgdxes.render;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapRenderer;

public interface SeparateLayerRenderer extends MapRenderer {
    void render(MapLayer... layers);
}
