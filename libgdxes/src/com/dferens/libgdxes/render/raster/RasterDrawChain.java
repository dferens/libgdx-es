package com.dferens.libgdxes.render.raster;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.dferens.libgdxes.GameWorld;
import com.dferens.libgdxes.render.DrawChain;
import com.dferens.libgdxes.render.Drawable;
import com.dferens.libgdxes.render.Position;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.render.raster.drawable.*;

public class RasterDrawChain extends DrawChain<SpriteBatch, RasterDrawChain> {
    private Drawable drawable;
    private Position texturePosition;
    private float widthPixels;
    private float heightPixels;
    private float widthScale;
    private float heightScale;

    public RasterDrawChain(RenderScope renderScope, Drawable drawable) {
        super(renderScope);
        this.drawable = drawable;
        this.texturePosition = Position.BOTTOM_LEFT;
        this.widthPixels = -1;
        this.heightPixels = -1;
        this.widthScale = 1;
        this.heightScale = 1;
    }
    public RasterDrawChain(RenderScope renderScope, Texture texture) {
        this(renderScope, new TextureDrawable(texture));
    }
    public RasterDrawChain(RenderScope renderScope, TextureRegion textureRegion) {
        this(renderScope, new TextureRegionDrawable(textureRegion));
    }
    public RasterDrawChain(RenderScope renderScope, GameWorld gameWorld, Box2DDebugRenderer debugRenderer) {
        this(renderScope, new GameWorldDrawable(gameWorld, debugRenderer, renderScope ));
    }
    public RasterDrawChain(RenderScope renderScope, BitmapFont font, String text) {
        this(renderScope, new BitmapFontDrawable(font, text));
    }
    public RasterDrawChain(RenderScope renderScope, ParticleEffect particleEffect) {
        this(renderScope, new ParticleEffectDrawable(particleEffect));
    }

    public RasterDrawChain startAt(Position position) {
        this.texturePosition = position;
        return this;
    }

    public RasterDrawChain transformInUnits(float newWidth, float newHeight) {
        return this.transformInPixels(renderScope.unitsToPixels(newWidth), renderScope.unitsToPixels(newHeight));
    }
    public RasterDrawChain transformInUnits(float newWidthHeight) {
        return this.transformInUnits(newWidthHeight, newWidthHeight);
    }
    public RasterDrawChain transformInPixels(float newWidth, float newHeight) {
        this.widthPixels = newWidth;
        this.heightPixels = newHeight;
        return this;
    }
    public RasterDrawChain scale(float scaleX, float scaleY) {
        this.widthScale = scaleX;
        this.heightScale = scaleY;
        return this;
    }
    public RasterDrawChain scale(float scaleXY) {
        return this.scale(scaleXY, scaleXY);
    }
    public RasterDrawChain shiftUnits(float shiftX, float shiftY) {
        this.temp.set(this.posPixelsX, this.posPixelsY, 0);
        this.renderScope.unprojectCoordinates(this.temp);
        return this.worldCoords(this.temp.x + shiftX, this.temp.y + shiftY);
    }
    public RasterDrawChain shiftPixels(float shiftX, float shiftY) {
        this.posPixelsX += shiftX;
        this.posPixelsY += shiftY;
        return this;
    }

    @Override
    public void execute(SpriteBatch renderObject, float deltaTime) {
        drawable.execute(renderObject, deltaTime,
                         this.posPixelsX, this.posPixelsY,
                         this.texturePosition, this.widthPixels, this.heightPixels, this.widthScale,
                         this.heightScale, this.rotationAngleDegrees);
    }
}
