//--------------------------------
// Location.java
// Defines and x and y coordinate on a specific level
//----------------------------------
package model;

public class Location {
    private int row;
    private int col;
    private int level;

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




    // public void serialize(DataOutputStream file) throws IOException {
    //     file.writeInt(row);
    //     file.writeInt(col);
    //     file.writeInt(level);
    // }

    // public void deserialize(DataInputStream file) throws IOException {
    //     this.row = file.readInt();
    //     this.col = file.readInt();
    //     this.level = file.readInt();
    // }
}
