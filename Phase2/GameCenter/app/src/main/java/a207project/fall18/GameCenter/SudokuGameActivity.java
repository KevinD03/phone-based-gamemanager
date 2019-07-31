package a207project.fall18.GameCenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import a207project.fall18.GameCenter.dao.SaveDao;

/**
 * Sodoku game activity. Cite from GitHub
 */
public class SudokuGameActivity extends AppCompatActivity implements
        TileGroupFragment.OnFragmentInteractionListener, Observer {

    private SaveDao savingManager;
    private final String TAG = "SlidingTilesGameActivity";
    private TimerTextView timerTextView;
    private SudokuBoardManager startBoardManager;
    private SudokuBoardManager currentBoardManager;
    static int difficulty;
    int cellGroupFragments[] = new int[]{R.id.cellGroupFragment, R.id.cellGroupFragment2,
            R.id.cellGroupFragment3, R.id.cellGroupFragment4, R.id.cellGroupFragment5,
            R.id.cellGroupFragment6, R.id.cellGroupFragment7, R.id.cellGroupFragment8,
            R.id.cellGroupFragment9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_game);

        savingManager = MyApplication.getInstance().getSavingManager();
        ArrayList<SudokuBoardManager> boards = readGameBoards(difficulty);
        if (MyApplication.getInstance().getBoardManager() == null) {
            startBoardManager = chooseRandomBoard(boards);
            MyApplication.getInstance().setBoardManager(startBoardManager);
            currentBoardManager = new SudokuBoardManager();
            currentBoardManager.setSudokuBoard(new SudokuBoard());
            currentBoardManager.copyValues(startBoardManager.getBoard());
        } else {
            startBoardManager = (SudokuBoardManager) MyApplication.getInstance().getBoardManager();
            currentBoardManager = (SudokuBoardManager) savingManager.query
                    ("get slidingTilesBoardManager").get(0);
        }
        currentBoardManager.getBoard().addObserver(this);
        updateTiles();
        this.timerTextView = findViewById(R.id.timer);
        timerTextView.setStartTime(System.currentTimeMillis());
        timerTextView.startTimer();
    }

    /**
     * Updates the Sudoku tiles
     */
    private void updateTiles(){
        for (int i = 0; i < 9; i++) {
            TileGroupFragment thisTileGroupFragment = (TileGroupFragment)
                    getSupportFragmentManager().findFragmentById(cellGroupFragments[i]);
            assert thisTileGroupFragment != null;
            thisTileGroupFragment.setGroupId(i+1);
        }
        TileGroupFragment tempTileGroupFragment;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int column = j / 3;
                int row = i / 3;

                int fragmentNumber = (row * 3) + column;
                tempTileGroupFragment = (TileGroupFragment) getSupportFragmentManager().
                        findFragmentById(cellGroupFragments[fragmentNumber]);

                int groupColumn = j % 3;
                int groupRow = i % 3;
                int groupPosition = (groupRow * 3) + groupColumn;
                int currentValue = currentBoardManager.getBoard().getTile(i,j);
                    if (currentValue != startBoardManager.getBoard().getTile(i,j)) {
                        assert tempTileGroupFragment != null;
                        tempTileGroupFragment.markInput(groupPosition, currentValue,
                                currentBoardManager.checkDupulicate(i, j));
                    } else {
                        assert tempTileGroupFragment != null;
                        tempTileGroupFragment.setValue(groupPosition, currentValue);}
            }
        }
    }

    /**
     * @param difficulty an int that represents the difficulty of the game
     * @return an array list of SudokuBoardManagers with the same difficulty, generalizing a random
     * tiles to start with
     */
    private ArrayList<SudokuBoardManager> readGameBoards(int difficulty) {
        ArrayList<SudokuBoardManager> boards = new ArrayList<>();
        int fileId = 3;
        if (difficulty == 4) {
            fileId = R.raw.normal;
        } else if (difficulty == 3) {
            fileId = R.raw.easy;
        } else if (difficulty == 5) {
            fileId = R.raw.hard;
        }
        filereader(fileId, boards);
        return boards;
    }

    /**
     * @param fileId an int that represents the R file at certain difficulty
     * @param boards an array list of Sudoku Boards that are able to be displayed
     */
    @SuppressLint("LongLogTag")
    private void filereader(int fileId, ArrayList<SudokuBoardManager> boards){
        InputStream inputStream = getResources().openRawResource(fileId);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String line = bufferedReader.readLine();
            while (line != null) {
                SudokuBoardManager boardmanager = new SudokuBoardManager();
                boardmanager.setSudokuBoard(new SudokuBoard());
                for (int i = 0; i < 9; i++) {
                    String rowTiles[] = line.split(" ");
                    for (int j = 0; j < 9; j++) {
                        if (rowTiles[j].equals("-")) {
                            boardmanager.getBoard().setTile(i, j, 0);
                        } else {
                            boardmanager.getBoard().setTile(i, j, Integer.parseInt(rowTiles[j]));
                        }
                    }
                    line = bufferedReader.readLine();
                } boards.add(boardmanager);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * @param boards an array list of SudokuBoardManagers
     * @return an randomly selected a207project.fall18.GameCenter.SudokuBoard
     */
    // Controller
    private SudokuBoardManager chooseRandomBoard(ArrayList<SudokuBoardManager> boards) {
        int randomNumber = (int) (Math.random() * boards.size());
        return boards.get(randomNumber);
    }


    /**
     * @param group a selected group
     * @param cell a selected cell
     * @return if the selected is a starting piece
     */
    // Controller
    private boolean isStartPiece(int group, int cell) {
        int row = ((group-1)/3)*3 + (cell/3);
        int column = ((group-1)%3)*3 + ((cell)%3);
        return startBoardManager.getBoard().getTile(row, column) != 0;
    }


    /**
     * @return if layout TileGroupFragment has correct groups
     */
    private boolean checkAllGroups() {
        for (int i = 0; i < 9; i++) {
            TileGroupFragment thisTileGroupFragment = (TileGroupFragment)
                    getSupportFragmentManager().findFragmentById(cellGroupFragments[i]);
            assert thisTileGroupFragment != null;
            if (!thisTileGroupFragment.checkGroupCorrect()) {
                return false;
            }
        }
        return true;
    }

    public void onCheckBoardButtonClicked(View view) {
        if(checkAllGroups() && currentBoardManager.isBoardCorrect()) {
            Toast.makeText(this, getString(R.string.board_correct),
                    Toast.LENGTH_SHORT).show();
            timerTextView.stopTimer();
            String duration = timerTextView.getDurationBreakdown
                    (System.currentTimeMillis() - timerTextView.getStartTime());
            String[] temp = duration.split(":");
            int time = Integer.parseInt(temp[0]) + Integer.parseInt(temp[1]) + Integer.parseInt(temp[2]);
            currentBoardManager.setFinalScore((int) 10000/time);
            currentBoardManager.setScore();
            MyApplication.getInstance().getScoreDao().uploadScore(currentBoardManager.getScore());


            timerTextView.setText(duration);
            Intent intent = new Intent(this, ScoreboardActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.board_incorrect),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onGoBackButtonClicked(View view) {
        finish();
    }

    public void onShowInstructionsButtonClicked(View view) {
        Intent intent = new Intent( this, SudokuInstructionsActivity.class);
        startActivity(intent);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onFragmentInteraction(int groupId, int cellId, View view) {
        Log.i(TAG, "Clicked group " + groupId + ", cell " + cellId);
        if (!isStartPiece(groupId, cellId)) {
            int row = ((groupId -1)/3)*3 + (cellId /3);
            int column = ((groupId -1)%3)*3 + ((cellId)%3);
            KeypadDialog keypadDialog = new KeypadDialog(this,
                    new KeypadDialog.PriorityListener() {
                @Override
                public void refreshPriorityUI(String string) {
                    currentBoardManager.getBoard().setTile(row, column, Integer.parseInt(string));
                    savingManager.autoSave(currentBoardManager);
                }
            } );
            keypadDialog.show();
        } else {
            Toast.makeText(this, getString(R.string.start_piece_error),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateTiles();
    }
}
