package model;

public interface Move {
    public void turn(Board b, Player enemy);
    public boolean isFree(int row, int col, Board b, Player p);
}