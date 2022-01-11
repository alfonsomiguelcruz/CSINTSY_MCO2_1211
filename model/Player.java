package model;

import java.util.ArrayList;
import view.View;

public class Player {
    protected ArrayList<Piece> pieces;
    protected char cPiece;
    protected int numPieces;
    private boolean isDone;
    protected enum directions {
        NW, NE, SW, SE
    };

    public Player() {
        isDone = false;
        numPieces = 12;
        pieces = new ArrayList<>();
    }

    public void initPieces(Board b, char ch) {
        int i, j, k;

        k = 0;
        for(i = 0; i < b.getSize(); i++)
            for(j = 0; j < b.getSize(); j++) {
                if(b.getSquare(i, j).getSymbol() == ch) {
                    pieces.add(k, new Piece(1, ch));
                    pieces.get(k).initLoc(i, j);
                    k++;
                }
            }
    }

    public char getCharPiece () {
        return cPiece;
    }

    public void removePiece(int nRow, int nCol, Player p) {
        Piece temp;
        int i;

        for(i = 0; i < p.pieces.size(); i++)
            if(p.pieces.get(i).getLocation().equals(new Location (nRow, nCol)))
                temp = p.pieces.remove(i);
        numPieces -= 1;
    }

    public boolean isLoser () {
        return numPieces == 0;
    }
    
    public boolean isDone () {
        return isDone;
    }

    public void setDone (boolean b) {
        isDone = b;
    }

    public boolean isValidPiece (int row, int col, Board b) {
        if(isValidRange(row, col))
            return b.getSquare(row, col).getSymbol() == cPiece ||
                   b.getSquare(row, col).getSymbol() == (cPiece % 'a' + 'A') ;
        else return false;
    }

    public boolean isValidMove (String move, int row, int col, Board b, Player p) {
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
                            b.getSquare(newRow, newCol).getSymbol() == p.cPiece &&
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
                            b.getSquare(newRow, newCol).getSymbol() == p.cPiece &&
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
                            b.getSquare(newRow, newCol).getSymbol() == p.cPiece &&
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
                            b.getSquare(newRow, newCol).getSymbol() == p.cPiece &&
                            b.getSquare(newRow + 1, newCol + 1).getSymbol() == '_')
                        return true;
                    else
                        return false;
                } 
                break;
        }

        return false;
    }

    public boolean isValidCapture(String sMove, int row, int col, Board b, Player p) {
        switch(directions.valueOf(sMove)) {
            case NW:
                return isValidRange(row - 1, col - 1) &&
                        b.getSquare(row, col).getSymbol() == p.cPiece &&
                        b.getSquare(row - 1, col - 1).getSymbol() == '_';
            case NE:
                return isValidRange(row - 1, col + 1) &&
                        b.getSquare(row, col).getSymbol() == p.cPiece &&
                        b.getSquare(row - 1, col + 1).getSymbol() == '_';
            case SW:
                return isValidRange(row + 1, col - 1) &&
                        b.getSquare(row, col).getSymbol() == p.cPiece &&
                        b.getSquare(row + 1, col - 1).getSymbol() == '_';
            case SE:
                return isValidRange(row + 1, col + 1) &&
                        b.getSquare(row, col).getSymbol() == p.cPiece &&
                        b.getSquare(row + 1, col + 1).getSymbol() == '_';
            default:
                return false;
        }
    }

    public boolean isValidRange (int row, int col) {
        return (0 <= row && row <= 7) && (0 <= col && col <= 7);
    }

    public void displayPcs() {
        int i;

        for(i = 0; i < pieces.size(); i++)
            System.out.println(pieces.get(i).getCoord());
    }

    public void move (Location oCoord, Location nCoord, Board b, Player p) {
        Piece temp;
        int i;

        for(i = 0; i < pieces.size(); i++)
            if(pieces.get(i).getLocation().equals(oCoord))
                pieces.get(i).setLoc(nCoord.getRow(), nCoord.getCol());

        //FINAL
        temp = b.getSquare(nCoord.getRow(), nCoord.getCol()).getPiece();
        b.getSquare(nCoord).setPiece(b.getSquare(oCoord).getPiece());
        b.getSquare(oCoord).setPiece(temp);

        this.isDone = !isDone;
        p.setDone(!isDone);

        View.updateSpace(oCoord, nCoord, cPiece, b.getSquare(nCoord).getPiece().isKing());
    }

    public void capture (Board b, Player enemy, int nRow, int nCol) {
        enemy.removePiece(nRow, nCol, enemy);
        b.getSquare(nRow, nCol).setPiece(new Piece(0, '_'));
        View.clearPiece(nRow, nCol);
    }
}