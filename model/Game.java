/**
 * CSINTSY, Term 1, A.Y. 2021 - 2022
 * Machine Course Output 2: Checkers using Adversarial Search
 * 
 * Name     :   Alfonso Miguel G. Cruz
 * Section  :   S12
 * Date     :   January 31, 2022
 * Teacher  :   Ms. Yamie Suarez
 */

package model;

public class Game {
    /** Board object for the game */
    private Board b;

    /** Agent player for the game */
    private Player agent;
    
    /** Human player for the game */
    private Player human;


    /** Constructs a game object */
    public Game () {
        b     = new Board();
		b.initBoard();
        agent = new Agent(b);
        human = new Human(b);
    }

    /** Execute turns of both players, starting with
     *  the agent
     * 
     * @param nCoord Piece input for the Human player
     * @param sDir Direction input for the Human player
     */
    public void playHuman(int nCoord, String sDir) {
		((Human)human).setInputs(nCoord, sDir);
        ((Human)human).turn(b, agent);
    }

    
    public void playAgent () {
        ((Agent)agent).turn(b, human);
    }
    
    /** Gets the Human player object
     * 
     * @return Human Player
     */
    public Human getHuman () {
        return (Human) human;
    }


    /** Gets the Agent player object
     * 
     * @return Agent Player
     */
    public Agent getAgent () {
        return (Agent) agent;
    }
}
