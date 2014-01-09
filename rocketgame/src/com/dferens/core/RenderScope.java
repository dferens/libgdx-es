package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.dferens.core.utils.StateMachine;

public class RenderScope extends StateMachine implements Disposable {
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
    private State drawingState;
    private Box2DDebugRenderer debugRenderer;

    public RenderScope(GameManager gameManager) {
        this.gameManager = gameManager;
        this.batch = new SpriteBatch();
        this.camera = this.createCamera(gameManager.getSettings().renderVisibleUnits);
        this.moveCamera(camera.viewportWidth / 2, camera.viewportHeight / 2);
        this.readyState = new ReadyState();
        this.drawingState = new DrawingState();
        this.debugRenderer = new Box2DDebugRenderer();
        this.switchTo(readyState);
    }

    public void moveCamera(float dx, float dy) {
        this.camera.translate(dx, dy);
        this.camera.update();
        this.batch.setProjectionMatrix(camera.combined);
    }

    public void moveCamera(Vector2 pos) {
        this.moveCamera(pos.x, pos.y);
    }
    public void draw(Texture texture, PhysicsBody body) {
        this.switchTo(this.drawingState);
        Vector3 position = this.convertCoordinates(body.getX(), body.getY());
        batch.draw(texture, position.x, position.y);
    }
    public void draw(Texture texture, PhysicsBody body, float width, float height) {
        this.switchTo(this.drawingState);
        Vector3 position = this.convertCoordinates(body.getX(), body.getY());
        batch.draw(texture, position.x, position.y, unitsToPixels(width), unitsToPixels(height));
    }
    public void commit() {
        if (this.gameManager.getSettings().debugModeOn) {
            GameWorld world = gameManager.getEntityManager().getWorld();
            world.draw(this.debugRenderer, this.camera);
        }
        this.switchTo(this.readyState);
    }
    public void synchronise(MapRenderer renderer) {
        renderer.setView(this.camera);
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
    private Vector3 convertCoordinates(float xUnits, float yUnits) {
        Vector3 position = new Vector3(xUnits, yUnits, 0);
        camera.project(position);
        return position;
    }
    private void convertCoordinates(Vector3 coords) {
        camera.project(coords);
    }
    private float unitsToPixels(float units) { return units * this.getPixelsPerUnit(); }
    private float getPixelsPerUnit() { return (Gdx.graphics.getWidth() / camera.viewportWidth); }

    @Override
    public void dispose() { batch.dispose(); }
}
