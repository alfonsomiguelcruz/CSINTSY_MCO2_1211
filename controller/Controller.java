package controller;

import java.awt.event.*;

import javax.swing.SwingUtilities;

import model.Game;
import view.View;

public class Controller implements ActionListener{
    private Game g;
    private View v;

    private int nCoord;
    private String sDir;

    public Controller () {
        g = new Game();
        v = new View();
		
		v.setActionListener(this);
    }

    public void actionPerformed (ActionEvent e) {
        if(e.getActionCommand().equals("MOVE")) {
            nCoord = v.getCoordinates();
            sDir = v.getDirection();
            
            System.out.println("HUMAN'S TURN");
            if(!g.getHuman().isLoser() && !g.getHuman().isDone()) {
                g.playHuman(nCoord, sDir);
            } else View.setNotif("Error Human");

            if(g.getHuman().isDone()) {
                v.clearFields();
                View.setNotif("Agent Turn");
                SwingUtilities.updateComponentTreeUI(v);
            }

            displayHuman();
            displayAgent();
            if(g.getAgent().isLoser() && !g.getHuman().isLoser())
                View.setNotif("Human Wins");

        } else if (e.getActionCommand().equals("AGENT")) {
            nCoord = v.getCoordinates();
            sDir = v.getDirection();

            System.out.println("AGENT's TURN");
            if(!g.getAgent().isLoser() && !g.getAgent().isDone())
                g.playAgent(nCoord, sDir);
            else View.setNotif("Error Agent");

            if(g.getAgent().isDone() && !g.getAgent().isLoser()) {
                v.clearFields();
                View.setNotif("Human Turn");
                SwingUtilities.updateComponentTreeUI(v);
            }

            displayHuman();
            displayAgent();
           if(!g.getAgent().isLoser() && g.getHuman().isLoser())
                View.setNotif("Agent Wins");
        }
    }

    void displayHuman () {
        System.out.println("HUMAN LOSER? " + g.getHuman().isLoser());
        System.out.println("HUMAN DONE? " + g.getHuman().isDone());
    }

    void displayAgent() {
        System.out.println("AGENT LOSER? " + g.getAgent().isLoser());
        System.out.println("AGENT DONE? " + g.getAgent().isDone());
    }

    public static void main(String[] args) {
        Controller c = new Controller();
    }
}
