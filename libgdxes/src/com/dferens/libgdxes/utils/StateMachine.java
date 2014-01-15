package com.dferens.libgdxes.utils;

public class StateMachine {
    public static class State {
        public void onEnter(StateMachine machine) {}
        public void onExit(StateMachine machine) {}
    }

    public static class StateMachineException extends Throwable {
        public StateMachineException(String s) {
            super(s);
        }
    }

    private State currentState;
    public StateMachine(State initialState) {
        this.currentState = initialState;
    }

    public StateMachine() {
        this(null);
    }
    public State getState() throws StateMachineException {
        if (this.currentState == null) {
            throw new StateMachineException("State is undefined");
        }
        return this.currentState;
    }

    public void switchTo(State targetState) {
        if (this.currentState != targetState) {
            if (currentState != null)
                currentState.onExit(this);

            this.currentState = targetState;
            currentState.onEnter(this);
        }
    }
}
