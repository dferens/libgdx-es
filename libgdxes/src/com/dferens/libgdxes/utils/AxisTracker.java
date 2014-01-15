package com.dferens.libgdxes.utils;

public class AxisTracker {
    private float forward;
    private float backward;
    private boolean flipped;

    public void setForward(float forward) { this.forward = forward; }
    public void setBackward(float backward) { this.backward = backward; }

    public AxisTracker(float forward, float b) {
        setForward(forward);
        setBackward(b);
        this.flipped = false;
    }

    public float calculateCameraTranslate(float currentValue) {
        float diff = 0;
        if (flipped == false) {
            if (currentValue > this.forward) {
                diff = currentValue - this.forward;
            } else if (currentValue < this.backward) {
                diff = - (this.forward + this.backward + (this.backward - currentValue));
                this.forward = 1 - this.forward;
                this.backward = 1 - this.backward;
                flipped = true;
            }
        } else {
            if (currentValue < this.forward) {
                diff =  currentValue - this.forward;
            } else if (currentValue > this.backward) {
                diff = this.backward - (1 - this.forward) + (currentValue - this.backward);
                this.forward = 1 - this.forward;
                this.backward = 1 - this.backward;
                flipped = false;
            }
        }
        return diff;
    }
}