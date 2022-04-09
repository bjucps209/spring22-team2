package model;

public class Location {
    int row;
    int col;
    int level;

    public Location(int row, int col, int level) {
        this.row = row;
        this.col = col;
        this.level = level;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
