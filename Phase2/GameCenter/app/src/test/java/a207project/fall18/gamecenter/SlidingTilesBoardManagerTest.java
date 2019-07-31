package a207project.fall18.gamecenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import a207project.fall18.GameCenter.Board;
import a207project.fall18.GameCenter.BoardManager;
import a207project.fall18.GameCenter.SlidingTile;
import a207project.fall18.GameCenter.SlidingTilesBoard;
import a207project.fall18.GameCenter.SlidingTilesBoardManager;
import a207project.fall18.GameCenter.Tile;

import static org.junit.Assert.*;

public class SlidingTilesBoardManagerTest {

//    private List<Tile> lst;

    private SlidingTilesBoardManager boardManager;

    @Before
    public void setUp() throws Exception {
        SlidingTilesBoard.setNumRowsCols(3);
        List<SlidingTile> lst = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            lst.add(new SlidingTile(i, i));
        }
        boardManager = new SlidingTilesBoardManager(lst);
    }


    @Test
    public void getBoard() {
        Board output = boardManager.getBoard();
        int expectedId = 1;
        for (int row = 0; row != 3; row++) {
            for (int col = 0; col != 3; col++) {
                if (((SlidingTilesBoard) output).getTile(row, col).getId() != expectedId) {fail("Wrong board!");}
                expectedId++;
            }

        }
    }

    @Test
    public void puzzleSolved() {
        boolean output = boardManager.puzzleSolved();
        assertTrue(output);
    }

    @Test
    public void undoTimePlus() {
        int before = boardManager.undo_time;
        int expected = before + 1;
        int output;
        boardManager.undoTimePlus();
        output = boardManager.undo_time;
        assertEquals(expected, output);
    }

    @Test
    public void setCanUndoTime() {
        int input = 5;
        int output;
        boardManager.setCanUndoTime(input);
        output = boardManager.can_undo_time;
        assertEquals(input, output);
    }

    @Test
    public void isValidTap() {
        boolean output = boardManager.isValidTap(1);
        assertFalse(output);
    }

    @Test
    public void touchMove() {
        int expectedId = 8;
        int output;
        boardManager.touchMove(7);
        output = boardManager.getBoard().getTile(2, 2).getId();
        assertEquals(expectedId, output);
    }

    @Test
    public void undo() {
        int expectedId = 9;
        int output;
        boardManager.setCanUndoTime(5);
        boardManager.touchMove(7);
        boardManager.undo();
        output = boardManager.getBoard().getTile(2, 2).getId();
        assertEquals(expectedId, output);
    }

    @Test
    public void getScore() {
        boardManager.getScore().setId(1);
        int output = boardManager.getScore().getId();
        assertEquals(1, output);
    }

    @Test
    public void setScore() {
        int expected = 0;
        int output;
        output = boardManager.getScore().getFinalScore();
        assertEquals(expected, output);
    }

    @After
    public void tearDown() throws Exception {
        boardManager = null;
    }
}