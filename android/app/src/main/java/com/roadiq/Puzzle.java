package com.roadiq;

import java.util.*;

public class Puzzle {

	private static int row[] = { 1, 0, -1, 0 };
	private static int col[] = { 0, -1, 0, 1 };
	private static int posX[], posY[];
	private static char moves[] = { 'D', 'L', 'U', 'R' };

	public static PuzzleSolution solve(String initialConf, String goalConf, char distType) {

		String temp[] = null;

		temp = initialConf.split(",");

		int N = (int) Math.sqrt(temp.length);

		int initial[][] = new int[N][N];

		for (int i = 0, k = 0; i < N; i++)
			for (int j = 0; j < N; j++, k++)
				try {
					initial[i][j] = Integer.parseInt(temp[k].trim());
				} catch (NumberFormatException e) {
					if (temp[k].trim().equals(""))
						return new PuzzleSolution(-2);
					return new PuzzleSolution(-4);
				}

		temp = goalConf.split(",");
		int goal[][] = new int[N][N];
		for (int i = 0, k = 0; i < N; i++)
			for (int j = 0; j < N; j++, k++)
				try {
					goal[i][j] = Integer.parseInt(temp[k].trim());
				} catch (NumberFormatException e) {
					if (temp[k].trim().equals(""))
						return new PuzzleSolution(-3);
					return new PuzzleSolution(-5);
				}

		return Puzzle.solve(initial, goal, distType);
	}

	public static PuzzleSolution solve(int initial[][], int goal[][], char distType) {
		try {
			if (!isValidState(initial))
				return new PuzzleSolution(-6);
		} catch (ArrayIndexOutOfBoundsException e) {
			return new PuzzleSolution(-8);
		}

		try {
			if (!isValidState(goal))
				return new PuzzleSolution(-7);
		} catch (ArrayIndexOutOfBoundsException e) {
			return new PuzzleSolution(-9);
		}

		if (!isSolvable(initial, goal))
			return new PuzzleSolution(-1);

		int N = initial.length;

		PriorityQueue<Node> pq = new PriorityQueue<Node>();

		posX = new int[N * N];
		posY = new int[N * N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				posX[goal[i][j]] = i;
				posY[goal[i][j]] = j;
			}

		int x = -1, y = -1;
		for (int i = 0; i < initial.length; i++)
			for (int j = 0; j < initial.length; j++)
				if (initial[i][j] == 0) {
					x = i;
					y = j;
				}

		Node root = newNode(initial, x, y, x, y, 0, null, '0');

		switch (distType) {
		case 'h':
			root.cost = calculateCostHamming(initial, goal);
			break;

		case 'm':
			root.cost = calculateCostManhatten(initial, goal);
			break;

		case 'e':
			root.cost = calculateCostEuclidean(initial, goal);
			break;

		default:
			break;
		}

		pq.add(root);

		int exploredCount = 0;

		while (!pq.isEmpty()) {
			Node min = pq.poll();

			if (min.cost == 0) {
				// printSteps(min);
				return new PuzzleSolution(min, exploredCount);
			}

			exploredCount++;
			for (int i = 0; i < 4; i++) {
				if (isSafe(min.x + row[i], min.y + col[i], N) && !isExplored(min.move, moves[i])) {
					Node child = newNode(min.mat, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min,
							moves[i]);

					switch (distType) {
					case 'h':
						child.cost = calculateCostHamming(child.mat, goal);
						break;

					case 'm':
						child.cost = calculateCostManhatten(child.mat, goal);
						break;

					case 'e':
						child.cost = calculateCostEuclidean(child.mat, goal);
						break;

					default:
						break;
					}

					pq.add(child);
				}
			}

		}

		return null;
	}

	/*
	 * Check for valid Conf
	 */
	private static boolean isValidState(int[][] state) throws ArrayIndexOutOfBoundsException {
		int N = state.length;
		boolean present[] = new boolean[N * N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++) {
				if (present[state[i][j]])
					return false;
				present[state[i][j]] = true;
			}

		return true;

	}

	private static boolean isExplored(char parentMove, char childMove) {
		if (parentMove == 'D' && childMove == 'U')
			return true;

		if (parentMove == 'U' && childMove == 'D')
			return true;

		if (parentMove == 'L' && childMove == 'R')
			return true;

		if (parentMove == 'R' && childMove == 'L')
			return true;

		return false;
	}

	/*
	 * Cost Functions
	 */
	private static int calculateCostEuclidean(int initial[][], int goal[][]) {
		int cost = 0;
		int N = initial.length;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (initial[i][j] != 0 && initial[i][j] != goal[i][j])
					cost += (int) Math
							.ceil(Math.sqrt(square(i - posX[initial[i][j]]) + square(j - posY[initial[i][j]])));

		return cost;
	}

	private static int calculateCostManhatten(int initial[][], int goal[][]) {
		int cost = 0;
		int N = initial.length;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (initial[i][j] != 0 && initial[i][j] != goal[i][j])
					cost += mod(i - posX[initial[i][j]]) + mod(j - posY[initial[i][j]]);

		return cost;
	}

	private static int calculateCostHamming(int initial[][], int goal[][]) {
		int cost = 0;
		int N = initial.length;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (initial[i][j] != 0 && initial[i][j] != goal[i][j])
					cost++;
		return cost;
	}

	/*
	 * Check if Solvable or not
	 */

	private static boolean isSolvable(int[][] initial, int[][] goal) {
		if (invIndex(initial) % 2 == invIndex(goal) % 2)
			return true;
		return false;
	}

	private static int invIndex(int state[][]) {
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

	/*
	 * Utility Functions
	 */

	private static Node newNode(int mat[][], int x, int y, int newX, int newY, int level, Node parent, char move) {
		Node node = new Node(mat, parent, level, newX, newY, move);

		int temp = node.mat[x][y];
		node.mat[x][y] = node.mat[newX][newY];
		node.mat[newX][newY] = temp;

		return node;
	}

	private static boolean isSafe(int x, int y, int N) {
		return (x >= 0 && x < N && y >= 0 && y < N);
	}

	private static int square(int x) {
		return x * x;
	}

	private static int mod(int x) {
		return x < 0 ? -x : x;
	}

	/*
	 * To print all steps:
	 */

	private static void printSteps(Node node) {
		printReverse(node);
	}

	private static void printReverse(Node node) {
		if (node == null)
			return;
		printReverse(node.parent);
		printMatrix(node.mat);
	}

	private static void printMatrix(int mat[][]) {
		int N = mat.length;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				System.out.print(mat[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}

};
