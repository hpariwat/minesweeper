package base;

import gameinterface.Location;
import gameinterface.MineSweeperModel;

import java.util.Random;

public class MyMinesweeper implements MineSweeperModel{

    private int amount, width, height, action;
    private char[][] arena;
    private Tile[][] tiles;
    private boolean lost, firsttime;

    /*public static void main(String[] args) {

        Minesweeper mswp = new Minesweeper(10,8, 8);
    }*/

    public MyMinesweeper(int height, int width, int amount) throws MyInvalidRangeException {

        if(amount > height * width || height <= 0 || width <= 0) throw new MyInvalidRangeException("Error!");

        arena = new char[height][width];
        this.amount = amount;
        this.height = height;
        this.width = width;
        action = 0;
        lost = false;
        firsttime = true;
        generateMines();
        generateNumbers();
        //print();
        makeTiles();
    }

    public MyMinesweeper(int height, int width) throws MyInvalidRangeException {

        this(height, width, 0);
    }

    public void generateMines() {

        int mines = 0;
        while(mines < amount) {
            int h = new Random().nextInt(height);
            int w = new Random().nextInt(width);
            if(arena[h][w] != '*') {
                arena[h][w] = '*';
                mines++;
            }
        }
    }

    public void generateNumbers() {

        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                if(arena[h][w] != '*') {
                    arena[h][w] = this.adjacent(h, w);
                }
            }
        }
    }

    private char adjacent(int h, int w) {

        int amount = 0;
        amount += check(h - 1, w - 1);
        amount += check(h - 1, w);
        amount += check(h - 1, w + 1);
        amount += check(h, w - 1);
        amount += check(h, w + 1);
        amount += check(h + 1, w - 1);
        amount += check(h + 1, w);
        amount += check(h + 1, w + 1);
        if(amount > 0) {
            return (char)(amount + 48);
        } else {
            return '.';
        }

    }

    private int check(int h, int w) {

        if(h >= 0 && h < height && w >= 0 && w < width && arena[h][w] == '*') {
            return 1;
        } else {
            return 0;
        }
    }

    public void print() {

        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                System.out.print(arena[h][w] + " ");
            }
            System.out.println();
        }
    }

    public Tile[][] makeTiles() {

        tiles = new Tile[height][width];

        for(int h = 0; h < height; h++) {
            for(int w = 0; w < width; w++) {
                if (arena[h][w] == '.') {
                    tiles[h][w] = new EmptyTile();
                } else if (arena[h][w] == '*') {
                    tiles[h][w] = new BombTile();
                } else {
                    tiles[h][w] = new NumberTile(arena[h][w]);
                }
            }
        }

        /*System.out.println("You clicked the tile 0 0. " + tiles[0][0].leftClick());
        System.out.println("You clicked the tile 0 1. " + tiles[0][1].leftClick());
        System.out.println("You clicked the tile 1 0. " + tiles[1][0].leftClick());
        System.out.println("You clicked the tile 1 1. " + tiles[1][1].leftClick());
        tiles[2][5].rightClick();
        System.out.println("You flagged the tile 2 5.");*/

        return tiles;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public String getValueAt(Location location) {

        int r = location.getRow();
        int c = location.getColumn();

        if(r >= 0 && r < height && c >= 0 && c < width) {
            //System.out.println(String.valueOf(tiles[r][c].getChar()));
            return String.valueOf(tiles[r][c].getChar());
        } else {
            return null;
        }
    }

    @Override
    public void checkLocation(Location location) {

        int r = location.getRow();
        int c = location.getColumn();

        if(r >= 0 && r < height && c >= 0 && c < width) {
            action++;
            System.out.println(tiles[r][c].leftClick());
            if(tiles[r][c].getChar() == '*' && !firsttime){
                lost = true;
            }
            firsttime = false;
        }
    }

    @Override
    public void flagLocation(Location location) {

        int r = location.getRow();
        int c = location.getColumn();

        if(r >= 0 && r < height && c >= 0 && c < width) {
            tiles[r][c].rightClick();
            //action++;
            amount--;
            if(amount < 0) {
                amount = 0;
            }
        }
    }

    @Override
    public int getNrOfActions() {
        return action;
    }

    @Override
    public long getNrOfMinesLeft() {
        return amount;
    }

    @Override
    public boolean getLost() {
        return lost;
    }
}