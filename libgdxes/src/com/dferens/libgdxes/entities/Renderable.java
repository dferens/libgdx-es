package com.dferens.libgdxes.entities;

import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.render.AssetContainer;
import com.dferens.libgdxes.render.RenderScope;

public interface Renderable extends Entity {
    void render(float deltaTime, Context context, RenderScope renderer);
    void loadAssets(AssetContainer assetContainer);
    int getRenderPriority();
}
