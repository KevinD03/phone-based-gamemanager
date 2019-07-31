package a207project.fall18.GameCenter.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import a207project.fall18.GameCenter.BoardManager;
import a207project.fall18.GameCenter.bean.Score;

/**
 * Score database class
 */
public class ScoreDao extends Dao<Score>{

    public ScoreDao(Context context){
        super(context);
        sqLiteHelper = new SQLiteHelper(context);
    }
    @Override
    public long insert(Score score) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", score.getUserId());
        values.put("nickname", score.getNickname());
        values.put("gameType", score.getGameType());
        values.put("finalScore", score.getFinalScore());
        long id = db.insert("Score", null, values);
        db.close();
        return id;
    }


    @Override
    public boolean delete(int ID) {
        return false;
    }

    @Override
    public boolean update(Score score) {

        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("finalScore", score.getFinalScore());
        int result = db.update("Score", values, "userId=? and gameType=?", new String[]{String.valueOf(score.getUserId()), score.getGameType()});
        boolean R = (result > 0) ? true : false;
        db.close();
        return R;
    }

    @Override
    public List<Score> query(String game) {


        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = db.query("Score",null,"gameType=?",new String[]{game},null,null,"finalScore DESC");
        List<Score> scoreList = new ArrayList<Score>();


        if ( cursor != null && cursor.getCount() >= 1) {

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nickname = cursor.getString(2);
                String gametype = cursor.getString(3);
                int finalScore = cursor.getInt(4);

                Score score = new Score();
                score.setId(id);
                score.setNickname(nickname);
                score.setGameType(gametype);
                score.setFinalScore(finalScore);
                scoreList.add(score);

            }
        }
        cursor.close();
        db.close();
        return scoreList;
    }

    /**
     * @param score game score
     * @return if it finds a loaded game score
     */
    private boolean find(Score score) {
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cs = db.query("Score", null, "userId=? and gameType=?", new String[]{String.valueOf(score.getUserId()), score.getGameType()}, null, null, null);
        boolean result = cs.moveToNext();
        cs.close();
        db.close();
        return result;
    }


    /**
     * @param score game score
     */
    public void uploadScore(Score score){

        if (!find(score)){
           insert(score);
        }
        else{
            SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
            Cursor cs = db.query("Score", null, "userId=? and gameType=?", new String[]{String.valueOf(score.getUserId()), score.getGameType()}, null, null, null);
            int benchMark = 0;
            if (cs != null && cs.getCount() >= 1) {
                while (cs.moveToNext()) {
                    benchMark = cs.getInt(cs.getColumnIndex("finalScore"));
                }
            }
            if (score.getFinalScore() >= benchMark){
                update(score);
            }
            cs.close();
            db.close();
        }
    }

}
