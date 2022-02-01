package model;

import java.util.ArrayList;
import view.View;

public class Player {
    /** Pieces of a player */
    protected ArrayList<Piece> pieces;

    /** Character representing the player */
    protected char cPiece;

    /** State whether a player is done with their turn */
    protected boolean isDone;

    /** Directions available in playing checkers */
    protected enum directions {
        NW, NE, SW, SE
    };

    
    /** Constructs a player object */
    public Player() {
        pieces = new ArrayList<>();
    }


    /** Initializes the pieces of the player
     * 
     * @param b Starting state of the board
     * @param ch Character of the player
     */
    public void initPieces(Board b, char ch) {
        int i, j, k;

        k = 0;
        for(i = 0; i < b.getSize(); i++)
            for(j = 0; j < b.getSize(); j++) {
                if(b.getSquare(i, j).getSymbol() == ch) {
                    pieces.add(k, new Piece(ch));
                    pieces.get(k).initLoc(i, j);
                    k++;
                }
            }
    }


    /** Gets the player's character piece
     * 
     * @return character of the player
     */
    public char getCharPiece () {
        return cPiece;
    }


    /** Removes a piece from the enemy's list
     * 
     * @param nRow Row component of the piece
     * @param nCol Column component of the piece
     * @param p Opponent of the player
     */
    public void removePiece(int nRow, int nCol, Player p) {
        Piece temp;
        int i;

        for(i = 0; i < p.pieces.size(); i++)
            if(p.pieces.get(i).getLocation().equals(new Location (nRow, nCol)))
                temp = p.pieces.remove(i);
    }


    /** Determins if the player is a loser
     * 
     * @return State of the player in the game
     */
    public boolean isLoser () {
        return pieces.size() == 0;
    }
    

    /** Returns the moving state of the player
     * 
     * @return Move state of the player
     */
    public boolean isDone () {
        return isDone;
    }


    /** Sets the move state of the player
     * 
     * @param b New move state of the player
     */
    public void setDone (boolean b) {
        isDone = b;
    }


    /** Determines if the piece is a valid piece coming from
     *  the player's list of pieces
     * 
     * @param row Row component of the piece
     * @param col Column component of the piece
     * @param b Board of the game
     * @return Validity of the piece
     */
    public boolean isValidPiece (int row, int col, Board b) {
        if(isValidRange(row, col))
            return b.getSquare(row, col).getSymbol() == cPiece ||
                   b.getSquare(row, col).getSymbol() == toUp(cPiece) ;
        else return false;
    }


    /** Determines if the piece is the enemy's piece from
     *  their list of pieces
     * 
     * @param row Row component of the piece
     * @param col Column component of the piece
     * @param b Board of the game
     * @param p Opponent in the game
     * @return Validity of the piece
     */
    public static boolean isSymbol(int row, int col, Board b, Player p) {
        return b.getSquare(row, col).getSymbol() == p.cPiece ||
               b.getSquare(row, col).getSymbol() == toUp(p.cPiece);
    }


    /** Determines if the move committed by the player is valid
     * 
     * @param move Direction of the piece
     * @param row Row component of the piece
     * @param col Column component of the piece
     * @param b Board of the game
     * @param p Opponent in the game
     * @return Validity of move on a piece
     */
    public static boolean isValidMove (String move, int row, int col, Board b, Player p) {
        int newRow;
        int newCol;

        newRow = row;
        newCol = col;
        switch(directions.valueOf(move)) {
            case NW:
                newRow -= 1;
                newCol -= 1; 

                if(isValidRange(newRow, newCol)) {
                    if (b.getSquare(newRow, newCol).getSymbol() == '_')
                        return true;
                    else if (isValidRange(newRow - 1, newCol - 1) &&
                            isSymbol(newRow, newCol, b, p) &&
                            b.getSquare(newRow - 1, newCol - 1).getSymbol() == '_')
                        return true;
                    else
                        return false;
                }               
                break;
            case NE:
                newRow -= 1;
                newCol += 1;
                
                if(isValidRange(newRow, newCol)) {
                    if (b.getSquare(newRow, newCol).getSymbol() == '_')
                        return true;
                    else if (isValidRange(newRow - 1, newCol + 1) &&
                            isSymbol(newRow, newCol, b, p) &&
                            b.getSquare(newRow - 1, newCol + 1).getSymbol() == '_')
                        return true;
                    else
                        return false;
                } 
                break;
            case SW:
                newRow += 1;
                newCol -= 1; 

                if(isValidRange(newRow, newCol)) {
                    if (b.getSquare(newRow, newCol).getSymbol() == '_')
                        return true;
                    else if (isValidRange(newRow + 1, newCol - 1) &&
                            isSymbol(newRow, newCol, b, p) &&
                            b.getSquare(newRow + 1, newCol - 1).getSymbol() == '_')
                        return true;
                    else
                        return false;
                } 
                break;
            case SE:
                newRow += 1;
                newCol += 1;

                if(isValidRange(newRow, newCol)) {
                    if (b.getSquare(newRow, newCol).getSymbol() == '_')
                        return true;
                    else if (isValidRange(newRow + 1, newCol + 1) &&
                            isSymbol(newRow, newCol, b, p) &&
                            b.getSquare(newRow + 1, newCol + 1).getSymbol() == '_')
                        return true;
                    else
                        return false;
                } 
                break;
        }

        return false;
    }

    
    /** Returns the capitalized character
     * 
     * @param ch Lowercase character
     * @return Uppercase character
     */
    public static char toUp (char ch) {
        return (char) (ch % 'a' + 'A');
    }
    

