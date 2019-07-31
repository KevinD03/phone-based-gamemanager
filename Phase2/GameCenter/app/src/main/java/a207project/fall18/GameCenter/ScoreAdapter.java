package a207project.fall18.GameCenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import a207project.fall18.GameCenter.bean.Score;

/**
 * A ScoreAdapter class.
 */
public class ScoreAdapter extends BaseAdapter {
    /**
     * List of scores
     */
    List<Score> ScoreList;
    /**
     * Inflater
     */
    /**
     * Inflater
     */
    LayoutInflater inflater;
    private String TAG="ScoreAdminAdapter";

    /**
     * Constructs a ScoreAdapter
     *
     * @param context context
     * @param ScoreList list of scores
     */
    public ScoreAdapter(Context context, List<Score> ScoreList){
        this.ScoreList = ScoreList;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return ScoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return ScoreList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ScoreAdapter.ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_score, null);
            viewHolder = new ScoreAdapter.ViewHolder();
            viewHolder.tvNickname =  convertView.findViewById(R.id.tv_nickname);
            viewHolder.tvScore = convertView.findViewById(R.id.tv_score);
            viewHolder.tvType = convertView.findViewById(R.id.tv_type);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ScoreAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.tvNickname.setText(ScoreList.get(position).getNickname());
        viewHolder.tvScore.setText(ScoreList.get(position).getFinalScore()+"");
        viewHolder.tvType.setText(ScoreList.get(position).getGameType()+"");
        return convertView;
    }

    class ViewHolder{
        TextView tvNickname;
        TextView tvScore;
        TextView tvType;
    }
}
