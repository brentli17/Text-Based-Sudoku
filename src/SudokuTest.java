import java.util.*;

public class SudokuTest {
    //test to see if every row of the sudoku board is valid
    public boolean testRow(int [][] board){
        ArrayList<Integer> row = new ArrayList<>();
        int index = 1;
        //loop through each column
        for(int i = 0; i < 9; i++){
            //loop through each row
            for (int j = 0; j < 9; j++){
                row.add(board[j][i]);
            }
            //sort row then make sure the row goes from 1-9
            Collections.sort(row);
            for(int j = 0; j < 9; j++){
                if (row.get(j) != index){
                    return false;
                }
                index++;
            }
            row.clear();
            index = 1;
        }
        return true;
    }

    //test to see if each column of the sudoku board is valid
    public boolean testColumn(int [][] board){
        ArrayList<Integer> col = new ArrayList<>();
        int index = 1;
        //loop through each row
        for(int i = 0; i < 9; i++){
            //loop through each column
            for (int j = 0; j < 9; j++){
                col.add(board[i][j]);
            }
            //sort row then make sure the column goes from 1-9
            Collections.sort(col);
            for(int j = 0; j < 9; j++){
                if (col.get(j) != index){
                    return false;
                }
                index++;
            }
            col.clear();
            index = 1;
        }
        return true;
    }

    //test to see if every 3x3 square is valid
    public boolean testSmallSquare(int [][] board){
        ArrayList<Integer> smallSquare = new ArrayList<>();
        int x = 3;
        int y = 3;
        int index = 1;

        //loop through all 9 small squares
        for(int row = 0; row < 3; row++){
            for(int col = 0; col < 3; col++){
                //loop through each square in each small square
                for (int i = y - 3; i < y; i++){
                    for (int j = x - 3; j < x; j++){
                        smallSquare.add(board[i][j]);
                    }
                }
                //sort the small square then make sure if the square goes from 1-9
                Collections.sort(smallSquare);
                for(int k = 0; k < 9; k++){
                    if (smallSquare.get(k) != index){
                        return false;
                    }
                    index++;
                }
                smallSquare.clear();
                index = 1;
                y += 3;
            }
            y = 3;
            x += 3;
        }
        return true;
    }

    //draws one sudoku puzzle
    public void drawOnePuzzle(){
        SudokuFunctionality run = new SudokuFunctionality();
        int failedAttempts = 0;

        //draw all 9 numbers onto the sudoku board
        for(int i = 1; i < 10; i++){
            //handles failed attempts to draw i
            while(run.drawNumber(i)){
                run.removeNum(i);
                failedAttempts++;
                //for if the board physically cannot be completed with the current layout
                if(failedAttempts > 30){
                    //clear the board
                    for(int j = 1; j < 10; j++){
                        run.removeNum(j);
                    }
                    //reset i to draw all numbers again
                    i = 1;
                    failedAttempts = 0;
                }
            }
            failedAttempts = 0;
        }
        //display completed sudoku board
        run.displaySudoku();
        testPuzzle(run.getBoard());
        System.out.println();

        //display partially empty sudoku board
        run.removeNums();
        run.displaySudoku();
        System.out.println();

        //display solved sudoku board
        run.solveSudoku();
        run.displaySudoku();
        testPuzzle(run.getBoard());
    }

    //tests to see if a filled sudoku board is valid
    public void testPuzzle(int[][] board){
        if(!testRow(board) || !testColumn(board) || !testSmallSquare(board)){
            if(!testRow(board)){
                System.out.println("Row Test Failed");
                }
            else if(!testColumn(board)){
                System.out.println("Column Test Failed");
                }
            else{
                System.out.println("Small Square Test Failed");
                }
        }
    }
}