package model;

import gameinterface.Location;

public class MyLocation implements Location {

    private int row, col;

    public MyLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return col;
    }
}
