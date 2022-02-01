package model;

public class Action {
    /** Move to get to the current state s' from state s */
    String move;
    
    /** Location of the piece moved to get to the current state */
    int loc;
    
    /** Utility of state after moving a piece */
    int utilVal;

    /** Constructs an action for a state */
    public Action () {
        move = null;
		utilVal = -999;
        loc = -999;
    }


    /** Calls the constructor and changes the value of the piece location */
    public Action (int pcLoc) {
        this();
        loc = pcLoc;
    }


    /** Returns the move of the action
     * 
     * @return Direction to move to
     */
    public String getMove () {
        return move;
    }


    /** Gets the utility value of the action
     * 
     * @return Utility value of the state
     */
    public int getEval () {
        return utilVal;
    }


    /** Gets the location or piece of an action
     * 
     * @return location of the piece
     */
    public int getLoc () {
        return loc;
    }


    /** Sets the move of an action
     * 
     * @param s New direction of an action
     */
    public void setMove (String s) {
        move = s;
    }


    /** Sets the evaluation of the action
     * 
     * @param n New utility value of the action
     */
    public void setEval (int n) {
        utilVal = n;
    }


    /** Sets the location of the action
     * 
     * @param l New piece selected for the action
     */
    public void setLoc (int l) {
        loc = l;
    }


    /** Updates the values of the action object
     * 
     * @param action New direction of the action
     * @param util New utility value of the action
     * @param l New location of the action
     */
    public void setAction (String action, int util, int l) {
        utilVal = util;
        move = action;
        loc = l;
    }


    /** Updates the values of the action from another action
     * 
     * @param a New values for the action
     */
    public void setAction (Action a) {
        utilVal = a.utilVal;
        move    = a.move;
        loc     = a.loc;
    }
}
