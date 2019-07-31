package a207project.fall18.GameCenter;

import java.io.Serializable;
import java.util.Observable;

import a207project.fall18.GameCenter.bean.Score;

/**
 * A super class BoardManager
 */
public abstract class BoardManager extends Observable implements Serializable {

    /**
     * Board
     */
    private Board board;


    /**
     * game score
     */
    private Score score;

    /**
     * set a score
     */
    abstract public void setScore();

    /**
     * @return game score
     */
    public Score getScore() {
        return score;
    }

    /**
     * @return board
     */
    public Board getBoard() {return board;};

    /**
     * @param board board being set
     */
    public void setBoard(Board board) {
        this.board = board;
    }

}





