package gameinterface;

//import model.MyLocation;

/**
 * MineSweeper model interface, to make it easier to attach whatever kind of UI or test class to the game
 */
public interface MineSweeperModel {
    /**
     * Returns the width of the game board
     * @return width of board
     */
    int getWidth();

    /**
     * Returns the height of the game board
     * @return height of board
     */
    int getHeight();

    /**
     * Returns the value of a cell on the game board
     * @param location the location of the cell with row and column
     * @return value of cell on specified row and column
     */
    String getValueAt(gameinterface.Location location);

    /**
     * Method to fire a checkLocation on a cell on the game board
     * @param location the location of the cell with row and column
     */
    void checkLocation(gameinterface.Location location);

    /**
     * Method to fire a flagLocation on a cell on the game board
     * @param location the location of the cell with row and column
     */
    void flagLocation(gameinterface.Location location);
    /**
     * The number of actions so far
     * @return the number of clicks
     */
    int getNrOfActions();

    /**
     * The number of mines left uncovered by a flag
     * @return the number of mines to discover
     */
    long getNrOfMinesLeft();

    /**
     * Method to indicate the game is lost (by clicking a mine)
     * @return true if the game is lost
     */
    boolean getLost();

    /**
     * Method to getValue a game board
     */
    default void printBoard() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                //System.out.print(" " + getValueAt(new MyLocation(i,j)) + " ");
            }
            System.out.println();
        }
        System.out.println("nr of actions: " + getNrOfActions() + " :: mines to go: " + getNrOfMinesLeft());
    }
}
