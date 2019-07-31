package a207project.fall18.GameCenter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;


/**
 * A Tile group of Fragment. Cite from GitHub
 */
public class TileGroupFragment extends Fragment {
    private int groupId;
    private OnFragmentInteractionListener mListener;
    private View view;

    /**
     * TileGroupFragment constructor
     */
    public TileGroupFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_cell_group, container, false);
        int textViews[] = new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,
                R.id.textView5, R.id.textView6, R.id.textView7, R.id.textView8, R.id.textView9};
        for (int cell : textViews) {
            TextView textView = view.findViewById(cell);
            textView.setOnClickListener(view ->
                    mListener.onFragmentInteraction(groupId,
                            Integer.parseInt(view.getTag().toString()), view));
        } return view;
    }

    /**
     * @param groupId group Id of the fragment
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }


    /**
     * @param position the postion of a tile
     * @param value the value at the tile with being set.
     */
    public void setValue(int position, int value) {
        if (value != 0){
            getTextViews(position).setText(String.valueOf(value));
            getTextViews(position).setTextColor(Color.BLACK);
            getTextViews(position).setTypeface(Typeface.SERIF, Typeface.BOLD);
        } else {getTextViews(position).setText("");}
    }


    /**
     * @param position the postion of a tile
     * @param value the value at the tile with being set
     * @param isDuplicate a boolean tells if there is a violation of the rule of Sudoku
     */
    public void markInput(int position, int value, boolean isDuplicate){
        getTextViews(position).setText(String.valueOf(value));
        if (isDuplicate){
            getTextViews(position).setTextColor(Color.BLUE);
            getTextViews(position).setTypeface(Typeface.SERIF, Typeface.BOLD);
        } else getTextViews(position).setTextColor(Color.GRAY);
    }

    /**
     * @param position the postion of a tile
     * @return Textview of the tile
     */
    public TextView getTextViews(int position){
        int textViews[] = new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,
                R.id.textView5, R.id.textView6, R.id.textView7, R.id.textView8, R.id.textView9};
        return view.findViewById(textViews[position]);
    }

    /**
     * @return if the groups are in correct arrangement, after sorting
     */
    public boolean checkGroupCorrect() {
        ArrayList<Integer> numbers = new ArrayList<>();
        int textViews[] = new int[]{R.id.textView1, R.id.textView2, R.id.textView3, R.id.textView4,
                R.id.textView5, R.id.textView6, R.id.textView7, R.id.textView8, R.id.textView9};
        for (int tile : textViews) {
            if (numbers.contains(tile)) {
                return false;
            } else {
                numbers.add(tile);
            }
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int groupId, int cellId, View view);
    }
}