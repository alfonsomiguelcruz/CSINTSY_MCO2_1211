package model;

import view.View;

public class Human extends Player implements Move {
    /** Piece Input */
    private int nCoordInput;

    /** Direction Input */
    private String sDirInput;
    
    
    /** Creates a Human player for the game
     * 
     * @param b Board for the game
     */
    public Human (Board b) {
        super();
		isDone = true;
        cPiece = 'o';
        initPieces(b, 'o');
    }


    /** Turn for the Player to move
     * 
     * @param b Board of the Game
     * @param enemy Enemy of the Game
     */
    public void turn (Board b, Player enemy) {
        String sMove;
        Location oldCoord, newCoord;
        int nRow, pRow; //Input Row, Player's NEW Row
        int nCol, pCol; //Input Col, Player's NEW Col

        pRow = -1;
        pCol = -1;
        oldCoord = null;
        newCoord = null;

        nRow = nCoordInput / 10 - 1;
        nCol = nCoordInput % 10 - 1;
    
        // Piece is Valid and Free
        if(isValidPiece(nRow, nCol, b) && isFree(nRow, nCol, b, enemy)) {
            sMove = sDirInput;

            // Move is Valid
            if(isValidMove(sDirInput, nRow, nCol, b, enemy)) {
                switch(directions.valueOf(sMove)) {
                    case NW:
                        pRow = nRow - 1;
                        pCol = nCol - 1;

                        // Capture is Valid
                        if(isValidCapture(sMove, pRow, pCol, b, enemy)) {
                            capture(b, enemy, pRow, pCol);

                            pRow -= 1;
                            pCol -= 1;
                        }
                        
                        oldCoord = new Location(nRow, nCol);
                        newCoord = new Location(pRow, pCol);

                        if(pRow == 0 && !b.getSquare(oldCoord).getPiece().isKing()) {
                            b.getSquare(oldCoord).getPiece().setKing(true);
                            b.getSquare(oldCoord).getPiece().setCharPiece('O');
                        }
                        
                        move(oldCoord, newCoord, b, enemy);
                        break;
                    case NE:
                        pRow = nRow - 1;
                        pCol = nCol + 1;

                        // Capture is Valid
                        if(isValidCapture(sMove, pRow, pCol, b, enemy)) {
                            capture(b, enemy, pRow, pCol);

                            pRow -= 1;
                            pCol += 1;
                        } 
                        
                        oldCoord = new Location(nRow, nCol);
                        newCoord = new Location(pRow, pCol);

                        if(pRow == 0 && !b.getSquare(oldCoord).getPiece().isKing()) {
                            b.getSquare(oldCoord).getPiece().setKing(true);
                            b.getSquare(oldCoord).getPiece().setCharPiece('O');
                        }

                        move(oldCoord, newCoord, b, enemy);
                        break;
                    case SW:
                        if(b.getSquare(nRow, nCol).getPiece().isKing()) {
                            pRow = nRow + 1;
                            pCol = nCol - 1;

                            // Capture is Valid
                            if(isValidCapture(sMove, pRow, pCol, b, enemy)) {
                                capture(b, enemy, pRow, pCol);

                                pRow += 1;
                                pCol -= 1;
                            }
                            
                            oldCoord = new Location(nRow, nCol);
                            newCoord = new Location(pRow, pCol);

                            if(pRow == 0 && !b.getSquare(oldCoord).getPiece().isKing()) {
                                b.getSquare(oldCoord).getPiece().setKing(true);
                                b.getSquare(oldCoord).getPiece().setCharPiece('O');
                            }

                            move(oldCoord, newCoord, b, enemy);
                        } else View.setNotif("Invalid Move: Not a King Piece Yet!");
                        break;
                    case SE:
                        if(b.getSquare(nRow, nCol).getPiece().isKing()) {
                            pRow = nRow + 1;
                            pCol = nCol + 1;

                            // Capture is Valid
                            if(isValidCapture(sMove, pRow, pCol, b, enemy)) {
                                capture(b, enemy, pRow, pCol);

                                pRow += 1;
                                pCol += 1;
                            }

                            oldCoord = new Location(nRow, nCol);
                            newCoord = new Location(pRow, pCol);

                            if(pRow == 0 && !b.getSquare(oldCoord).getPiece().isKing()) {
                                b.getSquare(oldCoord).getPiece().setKing(true);
                                b.getSquare(oldCoord).getPiece().setCharPiece('O');
                            }

                            move(oldCoord, newCoord, b, enemy);
                        } else View.setNotif("Invalid Move: Not a King Piece Yet!");
                        break;
                }
            } else View.setNotif("Invalid Piece Move!");
        } else View.setNotif("Invalid Piece! (Wrong Coordinates or Unfree Piece)");
    }

    
    
