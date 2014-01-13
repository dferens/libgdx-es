package com.dferens.libgdxes.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.dferens.libgdxes.*;
import com.dferens.libgdxes.utils.StateMachine;

public class RenderScope extends StateMachine implements Disposable, Scope, UnitConverter {
    private static class NotReadyState extends State {
        @Override
        public void onExit(StateMachine machine) {
            if (((RenderScope) machine).camera == null) {
                throw new UnsupportedOperationException();
            }
        }
    }
    private static class ReadyState extends State { }
    private static class DrawingState extends State {
        @Override
        public void onEnter(StateMachine machine) {
            ((RenderScope) machine).batch.begin();
        }
        @Override
        public void onExit(StateMachine machine) {
            ((RenderScope) machine).batch.end();
        }
    }

    private GameManager gameManager;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private State readyState;
    private State notReadyState;
    private State drawingState;
    private Color backgroundColor;

    public AssetContainer getAssetStorage() { return this.gameManager.getAssetStorage(); }
    public Matrix4 getProjectionMatrix() { return this.camera.combined; }
    public Color getBackgroundColor() { return this.backgroundColor; }
    public float getViewportWidth() { return this.camera.viewportWidth; }
    public float getViewportHeight() { return this.camera.viewportHeight; }
    public Vector3 getCameraPosition() { return this.camera.position.cpy(); }

    public void setBackgroundColor(Color value) { this.backgroundColor = value; }

    public RenderScope(GameManager gameManager) {
        this.gameManager = gameManager;
        this.batch = new SpriteBatch();
        this.readyState = new ReadyState();
        this.notReadyState = new NotReadyState();
        this.drawingState = new DrawingState();
        this.backgroundColor = Color.WHITE;
        this.switchTo(notReadyState);
    }

    @Override
    public float unitsToPixels(float units) { return units * this.getPixelsPerUnit(); }
    @Override
    public float getPixelsPerUnit() { return (Gdx.graphics.getWidth() / camera.viewportWidth); }
    public Vector2 getViewportCoords(Vector2 positionUnits) {
        Vector2 position = new Vector2(this.camera.position.x, this.camera.position.y);
        return position.sub(camera.viewportWidth / 2, camera.viewportHeight / 2)
                .sub(positionUnits)
                .scl(-1)
                .div(camera.viewportWidth, camera.viewportHeight);
    }

    @Override
    public void initialize() {
        this.setupCamera(gameManager.getSettings());
        this.moveCameraBy(camera.viewportWidth / 2, camera.viewportHeight / 2);
        this.switchTo(readyState);
    }

    public void moveCameraBy(float dx, float dy) {
        this.camera.translate(dx, dy);
        this.camera.update();
    }
    public void moveCameraByX(float dx) { this.moveCameraBy(dx, 0); }
    public void moveCameraByY(float dy) { this.moveCameraBy(0, dy); }
    public void moveCameraTo(Vector2 pos) {
        moveCameraBy(pos.x - camera.position.x, pos.y - camera.position.y);
    }
    public void moveCamera(Vector2 pos) { this.moveCameraBy(pos.x, pos.y); }
    public DrawChain draw(Texture texture) { return new DrawChain(this, texture); }
    public DrawChain draw(TextureRegion textureRegion) { return new DrawChain(this, textureRegion); }
    public DrawChain draw(BitmapFont font, String text) { return new DrawChain(this, font, text); }
    public DrawChain drawWorld(GameWorld gameWorld, Box2DDebugRenderer debugRenderer) {
        return new DrawChain(this, gameWorld, debugRenderer);
    }
    public <T extends Object> DrawChain draw(String assetAlias, Class<T> assetType) {
        T asset = this.getAssetStorage().get(assetAlias, assetType);
        if (asset == null) {
            throw new GdxRuntimeException(String.format("Asset not found: %s", assetAlias));
        } else {
            if (assetType == Texture.class) {
                return new DrawChain(this, (Texture) asset);
            } else if (assetType == TextureRegion.class) {
                return new DrawChain(this, (TextureRegion) asset);
            } else if (assetType == ParticleEffect.class) {
                return new DrawChain(this, (ParticleEffect) asset);
            } else {
                throw new UnsupportedOperationException();
            }
        }
    }
    public DrawChain drawText(String fontAssetAlias, String text) {
        BitmapFont fontAsset = this.getAssetStorage().get(fontAssetAlias, BitmapFont.class);
        return new DrawChain(this, fontAsset, text);
    }

    public void render(DrawChain drawChain) {
        float lastTimeStep = this.gameManager.getLastTimeStep();
        drawChain.execute(this.batch, lastTimeStep);
    }
    public void clearScreen() {
        Gdx.gl.glClearColor(backgroundColor.r,
                            backgroundColor.g,
                            backgroundColor.b,
                            backgroundColor.a);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
    public void beginDraw() {
        this.switchTo(this.drawingState);
    }
    public void commitDraw(boolean stay) {
        this.switchTo(this.readyState);
        if (stay) this.switchTo(this.drawingState);
    }
    public void commitDraw() {
        commitDraw(false);
    }
    public void synchronise(MapRenderer renderer) {
        renderer.setView(this.camera);
    }

    private void setupCamera(Settings settings) {
        float visibleUnits = settings.renderVisibleUnits;
        float widthPixels = Gdx.graphics.getWidth(), heightPixels = Gdx.graphics.getHeight();
        float viewportWidth, viewportHeight;

        if (widthPixels >= heightPixels) {
            viewportWidth = visibleUnits;
            viewportHeight = visibleUnits * heightPixels / widthPixels;
        }
        else {
            viewportHeight = visibleUnits;
            viewportWidth = visibleUnits * widthPixels / heightPixels;
        }
        this.camera = new OrthographicCamera(viewportWidth, viewportHeight);
    }

    @Override
    public Vector3 convertCoordinates(float xUnits, float yUnits) {
        Vector3 position = new Vector3(xUnits, yUnits, 0);
        camera.project(position);
        return position;
    }
    @Override
    public void unitsToPixels(Vector3 coords) {
        camera.project(coords);
    }
    @Override
    public void pixelsToUnits(Vector3 coords) {
        camera.unproject(coords);
    }
    @Override
    public void dispose() { batch.dispose(); }
}
