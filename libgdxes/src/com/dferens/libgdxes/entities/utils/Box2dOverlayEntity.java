package com.dferens.libgdxes.entities.utils;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.GameWorld;
import com.dferens.libgdxes.RenderScope;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.utils.StandardPriorities;

public class Box2dOverlayEntity implements Renderable {
    private static Box2DDebugRenderer defaultDebugRenderer = new Box2DDebugRenderer();

    private Box2DDebugRenderer renderer;

    public Box2dOverlayEntity(Box2DDebugRenderer renderer) { this.renderer = renderer; }
    public Box2dOverlayEntity() { this(defaultDebugRenderer); }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderer) {
        GameWorld world = context.getEntityManager().getWorld();
        renderer.draw(world, this.renderer);
    }

    @Override
    public int getRenderPriority() { return StandardPriorities.BOX2D_DEBUG_OVERLAY; }
}
