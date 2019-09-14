package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
	final static short X = Puzzle.emptyCellValue;

	public static void main(String[] args) {

		int sizeX = 3;
		int sizeY = 3;
		
		Puzzle p = new Puzzle(new int[] {sizeX, sizeY});
		int difficulty = 3;

		p.generateProblem(getProblem(difficulty));

		int printedXSize = (sizeX-1) + (sizeX*sizeY)*3;
		
		int rep = (printedXSize - 2 - "Problem".length())/2;
		System.out.println(repeatString(printedXSize, "-") + "\n" + repeatString(rep, "-") + " Problem " + repeatString(rep, "-") + "\n" + repeatString(printedXSize, "-"));
		p.printPuzzle(p);

		
		
		Solver sol = new Solver(p);
		Puzzle p_solved = sol.solve();

		rep = (printedXSize - 2 - "Solution".length())/2;
		System.out.println(repeatString(printedXSize, "-") + "\n" + repeatString(rep, "-") + " Solution " + repeatString(rep+1, "-") + "\n" + repeatString(printedXSize, "-"));
		p_solved.printPuzzle(p);
		System.out.println("\nExecuted in " + (sol.getExecutionTime()) + " seconds");
	}


	public static short[][] getProblem(int difficulty){
		List<short[][]> problemEasyRepository = new ArrayList<short[][]>();
		List<short[][]> problemIntermediateRepository = new ArrayList<short[][]>();
		List<short[][]> problemDifficultRepository = new ArrayList<short[][]>();
		List<short[][]> problemInsaneRepository = new ArrayList<short[][]>();
		
		// ---------------------------------------------------------------------------
		// -------------------------- Define EASY problems ---------------------------
		// ---------------------------------------------------------------------------
		
		problemEasyRepository.add(new short[][] {
			{X,X,X,2,6,X,7,X,1},
			{6,8,X,X,7,X,X,9,X},
			{1,9,X,X,X,4,5,X,X},
			{8,2,X,1,X,X,X,4,X},
			{X,X,4,6,X,2,9,X,X},
			{X,5,X,X,X,3,X,2,8},
			{X,X,9,3,X,X,X,7,4},
			{X,4,X,X,5,X,X,3,6},
			{7,X,3,X,1,8,X,X,X}
		});
		/*
		problemEasyRepository.add(new short[][] {
			{X,X,4,X,X,6},
			{2,X,X,X,X,X},
			{4,X,X,2,X,X},
			{X,X,5,X,X,4},
			{X,X,X,X,X,5},
			{1,X,X,4,X,X}
		});
		

		problemEasyRepository.add(new short[][] {
			{X,2,4,X},
			{1,X,X,3},
			{4,X,X,2},
			{X,1,3,X}
		});
		*/
		// ---------------------------------------------------------------------------
		// --------------------- Define INTERMEDIATE problems ------------------------
		// ---------------------------------------------------------------------------
		problemIntermediateRepository.add(new short[][] {
			{X,2,X,6,X,8,X,X,X},
			{5,8,X,X,X,9,7,X,X},
			{X,X,X,X,4,X,X,X,X},
			{3,7,X,X,X,X,5,X,X},
			{6,X,X,X,X,X,X,X,4},
			{X,X,8,X,X,X,X,1,3},
			{X,X,X,X,2,X,X,X,X},
			{X,X,9,8,X,X,X,3,6},
			{X,X,X,3,X,6,X,9,X}
		});
		
		// ---------------------------------------------------------------------------
		// ----------------------- Define DIFFICULT problems -------------------------
		// ---------------------------------------------------------------------------
		
		
		// ---------------------------------------------------------------------------
		// ------------------------- Define INSANE problems --------------------------
		// ---------------------------------------------------------------------------
		problemInsaneRepository.add(new short[][] {
			{8,X,X,X,X,X,X,X,X},
			{X,X,3,6,X,X,X,X,X},
			{X,7,X,X,9,X,2,X,X},
			{X,5,X,X,X,7,X,X,X},
			{X,X,X,X,4,5,7,X,X},
			{X,X,X,1,X,X,X,3,X},
			{X,X,1,X,X,X,X,6,8},
			{X,X,8,5,X,X,X,1,X},
			{X,9,X,X,X,X,4,X,X}
		});
		
		Random rand = new Random();
		
		switch (difficulty) {
		case 0:
			return problemEasyRepository.get(rand.nextInt(problemEasyRepository.size()));
			
		case 1:
			return problemIntermediateRepository.get(rand.nextInt(problemIntermediateRepository.size()));
			
		case 2:
			return problemDifficultRepository.get(rand.nextInt(problemDifficultRepository.size()));
			
		case 3:
			return problemInsaneRepository.get(rand.nextInt(problemInsaneRepository.size()));
			
		default:
			System.err.println("Main::getProblem: Wrong difficulty index.");
			return null;
		}
		
	}
	
	private static String repeatString(int n, String s) {
		return String.join("", Collections.nCopies(n, s));
	}
	
}
