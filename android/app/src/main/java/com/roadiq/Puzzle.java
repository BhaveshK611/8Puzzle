package com.roadiq;

import java.util.*;

public class Puzzle {

	public static int row[] = { 1, 0, -1, 0 };
	public static int col[] = { 0, -1, 0, 1 };
	public static char moves[] = { 'D', 'L', 'U', 'R' };
	public static int posX[], posY[];

	public static PuzzleSolution solve(int initial[][], int result[][]) {
		if (!isSolvable(initial, result)) {
			// System.out.println("Not possible to goal state from given state!");
			return null;
		}

		int N = initial.length;
		int x = 0, y = 0;

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (initial[i][j] == 0) {
					x = i;
					y = j;
				}

		PriorityQueue<Node> pq = new PriorityQueue<Node>();

		posX = new int[N * N];
		posY = new int[N * N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				posX[result[i][j]] = i;
				posY[result[i][j]] = j;
			}

		Node root = newNode(initial, x, y, x, y, 0, null, '0');
		root.cost = calculateCostEuclidean(initial, result);

		pq.add(root);

		int exploredCount = 0;

		while (!pq.isEmpty()) {
			Node min = pq.poll();
			exploredCount++;

			if (min.cost == 0) {
				// int steps = printPath(min) - 1;
				// System.out.println("\nNo of moves: " + steps);
				// System.out.println("No of nodes explored: " + exploredCount);
				StringBuilder path = new StringBuilder("");

				while (min.parent != null) {
					path.append(min.move);
					min = min.parent;
				}

				return new PuzzleSolution(path.reverse().toString(), exploredCount);
			}

			for (int i = 0; i < 4; i++) {
				if (isSafe(min.x + row[i], min.y + col[i], N)) {
					Node child = newNode(min.mat, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min,
							moves[i]);
					child.cost = calculateCostEuclidean(child.mat, result);
					pq.add(child);
				}
			}

		}

		return null;
	}

	private static boolean isSolvable(int[][] initial, int[][] result) {
		if (invIndex(initial) % 2 == invIndex(result) % 2)
			return true;
		return false;
	}

	public static int invIndex(int state[][]) {
		int indexSum = 0;
		int N = state.length;

		int arr[] = new int[N * N];

		for (int i = 0, k = 0; i < N; i++)
			for (int j = 0; j < N; j++, k++)
				arr[k] = state[i][j];

		for (int i = 0; i < N * N; i++)
			for (int j = i + 1; j < N * N; j++)
				if (arr[i] != 0 && arr[j] != 0 && arr[j] < arr[i])
					indexSum++;
		return indexSum;

	}

	public static int calculateCostEuclidean(int initial[][], int result[][]) {
		int cost = 0;
		int N = initial.length;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (initial[i][j] != 0 && initial[i][j] != result[i][j])
					cost += (int) Math
							.ceil(Math.sqrt(square(i - posX[initial[i][j]]) + square(j - posY[initial[i][j]])));

		return cost;
	}

	public static int calculateCostManhatten(int initial[][], int result[][]) {
		int cost = 0;
		int N = initial.length;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (initial[i][j] != 0 && initial[i][j] != result[i][j])
					cost += mod(i - posX[initial[i][j]]) + mod(j - posY[initial[i][j]]);

		return cost;
	}

	public static int calculateCostHamming(int initial[][], int result[][]) {
		int cost = 0;
		int N = initial.length;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (initial[i][j] != 0 && initial[i][j] != result[i][j])
					cost++;
		return cost;
	}

	public static Node newNode(int mat[][], int x, int y, int newX, int newY, int level, Node parent, char move) {
		Node node = new Node(mat, parent, level, newX, newY, move);

		int temp = node.mat[x][y];
		node.mat[x][y] = node.mat[newX][newY];
		node.mat[newX][newY] = temp;

		return node;
	}

	public static boolean isSafe(int x, int y, int N) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}

	public static int printPath(Node root) {
		if (root == null)
			return 0;
		int steps = printPath(root.parent);
		printMatrix(root.mat);

		System.out.println();

		return ++steps;
	}

	public static void printMatrix(int mat[][]) {
		int N = mat.length;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				System.out.print(mat[i][j] + " ");
			System.out.println();
		}
	}

	public static int square(int x) {
		return x * x;
	}

	public static int mod(int x) {
		return x < 0 ? -x : x;
	}

};
