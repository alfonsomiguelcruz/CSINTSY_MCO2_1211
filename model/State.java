package model;

import java.util.ArrayList;
import model.Player.*;

public class State {
    /** Current Board state */
	private Board b;

    /** Action committed to get to THIS state */
	private Action act;


    /** Creates a state */
    public State () {
        b = new Board();
		act = new Action();
    }
	

    /** Evaluates the board, the pieces, and winning state
     *  of the players for the Minimax algorithm
     * 
     * @return Value of the board after executing an action
     */
	public int eval() {
		int i, j, utilVal;
		int nPcHuman, nPcAgent;

        nPcHuman = 0;
        nPcAgent = 0;
		utilVal = 0;

        /* [1] Count the pieces for agent and human
               and use the scoring criteria on the board
        */
		for(i = 0; i < 8; i++)
			for(j = 0; j < 8; j++)
				switch(b.getSquare(i, j).getSymbol()) {
					case 'X':
                        utilVal += 10;
                        nPcAgent++;
						break;
					case 'x':
                        utilVal += 5;
                        nPcAgent++;
						break;
					case 'O':
                        utilVal -= 10;
                        nPcHuman++;
						break;
					case 'o':
                        utilVal -= 5;
                        nPcHuman++;
						break;
				}

        /* [2] Use the number of agent and human pieces for
               the capturing scoring. The lesser the agent
               pieces captured, the higher the score.        
        */  
        utilVal += ((12 - nPcHuman) *  100);
        utilVal += ((12 - nPcAgent) * -100);
        
        /* [3] Evaluate the state whether agent wins,
               or human wins, depending on which player
               has 0 pieces first.
        */
        switch(hasWinner()) {
            case 1:
                utilVal += 10000;
                break;
            case -1:
                utilVal -= 10000;
                break;
            case 0:
                utilVal += 0;
                break;
        }

        /* [4] Set the utility value on the action
               and return the utility value.
        
        */
        act.setEval(utilVal);
		return utilVal;
	}


    /** Copies the board into the state as a
     *  separate copy (in memory)
     * 
     * @param bCopy Board to copy from
     */
	public void copyBoard (Board bCopy) {
		int i, j;

		/* (1) Initialize the squares of the board
		       and their location
        */
		b.initBoardSpaces();

		/* (2) Initialize and Copy the pieces of the board
        */
		for(i = 0; i < bCopy.getSize(); i++)
			for(j = 0; j < bCopy.getSize(); j++)
				b.getSquare(i, j).copyPiece(bCopy.getSquare(i, j));
	}


    /** Determines if there is a winner based on the board state
     * 
     * @return 1 : Agent Wins, -1 : Human Wins, 0 : Neither
     */
    public int hasWinner() {
        int i, j;
        int nPcAgent, nPcHuman;

        nPcAgent = 0;
        nPcHuman = 0;

        for(i = 0; i < 8; i++)
            for(j = 0; j < 8; j++) {
                if(b.getSquare(i, j).getSymbol() == 'x' ||
                   b.getSquare(i, j).getSymbol() == 'X')
                    nPcAgent++;
                else if (b.getSquare(i, j).getSymbol() == 'o' ||
                         b.getSquare(i, j).getSymbol() == 'O')
                    nPcHuman++;
            }

        if(nPcHuman == 0)
            return 1;
        else if (nPcAgent == 0)
            return -1;
        else return 0;
    }


