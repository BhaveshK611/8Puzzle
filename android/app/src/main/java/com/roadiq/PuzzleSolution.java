package com.roadiq;

public class PuzzleSolution {
	private String path;
	private int nodesExplored;
	private int errorCode;

	public PuzzleSolution(Node goal, int nodesExplored) {
		this.errorCode = 0;
		this.nodesExplored = nodesExplored;
		StringBuilder path = new StringBuilder("");

		Node temp = goal;
		while (temp.parent != null) {
			path.append(temp.move);
			temp = temp.parent;
		}

		this.path = path.reverse().toString();
	}

	public PuzzleSolution(int errorCode) {
		this.errorCode = errorCode;
		this.path = "";
		this.nodesExplored = 0;
	}

	public String getPath() {
		return this.path;
	}

	public int getNodesExplored() {
		return this.nodesExplored;
	}

	public int getErrorCode() {
		return errorCode;
	}

}