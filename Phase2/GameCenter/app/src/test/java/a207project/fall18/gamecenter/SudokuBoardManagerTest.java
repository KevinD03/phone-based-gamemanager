package a207project.fall18.gamecenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import a207project.fall18.GameCenter.BoardManager;
import a207project.fall18.GameCenter.SudokuBoard;
import a207project.fall18.GameCenter.SudokuBoardManager;
import a207project.fall18.GameCenter.bean.Score;

import static org.junit.Assert.*;

public class SudokuBoardManagerTest {
    private SudokuBoardManager sudokuboardmanager1;
    private SudokuBoardManager sudokuboardmanager2;
    private SudokuBoardManager sudokuboardmanager3;
    private SudokuBoard sudokuboard1;
    private int score1 = 1;



    @Before
    public void setUp() throws Exception {
        sudokuboardmanager1 = new SudokuBoardManager();
        sudokuboardmanager2 = new SudokuBoardManager();
        sudokuboardmanager3 = new SudokuBoardManager();
    }

    @After
    public void tearDown() throws Exception {
        sudokuboardmanager1 = null;
        sudokuboardmanager2 = null;
        sudokuboardmanager3 = null;
    }



//    @Test
//    public void getScoreTest() {
//    }
//
//    @Test
//    public void setScoreTest() {
//    }

    @Test
    public void getBoardTest() {

    }

    @Test
    public void copyValuesTest() {
    }

    @Test
    public void isBoardCorrectTest() {
    }

    @Test
    public void checkDupulicateTest() {
    }
}