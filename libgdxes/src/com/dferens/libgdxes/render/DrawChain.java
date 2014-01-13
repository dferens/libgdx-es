package com.dferens.libgdxes.render;

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
import com.dferens.libgdxes.render.drawable.*;

public class DrawChain {
    private RenderScope renderScope;
    private Drawable drawable;
    private float posPixelsX;
    private float posPixelsY;
    private Position texturePosition;
    private float widthPixels;
    private float heightPixels;
    private float widthScale;
    private float heightScale;
    private float rotationAngleDegrees;
    private Vector3 temp;

    public DrawChain(RenderScope renderScope, Drawable drawable) {
        this.renderScope = renderScope;
        this.drawable = drawable;
        this.texturePosition = Position.BOTTOM_LEFT;
        this.posPixelsX = 0;
        this.posPixelsY = 0;
        this.widthPixels = -1;
        this.heightPixels = -1;
        this.widthScale = 1;
        this.heightScale = 1;
        this.temp = new Vector3();
    }
    public DrawChain(RenderScope renderScope, Texture texture) {
        this(renderScope, new TextureDrawable(texture));
    }
    public DrawChain(RenderScope renderScope, TextureRegion textureRegion) {
        this(renderScope, new TextureRegionDrawable(textureRegion));
    }
    public DrawChain(RenderScope renderScope, GameWorld gameWorld, Box2DDebugRenderer debugRenderer) {
        this(renderScope, new GameWorldDrawable(gameWorld, debugRenderer, renderScope ));
    }
    public DrawChain(RenderScope renderScope, BitmapFont font, String text) {
        this(renderScope, new BitmapFontDrawable(font, text));
    }
    public DrawChain(RenderScope renderScope, ParticleEffect particleEffect) {
        this(renderScope, new ParticleEffectDrawable(particleEffect));
    }

    public DrawChain bodyCoords(PhysicsBody body) {
        return this.worldCoords(body.getX(), body.getY());
    }
    public DrawChain screenCoords(float x, float y) {
        this.posPixelsX = x;
        this.posPixelsY = y;
        return this;
    }
    public DrawChain screenCoords(Vector2 coords) {
        return this.screenCoords(coords.x, coords.y);
    }
    public DrawChain worldCoords(float x, float y) {
        this.temp.set(x, y, 0);
        this.renderScope.unitsToPixels(this.temp);
        return this.screenCoords(this.temp.x, this.temp.y);
    }
    public DrawChain worldCoords(Vector2 coords) {
        return worldCoords(coords.x, coords.y);
    }
    public DrawChain startAt(Position position) {
        this.texturePosition = position;
        return this;
    }

    public DrawChain transformInUnits(float newWidth, float newHeight) {
        return this.transformInPixels(renderScope.unitsToPixels(newWidth), renderScope.unitsToPixels(newHeight));
    }
    public DrawChain transformInPixels(float newWidth, float newHeight) {
        this.widthPixels = newWidth;
        this.heightPixels = newHeight;
        return this;
    }
    public DrawChain scale(float scaleX, float scaleY) {
        this.widthScale = scaleX;
        this.heightScale = scaleY;
        return this;
    }
    public DrawChain scale(float scaleXY) {
        return this.scale(scaleXY, scaleXY);
    }
    public DrawChain shiftUnits(float shiftX, float shiftY) {
        this.temp.set(this.posPixelsX, this.posPixelsY, 0);
        this.renderScope.pixelsToUnits(this.temp);
        return this.worldCoords(this.temp.x + shiftX, this.temp.y + shiftY);
    }
    public DrawChain shiftPixels(float shiftX, float shiftY) {
        this.posPixelsX += shiftX;
        this.posPixelsY += shiftY;
        return this;
    }

    public DrawChain rotateDegrees(float angle) {
        this.rotationAngleDegrees = angle;
        return this;
    }
    public DrawChain rotateRadians(float angle) {
        return this.rotateDegrees((float)Math.toDegrees(angle));
    }

    public void commit() { this.renderScope.render(this); }

    void execute(SpriteBatch batch, float deltaTime) {
        drawable.execute(batch, deltaTime,
                         this.posPixelsX, this.posPixelsY,
                         this.texturePosition, this.widthPixels, this.heightPixels, this.widthScale,
                         this.heightScale, this.rotationAngleDegrees);
    }
}
