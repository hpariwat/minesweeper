import base.MyInvalidRangeException;

public class Main implements Runnable {

    private UserInterface userInterface = new UserInterface();

    public Main() throws MyInvalidRangeException {
    }

    public static void main(String[] args) throws MyInvalidRangeException {
        new Thread(new Main()).start();
    }

    @Override
    public void run() {
        while(true) {
            userInterface.repaint();
        }
    }
}
