/**
 * CSINTSY, Term 1, A.Y. 2021 - 2022
 * Machine Course Output 2: Checkers using Adversarial Search
 * 
 * Name     :   Alfonso Miguel G. Cruz
 * Section  :   S12
 * Date     :   January 31, 2022
 * Teacher  :   Ms. Yamie Suarez
 */

package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

import model.Location;

public class View extends JFrame {
    // Images for all pieces
    private static ImageIcon imgRed;
    private static ImageIcon imgRedKing;
    private static ImageIcon imgBlk;
    private static ImageIcon imgBlkKing;

    // Colors needed for the User Interface
    private final static Color RD = new Color(105, 20, 20);
    private final static Color BK = new Color(15, 15, 15);
    private final static Color LG = new Color(242, 242, 242);
    private final static Color DG = new Color(38, 38, 38);

    // Panels in creating the User Interface
    private static JPanel pBoard;
    private static JPanel pMenu;
    private static JPanel pNotif;
    private static JPanel pBtn;
    private static JPanel pInput;
    private static JPanel pCoord;
    private static JPanel pDir;

    // Button for the Human Player to move
    private static JButton btnMove;

    // Labels for the User Interface
    private static JLabel lblCoord;
    private static JLabel lblDir;
    private static JLabel lblNotif;

    // Text Input containers for the User Interface
    private static JTextField txtCoord;
    private static JTextField txtDir;
    
    // Main Board Panel for the User Interface
    private static JPanel[][] pGrid;

