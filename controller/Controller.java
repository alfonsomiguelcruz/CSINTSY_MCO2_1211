/**
 * CSINTSY, Term 1, A.Y. 2021 - 2022
 * Machine Course Output 2: Checkers using Adversarial Search
 * 
 * Name     :   Alfonso Miguel G. Cruz
 * Section  :   S12
 * Date     :   January 31, 2022
 * Teacher  :   Ms. Yamie Suarez
 */

package controller;

import java.awt.event.*;
import javax.swing.SwingUtilities;

import model.Game;
import view.View;

public class Controller implements ActionListener {
    private Game g;
    private View v;

    private int nCoord;
    private String sDir;

    public Controller () {
        g = new Game();
        v = new View();
				
		g.playAgent();
		v.setActionListener(this);
        SwingUtilities.updateComponentTreeUI(v);
    }

    public void actionPerformed (ActionEvent e) {
        if(e.getActionCommand().equals("MOVE")) {
            nCoord = v.getCoordinates();
            sDir = v.getDirection();
        
            if(!g.getHuman().isLoser() && !g.getHuman().isDone()) {
                g.playHuman(nCoord, sDir);
                SwingUtilities.updateComponentTreeUI(v);
            } else View.setNotif("Error Human");

            
            if(g.getHuman().isDone()) {
                v.clearFields();
                SwingUtilities.updateComponentTreeUI(v);

                View.setNotif("Agent Turn");
				
                g.playAgent();
                SwingUtilities.updateComponentTreeUI(v);

                View.setNotif("Human Turn");
            }


            if(g.getAgent().isLoser() && !g.getHuman().isLoser())
                View.setNotif("Human Wins");
            else if(!g.getAgent().isLoser() && g.getHuman().isLoser())
                View.setNotif("Agent Wins");

        }
    }

    public static void main(String[] args) {
        Controller c = new Controller();
    }
}
