package com.dferens.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.dferens.utils.State;
import com.dferens.utils.StateMachine;

public class GameRenderer extends StateMachine implements Disposable {
    class BatchModeState extends State {
        @Override
        public void onEnter(StateMachine machine) {
            GameRenderer renderer = ((GameRenderer)machine);
            renderer.batch.setProjectionMatrix(camera.combined);
            renderer.batch.begin();
        }
        @Override
        public void onExit(StateMachine machine) {
            GameRenderer renderer = ((GameRenderer)machine);
            renderer.batch.end();
        }
    }

    private final State readyMode;
    private final State batchMode;
    private SpriteBatch batch;
    private OrthographicCamera camera;

    public GameRenderer(float visibleUnits) {
        super(new State());
        readyMode = this.getState();
        batchMode = new BatchModeState();
        batch = new SpriteBatch();

        this.camera = this.setupCamera(visibleUnits);
    }

    public void draw(Texture texture, PhysicsBody body) {
        this.switchTo(batchMode);
        Vector3 position = new Vector3(body.getX(), body.getY(), 0);
        camera.project(position);
        batch.draw(texture, position.x, position.y);
    }
    public void draw(Sprite sprite) {
        this.switchTo(batchMode);
        sprite.draw(this.batch);
    }

    public void end() {
        this.switchTo(readyMode);
    }

    private OrthographicCamera setupCamera(float visibleUnits) {
        float viewportWidth, viewportHeight;
        float widthPixels = Gdx.graphics.getWidth();
        float heightPixels = Gdx.graphics.getHeight();

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
    public void dispose() {
        batch.dispose();
    }
}
