package com.dferens.libgdxes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.dferens.libgdxes.utils.ScaledOrthogonalTiledMapRenderer;
import com.dferens.libgdxes.utils.StateMachine;

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

    public RenderScope(GameManager gameManager) {
        this.gameManager = gameManager;
        this.batch = new SpriteBatch();
        this.camera = this.createCamera(gameManager.getSettings().renderVisibleUnits);
        this.moveCamera(camera.viewportWidth / 2, camera.viewportHeight / 2);
        this.readyState = new ReadyState();
        this.drawingState = new DrawingState();
        this.switchTo(readyState);
    }

    public void moveCamera(float dx, float dy) {
        this.camera.translate(dx, dy);
        this.camera.update();
    }

    public void moveCamera(Vector2 pos) {
        this.moveCamera(pos.x, pos.y);
    }

    public void draw(Texture texture, PhysicsBody body) {
        /**
         * TODO: draw methods refactoring
         *
         *  Possible solution:
         *
         *      renderScope.draw(drawingObject, positionX, positionY, isPointCenter)
         *                 .transform(newWidth, newHeight, rotation)
         *
         *      renderScope.draw(textureOrSpriteOrFontOrAnything)
         *                 .screenCoordinates(screenPixelsX, screenPixelsY)
         *
         *  Requirements:
         *   - different types of drawable objects (TextureRegion, Sprite etc.);
         *   - screen / world coordinates;
         *   - positioning and transformations;
         */
        Vector3 position = this.convertCoordinates(body.getX(), body.getY());
        batch.draw(texture, position.x, position.y);
    }
    public void draw(Texture texture, PhysicsBody body, float width, float height) {
        Vector3 position = this.convertCoordinates(body.getX(), body.getY());
        batch.draw(texture, position.x, position.y, unitsToPixels(width), unitsToPixels(height));
    }
    public void draw(GameWorld world, Box2DDebugRenderer boxDebugRenderer) {
        // For the unknown reasons, debug renderer breaks all SpriteBatch objects
        // Flushing sprite batch helps
        this.commitDraw(true);
        world.draw(boxDebugRenderer, this.camera);
        this.commitDraw(true);
    }
    public void drawDirectly(BitmapFont renderFont, String text, Vector2 screenCoordinates) {
        renderFont.draw(batch, text, screenCoordinates.x, screenCoordinates.y);
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
