package com;

//import java.util.Map;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the number of random moves to scramble: ");
		int moves = scanner.nextInt();
		
		Solver solve = new Solver();
		solve.displayCube();

		solve.scramble(moves);
		
//		solve.displayCube();
//		solve.solve();
//		solve.displayCube();
		
//		solve.rotateLPrime();
//		solve.displayCube();
//
//		solve.rotateDPrime();
//		solve.displayCube();

//		solve.rotateL();
		solve.displayCube();
		
		scanner.close();
		

	}

}