    /** Expands the current state to different possible states after
     *  executing a number of moves
     * 
     * @param enemy Opponent of the player
     * @param states List to place expanded states in for evaluation
     * @param depth Depth in the tree
     */
	public void expand (Player enemy, ArrayList<State> states, int depth) {
		int i, j, k, util;
		int numStates;
		int pRow, pCol;
		int nRow, nCol;
		
		numStates = 0;
		String move = "NW";

		for(i = 0; i < 8; i++) {
			for(j = 0; j < 8; j++) {
                // Set the piece coordinates according to i and j
                nRow = i;
                nCol = j;
                
                /* Check for the following:
                [1] Depth is Even (for MaxValue and for Agent)
                [2] Character at the designated coordinates is the agent's
                [3] Agent is free on at least one direction
                */
				if(depth % 2 == 0 && (b.getSquare(i, j).getSymbol() == 'x'  ||
									  b.getSquare(i, j).getSymbol() == 'X') &&
									  isFree_Agent(i, j, b, enemy))
                {		
                    // Check in all four possible directions
                    for(k = 0; k < 4; k++) {
                        switch(directions.valueOf(move)) {
                            /** NORTHWEST AGENT */
                            case NW:
                                // Free in the NW direction, and move is valid
                                if(isFree_NW_Agent(i, j, b, enemy) &&
                                   Player.isValidMove(move, i, j, b, enemy)) {
                                    
                                    //Generate a new state and copy the board
                                    states.add(new State());
									states.get(numStates).copyBoard(b);
                                    
                                    pRow = nRow - 1;
                                    pCol = nCol - 1;

                                    //Capture is a valid option
                                    if(Player.isValidCapture(move, pRow, pCol, b, enemy)) {
                                        // Capture the piece
                                        states.get(numStates).stateCapture(pRow, pCol);
                                        pRow -= 1;
                                        pCol -= 1;
                                    }
                                    
                                    // Move the piece
                                    states.get(numStates).stateMove(nRow, nCol, pRow, pCol);

                                    // Get the state, init action to [direction, utilVal, location]
                                    util = states.get(numStates).eval();
                                    states.get(numStates).getAction().setAction(move, util, i * 10 + j);
                                    numStates++;
                                }

                                move = "NE";
                            break;

                            case NE:
                                /** NORTHEAST AGENT */
                                if(isFree_NE_Agent(i, j, b, enemy) &&
                                    Player.isValidMove(move, i, j, b, enemy)) {
                                    //Generate a new state
                                    states.add(new State());
                                    states.get(numStates).copyBoard(b);

                                    pRow = nRow - 1;
                                    pCol = nCol + 1;

                                    if(Player.isValidCapture(move, pRow, pCol, b, enemy)) {
                                        states.get(numStates).stateCapture(pRow, pCol);
                                        pRow -= 1;
                                        pCol += 1;
                                    }

                                    states.get(numStates).stateMove(nRow, nCol, pRow, pCol);

                                    // Get the state, init action to [direction, utilVal, location]
                                    util = states.get(numStates).eval();
                                    states.get(numStates).getAction().setAction(move, util, i * 10 + j);

                                    numStates++;
                                }

                                move = "SW";
                            break;

                            case SW:
                                /** SOUTHWEST AGENT */
                                if(isFree_SW_Agent(i, j, b, enemy) && 
                                   Player.isValidMove(move, i, j, b, enemy)) {
                                    //Generate a new state
                                    states.add(new State());
                                    states.get(numStates).copyBoard(b);

                                    pRow = nRow + 1;
                                    pCol = nCol - 1;

                                    if(Player.isValidCapture(move, pRow, pCol, b, enemy)) {
                                        states.get(numStates).stateCapture(pRow, pCol);
                                        pRow += 1;
                                        pCol -= 1;
                                    }

                                    states.get(numStates).stateMove(nRow, nCol, pRow, pCol);

                                   // Get the state, init action to [direction, utilVal, location]
                                   util = states.get(numStates).eval();
                                   states.get(numStates).getAction().setAction(move, util, i * 10 + j);
                                    numStates++;
                                }

                                move = "SE";
                            break;

                            case SE:
                                /** SOUTHEAST AGENT */
                                if(isFree_SE_Agent(i, j, b, enemy) &&
                                   Player.isValidMove(move, i, j, b, enemy)) {
                                    //Generate a new state
                                    states.add(new State());
                                    states.get(numStates).copyBoard(b);

                                    pRow = nRow + 1;
                                    pCol = nCol + 1;

                                    if(Player.isValidCapture(move, pRow, pCol, b, enemy)) {
                                        states.get(numStates).stateCapture(pRow, pCol);
                                        pRow += 1;
                                        pCol += 1;
                                    }

                                    states.get(numStates).stateMove(nRow, nCol, pRow, pCol);
                                    
                                    // Get the state, init action to [direction, utilVal, location]
                                    util = states.get(numStates).eval();
                                    states.get(numStates).getAction().setAction(move, util, i * 10 + j);
                                    numStates++;
                                }

                                move = "NW";
                            break;
                        }
                    }
                } else if (depth % 2 != 0 && (b.getSquare(i, j).getSymbol() == enemy.cPiece  ||
                                              b.getSquare(i, j).getSymbol() == Player.toUp(enemy.cPiece)) &&
                                              isFree_Human(i, j, b)) //FOR HUMANS
                {						
                    for(k = 0; k < 4; k++) {
                        switch(directions.valueOf(move)) {
                            case NW:
                                /** NORTHWEST HUMAN */
                                if(isFree_NW_Human(i, j, b) &&
                                   Player.isValidMove(move, i, j, b, enemy)) {
                                    
                                    //Generate a new state
                                    states.add(new State());
                                    states.get(numStates).copyBoard(b);

                                    pRow = nRow - 1;
                                    pCol = nCol - 1;

                                    if(Player.isValidCapture(move, pRow, pCol, b, enemy)) {
                                        states.get(numStates).stateCapture(pRow, pCol);
                                        pRow -= 1;
                                        pCol -= 1;
                                    }

                                    states.get(numStates).stateMove(nRow, nCol, pRow, pCol);

                                    // Get the state, init action to [direction, utilVal, location]
                                    util = states.get(numStates).eval();
                                    states.get(numStates).getAction().setAction(move, util, i * 10 + j);
                                    numStates++;
                                }

                                move = "NE";
                            break;

                            case NE:
                                /** NORTHEAST HUMAN */
                                if(isFree_NE_Human(i, j, b) &&
                                    Player.isValidMove(move, i, j, getBoard(), enemy)) {
                                    //Generate a new state
                                    states.add(new State());
                                    states.get(numStates).copyBoard(b);

                                    pRow = nRow - 1;
                                    pCol = nCol + 1;

                                    if(Player.isValidCapture(move, pRow, pCol, b, enemy)) {
                                        states.get(numStates).stateCapture(pRow, pCol);
                                        pRow -= 1;
                                        pCol += 1;
                                    }

                                    states.get(numStates).stateMove(nRow, nCol, pRow, pCol);

                                    // Get the state, init action to [direction, utilVal, location]
                                    util = states.get(numStates).eval();
                                    states.get(numStates).getAction().setAction(move, util, i * 10 + j);
                                    numStates++;
                                }

                                move = "SW";
                            break;

                            case SW:
                                /** SOUTHWEST HUMAN */
                                if(isFree_SW_Human(i, j, b) && 
                                   Player.isValidMove(move, i, j, b, enemy)) {
                                    //Generate a new state
                                    states.add(new State());
                                    states.get(numStates).copyBoard(b);

                                    pRow = nRow + 1;
                                    pCol = nCol - 1;
                                    if(Player.isValidCapture(move, pRow, pCol, b, enemy)) {
                                        states.get(numStates).stateCapture(pRow, pCol);
                                        pRow += 1;
                                        pCol -= 1;
                                    }
                                    states.get(numStates).stateMove(nRow, nCol, pRow, pCol);

                                    // Get the state, init action to [direction, utilVal, location]
                                    util = states.get(numStates).eval();
                                    states.get(numStates).getAction().setAction(move, util, i * 10 + j);
                                    numStates++;
                                }

                                move = "SE";
                            break;

                            case SE:
                                /** SOUTHEAST HUMAN */
                                if(isFree_SE_Human(i, j, b) &&
                                Player.isValidMove(move, i, j, b, enemy)) {
                                    //Generate a new state
                                    states.add(new State());
                                    states.get(numStates).copyBoard(b);

                                    pRow = nRow + 1;
                                    pCol = nCol + 1;
                                    if(Player.isValidCapture(move, pRow, pCol, b, enemy)) {
                                        states.get(numStates).stateCapture(pRow, pCol);
                                        pRow += 1;
                                        pCol += 1;
                                    }
                                    
                                    states.get(numStates).stateMove(nRow, nCol, pRow, pCol);

                                    // Get the state, init action to [direction, utilVal, location]
                                    util = states.get(numStates).eval();
                                    states.get(numStates).getAction().setAction(move, util, i * 10 + j);
                                    numStates++;
                                }

                                move = "NW";
                            break;
                        }
                    }
                }
			}
		}
	}


