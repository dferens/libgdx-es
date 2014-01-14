package com.dferens.libgdxes.render.chains;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.dferens.libgdxes.GameWorld;
import com.dferens.libgdxes.PhysicsBody;
import com.dferens.libgdxes.render.DrawChain;
import com.dferens.libgdxes.render.Position;
import com.dferens.libgdxes.render.RenderScope;
import com.dferens.libgdxes.render.drawable.*;

public class RasterDrawChain extends DrawChain<SpriteBatch> {
    private float posPixelsX;
    private float posPixelsY;
    private Position texturePosition;
    private float widthPixels;
    private float heightPixels;
    private float widthScale;
    private float heightScale;
    private float rotationAngleDegrees;
    private Vector3 temp;

    public RasterDrawChain(RenderScope renderScope, Drawable drawable) {
        super(renderScope, drawable);
        this.posPixelsY = 0;
        this.posPixelsX = 0;
        this.texturePosition = Position.BOTTOM_LEFT;
        this.widthPixels = -1;
        this.heightPixels = -1;
        this.widthScale = 1;
        this.heightScale = 1;
        this.temp = new Vector3();
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

    public RasterDrawChain screenCoords(float x, float y) {
        this.posPixelsX = x;
        this.posPixelsY = y;
        return this;
    }

    public RasterDrawChain screenCoords(Vector2 coords) {
        return this.screenCoords(coords.x, coords.y);
    }

    public RasterDrawChain worldCoords(float x, float y) {
        this.temp.set(x, y, 0);
        this.renderScope.unitsToPixels(this.temp);
        return this.screenCoords(this.temp.x, this.temp.y);
    }

    public RasterDrawChain worldCoords(Vector2 coords) {
        return worldCoords(coords.x, coords.y);
    }

    public RasterDrawChain bodyCoords(PhysicsBody body) {
        return this.worldCoords(body.getX(), body.getY());
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
        this.renderScope.pixelsToUnits(this.temp);
        return this.worldCoords(this.temp.x + shiftX, this.temp.y + shiftY);
    }
    public RasterDrawChain shiftPixels(float shiftX, float shiftY) {
        this.posPixelsX += shiftX;
        this.posPixelsY += shiftY;
        return this;
    }

    public RasterDrawChain rotateDegrees(float angle) {
        this.rotationAngleDegrees = angle;
        return this;
    }
    public RasterDrawChain rotateRadians(float angle) {
        return this.rotateDegrees((float)Math.toDegrees(angle));
    }

    @Override
    public void execute(SpriteBatch renderObject, float deltaTime) {
        drawable.execute(renderObject, deltaTime,
                         this.posPixelsX, this.posPixelsY,
                         this.texturePosition, this.widthPixels, this.heightPixels, this.widthScale,
                         this.heightScale, this.rotationAngleDegrees);
    }
}
