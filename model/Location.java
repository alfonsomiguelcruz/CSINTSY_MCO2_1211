package model;

public class Location {
    /** Row component */
    private int r;

    /** Column component */
    private int c;

    /** Constructs a location object
     * 
     * @param r Row component
     * @param c Column component
     */
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
     * @param obj Location object to compare
     * @return Equality of the location objects
     */
    @Override
    public boolean equals(Object obj) {
        Location temp = (Location) obj;

        return r == temp.r && c == temp.c;
    }
}
