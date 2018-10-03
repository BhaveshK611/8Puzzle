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

		int N = initial.length;

		if (!isSolvable(initial, goal))
			return new PuzzleSolution(-1);

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

		case 'c':
			root.cost = calculateCostChebyshev(initial, goal);
			break;

		case 'l':
			root.cost = calculateCostLinearConflict(initial, goal);
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
					Node child = newNode(min.state, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min,
							moves[i]);

					switch (distType) {
					case 'h':
						child.cost = calculateCostHamming(child.state, goal);
						break;

					case 'm':
						child.cost = calculateCostManhatten(child.state, goal);
						break;

					case 'e':
						child.cost = calculateCostEuclidean(child.state, goal);
						break;

					case 'c':
						child.cost = calculateCostChebyshev(child.state, goal);
						break;

					case 'l':
						child.cost = calculateCostLinearConflict(child.state, goal);
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

	private static int calculateCostChebyshev(int initial[][], int goal[][]) {
		int cost = 0;
		int N = initial.length;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				if (initial[i][j] != 0 && initial[i][j] != goal[i][j])
					cost += max(mod(i - posX[initial[i][j]]), mod(j - posY[initial[i][j]]));

		return cost;
	}

	private static int calculateCostLinearConflict(int initial[][], int goal[][]) {
		int conflicts = 0;
		int N = initial.length;

		int xy[][] = new int[N * (N - 1) / 2][2];

		for (int i = 0, k = 0; i < N; i++)
			for (int j = i + 1; j < N; j++, k++) {
				xy[k][0] = i;
				xy[k][1] = j;
			}

		for (int k = 0; k < N; k++)
			for (int i = 0; i < xy.length; i++)
				if (initial[k][xy[i][0]] != 0 && initial[k][xy[i][1]] != 0)
					if ((posX[initial[k][xy[i][0]]] == k && posX[initial[k][xy[i][0]]] == posX[initial[k][xy[i][1]]])
							&& (posY[initial[k][xy[i][0]]] > posY[initial[k][xy[i][1]]]))
						conflicts++;

		for (int k = 0; k < N; k++)
			for (int i = 0; i < xy.length; i++)
				if (initial[xy[i][0]][k] != 0 && initial[xy[i][1]][k] != 0)
					if ((posY[initial[xy[i][0]][k]] == k && posY[initial[xy[i][0]][k]] == posY[initial[xy[i][1]][k]])
							&& (posX[initial[xy[i][0]][k]] > posX[initial[xy[i][0]][1]]))
						conflicts++;

		return calculateCostManhatten(initial, goal) + 2 * conflicts;
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

	private static Node newNode(int state[][], int x, int y, int newX, int newY, int level, Node parent, char move) {
		Node node = new Node(state, parent, level, newX, newY, move);

		int temp = node.state[x][y];
		node.state[x][y] = node.state[newX][newY];
		node.state[newX][newY] = temp;

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

	private static int max(int a, int b) {
		return a > b ? a : b;
	}

	/*
	 * Printing Functions:
	 */

	private static void printSolution(PuzzleSolution solution) {
		if (solution.getErrorCode() == 0) {
			System.out.println("\nPath: " + solution.getPath());
			System.out.print("No of moves: ");
			System.out.print(solution.getPath().length());
			System.out.println();
			System.out.print("No of nodes explored: ");
			System.out.print(solution.getNodesExplored());
			System.out.println();
		} else
			System.out.println("\nErrorCode: " + solution.getErrorCode() + "\n");
	}

	private static void printSteps(Node node) {
		printReverse(node);
	}

	private static void printReverse(Node node) {
		if (node == null)
			return;
		printReverse(node.parent);
		printState(node.state);
	}

	private static void printState(int state[][]) {
		int N = state.length;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++)
				System.out.print(state[i][j] + " ");
			System.out.println();
		}
		System.out.println();
	}

};
