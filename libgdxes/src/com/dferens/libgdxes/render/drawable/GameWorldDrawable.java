package com.dferens.libgdxes.render.drawable;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.dferens.libgdxes.GameWorld;
import com.dferens.libgdxes.render.Position;
import com.dferens.libgdxes.render.RenderScope;

public class GameWorldDrawable extends RasterDrawable {
    private final GameWorld world;
    private final Box2DDebugRenderer debugRenderer;
    private final RenderScope renderScope;

    public GameWorldDrawable(GameWorld world, Box2DDebugRenderer debugRenderer, RenderScope renderScope) {
        this.world = world;
        this.debugRenderer = debugRenderer;
        this.renderScope = renderScope;
    }

    @Override
    public void execute(SpriteBatch renderObject, float deltaTime,
                        float x, float y, Position position,
                        float width, float height, float scaleX, float scaleY,
                        float angle) {
        // Pausing outer batch
        renderScope.commitDraw();
        world.draw(debugRenderer, renderScope.getProjectionMatrix());
        renderScope.beginDraw();
    }
}
