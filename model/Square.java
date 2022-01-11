package model;

public class Square {
    private Location loc;
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

    
    /** 
     * @param p
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
    public void initPiece (int value, char piece) {
        pc = new Piece(value, piece);
        pc.setLoc(loc);
    }
}
