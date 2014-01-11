package com.dferens.libgdxes.entities.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.entities.Renderable;
import com.dferens.libgdxes.render.Position;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.utils.StandardPriorities;

public class FPSLoggerEntity implements Renderable {
    protected final BitmapFont renderFont;
    protected final int updatePerMilliseconds;
    protected long startTime;
    protected String cachedFpsValue;

    public FPSLoggerEntity(int updatePerMilliseconds, BitmapFont renderFont) {
        this.startTime = System.currentTimeMillis();
        this.updatePerMilliseconds = updatePerMilliseconds;
        this.renderFont = renderFont;

        this.invalidate();
    }
    public FPSLoggerEntity(BitmapFont renderFont) {
        this(100, renderFont);
    }

    private boolean shouldRender() { return (System.currentTimeMillis() - this.startTime > this.updatePerMilliseconds); }
    private void invalidate() {
        this.cachedFpsValue = "FPS: " + Integer.toString(Gdx.graphics.getFramesPerSecond());
        this.startTime = System.currentTimeMillis();
    }

    @Override
    public void render(float deltaTime, Context context, RenderScope renderScope) {
        if (this.shouldRender()) this.invalidate();
        renderScope.draw(this.renderFont, this.cachedFpsValue)
                   .screenCoords(Gdx.graphics.getWidth() - 20, Gdx.graphics.getHeight() - 20)
                   .startAt(Position.CENTER_RIGHT)
                   .commit();
    }

    @Override
    public int getRenderPriority() { return StandardPriorities.FPS_LOGGER; }
}