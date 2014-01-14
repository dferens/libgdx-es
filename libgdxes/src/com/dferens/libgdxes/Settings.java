package com.dferens.libgdxes;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class Settings {
    public Vector2 worldGravity;
    public int worldVelocityIterations;
    public int worldPositionIterations;
    public float renderVisibleUnits;
    public float maxTimeStep;
    public boolean debugModeOn;
    public BitmapFont systemFont;

    public Settings(boolean debugModeOn) {
        this.worldGravity = new Vector2(0, -9.8f);
        this.worldVelocityIterations = 6;
        this.worldPositionIterations = 2;
        this.renderVisibleUnits = 40;
        this.maxTimeStep = 1/30f;
        this.debugModeOn = debugModeOn;
    }
    public Settings() { this(false); }
}
