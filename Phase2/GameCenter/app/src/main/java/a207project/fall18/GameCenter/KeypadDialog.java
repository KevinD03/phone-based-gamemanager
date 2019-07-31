package a207project.fall18.GameCenter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;


/**
 * KeypadDialog class.
 */
public class KeypadDialog extends Dialog{

    /**
     * Array of View.
     */
    private final View keys[] = new View[12];

    /**
     * Priority listener
     */
    public interface PriorityListener {
        /**
         * Refresher
         */
        default void refreshPriorityUI(String string) {

        }
    }

    /**
     * A listener
     */
    private PriorityListener listener;


    /**
     * Constructs a KeypadDialog
     *
     * @param context context
     * @param listener a listener
     */
    public KeypadDialog(Context context, PriorityListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keypad);
        findViews();
        setKeysListeners();
    }

    /**
     * Finds View
     */
    private void findViews(){
        keys[0]=findViewById(R.id.keypad_1);
        keys[1]=findViewById(R.id.keypad_2);
        keys[2]=findViewById(R.id.keypad_3);
        keys[3]=findViewById(R.id.keypad_4);
        keys[4]=findViewById(R.id.keypad_5);
        keys[5]=findViewById(R.id.keypad_6);
        keys[6]=findViewById(R.id.keypad_7);
        keys[7]=findViewById(R.id.keypad_8);
        keys[8]=findViewById(R.id.keypad_9);
        keys[9]=findViewById(R.id.keypad_10);
        keys[10]=findViewById(R.id.keypad_11);
        keys[10].setVisibility(View.INVISIBLE);
        keys[11]=findViewById(R.id.keypad_12);
    }


    /**
     * Sets result.
     *
     * @param cell int of cell
     */
    private void setResult(int cell){
        listener.refreshPriorityUI(String.valueOf(cell));
        dismiss();
    }

    /**
     * Sets up keys listener.
     */
    private void setKeysListeners(){
        for (int i=0; i < keys.length; i++){
            int cell;
            if (i < 9) {
                cell = i + 1;
            } else cell = 0;
            keys[i].setOnClickListener(v -> setResult(cell));
        }
        // Go back to Sudoku tiles
        keys[11].setOnClickListener(v -> dismiss());
    }
}