    /** Checks if the Human Pieces are free to move
     * @param row Base row coordinate of the piece to move
     * @param col Base col coordinate of the piece to move
     * @param b Board of the game
     * @param p Enemy player
     * @return Free state of the piece
     */
    public boolean isFree(int row, int col, Board b, Player p) {
        boolean NW, NE, SW, SE;

        NW = false;
        NE = false;
        SW = false;
        SE = false;

        //Northwest: --
        if(isValidRange(row - 1, col - 1)) {
            // If one square NW is the same piece as player's, its not free
            if(b.getSquare(row - 1, col - 1).getSymbol() == cPiece ||
               b.getSquare(row - 1, col - 1).getSymbol() == toUp(cPiece))
                NW = false; //NW (1 units) is blocked
            // If one square NW is the same piece as enemy's 
            else if(b.getSquare(row - 1, col - 1).getSymbol() == p.cPiece ||
                    b.getSquare(row - 1, col - 1).getSymbol() == toUp(p.cPiece)) {
                if(isValidRange(row - 2, col - 2)) {
                    //If one square NW (2 units) is a free space, it's free
                    if(b.getSquare(row - 2, col  - 2).getSymbol() == '_')
                        NW = true; //NW (2 units) is free
                } else NW = false; //NW (2 units) is blocked
            } else NW = true; //NW (1 units) is free
        } else NW = false; //NW is blocked
        
        //Northeast: -+
        if(isValidRange(row - 1, col + 1)) {
            // If one square NE is the same piece as player's, its not free
            if(b.getSquare(row - 1, col + 1).getSymbol() == cPiece ||
               b.getSquare(row - 1, col + 1).getSymbol() == toUp(cPiece))
                NE = false; //NE (1 units) is blocked
            // If one square NE is the same piece as enemy's 
            else if(b.getSquare(row - 1, col + 1).getSymbol() == p.cPiece) {
                if(isValidRange(row - 2, col + 2)) {
                    //If one square NE (2 units) is a free space, it's free
                    if(b.getSquare(row - 2, col  + 2).getSymbol() == '_')
                        NE = true; //NE (2 units) is free
                } else NE = false; //NE (2 units) is blocked
            } else NE = true; //NE (1 units) is free
        } else NE = false; //NE is blocked

        //Southwest: +-; King - State for Human Pieces go SW and SE
        if(isValidRange(row + 1, col - 1) && b.getSquare(row, col).getPiece().isKing()) {
            // If one square SW is the same piece as player's, its not free
            if(b.getSquare(row + 1, col - 1).getSymbol() == cPiece ||
               b.getSquare(row + 1, col - 1).getSymbol() == toUp(cPiece))
                SW = false; //SW (1 units) is blocked
            // If one square SW is the same piece as enemy's 
            else if(b.getSquare(row + 1, col - 1).getSymbol() == p.cPiece ||
                    b.getSquare(row + 1, col - 1).getSymbol() == toUp(p.cPiece)) {
                if(isValidRange(row + 2, col - 2)) {
                    //If one square SW (2 units) is a free space, it's free
                    if(b.getSquare(row + 2, col - 2).getSymbol() == '_')
                        SW = true; //SW (2 units) is free
                } else SW = false; //SW (2 units) is blocked
            } else SW = true; //SW (1 units) is free
        } else SW = false; //SW is blocked

        //Southeast: ++; King - State for Human Pieces go SW and SE
        if(isValidRange(row + 1, col + 1) && b.getSquare(row, col).getPiece().isKing()) {
            // If one square SE is the same piece as player's, its not free
            if(b.getSquare(row + 1, col + 1).getSymbol() == cPiece ||
               b.getSquare(row + 1, col + 1).getSymbol() == toUp(cPiece))
                SE = false; //SE (1 units) is blocked
            // If one square SE is the same piece as enemy's 
            else if(b.getSquare(row + 1, col + 1).getSymbol() == p.cPiece ||
                    b.getSquare(row + 1, col + 1).getSymbol() == toUp(p.cPiece)) {
                if(isValidRange(row + 2, col + 2)) {
                    //If one square SE (2 units) is a free space, it's free
                    if(b.getSquare(row + 2, col + 2).getSymbol() == '_')
                        SE = true; //SE (2 units) is free
                } else SE = false; //SE (2 units) is blocked
            } else SE = true; //SE (1 units) is free
        } else SE = false; //SE is blocked

        return NW || NE || SW || SE;
    }


    /** Sets the inputs of the player
     * 
     * @param nCoord Piece input of the player
     * @param sDir Direction input of the player
     */
    public void setInputs (int nCoord, String sDir) {
        nCoordInput = nCoord;
        sDirInput = sDir;
    }
}
