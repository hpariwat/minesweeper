package base;

public abstract class Tile {

    boolean flagged = false, visible = false;

    public Tile() {

    }

    public abstract String leftClick();

    public void rightClick() {

        if(!flagged) {
            flagged = true;
        } else {
            flagged = false;
        }
    }

    public abstract char getChar();
}

class EmptyTile extends Tile {

    public EmptyTile() {

    }

    public String leftClick() {
        visible = true;
        return "This is an empty tile!";
    }

    public char getChar() {
        if(!flagged) {
            if(visible) {
                return '.';
            } else {
                return 'O';
            }
        } else {
            return 'F';
        }
    }
}

class BombTile extends Tile {

    public BombTile() {

    }

    public String leftClick() {
        visible = true;
        return "This is a bomb tile! You lose!";
    }

    public char getChar() {
        if(!flagged) {
            if(visible) {
                return '*';
            } else {
                return 'O';
            }
        } else {
            return 'F';
        }
    }
}

class NumberTile extends Tile {

    private char value;

    public NumberTile(char value) {
        this.value = value;
    }

    public String leftClick() {
        visible = true;
        return "This is a number tile with value " + value;
    }

    public char getChar() {
        if(!flagged) {
            if(visible) {
                return value;
            } else {
                return 'O';
            }
        } else {
            return 'F';
        }
    }
}