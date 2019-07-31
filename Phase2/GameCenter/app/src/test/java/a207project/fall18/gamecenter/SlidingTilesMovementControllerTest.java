package a207project.fall18.gamecenter;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import a207project.fall18.GameCenter.SlidingTile;
import a207project.fall18.GameCenter.SlidingTilesBoard;
import a207project.fall18.GameCenter.SlidingTilesBoardManager;
import a207project.fall18.GameCenter.SlidingTilesMovementController;
import a207project.fall18.GameCenter.Tile;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;

public class SlidingTilesMovementControllerTest {

//    private Board board;

    private SlidingTilesMovementController controller;

    private SlidingTilesBoardManager boardManager;

    private List<SlidingTile> lst;

    private int numOfInversion;

    private boolean blankOnOddRowFromBottom;

    private Context context;

    @Before
    public void setUp() throws Exception {
        SlidingTilesBoard.setNumRowsCols(4);
        lst = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            lst.add(new SlidingTile(i, i));
        }
        lst.add(new SlidingTile(14, 14));
        lst.add(new SlidingTile(13, 13));
        lst.add(new SlidingTile(16, 16));
        lst.add(new SlidingTile(15, 15));
        boardManager = new SlidingTilesBoardManager(lst);
        controller = new SlidingTilesMovementController();
        controller.setBoardManager(boardManager);
        numOfInversion = 1;
        blankOnOddRowFromBottom = true;
    }

    @Test
    public void createSolvableBoard() {
        boolean outputSolvable;
//        if (((Board.getNumRows() % 2 != 0) && (numOfInversion % 2 == 0)) ||
//                ((Board.getNumRows() % 2 == 0) && ((blankOnOddRowFromBottom) ==
//                        (numOfInversion % 2 == 0)))) {
//            outputSolvable = true;
//        } else {outputSolvable = false;}
        outputSolvable = (((SlidingTilesBoard.getNumRows() % 2 != 0) && (numOfInversion % 2 == 0)) ||
                ((SlidingTilesBoard.getNumRows() % 2 == 0) && ((blankOnOddRowFromBottom) ==
                        (numOfInversion % 2 == 0))));
        assertFalse(outputSolvable);

    }

    @Test
    public void processTapMovement() {
//        List<Tile> lst = boardManager.getTilesList();

//        int i = findBlankIndex(lst);
//        if (i == 0) {
//            inputPosition = 1;
//        } else {inputPosition = i - 1;}

        int inputPosition = 15;
        int expectedId = 16;
        int outputId;
        controller.processTapMovement(context, inputPosition, true);
        outputId = boardManager.getBoard().getTile(3, 3).getId();
        assertEquals(expectedId, outputId);

    }

    @After
    public void tearDown() throws Exception {
        lst = null;
        boardManager = null;
        controller = null;
    }

}