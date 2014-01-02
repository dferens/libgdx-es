package com.dferens.utils;

public class StateMachine {
    private State currentState;

    public StateMachine(State initialState) {
        this.currentState = initialState;
    }

    public State getState() {
        return this.currentState;
    }
    public void switchTo(State targetState) {
        if (this.currentState != targetState) {
            currentState.onExit(this);
            this.currentState = targetState;
            currentState.onEnter(this);
        }
    }
}
