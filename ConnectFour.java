// Krish Doshi
// 10/12/2024
// CSE 123 
// C1: Abstract Strategy Game
// TA: Cynthia

import java.util.*;

// Represents the Connect Four game in which in which
// two players can play the game on a turn by turn basis
public class ConnectFour extends AbstractStrategyGame{
    private char[][] board;
    private boolean isPlayer1Turn;

    // Behavior: Constructs a new Connect Four game in which player one starts
    public ConnectFour(){
        board = new char[][]{{'=', '=', '=', '=', '=', '=', '='},
                             {'=', '=', '=', '=', '=', '=', '='},
                             {'=', '=', '=', '=', '=', '=', '='},
                             {'=', '=', '=', '=', '=', '=', '='},
                             {'=', '=', '=', '=', '=', '=', '='},
                             {'=', '=', '=', '=', '=', '=', '='}};

        isPlayer1Turn = true;
    }

    @Override
    // Behavior: provides the instructions for the Connect Four game to the client (including
    //            removing pieces)
    // Returns: complete instructions for Connect Four
    public String instructions() {
        String instructions = "";
        instructions += "Player 1 goes first. The player can either choose to add (type A) a\n";
        instructions += "piece to the board or remove one of their own pieces from the bottom \n";
        instructions += "row (type R). They do this by entering the column number of the\n";
        instructions += "corresponding column they want to make the action, where 0 is the far\n";
        instructions += "left and 6 is the far right. Spaces show as a = are empty. \n";
        instructions += "Player 1's spaces are represented by 1 and Player 2's spaces are\n";
        instructions += "represented by 2. The game ends when one player marks four spaces in\n";
        instructions += "a row, in which case that player wins, or when the board is full, in\n";
        instructions += "which case the game ends in a tie.\n";
        return instructions;
    }

    @Override
    // Behavior: creates a representation of the 7 wide by 6 tall Connect Four game board 
    //           with = representing an unoccupied space, 1 representing a space occupied
    //           by player 1, and 2 representing a space occupied by player 2
    // Returns: updated Connect Four game board representation
    public String toString() {
        String gameBoard = "";

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                gameBoard += board[i][j] + " ";
            }

            gameBoard += "\n";
        }

        return gameBoard;
    }

    @Override
    // Behavior: determines whether the game is still going, a player has won (four in a row 
    //           either horizontal, vertical, or diagonal), or a tie has occurred
    // Returns: 1 if player 1 has won the game, 2 if player 2 has won the game, 0 if the game
    //          has ended in a tie, and -1 if the game is ongoing
    public int getWinner() {
        boolean emptySpaceLeft = false;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                if (j < 4 && board[i][j] == board[i][j + 1] && //horizontal four in a row
                        board[i][j + 1] == board[i][j + 2] && 
                                board[i][j + 2] == board[i][j + 3] && board[i][j] != '=') {

                    if(board[i][j] == '1'){
                        return 1;
                    }

                    return 2;
                }

                if (i < 3 && board[i][j] == board[i + 1][j] && //vertical four in a row
                        board[i + 1][j] == board[i + 2][j] && 
                                board[i + 2][j] == board[i + 3][j] && board[i][j] != '=') {

                    if(board[i][j] == '1'){
                        return 1;
                    }

                    return 2;
                }

                if(i < 3 && j < 4) {
                    if (board[i][j] == board[i + 1][j + 1] && //diagonal (top to bottom)
                            board[i + 1][j + 1] == board[i + 2][j + 2] && 
                                    board[i + 2][j + 2] == board[i + 3][j + 3] &&
                                             board[i][j] != '=') {

                        if(board[i][j] == '1'){
                            return 1;
                        }
    
                        return 2;
                        
                    }

                    if (board[i + 3][j] == board[i + 2][j + 1] && //diagonal (bottom to top)
                            board[i + 2][j + 1] == board[i + 1][j + 2] && 
                                    board[i + 1][j + 2] == board[i][j + 3] && 
                                            board[i + 3][j] != '=') {

                        if(board[i + 3][j] == '1'){
                            return 1;
                        }
    
                        return 2;
                    }
                }

                if(board[i][j] == '='){
                    emptySpaceLeft = true;
                }
            }
        }

        if(emptySpaceLeft){
            return -1; //the game is still going
        }

        return 0; //game resulted in a tie (no winner, but all spaces filled)
    }

    @Override
    // Behavior: provides which player's turn it is
    // Returns: 1 if it's player 1's turn, 2 if it's player 2's turn, -1 if the game
    //          is over
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }

        if (isPlayer1Turn) {
            return 1;
        }

        return 2;
    }

    @Override
    // Behavior: has the player make their move (adding or removing a piece) and switches to the
    //           other player's turn afterwards
    // Exceptions: Throws an IllegalArgumentException if the client enters something other than 
    //             A or R for the type of action they will be doing, if they enter an invalid 
    //             column (less than 0 or greater than 6), if the column that they entered
    //             is already completely full when the they are trying to add a piece, if they
    //             are trying to remove a piece from an empty column, or if the they
    //             are trying to remove a piece that isn't their own
    // Parameter:
    //          - 'input': used to get user input (determining whether the player is adding or
    //                     removing a piece and which column they are doing that action in). It
    //                     is assumed to be non-null
    public void makeMove(Scanner input) {
        char currPlayer;

        if (isPlayer1Turn) {
            currPlayer = '1';
        }else {
            currPlayer = '2';
        }

        System.out.print("Remove(R) or Add(A): ");
        String action = input.next();

        if(!action.equalsIgnoreCase("R") && !action.equalsIgnoreCase("A")){
            throw new IllegalArgumentException("Invalid action type: " + action);
        }

        System.out.print("Column (0 - 6): ");
        int column = input.nextInt();

        makeMove(column, action, currPlayer);
        isPlayer1Turn = !isPlayer1Turn;
    }

    // Behavior: has the player make their move (adding or removing a piece) and switches to the
    //           other player's turn afterwards
    // Exceptions: Throws an IllegalArgumentException if the client enters an invalid column
    //             (less than 0 or greater than 6), if the column that they entered is
    //             already completely full when they are trying to add a piece, if they
    //             are trying to remove a piece from an empty column, or if they
    //             are trying to remove a piece that isn't their own
    // Parameter:
    //          - 'col': represents the column that the player wants to do the action in
    //          - 'action': either an A or R to determine whether the player will be adding or
    //                      removing a piece
    //          - 'player': either a 1 or 2 to represent the piece of the player whose turn it is
    private void makeMove(int column, String action, char player) {
        if (column < 0 || column >= board[0].length) {
                throw new IllegalArgumentException("Invalid move: " + column);
        }

        if (action.equalsIgnoreCase("A")) {
            if (board[0][column] != '=') {
                throw new IllegalArgumentException("Column completely occupied: " + column);
            }

            for (int i = board.length - 1; i >= 0; i--) {

                if (board[i][column] == '=') {
                    board[i][column] = player;
                    i = -1;
                }

            }

        }else {
            if (board[board.length - 1][column] == '=') {
                throw new IllegalArgumentException("Space not occupied: " + column);
            }

            if (board[board.length - 1][column] != player) {
                throw new IllegalArgumentException("Space occupied by other player: " + column);
            }

            for (int i = board.length - 1; i > 0; i--) {
                board[i][column] = board[i - 1][column];
            }

            board[0][column] = '=';
        }
    }

}
