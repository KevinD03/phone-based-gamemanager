package a207project.fall18.GameCenter;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * The sliding slidingTiles tiles.
 */
public class SlidingTilesBoard extends Board implements Serializable, Iterable<SlidingTile> {

    private int currentScore = 100 ;

    public static Stack s = new Stack();

    public static int getNumRows() {
        return NUM_ROWS;
    }

    /**
     * The number of rows.
     */
    static int NUM_ROWS;

    public static int getNumCols() {
        return NUM_COLS;
    }

    /**
     * The number of cols.
     */
    static int NUM_COLS;
    /**
     * The slidingTiles on the tiles in row-major order.
     */

    private SlidingTile[][] slidingTiles = new SlidingTile[NUM_ROWS][NUM_COLS];

    /**
     * A new tiles of slidingTiles in row-major order.
     * Precondition: len(slidingTiles) == NUM_ROWS * NUM_COLS
     *
     * @param slidingTiles the slidingTiles for the tiles
     */
    public SlidingTilesBoard(List<SlidingTile> slidingTiles) {
        Iterator<SlidingTile> iter = slidingTiles.iterator();
        for (int row = 0; row != SlidingTilesBoard.NUM_ROWS; row++) {
            for (int col = 0; col != SlidingTilesBoard.
                    NUM_COLS; col++) {
                this.slidingTiles[row][col] = iter.next();
            }
        }
    }

    public static void setNumRowsCols(int num) {
        NUM_ROWS = num;
        NUM_COLS = num;
    }

    /**
     * Return the number of slidingTiles on the tiles.
     *
     * @return the number of slidingTiles on the tiles
     */
    int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    public SlidingTile getTile(int row, int col) {
        return slidingTiles[row][col];
    }

    /**
     * Swap the slidingTiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    public void swapTiles(int row1, int col1, int row2, int col2) {

        SlidingTile t1 = slidingTiles[row1][col1];
        slidingTiles[row1][col1] = slidingTiles[row2][col2];
        slidingTiles[row2][col2] = t1;

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "slidingTiles=" + Arrays.toString(slidingTiles) +
                '}';
    }

    @NonNull
    @Override
    public Iterator<SlidingTile> iterator() {
        return new BoardIterator();
    }

    /**
     * The BoardIterator class
     */
    private class BoardIterator implements Iterator<SlidingTile> {

        /** The index of the tile. */
        int nextIndex = 0;
        int row = nextIndex / SlidingTilesBoard.NUM_COLS;
        int col = nextIndex % SlidingTilesBoard.NUM_COLS;

        /**
         * Return a boolean that if the current tile has the next tile
         * @return a boolean that if the current tile has the next tile
         */
        public boolean hasNext() {
            boolean toTell = true;
            if (row >= SlidingTilesBoard.NUM_ROWS) {
                toTell = false;
            }
            return toTell;
        }

        /**
         * Return the next tile at [row][col]
         * @return the next tile at [row][col]
         */
        public SlidingTile next() {
            if (hasNext()){
                SlidingTile result = slidingTiles[row][col];
                nextIndex++;
                return result;
            }
            throw new NoSuchElementException();
        }
    }

    public void scoring(){
        if (this.currentScore >0 ){
            this.currentScore -= 1;
        }
        setChanged();
        notifyObservers();
    }

    public int getCurrentScore(){
        return this.currentScore;
    }
    public void setCurrentScore(int score){this.currentScore = score;}


}
