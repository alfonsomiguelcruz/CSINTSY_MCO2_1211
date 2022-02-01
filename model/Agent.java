package model;

import java.util.ArrayList;

public class Agent extends Player implements Move {
    private final int NEG_INF = Integer.MIN_VALUE;
    private final int POS_INF = Integer.MAX_VALUE;

    /** Constructs the Agent player of the game
     * 
     * @param b Board of the game
     */
    public Agent (Board b) {
        super();

		isDone = false;
        cPiece = 'x';
        initPieces(b, 'x');
    }
    

    /** Main moving method for the agent in the game
     * @param b Current board state
     * @param enemy Human player
     */
    public void turn (Board b, Player enemy) {
        Action finalMove;
        String sMove;
        Location oldCoord, newCoord;
        int nRow, pRow; //Input Row, Player's NEW Row
        int nCol, pCol; //Input Col, Player's NEW Col

        pRow = -1;
        pCol = -1;
        nRow = -1;
        nCol = -1;
        oldCoord = null;
        newCoord = null;

        finalMove = AlphaBeta(b, enemy);

        sMove = finalMove.getMove();
        nRow = finalMove.getLoc() / 10;
        nCol = finalMove.getLoc() % 10;

        try
        {
        switch(directions.valueOf(sMove)) {
            case NW:
                if(b.getSquare(nRow, nCol).getPiece().isKing()) {
                    pRow = nRow - 1;
                    pCol = nCol - 1;

                    if(isValidCapture(sMove, pRow, pCol, b, enemy)) {
                        capture(b, enemy, pRow, pCol);
                        pRow -= 1;
                        pCol -= 1;
                    }
                    
                    oldCoord = new Location(nRow, nCol);
                    newCoord = new Location(pRow, pCol);

                    if(pRow == 7 && !b.getSquare(oldCoord).getPiece().isKing()) {
                        b.getSquare(oldCoord).getPiece().setKing(true);
                        b.getSquare(oldCoord).getPiece().setCharPiece('X');
                    }
                    
                    move(oldCoord, newCoord, b, enemy);
                }
                break;
            case NE:
                if(b.getSquare(nRow, nCol).getPiece().isKing()) {
                    pRow = nRow - 1;
                    pCol = nCol + 1;

                    if(isValidCapture(sMove, pRow, pCol, b, enemy)) {
                        capture(b, enemy, pRow, pCol);
                        pRow -= 1;
                        pCol += 1;
                    }
                    
                    oldCoord = new Location(nRow, nCol);
                    newCoord = new Location(pRow, pCol);

                    if(pRow == 7 && !b.getSquare(oldCoord).getPiece().isKing()) {
                        b.getSquare(oldCoord).getPiece().setKing(true);
                        b.getSquare(oldCoord).getPiece().setCharPiece('X');
                    }

                    move(oldCoord, newCoord, b, enemy);
                }
                break;
            case SW:
                    pRow = nRow + 1;
                    pCol = nCol - 1;

                    if(isValidCapture(sMove, pRow, pCol, b, enemy)) {
                        capture(b, enemy, pRow, pCol);
                        pRow += 1;
                        pCol -= 1;
                    }

                    oldCoord = new Location(nRow, nCol);
                    newCoord = new Location(pRow, pCol);

                    if(pRow == 7 && !b.getSquare(oldCoord).getPiece().isKing()) {
                        b.getSquare(oldCoord).getPiece().setKing(true);
                        b.getSquare(oldCoord).getPiece().setCharPiece('X');
                    }

                    move(oldCoord, newCoord, b, enemy);
                break;
            case SE:
                    pRow = nRow + 1;
                    pCol = nCol + 1;

                    if(isValidCapture(sMove, pRow, pCol, b, enemy)) {
                        capture(b, enemy, pRow, pCol);
                        pRow += 1;
                        pCol += 1;
                    }
                    
                    oldCoord = new Location(nRow, nCol);
                    newCoord = new Location(pRow, pCol);

                    if(pRow == 7 && !b.getSquare(oldCoord).getPiece().isKing()) {
                        b.getSquare(oldCoord).getPiece().setKing(true);
                        b.getSquare(oldCoord).getPiece().setCharPiece('X');
                    }

                    move(oldCoord, newCoord, b, enemy);
                break;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    

    /** Implements the Alpha - Beta pruning algorithm
     * 
     * @param b Current board state
     * @param p Enemy player - Human
     * @return
     */
    public Action AlphaBeta (Board b, Player p) {
        State initState = new State();
        Action aMove = new Action();

        // Copy the current board as the initial state
        initState.copyBoard(b);

        aMove = MaxValue (p, initState, NEG_INF, POS_INF, 0);
        return aMove;
    }


    /** Gets the Max value after evaluating a series of states
     *  done for the agent player to maximize efficiency of their move
     * 
     * @param p Enemy player
     * @param s Current state
     * @param alpha Value of alpha
     * @param beta Value of beta
     * @param depth Depth in the state tree
     * @return Best action amongst all states and actions
     */
    private Action MaxValue (Player p, State s, int alpha, int beta, int depth) {
        Action actOne = new Action(); //v, move
        Action actTwo = new Action(); //v2, a2
        Action finalMove = null;
        ArrayList<State> states = new ArrayList<>();
        int i;
	
        if(isCutoff(s, depth)) {
            return s.getAction();
        }

        actOne.setEval(NEG_INF);
        s.expand(p, states, depth);
        
        // For Alpha - Beta WITH Move Ordering only
        removeStates(states);

        for(i = 0; i < states.size(); i++) {
            // Get the action, utilVal, and location of v2, a2
            actTwo = MinValue(p, states.get(i), alpha, beta, depth + 1);

            if(actTwo.getEval() > actOne.getEval()) {
                if(depth == 0) {
                    if(finalMove == null)
                        finalMove = new Action();
                    finalMove.setAction(states.get(i).getAction());
                }

                actOne.setAction(actTwo);
                alpha = Integer.max(alpha, actOne.getEval());
            }

            if(actOne.getEval() >= beta && finalMove == null)
                return actOne;
            else if (actOne.getEval() >= beta && finalMove != null)
                return finalMove;
        }
		
        if(finalMove == null)
            return actOne;
        else
            return finalMove;
    }


    /** Gets the Min value after evaluating a series of states
     *  done for the human player to minimize efficency of their move
     * 
     * @param p Enemy player
     * @param s Current state
     * @param alpha Value of alpha
     * @param beta Value of beta
     * @param depth Depth in the state tree
     * @return Action amongst all states and actions evaluated
     */
    private Action MinValue (Player p, State s, int alpha, int beta, int depth) {
        Action actOne = new Action();
        Action actTwo = new Action();
        ArrayList<State> states = new ArrayList<>();
        int i;

        if(isCutoff(s, depth)) {
            return s.getAction();
        }

        actOne.setEval(POS_INF);
        s.expand(p, states, depth);

        // For Alpha - Beta WITH Move Ordering only
        removeStates(states);

        for(i = 0; i < states.size(); i++) {
            // Get the action, utilVal, and location of v2, a2
            actTwo = MaxValue(p, states.get(i), alpha, beta, depth + 1);
 
            if(actTwo.getEval() < actOne.getEval()) {
                actOne.setAction(actTwo);
                beta = Integer.min(beta, actOne.getEval());
            }

            if(actOne.getEval() <= alpha)
                return actOne;
        }

        return actOne;
    }
    
    
    /** Checks if the state tree has reached the cutoff mark
     *  whether a player has won, or has reached depth 4
     * 
     * @param s Current state
     * @param depth Depth at the state tree
     * @return
     */
    private boolean isCutoff(State s, int depth) {
        if(depth == 4)
            return true;
        else if (s.hasWinner() == 1 || s.hasWinner() == -1)
            return true;
        else
            return false;
    }
	
	/** Removes unwanted states for state traversal
     * 
     * @param states List of nodes to manipulate
     */
    private void removeStates (ArrayList<State> states) {
        int i, best;
        ArrayList<State> s = new ArrayList<>();
        State temp;
        best = -1;
		
        // Find the node with the highest utility value
        for(i = 0; i < states.size() - 1; i++) {
            if(states.get(i).getAction().getEval() > states.get(i + 1).getAction().getEval())
                best = i;
        }

        /* If there exists a node that has the highest utility value
           Remove all nodes except those whose values are only equal
           to the state at index [best]

           Else if not, keep all nodes as there are more nodes yet to explore.
        */
        if (best != -1) {
            for(i = 0; i < states.size(); i++)
                if(states.get(i).getAction().getEval() == states.get(best).getAction().getEval())
                    s.add(states.get(i));

            states.clear();
            
            for(i = 0; i < s.size(); i++)
                states.add(s.get(i));
        }
    }
}