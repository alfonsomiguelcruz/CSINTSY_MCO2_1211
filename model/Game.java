package model;

public class Game {
    private Board b;
    private Player agent;
    private Player human;

    public Game () {
        b     = new Board();
        agent = new Agent(b);
        human = new Human(b);
        display();
    }

    public void display () {
        b.display();
    }

    // public void play () {
    //     while(!agent.isLoser() && !human.isLoser()) {
    //         // System.out.println("AGENT TURN");
    //         // while(!human.isLoser() && !agent.isDone()) {
    //         //     display();
    //         //     ((Agent)agent).turn(b, human);
    //         // }

    //         //System.out.println("HUMAN TURN");
    //         //while(!agent.isLoser() && !human.isDone()) {
    //             display();
    //         //}
    //     }

    //     if(agent.isLoser())
    //         System.out.println("Human Player Wins!");
    //     else
    //         System.out.println("Agent Player Wins!");
    // }

    public void playHuman(int nCoord, String sDir) {
        ((Human)human).setInputs(nCoord, sDir);
        ((Human)human).turn(b, agent);
        display();
    }

    public void playAgent (int nCoord, String sDir) {
        ((Agent)agent).setInputs(nCoord, sDir);
        ((Agent)agent).turn(b, human);
        display();
    }

    public Human getHuman () {
        return (Human) human;
    }

    public Agent getAgent () {
        return (Agent) agent;
    }
}
