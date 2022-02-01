package model;

public class Piece {
    /** Location of the piece */
    private Location loc;

    /** Character located in the piece */
    private char cPc;

    /** King state of the piece */
    private boolean isKing;

    /** Constructs a piece for the game
     * 
     * @param piece Representing symbol for the piece
     */
    public Piece (char piece) {
        cPc = piece;
        isKing = false;
    }

    
    /** Gets the character symbol of the piece
     * @return Symbol of the piece
     */
    public char getPiece () {
        return cPc;
    }


    /** Gets the location of the piece
     * @return Location
     */
    public Location getLocation () {
        return loc;
    }

    
    /** Gets the king state of the piece
     * @return Current king state of the piece
     */
    public boolean isKing () {
        return isKing;
    }

    
    /** Sets the character of the piece
     * @param piece Symbol of the piece
     */
    public void setCharPiece (char piece) {
        cPc = piece;
    }

    
    /** Sets the king state of the piece
     * @param b
     */
    public void setKing (boolean b) {
        isKing = b;
    }

    
    /** Initializes the location of the piece
     * @param x
     * @param y
     */
    public void initLoc (int x, int y) {
        loc = new Location(x, y);
    }

    
    /** Sets the location of the place
     * @param x location Row
     * @param y location Column
     */
    public void setLoc (int x, int y) {
        loc.setLoc(x, y);
    }

    
    /** Sets the location of the piece
     * @param loc
     */
    public void setLoc (Location loc) {
        this.loc = loc;
    }

    
    /** Get coordinate of piece (if alive)
     * @return String
     */
    public String getCoord() {
        return loc.getRow() + " " + loc.getCol();
    }
}