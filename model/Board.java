package model;

public class Board {
    /** Maximum size of the board */
    private final int SIZE = 8;

    /** Main board used for the game or state */
    private Square[][] board;


    /** Constructs an 8 x 8 board */
    public Board () {
        board = new Square[SIZE][SIZE];
    }


    /** Initializes the starting state of the board */
    public void initBoard () {
        int i, j;

        for(i = 0; i < SIZE; i++)
            for(j = 0; j < SIZE; j++)
            {
                board[i][j] = new Square(i, j);
                if(((i == 0 || i == 2) && j % 2 == 1) ||
                   ((i == 1) && j % 2 == 0))
                    board[i][j].initPiece('x');
                else if (((i == 5 || i == 7) && j % 2 == 0) ||
                         ((i == 6) && j % 2 == 1))
                    board[i][j].initPiece('o');
                else
                    board[i][j].initPiece('_');
            }

        /********************
               1 2 3 4 5 6 7 8
               0 1 2 3 4 5 6 7

        1 0    _ x _ x _ x _ x
        2 1    x _ x _ x _ x _
        3 2    _ x _ x _ x _ x
        4 3    _ _ _ _ _ _ _ _
        5 4    _ _ _ _ _ _ _ _
        6 5    o _ o _ o _ o _
        7 6    _ o _ o _ o _ o
        8 7    o _ o _ o _ o _
        *********************/
    }

    
    /** Intializes the spaces of the board for the states */
    public void initBoardSpaces () {
        int i, j;

        //Initalize square and location of square
        for(i = 0; i < SIZE; i++)
            for(j = 0; j < SIZE; j++)
                board[i][j] = new Square(i, j);
    }

    
    /** Gets the size of the board
     * @return int
     */
    public int getSize() {
        return SIZE;
    }

    
    /** Gets the square of the board, given the row and column
     * @param r
     * @param c
     * @return Square
     */
    public Square getSquare(int r, int c) {
        return board[r][c];
    }

    
    /** Gets the square of the board, given the location
     * @param l
     * @return Square
     */
    public Square getSquare(Location l) {
        return board[l.getRow()][l.getCol()];
    }
}
