package a207project.fall18.GameCenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import a207project.fall18.GameCenter.bean.Score;

/**
 * A SudokuBoard Manager. Cite from GitHub
 */

public class SudokuBoardManager extends BoardManager implements Serializable {

    /**
     * Sudoku Game score
     */
    private Score score = new Score();

    /**
     * Sudoku Game board
     */
    private SudokuBoard sudokuBoard;

    /**
     * Final score of the game Sudoku
     */
    private int finalScore;

    /**
     * @param sudokuBoard A SudokuBoard
     */
    void setSudokuBoard(SudokuBoard sudokuBoard) {
        this.sudokuBoard = sudokuBoard;
    }


    /**
     * @param finalScore  an int of the game score
     */
    void setFinalScore(int finalScore){this.finalScore = finalScore;}

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public void setScore() {
        this.score.setFinalScore(finalScore);
        this.score.setUserId(MyApplication.getInstance().getUser().getId());
        this.score.setGameType(MyApplication.getInstance().getGame());
        this.score.setNickname(MyApplication.getInstance().getUser().getNickname());
    }

    /**
     * @return returnr a Sudoku Bard
     */
    public SudokuBoard getBoard() {
        return sudokuBoard;
    }


    /**
     * @param newBoard Copy a Sudoku Board
     */
    void copyValues(SudokuBoard newBoard) {
        for (int i = 0; i < newBoard.getSlidingTiles().length; i++) {
            for (int j = 0; j < newBoard.getSlidingTiles()[i].length; j++) {
                sudokuBoard.getSlidingTiles()[i][j] = newBoard.getSlidingTiles()[i][j];
            }
        }
    }

    /**
     * @return return if the tiles is solved correctly
     */
    boolean isBoardCorrect() {
        for (int i = 0; i < sudokuBoard.getSlidingTiles().length; i++) {
            ArrayList<Integer> horizontals = new ArrayList<>();
            ArrayList<Integer> verticals = new ArrayList<>();
            for (int j = 0; j < sudokuBoard.getSlidingTiles()[i].length; j++) {
                int number1 = sudokuBoard.getSlidingTiles()[i][j];
                int number2 = sudokuBoard.getSlidingTiles()[j][i];
                if ((horizontals.contains(number1) && (verticals.contains(number2)))) {
                    return false;
                } else {
                    horizontals.add(number1);
                    verticals.add(number2);
                }
            }
        }return true;
    }

    /**
     * @param row row
     * @param column column
     * @return return if there are duplicates of numbers
     */
    boolean checkDupulicate(int row, int column){
        ArrayList<Integer> horizontals = new ArrayList<>();
        ArrayList<Integer> verticals = new ArrayList<>();
        ArrayList<Integer> group = sudokuBoard.getTargetGroup(row,column);
        group.remove((Integer) sudokuBoard.getSlidingTiles()[row][column]);

        horizontals.addAll(Arrays.asList(sudokuBoard.getSlidingTiles()[row]));
        horizontals.remove((Integer) sudokuBoard.getSlidingTiles()[row][column]);

        for (int i = 0; i < sudokuBoard.getSlidingTiles().length; i++) {verticals.add(
                sudokuBoard.getSlidingTiles()[i][column]);}
        verticals.remove((Integer) sudokuBoard.getSlidingTiles()[row][column]);

        return horizontals.contains(sudokuBoard.getSlidingTiles()[row][column]) ||
                verticals.contains(sudokuBoard.getSlidingTiles()[row][column]) ||
                group.contains(sudokuBoard.getSlidingTiles()[row][column]);
    }
}


