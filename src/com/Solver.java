package com;

import java.util.Arrays;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Solver implements Cube {

	private Map<Character, Character[][]> cube = new HashMap<>();

	private char[] colors = { 'W', 'Y', 'R', 'O', 'G', 'B' };

	private Character[] views = { 'U', 'D', 'R', 'L', 'F', 'B' };

	private String[] moves = { "U", "D", "R", "L", "F", "B", "U'", "D'", "R'", "L'", "F", "B'" };

	public Solver() {
		fillCube();
	}

	void fillCube() {

		int j = 0;
		for (char color : colors) {
			Character[][] states = new Character[3][3];
			for (int i = 0; i < 3; i++) {
				Arrays.fill(states[i], color);
			}
			cube.put(views[j++], states);
		}
	}

	///// { Do not Remove this code }
//	public void displayCube() {
//
//		for (Map.Entry<Character, Character[][]> entry : cube.entrySet()) {
//
//			System.out.print(entry.getKey() + " : \n");
//			Character[][] temp = entry.getValue();
//
//			for (int i = 0; i < 3; i++) {
//				for (int k = 0; k < 3; k++) {
//					System.out.print(temp[i][k] + " ");
//				}
//				System.out.println();
//			}
//
//		}
//	}

	public void displayCube() {
		Character[][] U = cube.get('U');
		Character[][] D = cube.get('D');
		Character[][] L = cube.get('L');
		Character[][] R = cube.get('R');
		Character[][] F = cube.get('F');
		Character[][] B = cube.get('B');

		System.out.println("Current Cube State:\n");

		// Print U face
		System.out.println("        [U]");
		for (int i = 0; i < 3; i++) {
			System.out.print("         ");
			for (int j = 0; j < 3; j++) {
				System.out.print(U[i][j] + " ");
			}
			System.out.println();
		}

		// Print L, F, R, B faces in one row
		System.out.println("[L]     [F]     [R]     [B]");
		for (int i = 0; i < 3; i++) {
			// Left
			for (int j = 0; j < 3; j++) {
				System.out.print(L[i][j] + " ");
			}
			System.out.print("   ");

			// Front
			for (int j = 0; j < 3; j++) {
				System.out.print(F[i][j] + " ");
			}
			System.out.print("   ");

			// Right
			for (int j = 0; j < 3; j++) {
				System.out.print(R[i][j] + " ");
			}
			System.out.print("   ");

			// Back
			for (int j = 0; j < 3; j++) {
				System.out.print(B[i][j] + " ");

			}

			System.out.println();
		}

		// Print D face
		System.out.println("        [D]");
		for (int i = 0; i < 3; i++) {
			System.out.print("         ");
			for (int j = 0; j < 3; j++) {
				System.out.print(D[i][j] + " ");

			}
			System.out.println();
		}

		System.out.println(); // Empty line for clarity
	}

	private Character[][] rotateFace(Character[][] face) {
		Character[][] rotated = new Character[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				rotated[j][2 - i] = face[i][j];
			}
		}
		return rotated;
	}

	@Override
	public void rotateU() {

		// Rotate Upper by F->L->B->R
		// LEFT UPPER -> FRONT UPPER
		// BU -> LU
		// RU -> BU
		// FU -> RU

		// Step 1: rotate the Upper face
		cube.put('U', rotateFace(cube.get('U')));

		// Step 2: Rotate the edge rows to left
		Character[] temp = cube.get('L')[0].clone(); // Save L[0]

		cube.get('L')[0] = cube.get('F')[0].clone(); // F → L
		cube.get('F')[0] = cube.get('R')[0].clone(); // R → F
		cube.get('R')[0] = cube.get('B')[0].clone(); // B → R
		cube.get('B')[0] = temp;

	}

	@Override
	public void rotateD() {

		// Step 1: rotate the Upper face
		cube.put('D', rotateFace(cube.get('D')));

		// Step 2: Rotate the edge rows to right
		Character[] temp = cube.get('L')[2].clone(); // Save L[0]

		cube.get('L')[2] = cube.get('B')[2].clone(); // B → L
		cube.get('B')[2] = cube.get('R')[2].clone(); // R → B
		cube.get('R')[2] = cube.get('F')[2].clone(); // F → R
		cube.get('F')[2] = temp;

	}

	@Override
	public void rotateF() {

		// Rotate Front by L->U->R->D->L

		// Step 1: rotate the front face
		cube.put('F', rotateFace(cube.get('F')));

		// Step 2: Rotate the edge rows to clockwise
		Character[] temp = cube.get('U')[2].clone(); // Save U[2..0][2]

		for (int i = 0; i < 3; i++) {
			cube.get('U')[2][i] = cube.get('L')[2 - i][2];
		}

		// D[0][2..0] → L[0..2][2]
		for (int i = 0; i < 3; i++) {
			cube.get('L')[i][2] = cube.get('D')[0][2 - i];
		}

		// R[0..2][0] → D[0][0..2]
		for (int i = 0; i < 3; i++) {
			cube.get('D')[0][i] = cube.get('R')[i][0];
		}

		// temp (U's original row) → R[0..2][0]
		for (int i = 0; i < 3; i++) {
			cube.get('R')[i][0] = temp[i];
		}

	}

	@Override
	public void rotateB() {

		// Step 1: rotate the Back face clockwise
		cube.put('B', rotateFace(cube.get('B')));

		// Step 2: Store U[0]
		Character[] temp = cube.get('U')[0].clone();

		// L[2..0][0] -> U[0][0..2]
		for (int i = 0; i < 3; i++) {
			cube.get('U')[0][i] = cube.get('L')[2 - i][0];
		}

		// D[2][0..2] -> L[0..2][0] ← reverse of L
		for (int i = 0; i < 3; i++) {
			cube.get('L')[i][0] = cube.get('D')[2][i];
		}

		// R[0..2][2]-> D[2][2..0] ← reverse of R
		for (int i = 0; i < 3; i++) {
			cube.get('D')[2][2 - i] = cube.get('R')[i][2];
		}

		// temp -> R[0..2][2]
		for (int i = 0; i < 3; i++) {
			cube.get('R')[i][2] = temp[i];
		}

	}

	@Override
	public void rotateL() {
		// Step 1: Rotate the L face
		cube.put('L', rotateFace(cube.get('L')));

		// Step 2: Save U left column
		Character[] temp = new Character[3];
		for (int i = 0; i < 3; i++) {
			temp[i] = cube.get('U')[i][0];
		}

		// B right col (reverse) -> U left col
		for (int i = 0; i < 3; i++) {
			cube.get('U')[i][0] = cube.get('B')[2 - i][2];
		}

		// D left col -> B right col (reversed)
		for (int i = 0; i < 3; i++) {
			cube.get('B')[i][2] = cube.get('D')[2 - i][0];
		}

		// F left col -> D left col
		for (int i = 0; i < 3; i++) {
			cube.get('D')[i][0] = cube.get('F')[i][0];
		}

		// temp-> F left col
		for (int i = 0; i < 3; i++) {
			cube.get('F')[i][0] = temp[i];
		}

	}

	@Override
	public void rotateR() {

		// Step 1: Rotate the R face
		cube.put('R', rotateFace(cube.get('R')));

		// Step 2: Save U right column
		Character[] temp = new Character[3];
		for (int i = 0; i < 3; i++) {
			temp[i] = cube.get('U')[i][2];
		}

		// F right col -> U right col
		for (int i = 0; i < 3; i++) {
			cube.get('U')[i][2] = cube.get('F')[i][2];
		}

		// D right col -> F right col
		for (int i = 0; i < 3; i++) {
			cube.get('F')[i][2] = cube.get('D')[i][2];
		}

		// B left col (reversed) -> D right col
		for (int i = 0; i < 3; i++) {
			cube.get('D')[i][2] = cube.get('B')[2 - i][0];
		}

		// temp -> B left col (reversed)
		for (int i = 0; i < 3; i++) {
			cube.get('B')[i][0] = temp[2 - i];
		}

	}

	@Override
	public void rotateUPrime() {
		rotateU();
		rotateU();
		rotateU();
	}

	@Override
	public void rotateDPrime() {
		rotateD();
		rotateD();
		rotateD();
	}

	@Override
	public void rotateFPrime() {
		rotateF();
		rotateF();
		rotateF();
	}

	@Override
	public void rotateBPrime() {
		rotateB();
		rotateB();
		rotateB();
	}

	@Override
	public void rotateLPrime() {
		rotateL();
		rotateL();
		rotateL();
	}

	@Override
	public void rotateRPrime() {
		rotateR();
		rotateR();
		rotateR();
	}

	public void scramble(int steps) {

		String[] move = new String[steps];

		Random r = new Random();

		for (int i = 0; i < steps; i++) {
			int idx = r.nextInt(moves.length);
			move[i] = moves[idx];
			this.applyMove(moves[idx]);
		}

//		return cube;

	}

	private void applyMove(String move) {

//		System.out.println(move);
		switch (move) {
		case "U":
			this.rotateU();
			break;
		case "U'":
			this.rotateUPrime();
			break;
		case "D":
			this.rotateD();
			break;
		case "D'":
			this.rotateDPrime();
			break;
		case "F":
			this.rotateF();
			break;
		case "F'":
			this.rotateFPrime();
			break;
		case "B":
			this.rotateB();
			break;
		case "B'":
			this.rotateBPrime();
			break;
		case "L":
			this.rotateL();
			break;
		case "L'":
			this.rotateLPrime();
			break;
		case "R":
			this.rotateR();
			break;
		case "R'":
			this.rotateRPrime();
			break;
		default:
			System.out.println("Invalid move: " + move);
		}

//		this.displayCube();
//		System.out.println("-------------------");

	}

	public void solve() {

		solveWhiteCross();

	}

	private void solveWhiteCross() {
		System.out.println("Solving white cross...");

		// Step 1: bring white edges from D
		moveWhiteEdgesFromD(); // <- this is your safe D-to-U code

		// Step 2: align and insert
		alignWhiteCrossEdges();
	}

	private void alignWhiteCrossEdges() {

	}

	private void moveWhiteEdgesFromD() {

		System.out.println("Step 1: Solve White Cross from D face");

		for (int i = 0; i < 4; i++) {
			Character[][] D = cube.get('D');
			Character[][] U = cube.get('U');

			// If there's a white edge at the front of D face
			if (D[0][1] == 'W') {

				// Rotate U up to 4 times to find an empty spot at U[2][1]
//				for (int j = 0; j < 4; j++) {
//					Character[][] U = cube.get('U');
//
//					if (U[2][1] != 'W') {
//						applyMove("F");
//						applyMove("F"); // Flip white edge to top
//						break;
//					} else {
//						applyMove("U"); // Try the next white slot on U
//					}
//				}
								
				while(U[2][1] == 'W') {
					applyMove("U");
				}
				
				// Rotate the F two times 
				applyMove("F");
				applyMove("F");
				
				// Update the U
				U = cube.get('U');

			}

			applyMove("D"); // Rotate D to bring next white edge to D[0][1]
		}

		System.out.println("Step 1 complete: White edges from D are flipped to U.");

	}

}
