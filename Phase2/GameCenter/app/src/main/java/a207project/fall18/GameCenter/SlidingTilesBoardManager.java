package a207project.fall18.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import a207project.fall18.GameCenter.bean.Score;


/**
 * Manage a tiles, including swapping tiles, checking for a win, and managing taps.
 */
public class SlidingTilesBoardManager extends BoardManager implements Serializable {

    private Score score = new Score();

    /**
     * The tiles being managed.
     */
    private SlidingTilesBoard board;

    public static ArrayList<Integer> s = new ArrayList<>();

    public int undo_time = 0;

    public int can_undo_time ;


    public SlidingTilesBoardManager(SlidingTilesBoard board) {this.board = board;}

    /**
     * Manage a board that has been pre-populated.
     *
     * @param tiles the board in 2D List
     */
    public SlidingTilesBoardManager(List<SlidingTile> tiles) {
        this.board = new SlidingTilesBoard(tiles);
    }

    /**
     * Manage a new shuffled board.
     */
    SlidingTilesBoardManager() {
        List<SlidingTile> tiles = new ArrayList<>();
        final int numTiles = SlidingTilesBoard.NUM_ROWS * SlidingTilesBoard.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum));
        }

        this.board = new SlidingTilesBoard(tiles);
        score = new Score(MyApplication.getInstance().getUser(), "SlidingTiles");
        makeBoard(tiles);
    }

    private void makeBoard(List<SlidingTile> lst) {
        int blankIndex = findBlank(lst);

        System.out.println(blankIndex);

        Random r = new Random();
        int bound = r.nextInt((500 - 450) + 1) + 450;

        for (int i = 0; i < bound; i++) {

            System.out.println("wtf" + i);

            int row = blankIndex / SlidingTilesBoard.NUM_ROWS;
            int col = blankIndex % SlidingTilesBoard.NUM_COLS;
            String swapWith = pickRandomTile(row, col);
            switch (swapWith) {
                case "top":
                    board.swapTiles(row, col, row - 1, col);
                    if (SlidingTilesBoard.NUM_ROWS == 3) {blankIndex -= 3;}
                    else if (SlidingTilesBoard.NUM_ROWS == 4) {blankIndex -= 4;}
                    else {blankIndex -= 5;}
                    break;
                case "below":
                    board.swapTiles(row, col, row + 1, col);
                    if (SlidingTilesBoard.NUM_ROWS == 3) {blankIndex += 3;}
                    else if (SlidingTilesBoard.NUM_ROWS == 4) {blankIndex += 4;}
                    else {blankIndex += 5;}
                    break;
                case "left":
                    board.swapTiles(row, col, row, col - 1);
                    blankIndex -= 1;
                    break;
                case "right":
                    board.swapTiles(row, col, row, col + 1);
                    blankIndex += 1;
                    break;
            }
        }
    }


    private String pickRandomTile(int row, int col) {
        List<String> listOfTiles = new ArrayList<>();
        if (row != 0) {
            listOfTiles.add("top");
        } if (row != SlidingTilesBoard.NUM_ROWS - 1) {
            listOfTiles.add("below");
        } if (col != 0) {
            listOfTiles.add("left");
        } if (col != SlidingTilesBoard.NUM_COLS - 1) {
            listOfTiles.add("right");
        }
        Collections.shuffle(listOfTiles);
        return listOfTiles.get(0);
    }

    private int findBlank(List<SlidingTile> lst) {
        int id = 0;
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).getId() == SlidingTilesBoard.NUM_ROWS * SlidingTilesBoard.NUM_COLS) {id = i;}
        }
        return id;
    }



    /**
     * Return the current tiles.
     */
    public SlidingTilesBoard getBoard() {
        return board;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        for (int i = 0; i < SlidingTilesBoard.NUM_ROWS; i++) {
            for (int j = 0; j < SlidingTilesBoard.NUM_COLS; j++) {
                if (board.getTile(i, j).getId() != i * SlidingTilesBoard.NUM_COLS + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public void undoTimePlus(){
        this.undo_time += 1;
    }

    public void setCanUndoTime(int time) {

        this.can_undo_time = time;

    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        SlidingTile[] temp = tileArray(position);
        return (temp[0] != null && temp[0].getId() == blankId)
                || (temp[1] != null && temp[1].getId() == blankId)
                || (temp[2] != null && temp[2].getId() == blankId)
                || (temp[3] != null && temp[3].getId() == blankId);
    }

    public boolean undo() {
        if (undo_time < can_undo_time && board.getCurrentScore() < 100){
            undoTimePlus();
            int col1 = s.get(s.size()-3);
            int row1 = s.get(s.size()-4);
            int col2 = s.get(s.size()-1);
            int row2 = s.get(s.size()-2);
            board.swapTiles(row1, col1, row2, col2);
            s.remove(s.size()-1);
            s.remove(s.size()-1);
            s.remove(s.size()-1);
            s.remove(s.size()-1);
            int newScore = board.getCurrentScore() + 1;
            board.setCurrentScore(newScore);
            return true;
        }
        return false;
    }

    /**
     * Process a touch at position in the tiles, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {
        int row = position / SlidingTilesBoard.NUM_ROWS;
        int col = position % SlidingTilesBoard.NUM_COLS;
        int blankId = board.numTiles();
        SlidingTile above = row == 0 ? null : board.getTile(row - 1, col);
        SlidingTile below = row == SlidingTilesBoard.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        SlidingTile left = col == 0 ? null : board.getTile(row, col - 1);
        SlidingTile right = col == SlidingTilesBoard.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        if (isValidTap(position)) {
            if (above != null && above.getId() == blankId) {
                board.swapTiles(row, col, row - 1, col);
                s.add(row-1);
                s.add(col);
                s.add(row);
                s.add(col);
            } else if (below != null && below.getId() == blankId) {
                s.add(row+1);
                s.add(col);
                s.add(row);
                s.add(col);
                board.swapTiles(row, col, row + 1, col);
            } else if (left != null && left.getId() == blankId) {
                s.add(row);
                s.add(col-1);
                s.add(row);
                s.add(col);
                board.swapTiles(row, col, row, col - 1);
            } else if (right != null && right.getId() == blankId) {
                s.add(row);
                s.add(col+1);
                s.add(row);
                s.add(col);
                board.swapTiles(row, col, row, col + 1);
            }
            board.scoring();
        }
    }


    /**
     * Return an array of the four surrounding tiles.
     *
     * @param position the position
     * @return SlidingTile[]
     */
    private SlidingTile[] tileArray(int position) {

        int row = position / SlidingTilesBoard.NUM_COLS;
        int col = position % SlidingTilesBoard.NUM_COLS;
        SlidingTile above = row == 0 ? null : board.getTile(row - 1, col);
        SlidingTile below = row == SlidingTilesBoard.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        SlidingTile left = col == 0 ? null : board.getTile(row, col - 1);
        SlidingTile right = col == SlidingTilesBoard.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return new SlidingTile[]{above, below, left, right};
    }

    public Score getScore(){return this.score;}

    public void setScore(){
        this.score.setFinalScore(board.getCurrentScore());
        this.score.setUserId(MyApplication.getInstance().getUser().getId());
        this.score.setGameType(MyApplication.getInstance().getGame());
        this.score.setNickname(MyApplication.getInstance().getUser().getNickname());
    }

}