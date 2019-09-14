package sudoku;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Puzzle {

	public static short emptyCellValue = 0;

	// short: [-32.768 ; 32.767]. More than enough for the purposes of this project
	private short puzzle[][];
	private int[] smallGridSize;
	private ArrayList<Short>[][] guess;
	private int[] lastGuessPos;
	private short lastGuessValue;

	private boolean error;

	public Puzzle() {

	}

	@SuppressWarnings("unchecked")
	public Puzzle(int[] smallGridSize) {

		this.smallGridSize = smallGridSize;

		// input check
		if(smallGridSize[0] < 1 || smallGridSize[1] < 1) {
			System.err.println("Puzzle::Puzzle: Invalid small grid size. Both dimensions must be larger than 0!");
			puzzle = new short[0][0];
			error = true;
			return;
		}

		int totalSize = smallGridSize[0] * smallGridSize[1];
		puzzle = new short[totalSize][totalSize];
		guess = new ArrayList[totalSize][totalSize];
		for(int i = 0; i < totalSize; i++) {
			for(int j = 0; j < totalSize; j++) {
				guess[i][j] = new ArrayList<Short>();
			}
		}
	}

	public int[] getLastGuessPos() {
		return lastGuessPos;
	}

	public void setLastGuessPos(int[] lastGuessPos) {
		this.lastGuessPos = lastGuessPos;
	}

	public short getLastGuessValue() {
		return lastGuessValue;
	}

	public void setLastGuessValue(short lastGuessValue) {
		this.lastGuessValue = lastGuessValue;
	}

	public int[] getSmallGridSize() {
		return smallGridSize;
	}

	public short getCellValue(int RowCoord, int ColCoord) {
		// Cell numbering is indexed to 1 (not 0)
		return puzzle[RowCoord - 1][ColCoord - 1];
	}

	public short[][] getGrid(){
		return this.puzzle;
	}

	public ArrayList<Short> getGuessAt(int i, int j){
		return guess[i][j];
	}

	public ArrayList<Short>[][] getGuesses(){
		return guess;
	}

	public int getNumOfEmptyCells() {
		int ans = 0;
		int totalSize = smallGridSize[0] * smallGridSize[1];
		for(int i = 0; i < totalSize; i++) {
			for(int j = 0; j < totalSize; j++) {
				if(emptyCellQ(i + 1, j + 1)) {
					ans++;
				}
			}
		}

		return ans;
	}

	public int[] getPositionWithLessGuesses() {
		// Get the coordinates of the cell with less guesses. If minimum occurs in multiple cells pick at random.
		ArrayList<int[]> ansList = new ArrayList<int[]>();
		int less = Integer.MAX_VALUE;
		Random rand = new Random();
		int totalSize = smallGridSize[0] * smallGridSize[1];
		
		/*
		int[] pos = new int[2];
		pos[0] = rand.nextInt(totalSize);
		pos[1] = rand.nextInt(totalSize);
		while(!emptyCellQ(pos[0]+1, pos[1]+1)) {
			pos[0] = rand.nextInt(totalSize);
			pos[1] = rand.nextInt(totalSize);
		}
		return pos;
		*/

		for(int i = 0; i < totalSize; i++) {
			for(int j = 0; j < totalSize; j++) {
				if(!guess[i][j].isEmpty() && emptyCellQ(i+1, j+1)) {
					if(guess[i][j].size() < less) {
						ansList = new ArrayList<int[]>();
						ansList.add(new int[] {i, j});
						less = guess[i][j].size();
					}else if(guess[i][j].size() == less) {
						ansList.add(new int[] {i, j});
					}
				}
			}
		}
		 
		
		if(ansList.isEmpty()) {
			return new int[] {-1,-1};
		}else {
			return ansList.get(rand.nextInt(ansList.size()));
		}

	}

	public void setGuesses(ArrayList<Short>[][] guess){
		this.guess = guess;
	}

	public void setCell(int RowCoord, int ColCoord, short value) {
		int totalSize = smallGridSize[0] * smallGridSize[1];
		if(RowCoord < 1 || RowCoord > totalSize || ColCoord < 1 || ColCoord > totalSize) {
			return;
		}
		// Cell numbering is indexed to 1 (not 0)
		puzzle[RowCoord - 1][ColCoord - 1] = value;
	}

	public boolean errorQ() {
		return error;
	}

	public void eraseCell(int RowCoord, int ColCoord) {
		// Cell numbering is indexed to 1 (not 0)
		puzzle[RowCoord - 1][ColCoord - 1] = emptyCellValue;
	}

	public boolean emptyCellQ(int RowCoord, int ColCoord) {
		// Cell numbering is indexed to 1 (not 0)
		return (puzzle[RowCoord - 1][ColCoord - 1] == emptyCellValue);
	}

	public void printPuzzle(Puzzle origPuzzle) {
		int g_r = smallGridSize[0];
		int g_c = smallGridSize[1];
		int g_t = g_r * g_c;	// total grid size

		/*
		StringBuffer buffer = new StringBuffer();

		for(int i = 0; i < g_t; i++) {
			for(int j = 0; j < g_t; j++) {
				if(puzzle[i][j] != emptyCellValue) {
					if(puzzle[i][j] <= 9) {
						buffer.append(puzzle[i][j] + " ");
					}else if(puzzle[i][j] <= 35) {
						buffer.append((char)(puzzle[i][j] + 55));
						buffer.append(" ");
					}else {
						buffer.append("* ");
					}
					if(origPuzzle.emptyCellQ(i + 1, j + 1)) {
						System.err.print(buffer.toString());
					}else {
						System.out.print(buffer.toString());
					}
					buffer.setLength(0);
				}else {
					System.out.print(". ");
				}
				if(j % g_c == g_c - 1)System.out.print("  ");
			}
			System.out.print("\n");
			if(i % g_r == g_r - 1)System.out.print("\n");
		}

		 */

		StringBuffer buffer = new StringBuffer();

		for(int i = 0; i < g_t; i++) {
			for(int j = 0; j < g_t; j++) {
				if(puzzle[i][j] != emptyCellValue) {
					if(!origPuzzle.emptyCellQ(i + 1, j + 1)) {
						// Original puzzle hints
						if(puzzle[i][j] <= 9) {
							buffer.append("[" + puzzle[i][j] + "]");
						}else if(puzzle[i][j] <= 35) {
							buffer.append("[");
							buffer.append((char)(puzzle[i][j] + 55));
							buffer.append("]");
						}else {
							buffer.append("[*]");
						}
					}else {
						if(puzzle[i][j] <= 9) {
							buffer.append(" " + puzzle[i][j] + " ");
						}else if(puzzle[i][j] <= 35) {
							buffer.append(" ");
							buffer.append((char)(puzzle[i][j] + 55));
							buffer.append(" ");
						}else {
							buffer.append(" * ");
						}
					}

					System.out.print(buffer.toString());
					buffer.setLength(0);

				}else {
					System.out.print(" . ");
				}
				if(j % g_c == g_c - 1 && j != g_t-1)System.out.print("|");
			}
			System.out.print("\n");
			if(i % g_r == g_r - 1)System.out.print(repeatString(g_t*3 + (g_c-1), "-") + "\n");
		}
	}

	public void printGuesses() {
		int totalSize = smallGridSize[0] * smallGridSize[1];

		for(int i = 0; i < totalSize; i++) {
			for(int j = 0; j < totalSize; j++) {
				if(!guess[i][j].isEmpty()) {
					System.out.print((i+1) + ","+ (j+1) + " >> ");
					for(int k = 0; k < guess[i][j].size(); k++) {
						System.out.print(guess[i][j].get(k));
						if(k != guess[i][j].size() - 1) {
							System.out.print(", ");
						}
					}
					System.out.print("\n");
				}
			}
		}

	}

	static Puzzle copyPuzzle(Puzzle puzzle) {
		int[] smallGridSize =  puzzle.getSmallGridSize();
		Puzzle ans = new Puzzle(smallGridSize);
		int totalSize = smallGridSize[0] * smallGridSize[1];

		// copy grid
		for(int i = 1; i <= totalSize; i++) {
			for(int j = 1; j <= totalSize; j++) {
				ans.setCell(i, j, puzzle.getCellValue(i, j));
			}
		}

		// copy guesses
		for(int i = 0; i < totalSize; i++) {
			for(int j = 0; j < totalSize; j++) {
				for(int k = 0; k < puzzle.guess[i][j].size(); k++) {
					ans.guess[i][j].add(puzzle.guess[i][j].get(k));
				}
			}
		}

		return ans;
	}

	public void copyGrid(Puzzle puzzle) {
		int[] smallGridSize =  puzzle.getSmallGridSize();
		int totalSize = smallGridSize[0] * smallGridSize[1];

		// copy grid
		for(int i = 1; i <= totalSize; i++) {
			for(int j = 1; j <= totalSize; j++) {
				this.setCell(i, j, puzzle.getCellValue(i, j));
			}
		}
	}

	public void generateProblem(short[][] hints) {
		int totalSize = smallGridSize[0] * smallGridSize[1];
		for(int i = 0; i < totalSize; i++) {
			for(int j = 0; j < totalSize; j++) {
				if(hints[i][j] == 0) {
					puzzle[i][j] = emptyCellValue;
				}else {
					puzzle[i][j] = hints[i][j];
				}
			}
		}

		if(!this.validPuzzleQ()) {
			error = true;
		}
	}

	public boolean validPuzzleQ() {

		int totalSize = smallGridSize[0] * smallGridSize[1];

		// -------------------- Check against row, column and square --------------------
		// -------------------- Check if available guesses are enough --------------------

		// check rows and columns
		for(int i = 0; i < totalSize; i++) {
			// filled values
			ArrayList<Integer> row_filled = new ArrayList<Integer>();
			ArrayList<Integer> col_filled = new ArrayList<Integer>();

			// available guesses + filled guesses
			ArrayList<Integer> row_avail = new ArrayList<Integer>();
			ArrayList<Integer> col_avail = new ArrayList<Integer>();

			for(int j = 0; j < totalSize; j++) {

				// row
				if(!emptyCellQ(i + 1, j + 1)) {
					int val = (int)getCellValue(i + 1, j + 1);
					if(row_filled.contains(val)) {
						// repeated appearance
						return false;
					}else {
						row_filled.add(val);
					}
					if(!row_avail.contains(val)) {
						row_avail.add(val);
					}
				}else {
					for(int k = 0; k < guess[i][j].size(); k++) {
						row_avail.add((int)guess[i][j].get(k));
					}
				}

				// column
				if(!emptyCellQ(j + 1, i + 1)) {
					int val = (int)getCellValue(j + 1, i + 1);
					if(col_filled.contains(val)) {
						// repeated appearance
						return false;
					}else {
						col_filled.add(val);
					}
					if(!col_avail.contains(val)) {
						col_avail.add(val);
					}
				}else {
					for(int k = 0; k < guess[j][i].size(); k++) {
						col_avail.add((int)guess[j][i].get(k));
					}
				}
			}

			for(int k = 1; k <= totalSize; k++) {
				if(!row_avail.contains(k)) {
					// System.err.println("Row");
					return false;
				}
				if(!col_avail.contains(k)) {
					// System.err.println("Column");
					return false;
				}
			}
		}

		// for each small square
		for(int i = 0; i < totalSize; i += smallGridSize[0]) {
			for(int j = 0; j < totalSize; j += smallGridSize[1]) {
				ArrayList<Integer> square_filled = new ArrayList<Integer>();
				ArrayList<Integer> square_avail = new ArrayList<Integer>();

				// for each cell of the small square
				for(int m = 0; m < smallGridSize[0]; m++) {
					for(int n = 0; n < smallGridSize[1]; n++) {

						if(!emptyCellQ(i+m + 1, j+n + 1)) {
							int val = (int)getCellValue(i+m + 1, j+n + 1);
							if(square_filled.contains(val)) {
								// repeated appearance
								return false;
							}else {
								square_filled.add(val);
							}
							if(!square_avail.contains(val)) {
								square_avail.add(val);
							}
						}else {
							for(int k = 0; k < guess[i+m][j+n].size(); k++) {
								square_avail.add((int)guess[i+m][j+n].get(k));
							}
						}
					}
				}

				for(int k = 1; k <= totalSize; k++) {
					if(!square_avail.contains(k)) {
						// System.err.println("Square");
						return false;
					}
				}

			}
		}





		return true;
	}

	@SuppressWarnings("unchecked")
	public void initializeGuesses() {
		int totalSize = smallGridSize[0] * smallGridSize[1];
		guess = new ArrayList[totalSize][totalSize];
		for(int i = 0; i < totalSize; i++) {
			for(int j = 0; j < totalSize; j++) {
				guess[i][j] = new ArrayList<Short>();
			}
		}

		for(int i = 0; i < totalSize; i++) {
			for(int j = 0; j < totalSize; j++) {
				// add all guesses
				if(emptyCellQ(i + 1, j + 1)) {
					for(short k = 1; k <= totalSize; k++) {
						guess[i][j].add(k);
					}
				}
			}
		}

		deleteGuessesByBasicRules();
	}

	public void deleteGuessesByBasicRules() {

		// For each cell, create the List with all possible guesses (based on naive observation of row, column and square)

		// System.out.print("\n\n");

		int totalSize = smallGridSize[0] * smallGridSize[1];
		for(int i = 0; i < totalSize; i++) {
			for(int j = 0; j < totalSize; j++) {
				// If already filled with certainty skip guesses
				if(emptyCellQ(i + 1, j + 1)) {

					ArrayList<Short> remove = new ArrayList<Short>();
					// remove according to small square
					int x = Math.floorDiv(i, smallGridSize[0]) * smallGridSize[0];
					int y = Math.floorDiv(j, smallGridSize[1]) * smallGridSize[1];
					for(int m = x; m < x + smallGridSize[0]; m++) {
						for(int n = y; n < y + smallGridSize[1]; n++) {
							if(!emptyCellQ(m + 1, n + 1)) {
								short num = getCellValue(m + 1, n + 1);
								if(!remove.contains(num)) {
									remove.add(num);
								}
							}
						}
					}

					// remove according to row
					for(int k = 0; k < totalSize; k++) {
						if(!emptyCellQ(k + 1, j + 1)) {
							short num = getCellValue(k + 1, j + 1);
							if(!remove.contains(num)) {
								remove.add(num);
							}
						}
					}

					// remove according to column
					for(int k = 0; k < totalSize; k++) {
						if(!emptyCellQ(i + 1, k + 1)) {
							short num = getCellValue(i + 1, k + 1);
							if(!remove.contains(num)) {
								remove.add(num);
							}
						}
					}

					guess[i][j].removeAll(remove);
				}


			}
		}
	}

	public void fillIfPossible() {
		int totalSize = smallGridSize[0] * smallGridSize[1];

		boolean changed = true;
		while(changed) {
			changed = false;
			for(int i = 0; i < totalSize; i++) {
				for(int j = 0; j < totalSize; j++) {
					if(emptyCellQ(i + 1, j + 1)) {
						if(guess[i][j].size() == 1) {
							// Grab the only option
							puzzle[i][j] = guess[i][j].get(0);
							// Empty list
							guess[i][j] = new ArrayList<Short>();
							changed = true;
						}
					}
				}
			}

			// this.deleteGuessesIfOnly();
			this.deleteGuessesByBasicRules();
		}

	}

	@SuppressWarnings("unchecked")
	public void deleteGuessesIfOnly() {
		
		int g_x = smallGridSize[0];
		int g_y = smallGridSize[1];
			
		// if the guess in a certain cell is the only guess available in a row, column or square then that is the answer
		int totalSize = g_x * g_y;
		ArrayList<Short>[] row = new ArrayList[totalSize];
		ArrayList<Short>[] column = new ArrayList[totalSize];
		ArrayList<Short>[][] square = new ArrayList[g_y][g_x];

		for(int i = 0; i < totalSize; i++) {
			row[i] = new ArrayList<Short>();
			column[i] = new ArrayList<Short>();
		}
		for(int i = 0; i < g_y; i++) {
			for(int j = 0; j < g_x; j++) {
				square[i][j] = new ArrayList<Short>();
			}
		}

		// Generate list
		for(int i = 0; i < totalSize; i++) {
			for(int j = 0; j < totalSize; j++) {
				if(emptyCellQ(i + 1, j + 1)) {
					row[i].addAll(guess[i][j]);
					column[j].addAll(guess[i][j]);

					int s_i = i / g_x;
					int s_j = j / g_y;
					square[s_i][s_j].addAll(guess[i][j]);

					// scanner.nextLine();
				}
			}
		}

		// Iterate through lists and change guesses if needed
		for(int i = 0; i < totalSize; i++) {
			int[] row_b = new int[totalSize];
			int[] column_b = new int[totalSize];

			for(int j = 0; j < row[i].size(); j++) {
				row_b[row[i].get(j)-1]++;
			}
			for(int j = 0; j < column[i].size(); j++) {
				column_b[column[i].get(j)-1]++;
			}
			for(int j = 0; j < totalSize; j++) {
				if(row_b[j] == 1) {
					
					for(int k = 0; k < totalSize; k++) {
						if(guess[i][k].contains((short)(j+1))){
							
							System.err.println("Row: " + i + "," + k + ": " + (j+1));
							// scanner.nextLine();
							// Grab the only option
							puzzle[i][k] = (short)(j+1);
							// Empty list
							guess[i][k] = new ArrayList<Short>();
						}
					}
				}
				if(column_b[j] == 1) {
					for(int k = 0; k < totalSize; k++) {
						if(guess[k][i].contains((short)(j+1))){
							System.err.println("Column: " + k + "," + i + ": " + (j+1));
							// scanner.nextLine();
							// Grab the only option
							puzzle[k][i] = (short)(j+1);
							// Empty list
							guess[k][i] = new ArrayList<Short>();
						}
					}
				}
			}

			for(int j = 0; j < totalSize; j++) {
				int s_i = i / g_x;
				int s_j = j / g_y;
				int[] square_b = new int[totalSize];

				for(int k = 0; k < square[s_i][s_j].size(); k++) {
					square_b[square[s_i][s_j].get(k)-1]++;
				}
				for(int k = 0; k < totalSize; k++) {
					if(square_b[k] == 1) {
						
						for(int m = 0; m < g_x; m++) {
							for(int n = 0; n < g_y; n++) {
								if(guess[g_x*s_i+m][g_y*s_j+n].contains((short)(k+1))){
									System.err.println("Square");
									// Grab the only option
									puzzle[g_x*s_i+m][g_y*s_j+n] = (short)(k+1);
									// Empty list
									guess[g_x*s_i+m][g_y*s_j+n] = new ArrayList<Short>();
								}
							}
						}
					}
				}
			}
		}
	}

	public void eraseGuessAt(int i, int j, short num) {
		guess[i][j].remove(guess[i][j].indexOf(num));
	}

	private static String repeatString(int n, String s) {
		return String.join("", Collections.nCopies(n, s));
	}

}












































