package sudoku;

import java.util.ArrayList;
import java.util.Random;

public class Solver {

	private Puzzle origPuzzle;
	private Puzzle[] puzzleArray;
	private int puzzleArrayIndex;
	static int[] smallGridSize;
	private double executionTime;

	public Solver(Puzzle puzzle) {
		this.origPuzzle = puzzle;
		
		smallGridSize = origPuzzle.getSmallGridSize();
		// at tops, we will only need 'numberOfEmptyCells' ramifications of the puzzle
		puzzleArray = new Puzzle[puzzle.getNumOfEmptyCells() + 1];
		puzzleArray[0] = Puzzle.copyPuzzle(this.origPuzzle);
		puzzleArray[0].initializeGuesses();
		puzzleArrayIndex = 0;
	}

	public Puzzle solve(){
		long start = System.currentTimeMillis();
		
		puzzleArray[0].initializeGuesses();
		
		// check if initial problem is valid
		if(!puzzleArray[0].validPuzzleQ()) {
			System.err.println(">> Solver::solve: Original problem is not valid!");
		}

		// **** SOLVING ALGORITHM ****
		while(puzzleArray[puzzleArrayIndex].getNumOfEmptyCells() > 0 || !puzzleArray[puzzleArrayIndex].validPuzzleQ()) {
			
			// try to fill with obvious numbers
			puzzleArray[puzzleArrayIndex].fillIfPossible(); 

			
			
			// puzzleArray[puzzleArrayIndex].printPuzzle(origPuzzle);
			
			// if new update incurs in errors then go back
			if(!puzzleArray[puzzleArrayIndex].validPuzzleQ()) {
				
				if(puzzleArrayIndex > 0) {
					puzzleArrayIndex--;
				}
				
				// just delete the last guess
				int[] last_pos = puzzleArray[puzzleArrayIndex].getLastGuessPos();
				puzzleArray[puzzleArrayIndex].eraseCell(last_pos[0] + 1, last_pos[1] + 1);
				continue;
			}

			if(puzzleArray[puzzleArrayIndex].getNumOfEmptyCells() == 0 && puzzleArray[puzzleArrayIndex].validPuzzleQ()) {
				continue;
			}
			
			// Start with the cells with the lowest number of guesses and try guesses at random
			int[] pos = puzzleArray[puzzleArrayIndex].getPositionWithLessGuesses();
			if(pos[0] == -1) {
				if(puzzleArrayIndex > 0) {
					puzzleArrayIndex--;
					int[] last_pos = puzzleArray[puzzleArrayIndex].getLastGuessPos();
					puzzleArray[puzzleArrayIndex].eraseCell(last_pos[0] + 1, last_pos[1] + 1);
				}
			}else {
				ArrayList<Short> guessList = puzzleArray[puzzleArrayIndex].getGuessAt(pos[0], pos[1]);
				Random rand = new Random();
				short newGuess = guessList.get(rand.nextInt(guessList.size()));
				
				// puzzleArray[puzzleArrayIndex].printGuesses();
				// System.out.println("\nPos: " + (pos[0]+1) + ", " + (pos[1]+1) + ": " + newGuess);
				
				puzzleArray[puzzleArrayIndex].setLastGuessPos(pos);
				puzzleArray[puzzleArrayIndex].setLastGuessValue(newGuess);
				
				// erase the guess from the list of guesses
				puzzleArray[puzzleArrayIndex].eraseGuessAt(pos[0], pos[1], newGuess);
				// update cell
				puzzleArray[puzzleArrayIndex].setCell(pos[0] + 1, pos[1] + 1, newGuess);
				
				
				puzzleArray[puzzleArrayIndex + 1] = Puzzle.copyPuzzle(puzzleArray[puzzleArrayIndex]);
				puzzleArrayIndex++;
			}
			
			// System.out.println("\nFinal");
			// puzzleArray[puzzleArrayIndex].printPuzzle(origPuzzle);2 
		}
		
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;
		
		// System.out.println("\nThe final solution is " + ((puzzleArray[puzzleArrayIndex].validPuzzleQ())? "" : "not") + "valid!\n");
		executionTime = timeElapsed/1000.;

		return puzzleArray[puzzleArrayIndex];
		
	}
	
	public double getExecutionTime() {
		return executionTime;
	}

}

