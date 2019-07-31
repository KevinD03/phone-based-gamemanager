package a207project.fall18.GameCenter;


import java.util.ArrayList;
import java.util.Hashtable;

/**
 * TicTacToe score class, Cite from Github.
 */
class TicTacToeScore {
    /**
     * int Empty = 0
     */
    static final int EMPTY = TicTacToeBoardManager.EMPTY;
    /**
     * int Blocked = 100
     */
    static final int BLOCKED = 100;


    /**
     *  Line Status
     */
    private class LineStatus {
        int state;
        int count;
    }

    private Hashtable<String, LineStatus> lines;
    private int size;

    /**
     * TTT score which is scored by row, col, left diagonal, right diagonal.
     *
     * @param size dim of tiles.
     */
    TicTacToeScore(int size) {
        this.size = size;
        lines = new Hashtable<>();

        for (int i = 0; i < this.size; i++) {
            lines.put("ROW" + i, new LineStatus()); // rows
            lines.put("COL" + i, new LineStatus()); // columns
        }

        lines.put("DL", new LineStatus()); // diagonal left
        lines.put("DR", new LineStatus()); // diagonal right
    }

    /**
     * Update the new tiles after move/click.
     *
     * @param tileId Index of tile.
     * @param player the player.
     * @return win or not.
     */
    boolean Update(int tileId, int player) {
        boolean won = false;

        int row = tileId / size;
        int col = tileId % size;

        ArrayList<String> lineKeys = new ArrayList<>();
        lineKeys.add("ROW" + row); //
        lineKeys.add("COL" + col);

        if (row == col) {
            lineKeys.add("DL");
        }
        if (row + col + 1 == size) {
            lineKeys.add("DR");
        }

        for (String key : lineKeys) {
            LineStatus lineStatus = lines.get(key);
            if (lineStatus.state == EMPTY) {
                lineStatus.state = player;
            } else if (lineStatus.state != player) {
                lineStatus.state = BLOCKED;
            }

            lineStatus.count += player;

            if (lineStatus.count * player >= size) {
                won = true;
            }

        }
        return won;
    }
}
