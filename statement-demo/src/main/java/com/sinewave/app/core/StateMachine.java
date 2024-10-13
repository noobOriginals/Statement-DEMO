package com.sinewave.app.core;

import java.util.HashMap;

public class StateMachine implements Runnable {
    /* NOTE!!!
     * This is an "In-development DEMO" project. Do not rely on its functionality for creating any type of state machine.
     * There may be features that a state machine needs, but this project is missing.
     * It is not a fully functional app, only a DEMO.
     * 
     * State methods must not accept any parameters and must return a string that can be used in the startFromState methods
     * NOTE!!! */


    private HashMap<String, StateFunction> states = new HashMap<>(); // Holds all of the state methods in relation with their names
    private String currentState, threadState; // Utility strings
    private Thread stateThread; // Thread for running state machine separately from your application

    // Add a state by giving it a name and a method (Lambda or Reference) to execute
    public void addState(String name, StateFunction state) {
        states.put(name, state);
    }
    // Run a already added state directly by providing it's name
    public void runState(String name) throws Exception {
        if (!states.containsKey(name)) {
            try {
                throw new Exception("The called state is inexistent!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        states.get(name).call();
    }
    // Runs a state-to-state loop which ends when a state method returns a null string
    public void startFromState(String name) {
        String nextState = name;
        while (nextState != null) {
            if (!states.containsKey(nextState)) {
                try {
                    throw new Exception("The called state is inexistent!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            nextState = states.get(nextState).call();
        }
    }
    // Pre-sets a state that should be run later when the runCurrentState method is called
    public void setCurrentState(String name) {
        currentState = name;
    }
    // Run pre-set state
    public void runCurrentState() {
        try {
            runState(currentState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Starts a state-to-state loop on a separate thread which ends when a state method returns a null string
    public void startFromStateOnSeparateThread(String name) {
        threadState = name;
        stateThread = new Thread(this, "stateThread");
        stateThread.start();
    }
    // Thread implementation
    public void run() {
        String nextState = threadState;
        while (nextState != null) {
            if (!states.containsKey(nextState)) {
                try {
                    throw new Exception("The called state is inexistent!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            nextState = states.get(nextState).call();
        }
    }

    // Interface accepting the state methods (Lambda or Reference)
    @FunctionalInterface
    public interface StateFunction {
        public String call();
    }
}
