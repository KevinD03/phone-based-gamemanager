package a207project.fall18.GameCenter;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.Hashtable;
import java.util.Random;

/**
 * The tic tac toe game activity.
 */
public class TicTacToeGameActivity extends AppCompatActivity implements View.OnClickListener {
    private static Hashtable<Integer, Integer> boardImages = new Hashtable<>();
    /**
     * Dim of the tiles.
     */
    public static int size = 3;
    /**
     * The game with the num of the scale.
     */
    private static TicTacToeBoardManager ticTacToeBoardManager = new TicTacToeBoardManager(size);
    private static TicTacToeRandomPlayer computer = new TicTacToeRandomPlayer(ticTacToeBoardManager);
    private int player = TicTacToeBoardManager.X;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tictactoe_game);

        ticTacToeBoardManager = new TicTacToeBoardManager(size);
        ticTacToeBoardManager.SwitchAI(computer);
        boardImages.put(TicTacToeBoardManager.EMPTY, R.drawable.ttt_blank);
        boardImages.put(TicTacToeBoardManager.X, R.drawable.ttt_x);
        boardImages.put(TicTacToeBoardManager.O, R.drawable.ttt_o);

        GridLayout grid = findViewById(R.id.board);
        grid.setOnClickListener(this);
        grid.setColumnCount(size);

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                ImageView field = new ImageView(this);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                        GridLayout.spec(row, 1f),
                        GridLayout.spec(col, 1f));
                params.height = 0;
                params.width = 0;
                field.setLayoutParams(params);

                field.setScaleType(ImageView.ScaleType.FIT_XY);
                //noinspection ConstantConditions
                field.setImageResource(boardImages.get(TicTacToeBoardManager.EMPTY));
                field.setId(row * size + col);
                grid.addView(field);
            }
        }

        for (int i = 0; i < grid.getChildCount(); i++) {
            ImageView field = (ImageView) grid.getChildAt(i);
            field.setOnClickListener(this);
        }
        Random random = new Random();
        if (random.nextBoolean()) MoveOpponent();
        setaddBackButtonListener();
        setTitle("TicTacToe");
    }

    @Override
    public void onClick(View v) {
        ImageView field = (ImageView) v;
        int tileId = field.getId();

        if (ticTacToeBoardManager.Move(tileId, player)) {
            field.setImageResource(boardImages.get(player));

            if (ticTacToeBoardManager.won) {
                ticTacToeBoardManager.setScore();
                MyApplication.getInstance().getScoreDao().uploadScore(ticTacToeBoardManager.getScore());
                DeclareResult("You Win!!");
                Intent intent = new Intent(this, ScoreboardActivity.class);
                startActivity(intent);
            }
            else {
                MoveOpponent();
            }
        }

        if (!ticTacToeBoardManager.won && ticTacToeBoardManager.getTicTacToeBoard().isFull()) {
            DeclareResult("It's a draw!");
        }
    }

    /**
     * update when computer move and check finish the game or not.
     */
    private void MoveOpponent() {
        int opponent = player * -1;
        int moveId = ticTacToeBoardManager.getMove(opponent);

        if (moveId >= 0) {
            ticTacToeBoardManager.Move(moveId, opponent);
            ImageView opponentField = findViewById(moveId);
            opponentField.setImageResource(boardImages.get(opponent));

            if (ticTacToeBoardManager.won) {
                DeclareResult("You Lose!!");
            }
        }
    }


    /**
     * @param message A CharSequence message popping out to tell whether win or not
     */
    public void DeclareResult(CharSequence message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setNeutralButton("OK", (dialog, which) -> NewGame());
        alertDialogBuilder.setOnCancelListener(dialog -> NewGame());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void NewGame() {
        this.recreate();
    }

    /**
     * Back to the Game selection activity.
     */
    private void setaddBackButtonListener() {
        Button Game = findViewById(R.id.Game);
        Game.setOnClickListener((v) -> {
            Intent i = new Intent(this, GameSelectionActivity.class);
            startActivity(i);
        });
    }

}