package com.dferens.libgdxes.entities.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.dferens.libgdxes.Context;
import com.dferens.libgdxes.RenderScope;
import com.dferens.libgdxes.entities.Renderable;
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
    public void render(float deltaTime, Context context, RenderScope renderer) {
        if (this.shouldRender()) this.invalidate();
        BitmapFont.TextBounds bounds = this.renderFont.getBounds(this.cachedFpsValue);
        Vector2 renderPosition = new Vector2(Gdx.graphics.getWidth() - 2*bounds.width,
                                             Gdx.graphics.getHeight() - 2*bounds.height);
        renderer.drawDirectly(this.renderFont, this.cachedFpsValue, renderPosition);
    }

    @Override
    public int getRenderPriority() { return StandardPriorities.FPS_LOGGER; }
}