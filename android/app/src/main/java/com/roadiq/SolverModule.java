// SolverModule.java

package com.roadiq;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import java.util.Map;
import java.util.HashMap;

public class SolverModule extends ReactContextBaseJavaModule {

    public SolverModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "Solver";
    }

    @ReactMethod
    public void solve(String initialConf, String goalConf, Callback returnFunc) {

        PuzzleSolution solution = Puzzle.solve(initialConf, goalConf, 'm');
        returnFunc.invoke(solution.getErrorCode(), solution.getPath(), solution.getNodesExplored());
    }
}