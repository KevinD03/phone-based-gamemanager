package a207project.fall18.GameCenter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import a207project.fall18.GameCenter.dao.SaveDao;

/**
 * Starting activity of Sudoku Game. Cite from GitHub.
 */
public class SudokuInitialActivity extends AppCompatActivity {

    private SaveDao savingManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku_initial);

        this.savingManager = MyApplication.getInstance().getSavingManager();
        addLoadGameButton();
    }

    /**
     * @param view current view going to insruction page
     */
    public void onStartNewGameButtonClicked(View view) {
        Intent intent = new Intent(this, ComplexityActivity.class);
        startActivity(intent);
    }

    /**
     * Load game
     */
    private void addLoadGameButton() {
        Button load = findViewById(R.id.LoadGame);
        load.setOnClickListener((v) -> {
            if (MyApplication.getInstance().getBoardManager() != null){
                Intent intent = new Intent(this, SudokuGameActivity.class);
                startActivity(intent);
            }else {Toast.makeText(SudokuInitialActivity.this,"No History！",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @param view current view going to instruction page
     */
    public void onShowInstructionsButtonClicked(View view) {
        Intent intent = new Intent(this, SudokuInstructionsActivity.class);
        startActivity(intent);
    }
}