	/** State version for moving pieces
     * 
     * @param oRow Old row component
     * @param oCol Old column component
     * @param nRow New row component
     * @param nCol New column component
     */
	public void stateMove (int oRow, int oCol, int nRow, int nCol) {
		Piece temp;

		temp = b.getSquare(nRow, nCol).getPiece();
        b.getSquare(nRow, nCol).setPiece(b.getSquare(oRow, oCol).getPiece());
        b.getSquare(oRow, oCol).setPiece(temp);
	}


    /** State version for capturing pieces
     * 
     * @param row Row copmonent
     * @param col Column component
     */
	public void stateCapture (int row, int col) {
		b.getSquare(row, col).setPiece(new Piece('_'));
	}


    /* OTHER FUNCTIONS FOR AGENT */

    /** Is agent free when moving NW? */
    private boolean isFree_NW_Agent (int row, int col, Board b, Player p) {
        boolean NW;

        NW = false;
        if(Player.isValidRange(row - 1, col - 1) && b.getSquare(row, col).getPiece().isKing()) {
            // If one square NW is the same piece as player's, its not free
            if(b.getSquare(row - 1, col - 1).getSymbol() == 'x' ||
               b.getSquare(row - 1, col - 1).getSymbol() == Player.toUp('x'))
                NW = false; //NW (1 units) is blocked
            // If one square NW is the same piece as enemy's 
            else if(b.getSquare(row - 1, col - 1).getSymbol() == p.cPiece ||
                    b.getSquare(row - 1, col - 1).getSymbol() == Player.toUp(p.cPiece)) {
                if(Player.isValidRange(row - 2, col - 2)) {
                    //If one square NW (2 units) is a free space, it's free
                    if(b.getSquare(row - 2, col  - 2).getSymbol() == '_')
                        NW = true; //NW (2 units) is free
                } else NW = false; //NW (2 units) is blocked
            } else NW = true; //NW (1 units) is free
        } else NW = false; //NW is blocked

        return NW;
    }


