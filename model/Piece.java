package model;

public class Piece {
    private Location loc;
    private int nPcVal;
    private char cPc;
    private boolean isKing;

    public Piece (int value, char piece) {
        nPcVal = value;
        cPc = piece;
        isKing = false;
    }

    
    /** Gets the value of the piece
     * @return int
     */
    public int getValue () {
        return nPcVal;
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
        this.loc = new Location(loc.getRow(), loc.getCol());
    }

    
    /** Get coordinate of piece (if alive)
     * @return String
     */
    public String getCoord() {
        return loc.getRow() + " " + loc.getCol();
    }
}