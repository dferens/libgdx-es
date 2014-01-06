package com.dferens.core;

import com.badlogic.gdx.math.Vector2;

public class GameConfig {
    public Vector2 worldGravity;
    public int worldVelocityIterations;
    public int worldPositionIterations;
    public float renderVisibleUnits;
    public float minTimeStep;

    public GameConfig() {
        // Defaults
        this.worldVelocityIterations = 6;
        this.worldPositionIterations = 2;
        this.minTimeStep = 1/30f;
    }
}