    /** Is agent free when moving NE? */
    private boolean isFree_NE_Agent (int row, int col, Board b, Player p) {
        boolean NE;

        NE = false;
        //Northeast: -+; King - State for Human Pieces go NW and NE
        if(Player.isValidRange(row - 1, col + 1) && b.getSquare(row, col).getPiece().isKing()) {
            // If one square NE is the same piece as player's, its not free
            if(b.getSquare(row - 1, col + 1).getSymbol() == 'x' ||
               b.getSquare(row - 1, col + 1).getSymbol() == Player.toUp('x'))
                NE = false; //NE (1 units) is blocked
            // If one square NE is the same piece as enemy's 
            else if(b.getSquare(row - 1, col + 1).getSymbol() == p.cPiece ||
                    b.getSquare(row - 1, col + 1).getSymbol() == Player.toUp(p.cPiece)) {
                if(Player.isValidRange(row - 2, col + 2)) {
                    //If one square NE (2 units) is a free space, it's free
                    if(b.getSquare(row - 2, col + 2).getSymbol() == '_')
                        NE = true; //NE (2 units) is free
                } else NE = false; //NE (2 units) is blocked
            } else NE = true; //NE (1 units) is free
        } else NE = false; //NE is blocked

        return NE;
    }


    /** Is agent free when moving SW? */
    private boolean isFree_SW_Agent (int row, int col, Board b, Player p) {
        boolean SW;

        SW = false;
        if(Player.isValidRange(row + 1, col - 1)) {
            // If one square SW is the same piece as player's, its not free
            if(b.getSquare(row + 1, col - 1).getSymbol() == 'x' ||
               b.getSquare(row + 1, col - 1).getSymbol() == Player.toUp('x'))
                SW = false; //SW (1 units) is blocked
            // If one square SW is the same piece as enemy's 
            else if(b.getSquare(row + 1, col - 1).getSymbol() == p.cPiece ||
                    b.getSquare(row + 1, col - 1).getSymbol() == Player.toUp(p.cPiece)) {
                if(Player.isValidRange(row + 2, col - 2)) {
                    //If one square SW (2 units) is a free space, it's free
                    if(b.getSquare(row + 2, col - 2).getSymbol() == '_')
                        SW = true; //SW (2 units) is free
                } else SW = false; //SW (2 units) is blocked
            } else SW = true; //SW (1 units) is free
        } else SW = false; //SW is blocked

        return SW;
    }


