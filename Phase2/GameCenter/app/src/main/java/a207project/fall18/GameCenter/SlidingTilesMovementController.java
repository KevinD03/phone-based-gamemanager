package a207project.fall18.GameCenter;

import android.content.Context;
import android.widget.Toast;
import java.util.Collections;

import a207project.fall18.GameCenter.dao.ScoreDao;


public class SlidingTilesMovementController {

    private SlidingTilesBoardManager slidingTilesBoardManager = null;



    public SlidingTilesMovementController() {
    }

    public void setBoardManager(SlidingTilesBoardManager slidingTilesBoardManager) {
        this.slidingTilesBoardManager = slidingTilesBoardManager;
    }

    public void processTapMovement(Context context, int position,  boolean display) {
        if (slidingTilesBoardManager.isValidTap(position)) {
            slidingTilesBoardManager.touchMove(position);
            if (slidingTilesBoardManager.puzzleSolved()) {
                MyApplication.getInstance().getScoreDao().uploadScore(slidingTilesBoardManager.getScore());

                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }
}

