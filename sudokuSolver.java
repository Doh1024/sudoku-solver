/*** KimDohyeongA1Q2
 * 
 * COMP 2140 SECTION D01    
 * Assignment #1, question #1
 * Dohyeong Kim, 7892470
 * May 27th 2021
 *
 * PURPOSE: the purpose of this project is to solve a valid sudoku puzzle 
 */
import java.io.*;
import java.util.*;

public class A1Q2 {
    public static void main(String[] args) {
        String inputArr[];
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Please enter a game board, row by row, with - to represent empty cells and spaces between each cell:");
        String input = keyboard.nextLine();
        inputArr = input.split(" ");
        boolean invalid = false;
        int newSize = (int) Math.sqrt(inputArr.length);
        int newBoard[][] = new int[newSize][newSize];
        Sudoku board = new Sudoku(newSize, newBoard);

        
        //this method is for checking if the board that is inputed has a valid size.
       if (newSize != Math.sqrt(inputArr.length)) {
            invalid = true;
        }
        //this is for changing all dashes to 0s
        for (int i = 0; i < inputArr.length; i++) {
            if (inputArr[i].equals("-")) {
                inputArr[i] = "0";
            }
        }
        //this is for putting 1D arr into the 2D arr
        int num = 0;
        for (int i = 0; i < newSize; i++) {
            for (int j = 0; j < newSize; j++) {
                newBoard[i][j] = Integer.parseInt(inputArr[num++]);
            }
        }
        //this moethod is for checking if there are any numbers in the board that is greater than the size
        //of the board because if it does, then it means its invalid.
        for(int i = 0; i < newSize; i++)
        {
            for(int j = 0; j < newSize; j++)
            {
                if(newBoard[i][j] > newSize)
                {
                    invalid = true;
                }
            }
        }

        
        //this prints out the original board if its not invalid
        if (!invalid) {
            System.out.println("The original board is:\n");
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {
                    if (newBoard[i][j] == 0) {
                        System.out.print("- ");
                    } else {
                        System.out.print(newBoard[i][j] + " ");
                    }
                }
                System.out.print("\n");
            }
        }
        //this is for prining out the solved board of the input and if the board is invalid it will print out 
        //a board full of 0s 
        if (!invalid && board.solve()) {
            newBoard = board.getBoard();
            System.out.println("The solution is :\n");
            for (int i = 0; i < newSize; i++) {
                for (int j = 0; j < newSize; j++) {
                    System.out.print(newBoard[i][j] + " ");
                }
                System.out.print("\n");
            }
        } else {
            newBoard = new int[9][9];
            board = new Sudoku(9, newBoard);
            System.out.println("This board is invalid!\n");
            System.out.println("The original board is:\n");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (newBoard[i][j] == 0) {
                        System.out.print("- ");
                    } else {
                        System.out.print(newBoard[i][j] + " ");
                    }
                }
                System.out.print("\n");
            }

            board.solve();
            newBoard = board.getBoard();

            System.out.println("\n The solution is :\n");
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(newBoard[i][j] + " ");
                }
                System.out.print("\n");
            }
        }

        System.out.print("\nProgram terminated normally.");
    }
}

class Sudoku {
    private int[][] board;
    private int size;
    public static final int BLANK = 0;

    public Sudoku(int s, int gameBoard[][]) {
        board = new int[s][s];
        size = s;
        board = gameBoard;
    }

    public int[][] getBoard() {
        return board;
    }

    /*
    this method is for checking if there are any duplicates in the subbox of the sudoku.
    it takes in 3 ints: row, column, and the number we are looking for. 
    */
    public boolean dupeInSubBox(int r, int c, int num) {
        int col = c - c % (int) Math.sqrt(size);
        int row = r - r % (int) Math.sqrt(size);
        boolean output = false;
        for (int i = row; i < row + (int) Math.sqrt(size); i++) {
            for (int j = col; j < col + (int) Math.sqrt(size); j++) {
                if (board[i][j] == num) {
                    output = true;
                }
            }
        }
        return output;
    }

    /*
    this method is for checking duplicates in row and column.
    */
    public boolean dupeCheck(int r, int c, int num) {
        boolean output = false;

        for (int i = 0; i < board.length; i++) {
            if (board[i][c] == num) {
                output = true;
            }
        }

        for (int i = 0; i < board.length; i++) {
            if (board[r][i] == num) {
                output = true;
            }
        }
        return output;
    }

    /*
    this method is the one that solves the sudoku by backtracking. 
    when the for-loop finds a "blank" in the sudoku it will try numbers from 1-size and will check 
    if number overlaps first by using dupeInSubBox method and dupeCheck method, and if they return false
    it will assign k to the current board space. If solve method returns true, it will recurse.
    */
    public boolean solve() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == BLANK) {
                    for (int k = 1; k <= size; k++) {
                        if (!dupeInSubBox(i, j, k) && !dupeCheck(i, j, k)) {
                            board[i][j] = k;
                            if (solve()) //recurse
                            {
                                return true;
                            } else {
                                board[i][j] = BLANK;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}

/*question 1:
    to store the grid from user input, I stored it as a 1D array first and then made the 1D array 
    into 2D array to get the size of the grid using .length on the 1D array. I also used string first 
    for the dashes but changed everythign into ints when making it into a 2D array.

    question 2: 
    I feel like my entire main method is really inefficient and definetly could be better because 
    it feels like there are a lot of unnecessary methods that could've been a lot shorter.
    also the way I'm making the grid feels very inefficent. 
*/