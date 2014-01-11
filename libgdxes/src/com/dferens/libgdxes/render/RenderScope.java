package com.dferens.libgdxes.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.dferens.libgdxes.GameManager;
import com.dferens.libgdxes.GameWorld;
import com.dferens.libgdxes.Scope;
import com.dferens.libgdxes.UnitConverter;
import com.dferens.libgdxes.utils.ScaledOrthogonalTiledMapRenderer;
import com.dferens.libgdxes.utils.StateMachine;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RenderScope extends StateMachine implements Disposable, Scope, UnitConverter {
    private static class NotReadyState extends State {
        @Override
        public void onExit(StateMachine machine) {
            if (((RenderScope) machine).camera == null) {
                throw new NotImplementedException();
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
    private State readyState, notReadyState;
    private State drawingState;

    public Matrix4 getProjectionMatrix() { return this.camera.combined; }

    public RenderScope(GameManager gameManager) {
        this.gameManager = gameManager;
        this.batch = new SpriteBatch();
        this.readyState = new ReadyState();
        this.drawingState = new DrawingState();
        this.notReadyState = new NotReadyState();
        this.switchTo(notReadyState);
    }

    @Override
    public void initialize() {
        this.camera = this.createCamera(gameManager.getSettings().renderVisibleUnits);
        this.moveCamera(camera.viewportWidth / 2, camera.viewportHeight / 2);
        this.switchTo(readyState);
    }

    public void moveCamera(float dx, float dy) {
        this.camera.translate(dx, dy);
        this.camera.update();
    }
    public void moveCamera(Vector2 pos) {
        this.moveCamera(pos.x, pos.y);
    }

    public DrawChain draw(Texture texture) { return new DrawChain(this, texture); }
    public DrawChain draw(TextureRegion textureRegion) { return new DrawChain(this, textureRegion); }
    public DrawChain draw(BitmapFont font, String text) { return new DrawChain(this, font, text); }
    public DrawChain draw(GameWorld gameWorld, Box2DDebugRenderer debugRenderer) {
        return new DrawChain(this, gameWorld, debugRenderer);
    }
    public void render(DrawChain drawChain) { drawChain.execute(this.batch); }

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
    public ScaledOrthogonalTiledMapRenderer createMapRenderer(TiledMap map, float unitScale) {
        return new ScaledOrthogonalTiledMapRenderer(map, unitScale, this.batch);
    }

    private OrthographicCamera createCamera(float visibleUnits) {
        float widthPixels = Gdx.graphics.getWidth();
        float heightPixels = Gdx.graphics.getHeight();
        float viewportWidth, viewportHeight;

        if (widthPixels >= heightPixels) {
            viewportWidth = visibleUnits;
            viewportHeight = visibleUnits * heightPixels / widthPixels;
        }
        else {
            viewportHeight = visibleUnits;
            viewportWidth = visibleUnits * widthPixels / heightPixels;
        }
        return new OrthographicCamera(viewportWidth, viewportHeight);
    }
    @Override
    public Vector3 convertCoordinates(float xUnits, float yUnits) {
        Vector3 position = new Vector3(xUnits, yUnits, 0);
        camera.project(position);
        return position;
    }
    @Override
    public void convertCoordinates(Vector3 coords) {
        camera.project(coords);
    }
    @Override
    public float unitsToPixels(float units) { return units * this.getPixelsPerUnit(); }
    @Override
    public float getPixelsPerUnit() { return (Gdx.graphics.getWidth() / camera.viewportWidth); }

    @Override
    public void dispose() { batch.dispose(); }
}
