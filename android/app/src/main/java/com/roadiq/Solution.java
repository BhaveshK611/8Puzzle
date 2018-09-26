package com.roadiq;

public class Solution implements Comparable<Node>
{
    private String steps;
    private int nodesExplored;

	public Node(String steps, int nodesExplored)
	{
        this.steps=steps;
        this.nodesExplored=nodesExplored;
	}
}