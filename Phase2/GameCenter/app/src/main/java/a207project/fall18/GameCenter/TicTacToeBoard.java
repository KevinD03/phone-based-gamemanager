package a207project.fall18.GameCenter;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * TicTacToe Board class
 */
public class TicTacToeBoard extends Board{

    /**
     * The int dim.
     */
    public int size;

    /**
     * The tiles which is consisted by col and row.
     */
    public int[][] tiles;

    /**
     * A set of all tile indice
     */
    private Set<Integer> board_tile;

    /**
     * The tic tac toe tiles.
     * @param  size the dimension of the tiles
     */
    public TicTacToeBoard(int size) {
        this.size = size;
        this.tiles = new int[size][size];

        board_tile = new TreeSet<>();
        for (int i = 0; i < size * size; i++) {
            board_tile.add(i);
        }
    }

    /**
     * Check move or not, if can move, change the tile as the player.
     * @param tileID  Index of the tile of tictactoe.
     * @param player the player.
     * @return whether to move.
     */
    public boolean move(int tileID,  int player) {
        int row = tileID / size;
        int col = tileID % size;

        if (tiles[row][col] == 0) {
            tiles[row][col] = player;
            board_tile.remove(tileID);

            return true;
        }
        return false;
    }

    /**
     * check the tile of tiles is full or not.
     * @return whether the tile of tiles is full or not.
     */
    public boolean isFull() {
        return board_tile.isEmpty();
    }

    /**
     * get a random index of random tile which can be move.
     * @return  a random index of random tile which can be move.
     */
    public int getRandomEmpty() {
        int num = new Random().nextInt(size * size);
        Integer i = ((TreeSet<Integer>)board_tile).floor(num);

        if (i == null) {
            i = ((TreeSet<Integer>)board_tile).ceiling(num);
        }
        if (i == null) {
            return -1;
        }
        return i;
    }
}
