package com.dferens.core;

import com.badlogic.gdx.math.Vector2;

public class Settings {
    public Vector2 worldGravity;
    public int worldVelocityIterations;
    public int worldPositionIterations;
    public float renderVisibleUnits;
    public float minTimeStep;

    public Settings() {
        this.worldGravity = new Vector2(0, -9.8f);
        this.worldVelocityIterations = 6;
        this.worldPositionIterations = 2;
        this.renderVisibleUnits = 20;
        this.minTimeStep = 1/30f;
    }
}