    /** Is agent free when moving SE? */
    private boolean isFree_SE_Agent (int row, int col, Board b, Player p) {
        boolean SE;

        SE = false;
		
        if(Player.isValidRange(row + 1, col + 1)) {
            // If one square SE is the same piece as player's, its not free
            if(b.getSquare(row + 1, col + 1).getSymbol() == 'x' ||
               b.getSquare(row + 1, col + 1).getSymbol() == 'X')
                SE = false; //SE (1 units) is blocked
            // If one square SE is the same piece as enemy's 
            else if(b.getSquare(row + 1, col + 1).getSymbol() == p.cPiece ||
                    b.getSquare(row + 1, col + 1).getSymbol() == Player.toUp(p.cPiece)) {
                if(Player.isValidRange(row + 2, col + 2)) {
                    //If one square SE (2 units) is a free space, it's free
                    if(b.getSquare(row + 2, col + 2).getSymbol() == '_')
                        SE = true; //SE (2 units) is free
                } else SE = false; //SE (2 units) is blocked
            } else SE = true; //SE (1 units) is free
        } else SE = false; //SE is blocked

        return SE;
    }


    /** Is agent free in at least 1 direction? */
    private boolean isFree_Agent (int row, int col, Board b, Player p) {
        return isFree_NW_Agent(row, col, b, p) || isFree_NE_Agent(row, col, b, p) ||
               isFree_SW_Agent(row, col, b, p) || isFree_SE_Agent(row, col, b, p);
    }


    /** Is human free when moving NW? */
    private boolean isFree_NW_Human(int row, int col, Board b) {
        boolean NW;

        NW = false;
        if(Player.isValidRange(row - 1, col - 1)) {
            // If one square NW is the same piece as player's, its not free
            if(b.getSquare(row - 1, col - 1).getSymbol() == 'o' ||
               b.getSquare(row - 1, col - 1).getSymbol() == Player.toUp('o'))
                NW = false; //NW (1 units) is blocked
            // If one square NW is the same piece as enemy's 
            else if(b.getSquare(row - 1, col - 1).getSymbol() == 'x' ||
                    b.getSquare(row - 1, col - 1).getSymbol() == Player.toUp('x')) {
                if(Player.isValidRange(row - 2, col - 2)) {
                    //If one square NW (2 units) is a free space, it's free
                    if(b.getSquare(row - 2, col  - 2).getSymbol() == '_')
                        NW = true; //NW (2 units) is free
                } else NW = false; //NW (2 units) is blocked
            } else NW = true; //NW (1 units) is free
        } else NW = false; //NW is blocked

        return NW;
    }


