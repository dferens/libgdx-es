package com.dferens.libgdxes;

import com.badlogic.gdx.math.Vector3;

public interface UnitConverter {
    Vector3 convertCoordinates(float xUnits, float yUnits);

    void unitsToPixels(Vector3 coords);

    void pixelsToUnits(Vector3 coords);

    float unitsToPixels(float units);

    float getPixelsPerUnit();
}
