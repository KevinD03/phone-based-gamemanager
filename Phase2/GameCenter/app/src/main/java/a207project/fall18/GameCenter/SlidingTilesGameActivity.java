package a207project.fall18.GameCenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import a207project.fall18.GameCenter.dao.SaveDao;

/**
 * The game activity.
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements Observer, Serializable {

    private SaveDao savingManager;

    /**
     * The tiles manager.
     */
    private SlidingTilesBoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;
    // Grid View and calculated column height and width based on device size
    private SlidingTilesGestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = (SlidingTilesBoardManager) MyApplication.getInstance().getBoardManager();
        createTileButtons(this);
        setContentView(R.layout.activity_sliding_tiles_game);
        addUndoButtonListener();

        savingManager = MyApplication.getInstance().getSavingManager();



        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(SlidingTilesBoard.NUM_COLS);
        gridView.setSlidingTilesBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / SlidingTilesBoard.NUM_COLS;
                        columnHeight = displayHeight / SlidingTilesBoard.NUM_ROWS;

                        display();
                    }
                });
    }

    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.undo);
        undoButton.setOnClickListener((v) -> {
            if (this.boardManager.undo()) {
                updateTileButtons();
                gridView = findViewById(R.id.grid);
                gridView.setNumColumns(SlidingTilesBoard.NUM_COLS);
                gridView.setSlidingTilesBoardManager(boardManager);
                boardManager.getBoard().addObserver(this);
                display();
            }
            else{
                Toast.makeText(SlidingTilesGameActivity.this,"No More Undo chance！",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SlidingTilesBoard board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != SlidingTilesBoard.NUM_ROWS; row++) {
            for (int col = 0; col != SlidingTilesBoard.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    @SuppressLint("SetTextI18n")
    private void updateTileButtons() {
        SlidingTilesBoard board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / SlidingTilesBoard.NUM_ROWS;
            int col = nextPos % SlidingTilesBoard.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
        TextView scores = findViewById(R.id.Score);
        scores.setText("Scores : " + board.getCurrentScore());
        savingManager.autoSave(boardManager);
        boardManager.setScore();
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        boardManager.setScore();
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}