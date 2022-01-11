package model;

public class Location {
    private int r;
    private int c;

    public Location (int r, int c) {
        this.r = r;
        this.c= c;
    }

    
    /** Gets the row coordinate
     * @return Row coordinate
     */
    public int getRow () {
        return r;
    }

    
    /** Gets the column coordinate
     * @return Column coordinate
     */
    public int getCol () {
        return c;
    }

    
    /** Sets the location coordinates
     * @param newR new Row
     * @param newC new Col
     */
    public void setLoc (int newR, int newC) {
        r = newR;
        c = newC;
    }

    
    /** Determines if two locations are equal
     * @param obj Object to compare
     * @return Equality of objectss
     */
    @Override
    public boolean equals(Object obj) {
        Location temp = (Location) obj;

        return r == temp.r && c == temp.c;
    }
}