    /** Constructs the View or User Interface of the game  */
    public View () {
        super("Checkers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initImages();
        
        setMenu();
        setBoard();

        setSize(480, 720);
        setResizable(false);
        setVisible(true);
    }

    /** Initializes the image variables with the assets*/
    public void initImages () {
        imgRed     = new ImageIcon("view/red.png");
        imgRedKing = new ImageIcon("view/red_king.png");
        imgBlk     = new ImageIcon("view/black.png");
        imgBlkKing = new ImageIcon("view/black_king.png");
    }

    /** Sets up the panels and other objects of the User Interface  */
    public void setMenu () {
        pMenu = new JPanel();
        pMenu.setLayout(new BorderLayout());
        pMenu.setPreferredSize(new Dimension(480, 170));
        pMenu.setBackground(DG);
        
        btnMove = new JButton("MOVE");
        btnMove.setForeground(Color.WHITE);
        btnMove.setBackground(RD);
        btnMove.setFocusable(false);
        btnMove.setVisible(true);

        pBtn = new JPanel();
        pBtn.setLayout(new BorderLayout());
        pBtn.setPreferredSize(new Dimension(240, 120));
        pBtn.setBackground(DG);
        pBtn.add(btnMove, BorderLayout.CENTER);
        pBtn.setBorder(new EmptyBorder(30, 30, 30, 30));

        lblNotif = new JLabel ("<html>Welcome to Checkers!<br/>AI is Thinking...</html>");
        lblNotif.setForeground(Color.WHITE);

        pNotif = new JPanel();
        pNotif.setLayout(new FlowLayout());
        pNotif.setPreferredSize(new Dimension(480, 50));
        pNotif.setBackground(RD);
        pNotif.add(lblNotif);

        txtCoord = new JTextField();
        txtDir   = new JTextField();
        
        lblCoord = new JLabel ("Coordinate:");
        lblCoord.setForeground(Color.WHITE);
        lblDir   = new JLabel ("Direction:");
        lblDir.setForeground(Color.WHITE);

        //2 Textfields
        GridLayout gl = new GridLayout(2, 1);
        GridLayout gC = new GridLayout(1, 2);
        GridLayout gD = new GridLayout(1, 2);

        pInput = new JPanel();
        pInput.setLayout(gl);
        pInput.setPreferredSize(new Dimension(240, 120));
        pInput.setBackground(DG);

        pCoord = new JPanel();
        pCoord.setLayout(gC);
        pCoord.add(lblCoord.getName(), lblCoord);
        pCoord.add("Coordinate", txtCoord);
        pCoord.setBackground(DG);
        pCoord.setBorder(new EmptyBorder(5, 5, 5, 5));

        pDir = new JPanel();
        pDir.setLayout(gD);
        pDir.add(lblDir.getName(), lblDir);
        pDir.add("Direction", txtDir);
        pDir.setBackground(DG);
        pDir.setBorder(new EmptyBorder(5, 5, 5, 5));

        pInput.add(pCoord);
        pInput.add(pDir);
        pInput.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        pMenu.add(pNotif, BorderLayout.NORTH);
        pMenu.add(pInput, BorderLayout.WEST);
        pMenu.add(pBtn, BorderLayout.EAST);
        add(pMenu, BorderLayout.SOUTH);
        pMenu.setVisible(true);
    }

    /** Sets up the board component of the Game UI */
    public void setBoard() {
        int i, j;

        GridLayout gBoard = new GridLayout(8, 8);
        pBoard = new JPanel();
        pBoard.setLayout(gBoard);
        pBoard.setBackground(BK);

        pGrid = new JPanel[8][8];

        for(i = 0; i < 8; i++) {
            for(j = 0; j < 8; j++) {
                pGrid[i][j] = new JPanel();
                pGrid[i][j].setLayout(new FlowLayout());
                pGrid[i][j].setPreferredSize(new Dimension(60, 60));
                pGrid[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                
                if(((i % 2 == 0) && (j % 2 == 0)) ||
                    (i % 2 != 0) && (j % 2 != 0))
                    pGrid[i][j].setBackground(DG);
                else
                    pGrid[i][j].setBackground(LG);

                if(((i == 0 || i == 2) && j % 2 == 1) ||
                    ((i == 1) && j % 2 == 0))
                    pGrid[i][j].add(new JLabel(imgRed));
                else if (((i == 5 || i == 7) && j % 2 == 0) ||
                    ((i == 6) && j % 2 == 1))
                    pGrid[i][j].add(new JLabel(imgBlk));

                pBoard.add(pGrid[i][j]);
            }
        }

        add(pBoard, BorderLayout.CENTER);
    }

    /** Adds an action listener to the component
     * 
     * @param lis Action Listener object
     */
    public void setActionListener (ActionListener lis) {
        btnMove.addActionListener(lis);
    }

    /** Updates the spaces' contents in moving pieces
     * 
     * @param oCoord Old Coordinate of the piece
     * @param nCoord New Coordinate of the piece
     * @param cPiece Character representing the piece
     * @param isKing King state of the piece
     */
    public static void updateSpace (Location oCoord, Location nCoord, char cPiece, boolean isKing) {
        if(cPiece == 'x') {
            pGrid[oCoord.getRow()][oCoord.getCol()].removeAll();
            if(isKing)
                pGrid[nCoord.getRow()][nCoord.getCol()].add(new JLabel(imgRedKing));
            else
                pGrid[nCoord.getRow()][nCoord.getCol()].add(new JLabel(imgRed));
            }
        else {
            pGrid[oCoord.getRow()][oCoord.getCol()].removeAll();
            if(isKing)
                pGrid[nCoord.getRow()][nCoord.getCol()].add(new JLabel(imgBlkKing));
            else
                pGrid[nCoord.getRow()][nCoord.getCol()].add(new JLabel(imgBlk));
        }
    }

    /** Clears the contents of a panel at a coordinate
     * 
     * @param nRow Row component of the piece to remove
     * @param nCol Column component of the piece to remove
     */
    public static void clearPiece(int nRow, int nCol) {
        pGrid[nRow][nCol].removeAll();
    }

    /** Updates the notification of the game
     * 
     * @param s Message to display
     */
    public static void setNotif (String s) {
        lblNotif.setText(s);
    }
    
    /** Clears the input fields after moving  */
    public void clearFields () {
        txtCoord.setText("");
        txtDir.setText("");
    }

    /** Gets the coordinates of the pieces in one string
     * 
     * @return Integer as the row and column coordinates combined
     */
    public int getCoordinates () {
        return Integer.parseInt(txtCoord.getText());
    }

    /** Gets the direction of the piece in one string
     * 
     * @return Direction of the piece as a string
     */
    public String getDirection () {
        return txtDir.getText();
    }

    public void updateUI () {
        SwingUtilities.updateComponentTreeUI(this);
    }
}
