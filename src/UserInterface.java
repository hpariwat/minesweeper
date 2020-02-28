import base.MyInvalidRangeException;
import base.MyMinesweeper;
import base.Tile;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.*;

class UserInterface extends JFrame{

    private final int xSIZE = 12, ySIZE = 12, AMOUNT = 15;

    private int xPos, yPos, xTile, yTile, mineCount = AMOUNT, actionCount;
    private boolean gameOver;
    private boolean[][] revealed, flagged;
    private Tile[][] tiles;

    UserInterface() throws MyInvalidRangeException {

        new Game();

        for(int row = 0; row < xSIZE; row++) {
            for (int col = 0; col < ySIZE; col++) {
                revealed[row][col] = false;
                flagged[row][col] = false;
            }
        }

        actionCount = 0;

        this.setTitle("Minesweeper");
        //this.setSize(1024, 768);
        this.setSize(768, 768);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setContentPane(new Board());

        this.addMouseMotionListener(new MouseMove());
        this.addMouseListener(new MouseClick());

    }

    public class Board extends JPanel {

        Color cInit = new Color(41, 163, 219);
        Color cHover = new Color(109, 206, 255);
        Color cBomb = new Color(232, 30, 30);
        Color cFlag = new Color(235, 242, 50);
        Color cBackground = new Color(60, 82, 135);

        int prd = 50, stWidth = 72, stHeight = 80, xOff = 8, yOff = 32, spc = 2;
        //original stWidth is 209

        public void paintComponent(Graphics g) {
            g.setColor(cBackground);
            //g.fillRect(0, 0, 1018, 739);
            g.fillRect(0, 0, 762, 739);

            for(int row = 0; row < xSIZE; row++) {
                for(int col = 0; col < ySIZE; col++) {

                    g.setColor(cInit);

                    if(
                            xPos >= spc + row * prd + stWidth + xOff &&
                            xPos < row * prd + stWidth + prd - spc + xOff &&
                            yPos >= spc + col * prd + stHeight + yOff &&
                            yPos < spc + col * prd + stHeight + prd - 2 * spc + yOff)
                    {
                        g.setColor(cHover);
                        xTile = row;
                        yTile = col;
                    }

                    if(revealed[row][col]) {
                        tiles[col][row].leftClick();
                        g.setColor(Color.white);
                        if(tiles[col][row].getChar() == '*') {
                            g.setColor(cBomb);
                            gameOver = true;
                        } else if(tiles[col][row].getChar() == '.') {
                            reveal(row - 1, col - 1);
                            reveal(row - 1, col);
                            reveal(row - 1, col + 1);
                            reveal(row, col - 1);
                            reveal(row, col + 1);
                            reveal(row + 1, col - 1);
                            reveal(row + 1, col);
                            reveal(row + 1, col + 1);
                        }
                    }

                    if(flagged[row][col] && !revealed[row][col]) {
                        g.setColor(cFlag);
                    }

                    g.fillRect(
                            spc + row * prd + stWidth,
                            spc + col * prd + stHeight,
                            prd - 2 * spc,
                            prd - 2 * spc);

                    if(revealed[row][col] && tiles[col][row].getChar() != '*' && tiles[col][row].getChar() != '.') {
                        g.setColor(Color.black);
                        g.setFont(new Font("Verdana", Font.BOLD, 30));
                        g.drawString(Integer.toString(
                                tiles[col][row].getChar() - 48),
                                row * prd + stWidth + xOff + 2 * spc + 2,
                                col * prd + stHeight + yOff + 2 * spc);
                    }

                    g.setColor(Color.orange);
                    g.setFont(new Font("Verdana", Font.BOLD, 30));
                    g.drawString("Minesweeper v1.0", 220, 55);

                    g.setColor(Color.pink);
                    g.setFont(new Font("Verdana", Font.BOLD, 40));
                    g.drawString(Integer.toString(mineCount), 600, 55);

                    g.setColor(Color.cyan);
                    g.setFont(new Font("Verdana", Font.BOLD, 40));
                    g.drawString(Integer.toString(actionCount), 80, 55);
                }
            }

            if(gameOver) {
                for(int row = 0; row < xSIZE; row++) {
                    for (int col = 0; col < ySIZE; col++) {
                        tiles[col][row].leftClick();
                        if(tiles[col][row].getChar() == '*') {
                            reveal(row, col);
                        }
                        flagged[row][col] = false;
                        mineCount = AMOUNT;
                    }
                }
            }
        }

        void reveal(int row, int col) {
            if(row >= 0 && row < xSIZE && col >= 0 && col < ySIZE && !flagged[row][col]) {
                revealed[row][col] = true;
            }
        }
    }

    public class Game extends MyMinesweeper {

        Game() throws MyInvalidRangeException {

            super(ySIZE, xSIZE, mineCount);
            tiles = super.makeTiles();
            revealed = new boolean[xSIZE][ySIZE];
            flagged = new boolean[xSIZE][ySIZE];
        }
    }

    public class MouseMove implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent e) { }

        @Override
        public void mouseMoved(MouseEvent e) {
            xPos = e.getX();
            yPos = e.getY();
            //System.out.println(xpos + " " + ypos);
        }
    }

    public class MouseClick implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

            if(!gameOver) {

                actionCount++;

                if (SwingUtilities.isLeftMouseButton(e)) {
                    //System.out.println(xTile + " " + yTile);
                    revealed[xTile][yTile] = true;
                } else if (SwingUtilities.isRightMouseButton(e)) {

                    if (!flagged[xTile][yTile]) {
                        flagged[xTile][yTile] = true;
                        mineCount--;
                    } else {
                        flagged[xTile][yTile] = false;
                        mineCount++;
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

}