    /** Determines if the capture move is valid
     * 
     * @param move Direction of the piece
     * @param row Row component of the piece
     * @param col Column component of the piece
     * @param b Board of the game
     * @param p Opponent in the game
     * @return Validity of capture move on a piece
     */
    public static boolean isValidCapture(String sMove, int row, int col, Board b, Player p) {
        switch(directions.valueOf(sMove)) {
            case NW:
                return isValidRange(row - 1, col - 1) &&
                        (b.getSquare(row, col).getSymbol() == p.cPiece ||
                        b.getSquare(row, col).getSymbol() == toUp(p.cPiece)) &&
                        b.getSquare(row - 1, col - 1).getSymbol() == '_';
            case NE:
                return isValidRange(row - 1, col + 1) &&
                        (b.getSquare(row, col).getSymbol() == p.cPiece ||
                        b.getSquare(row, col).getSymbol() == toUp(p.cPiece)) &&
                        b.getSquare(row - 1, col + 1).getSymbol() == '_';
            case SW:
                return isValidRange(row + 1, col - 1) &&
                        (b.getSquare(row, col).getSymbol() == p.cPiece ||
                        b.getSquare(row, col).getSymbol() == toUp(p.cPiece)) &&
                        b.getSquare(row + 1, col - 1).getSymbol() == '_';
            case SE:
                return isValidRange(row + 1, col + 1) &&
                        (b.getSquare(row, col).getSymbol() == p.cPiece ||
                        b.getSquare(row, col).getSymbol() == toUp(p.cPiece)) &&
                        b.getSquare(row + 1, col + 1).getSymbol() == '_';
            default:
                return false;
        }
    }


    /** Determines if the location is in valid range
     *  of the board
     * 
     * @param row Row component
     * @param col Column component
     * @return
     */
    public static boolean isValidRange (int row, int col) {
        return (0 <= row && row <= 7) && (0 <= col && col <= 7);
    }


    /** Moves the piece from its old space to its new space
     *  and updates the User Interface regarding the pieces
     * 
     * @param oCoord Coordinates of the old location
     * @param nCoord Coordinates of the new location
     * @param b Board of the game
     * @param p Opponent in the game
     */
    public void move (Location oCoord, Location nCoord, Board b, Player p) {
        Piece temp;
        int i;

        for(i = 0; i < pieces.size(); i++)
            if(pieces.get(i).getLocation().equals(oCoord))
                pieces.get(i).setLoc(nCoord);

        temp = b.getSquare(nCoord).getPiece();
        b.getSquare(nCoord).setPiece(b.getSquare(oCoord).getPiece());
        b.getSquare(oCoord).setPiece(temp);

        this.isDone = !isDone;
        p.setDone(!isDone);

        View.updateSpace(oCoord, nCoord, cPiece, b.getSquare(nCoord).getPiece().isKing());
    }

    
    /** Removes the piece on a given location
     *  and updates the User Interface in clearing the piece
     * 
     * @param b Board of the game
     * @param enemy Opponent in the game
     * @param nRow Row component
     * @param nCol Column component
     */
    public void capture (Board b, Player enemy, int nRow, int nCol) {
        enemy.removePiece(nRow, nCol, enemy);
        b.getSquare(nRow, nCol).setPiece(new Piece('_'));
        View.clearPiece(nRow, nCol);
    }
}