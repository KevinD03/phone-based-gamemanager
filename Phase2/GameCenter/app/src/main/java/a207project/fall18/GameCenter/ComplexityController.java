package a207project.fall18.GameCenter;

import a207project.fall18.GameCenter.dao.SaveDao;

public class ComplexityController {
    private String game;
    private SaveDao savingManager;
    private BoardManager boardManager;
    private int undoTime;

    public ComplexityController(){
        game = MyApplication.getInstance().gameType;
        savingManager = MyApplication.getInstance().getSavingManager();
    }

    /**
     * Returns the board manager
     *
     * @return boardManager
     */
    public BoardManager getBoardManager() {
        return boardManager;
    }

    /**
     * Sets the undo times of game
     *
     * @param undoTime undo chances
     */
    public void SetUndoTime(int undoTime){
        this.undoTime = undoTime;
    }

    /**
     * Easy game
     */
    public void Easy(){
        boardManager = setBoardManager(game, 3);
    }

    /**
     * Intermediate game
     */
    public void Intermediate() {
       boardManager = setBoardManager(game, 4);
    }

    /**
     * Hard game
     */
    public void Difficult(){
        boardManager = setBoardManager(game, 5);
    }

    /**
     * Sets the boardManager for game.
     *
     * @param game game played
     * @param complexity of game
     * @return boardManager
     */
    private BoardManager setBoardManager(String game, int complexity){
        switch (game) {
            case "SlidingTiles":
                SlidingTilesBoard.setNumRowsCols(complexity);
                boardManager = new SlidingTilesBoardManager();
                ((SlidingTilesBoardManager) boardManager).setCanUndoTime(undoTime);
                savingManager.autoSave(boardManager);
                MyApplication.getInstance().setBoardManager(boardManager);
                break;
            case "TicTacToe":
                boardManager = new TicTacToeBoardManager(complexity);
                TicTacToeGameActivity.size = complexity;
                savingManager.autoSave(boardManager);
                MyApplication.getInstance().setBoardManager(boardManager);
                break;
            default:

                boardManager = new SudokuBoardManager();
                SudokuGameActivity.difficulty = complexity;
                break;
        }

        return boardManager;
    }
}
