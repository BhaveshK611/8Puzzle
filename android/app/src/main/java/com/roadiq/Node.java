package com.roadiq;

public class Node implements Comparable<Node> {
	Node parent;

	int mat[][];

	int x, y, level;

	int cost;

	char move;

	public Node(int mat[][], Node parent, int level, int x, int y, char move) {
		int N = mat.length;
		this.mat = new int[N][N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				this.mat[i][j] = mat[i][j];

		this.parent = parent;
		this.level = level;
		this.cost = Integer.MAX_VALUE;
		this.x = x;
		this.y = y;
		this.move = move;
	}

	public int compareTo(Node node) {
		return (this.cost + this.level) - (node.cost + node.level);
	}

}