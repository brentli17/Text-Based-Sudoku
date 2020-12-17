import java.util.*;

public class SudokuFunctionality{
    //Create a 2D array to represent the 9x9 sudoku board
    private int [][] board;
    public boolean drawNumberFailed;

    //constructor
    public SudokuFunctionality(){
        board = new int[9][9];
        drawNumberFailed = false;
    }

    //takes an input num and places them into the grid so that no same numbers are overlapping
    //num will always be 1-9
    public boolean drawNumber(int num){
        //an integer to keep track of how many times num as been attempted to be drawn
        int failed = 0;
        //boolean used to check if drawNumber worked
        drawNumberFailed = false;
        //randomly generated numbers from 0-8 used to choose a random index to place num
        Random rand = new Random();
        int rowRand;
        int colRand;

        //arrays from 0-8 that represent the coordinates for each row/column
        ArrayList <Integer> row = new ArrayList<>();
        ArrayList <Integer> col = new ArrayList<>();

        //initialize both row and column arrays to 0-8
        for (int i = 0; i < 9; i++){
            row.add(i);
            col.add(i);
        }

        //add num to a random spot from row and col arrays, then remove that spot so there are no duplicates in the same row/column
        for (int i = 0; i < 9; i++){
            //check if the random spot already has a number in it
            do{
                rowRand = rand.nextInt(row.size());
                colRand = rand.nextInt(col.size());

                if(failed >= (row.size() * col.size())){
                    drawNumberFailed = true;
                    return true;
                }
                failed++;
            }
            while((board[row.get(rowRand)][col.get(colRand)] != 0) || (smallSquareDuplicates(col.get(colRand), row.get(rowRand), num)));

            //add num into the board and remove that spot from the 2 arraylists
            board[row.get(rowRand)][col.get(colRand)] = num;
            row.remove(rowRand);
            col.remove(colRand);
            failed = 0;
        }
        return false;
    }

    //removes all instances of num from the board
    public void removeNum(int num){
        for(int row = 0; row < 9; row++){
            for(int col = 0; col < 9; col++){
                if(board[row][col] == num){
                    board[row][col] = 0;
                }
            }
        }
    }

    //test for duplicate numbers in the small 3x3 squares
    public boolean smallSquareDuplicates(int x, int y, int num){
        int upperRow = 0;
        int lowerRow = 0;
        int upperCol = 0;
        int lowerCol = 0;

        //find which 3x3 square the point is in
        if(x >= 0 && x <= 2){
            lowerCol = 0;
            upperCol = 2;
        }
        else if(x >= 3 && x <= 5){
            lowerCol = 3;
            upperCol = 5;
        }
        else if(x >= 6 && x <= 8){
            lowerCol = 6;
            upperCol = 8;
        }
        if(y >= 0 && y <= 2){
            lowerRow = 0;
            upperRow = 2;
        }
        else if(y >= 3 && y <= 5){
            lowerRow = 3;
            upperRow = 5;
        }
        else if(y >= 6 && y <= 8){
            lowerRow = 6;
            upperRow = 8;
        }

        //cycle through the 3x3 square and make sure there are no duplicates
        for(int row = lowerRow; row <= upperRow; row++){
            for(int col = lowerCol; col <= upperCol; col++){
                if(board[row][col] == num){
                    return true;
                }
            }
        }
        return false;
    }

    //removes numbers from the sudoku board to create the final game
    public void removeNums(){
        Random rand = new Random();
        //decides how many numbers to remove in each row
        int randRemove;
        int randomNum;

        //cycle through 5 rows
        for (int i = 0; i <= 4; i++){
            randRemove = rand.nextInt(10000) / 5000 + 5;
            if(i == 4){
                if(randRemove % 2 != 0){
                    board[i][i] = 0;
                }
                randRemove /= 2;
            }

            for(int j = 0; j < randRemove; j++){
                //choose a random number that hasn't been chosen yet
                do{
                    randomNum = rand.nextInt(9);
                }
                while(board[i][randomNum] == 0);

                //remove the number from the chosen random position from the corresponding mirrored rows
                board[i][randomNum] = 0;
                board[8 - i][8 - randomNum] = 0;
            }
        }
    }

    //tests to see if num is valid in the position that it is being placed
    public boolean numValid(int num, int row, int col, int[][] sudokuBoard){
        //test rows and columns
        for(int i = 0; i < 9; i++){
            if((sudokuBoard[row][i] == num) || (sudokuBoard[i][col] == num)){
                return false;
            }
        }

        //test 3x3 square
        int lowerRowBounds = row / 3 * 3;
        int lowerColBounds = col / 3 * 3;

        //test the num for the 3x3 square
        for(int r = lowerRowBounds; r <= lowerRowBounds + 2; r++) {
            for (int c = lowerColBounds; c <= lowerColBounds + 2; c++) {
                if (sudokuBoard[r][c] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    //recursive method to solve a sudoku puzzle
    public boolean solveSudoku(){
        int row = -1;
        int col = -1;
        boolean isFull = true;

        //loop through the board and find any blank squares
        for(int r = 0; r < 9; r++){
            for(int c = 0; c < 9; c++){
                if(board[r][c] == 0){
                    row = r;
                    col = c;
                    isFull = false;
                    break;
                }
            }
            if(!isFull){
                break;
            }
        }

        //base case to break the recursive loop
        if(isFull){
            return true;
        }

        //test all 9 numbers for each blank square
        for(int n = 1; n <= 9; n++){
            //if there is a valid number that can be placed, place it and keep going
            //if the recursion fails at some point, remove the previous number and try again
            if(numValid(n, row, col, board)){
                board[row][col] = n;
                if(solveSudoku()){
                    return true;
                }
                else{
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }

    //displays the sudoku board
    public void displaySudoku(){
        for(int row = 0; row < 9; row++){
            System.out.println();
            for(int col = 0; col < 9; col++){
                //System.out.print( board[row][col] != 0 ? board[row][col] + " " : "  ");
                System.out.print( board[row][col] + " ");
            }
        }
    }

    //returns the game board
    public int[][] getBoard(){
        return board;
    }
}