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

    private static final String DURATION_SHORT_KEY = "SHORT";
    private static final String DURATION_LONG_KEY = "LONG";

    public SolverModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "Solver";
    }

    @ReactMethod
    public void solve(String initialConf, String resultConf, Callback returnFunc) {

        String temp[] = null;

        temp = initialConf.split("#");

        int N = (int) Math.sqrt(temp.length);
        int initial[][] = new int[N][N];
        int result[][] = new int[N][N];

        for (int i = 0, k = 0; i < N; i++)
            for (int j = 0; j < N; j++, k++)
                initial[i][j] = Integer.parseInt(temp[k]);

        temp = resultConf.split("#");
        for (int i = 0, k = 0; i < N; i++)
            for (int j = 0; j < N; j++, k++)
                result[i][j] = Integer.parseInt(temp[k]);

        Solution sol = Puzzle.solve(initial, result);

        if (sol != null)
            returnFunc.invoke(sol.steps, sol.nodeExplored);
        else
            returnFunc.invoke(null, 0);
    }
}
