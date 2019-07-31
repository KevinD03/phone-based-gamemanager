package a207project.fall18.GameCenter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * SudokuBoard. Cite from GitHub
 */
public class SudokuBoard extends Board implements Serializable {
    /**
     * The tiles on the tiles in row-major order.
     */
    private Integer[][] tiles = new Integer[9][9];

    public void setTiles(int[][] tiles) {
        this.tiles = new Integer[9][9];
    }

    Integer[][] getSlidingTiles() {
        return tiles;
    }

    /**
     * @param row row
     * @param column column
     * @param value value being set
     */
    void setTile(int row, int column, int value) {
        tiles[row][column] = value;
        setChanged();
        notifyObservers();
    }

    /**
     * @param row row
     * @param column column
     * @return get the value at a given and a given column in the Sudoku Board
     */
    int getTile(int row, int column) {
        return tiles[row][column];
    }

    /**
     * @param row row
     * @param column column
     * @return return an array list of integers that are in specific cell group. The cell gorup
     * includes
     * the cell at the given row and column
     */
    ArrayList<Integer> getTargetGroup(int row, int column){

        int temp1 = row;
        int temp2 = column;
        if (temp1 % 3 == 0){temp1++;};
        if (temp1 % 3 == 2){temp1--;};
        if (temp2 % 3 == 0){temp2++;};
        if (temp2 % 3 == 2){temp2--;};
        return new ArrayList<>(Arrays.asList(
                tiles[temp1][temp2],tiles[temp1][temp2-1], tiles[temp1][temp2+1],
               tiles[temp1+1][temp2],tiles[temp1+1][temp2-1],tiles[temp1+1][temp2+1],
               tiles[temp1-1][temp2],tiles[temp1-1][temp2-1],tiles[temp1-1][temp2+1]));
    }
}
