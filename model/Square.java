package model;

public class Square {
    /** Location of the square */
    private Location loc;

    /** Piece contained in the square */
    private Piece pc;

    public Square (int r, int c) {
        loc = new Location(r, c);
    }

    
    /** Returns the symbol of the piece
     * @return char of the piece
     */
    public char getSymbol () {
        return pc.getPiece();
    }
    
    
    /** Returns the piece at a given square
     * @return Piece of the square
     */
    public Piece getPiece () {
        return pc;
    }

    
    /** Sets the piece and its contents
     * @param p Piece to update to the current piece
     */
    public void setPiece (Piece p) {
        pc = p;
    }
    
    
    /** Sets the location of a piece
     * @param row New piece row
     * @param col New piece column
     */
    public void setLoc (int row, int col) {
        pc.setLoc(row, col);
    }
    
    
    /** Sets the king state of the piece at a Square
     * @param b New state of the piece
     */
    public void setKingState (boolean b) {
        pc.setKing(b);
    }

    
    /** Initializes the piece's value and character
     * @param value Value of piece
     * @param piece Character of piece
     */
    public void initPiece (char piece) {
        pc = new Piece(piece);
        pc.setLoc(loc);
    }

    /** Copies the piece of an existing Square
     *  to an uninitialized square
     * 
     * @param sq Piece to copy from
     */
    public void copyPiece (Square sq) {
        /* (3) Get the copy of the piece from sq */

        //(3a) Initialize Piece with sq.pc's char
        pc = new Piece(sq.pc.getPiece());

        //(3b) Set the king state of the square
        pc.setKing(sq.pc.isKing());

        //(3c) Set the location of the piece
        pc.setLoc(sq.pc.getLocation());
    }
}
