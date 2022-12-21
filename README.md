# Sudoku-Solver
A Sudoku solver for any grid size.

The grid sizes ```sizeX``` and ```sizeY``` refer to the smaller rectangle and not the total grid size.
This is because the small cells don't need to be squares and its size defines the size of the total grid.

The main goal was to design the solving algorithm.
For simplicity, a GUI was not implemented. 

The problem grid can be inputed in the Main class.

Author:
* [Rafael Correia](https://sourcerer.io/leafarcoder)

# Examples

### Easy (2X2)
Solved in 0.000s (not enough precision)

![](https://github.com/LeafarCoder/Sudoku-Solver/blob/master/Images/easy_2x2_sudoku.PNG)

---

### Easy (3x2)
Solved in 0.006s

![](https://github.com/LeafarCoder/Sudoku-Solver/blob/master/Images/easy_3x2_sudoku.PNG)

---

### Easy (3x3)
Solved in 0.004s

![](https://github.com/LeafarCoder/Sudoku-Solver/blob/master/Images/easy_3x3_sudoku.PNG)

---

### Intermediate (3x3)
Solved in 0.375s

![](https://github.com/LeafarCoder/Sudoku-Solver/blob/master/Images/intermediate_3x3_sudoku.PNG)

---

### "Hardest" (3x3)
This puzzle was created in 2012 by a Finish mathematicion called Arto Inkala and is said to be the [hardest](https://www.conceptispuzzles.com/index.aspx?uri=info/article/424) 3x3 sudoku puzzle ever.

Solved in 0.712s

![](https://github.com/LeafarCoder/Sudoku-Solver/blob/master/Images/hardest_3x3_sudoku.PNG)

---

# License
This project is under [MIT License](https://github.com/LeafarCoder/Sudoku-Solver/blob/master/LICENSE).
