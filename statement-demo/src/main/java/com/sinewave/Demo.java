package com.sinewave;

import com.sinewave.app.core.StateMachine;

public class Demo {
    public static void main(String[] args) throws Exception {
        StateMachine stateMachine = new StateMachine(); // Create a state machine object, no constructor parameters needed
        stateMachine.addState("state1", Demo::state1); // Add a state to the newly created state machine.
        stateMachine.startFromStateOnSeparateThread("state1"); // Run a loop of that state on separate thread.
                                                                    // See com.sinewave.app.core.StateMachine for more info.
        System.out.println("In between");
        Thread.sleep(1);
        System.out.println("In between");
    }
    
    static int idx = 0, max = 5;
    public static String state1() {
        System.out.println("Hello, world!");
        idx++;
        if (idx >= max) {
            return null;
        } else {
            return "state1";
        }
    }
}