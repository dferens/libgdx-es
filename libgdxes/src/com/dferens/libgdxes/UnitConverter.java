package com.dferens.libgdxes;

import com.badlogic.gdx.math.Vector3;

public interface UnitConverter {
    Vector3 projectCoordinates(float xUnits, float yUnits);

    void projectCoordinates(Vector3 coords);

    void unprojectCoordinates(Vector3 coords);

    float unitsToPixels(float units);

    float getPixelsPerUnit();
}
