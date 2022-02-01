package model;

/** Interface for the move turns of both players */
public interface Move {
    /**  Main move for the players
     * 
     * @param b Current board state
     * @param enemy Opponent of the player
     */
    public void turn(Board b, Player enemy);
}