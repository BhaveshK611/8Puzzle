package com.roadiq;

public class PuzzleSolution {
    private String steps;
    private int nodesExplored;

    public PuzzleSolution(String steps, int nodesExplored) {
        this.steps = steps;
        this.nodesExplored = nodesExplored;
    }

    public String getSteps() {
        return this.steps;
    }

    public int getNodesExplored() {
        return this.nodesExplored;
    }
}