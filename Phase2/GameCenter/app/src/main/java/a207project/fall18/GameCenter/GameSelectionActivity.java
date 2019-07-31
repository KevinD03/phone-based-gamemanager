package a207project.fall18.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class
GameSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);
        setTitle("Select Game");

        setupGame1ButtonListener();
        setupGame2ButtonListener();
        setupGame3ButtonListener();
        LogoutButtonListener();
    }

    /**
     * Button for SlidingTiles
     */
    private void setupGame1ButtonListener(){
        Button Game1 = findViewById(R.id.Game1);
        Game1.setOnClickListener((v) -> {
            MyApplication.getInstance().setGame("SlidingTiles");
            MyApplication.getInstance().initSavingManager();

            Intent i = new Intent(this, SlidingTilesStartingActivity.class);
            startActivity(i);
        });
    }

    /**
     * Logout button
     */
    private void LogoutButtonListener(){
        Button Logout= findViewById(R.id.Logout);
        Logout.setOnClickListener((v) -> {
            Intent i = new Intent(this, SignInActivity.class);
            startActivity(i);
        });
    }

    /**
     * Button for SlidingTiles
     */
    private void setupGame2ButtonListener(){
        Button Game2 = findViewById(R.id.Game2);
        Game2.setOnClickListener((v) -> {
            MyApplication.getInstance().setGame("TicTacToe");
            MyApplication.getInstance().initSavingManager();
            Intent i = new Intent(this, ComplexityActivity.class);
            startActivity(i);
        });
    }

    /**
     * Button for SlidingTiles
     */
    private void setupGame3ButtonListener(){
        Button Game3 = findViewById(R.id.Game3);
        Game3.setOnClickListener((v) -> {
            MyApplication.getInstance().setGame("Sudoku");
            MyApplication.getInstance().initSavingManager();
            MyApplication.getInstance().setBoardManager(null);
            Intent i = new Intent(this, SudokuInitialActivity.class);
            startActivity(i);
        });
    }
}