    /** Is human free when moving NE? */
    private boolean isFree_NE_Human (int row, int col, Board b) {
        boolean NE;

        NE = false;
        if(Player.isValidRange(row - 1, col + 1)) {
            // If one square NE is the same piece as player's, its not free
            if(b.getSquare(row - 1, col + 1).getSymbol() == 'o' ||
               b.getSquare(row - 1, col + 1).getSymbol() == Player.toUp('o'))
                NE = false; //NE (1 units) is blocked
            // If one square NE is the same piece as enemy's 
            else if(b.getSquare(row - 1, col + 1).getSymbol() == 'x') {
                if(Player.isValidRange(row - 2, col + 2)) {
                    //If one square NE (2 units) is a free space, it's free
                    if(b.getSquare(row - 2, col  + 2).getSymbol() == '_')
                        NE = true; //NE (2 units) is free
                } else NE = false; //NE (2 units) is blocked
            } else NE = true; //NE (1 units) is free
        } else NE = false; //NE is blocked

        return NE;
    }


    /** Is human free when moving SW? */
    private boolean isFree_SW_Human (int row, int col, Board b) {
        boolean SW;

        SW = false;
        if(Player.isValidRange(row + 1, col - 1) && b.getSquare(row, col).getPiece().isKing()) {
            // If one square SW is the same piece as player's, its not free
            if(b.getSquare(row + 1, col - 1).getSymbol() == 'o' ||
               b.getSquare(row + 1, col - 1).getSymbol() == Player.toUp('o'))
                SW = false; //SW (1 units) is blocked
            // If one square SW is the same piece as enemy's 
            else if(b.getSquare(row + 1, col - 1).getSymbol() == 'x' ||
                    b.getSquare(row + 1, col - 1).getSymbol() == Player.toUp('x')) {
                if(Player.isValidRange(row + 2, col - 2)) {
                    //If one square SW (2 units) is a free space, it's free
                    if(b.getSquare(row + 2, col - 2).getSymbol() == '_')
                        SW = true; //SW (2 units) is free
                } else SW = false; //SW (2 units) is blocked
            } else SW = true; //SW (1 units) is free
        } else SW = false; //SW is blocked

        return SW;
    }


    /** Is human free when moving SE? */
    private boolean isFree_SE_Human (int row, int col, Board b) {
        boolean SE;

        SE = false;
        if(Player.isValidRange(row + 1, col + 1) && b.getSquare(row, col).getPiece().isKing()) {
            // If one square SE is the same piece as player's, its not free
            if(b.getSquare(row + 1, col + 1).getSymbol() == 'o' ||
               b.getSquare(row + 1, col + 1).getSymbol() == Player.toUp('o'))
                SE = false; //SE (1 units) is blocked
            // If one square SE is the same piece as enemy's 
            else if(b.getSquare(row + 1, col + 1).getSymbol() == 'x' ||
                    b.getSquare(row + 1, col + 1).getSymbol() == Player.toUp('x')) {
                if(Player.isValidRange(row + 2, col + 2)) {
                    //If one square SE (2 units) is a free space, it's free
                    if(b.getSquare(row + 2, col + 2).getSymbol() == '_')
                        SE = true; //SE (2 units) is free
                } else SE = false; //SE (2 units) is blocked
            } else SE = true; //SE (1 units) is free
        } else SE = false; //SE is blocked

        return SE;
    }


    /** Is human free in at least one direction? */
    private boolean isFree_Human (int row, int col, Board b) {
        return isFree_NW_Human(row, col, b) || isFree_NE_Human(row, col, b) ||
               isFree_SW_Human(row, col, b) || isFree_SE_Human(row, col, b);
    }

	private int isAdvantage () {
		return -1;
	}

	private int isDisadvantage () {
		return -1;
	}


    /** Gets the action in the state
     * 
     * @return Action contained in the state
     */
	public Action getAction() {
		return act;
	}


    /** Gets the board in the state
     * 
     * @return Board contained in the state
     */
	public Board getBoard () {
		return b;
	}
}