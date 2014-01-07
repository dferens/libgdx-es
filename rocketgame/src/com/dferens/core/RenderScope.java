package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

    private SpriteBatch batch;
    private OrthographicCamera camera;

    private State readyState;
    private State drawingState;
    public SpriteBatch getBatch() { return batch; }

    public RenderScope(float visibleUnits) {
        this.batch = new SpriteBatch();
        this.camera = this.createCamera(visibleUnits);
        this.moveCamera(camera.viewportWidth / 2, camera.viewportHeight / 2);
        this.readyState = new ReadyState();
        this.drawingState = new DrawingState();
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

        Vector3 position = new Vector3(body.getX(), body.getY(), 0);
        camera.project(position);
        batch.draw(texture, position.x, position.y);
    }
    public void drawingDone() {
        this.switchTo(this.readyState);
    }
    public void syncronize(MapRenderer renderer) {
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

    @Override
    public void dispose() { batch.dispose(); }
}
