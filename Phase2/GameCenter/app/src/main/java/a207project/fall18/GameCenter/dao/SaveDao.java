package a207project.fall18.GameCenter.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import a207project.fall18.GameCenter.BoardManager;

/**
 * Saving manager class
 */
public class SaveDao extends Dao<BoardManager> {

    private String gameType;
    private String username;

    public SaveDao(Context context, String gameType, String username){
        super(context);
        this.gameType = gameType;
        this.username = username;
        sqLiteHelper = new SQLiteHelper(context);
    }
    @Override
    public long insert(BoardManager boardManager) {
        byte[] boardMan = getSerializedObject(boardManager);
        SQLiteDatabase db =  sqLiteHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("username", username);
        value.put("gameType", gameType);
        value.put("auto", boardMan);
        long result = db.insert("SaveFile", null, value);
        db.close();
        return result;
    }

    @Override
    public boolean delete(int ID) {
        return false;
    }

    @Override
    public boolean update(BoardManager boardManager) {
        byte[] boardMan = getSerializedObject(boardManager);
        SQLiteDatabase db =  sqLiteHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("auto", boardMan);
        int result = db.update("SaveFile", value, "username=? and gameType=?", new String[]{username, gameType});
        boolean R = (result > 0) ? true : false;
        db.close();
        return R;
    }

    @Override
    public List<BoardManager> query(String s) {
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cs = db.query("SaveFile", null, "username=? and gameType=?", new String[]{username, gameType}, null,null,null);

        List<BoardManager> result = new ArrayList<>();

        if (cs != null && cs.getCount() >= 1) {
            while (cs.moveToNext()) {
                result.add((BoardManager) readSerializedObject(cs.getBlob(3)));
            }
        }
        cs.close();
        db.close();
        return result;

    }

    /**
     * @return if it finds the loaded game
     */
    private boolean find(){
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cs = db.query("SaveFile", null, "username=? and gameType=?", new String[]{username, gameType}, null, null, null);
        boolean result = cs.moveToNext();
        cs.close();
        db.close();
        return result;
    }

    public void autoSave(BoardManager boardManager){

        if (!find()){
            insert(boardManager);
        }
        else{
            update(boardManager);
        }
    }


    /**
     * @param s a serializable item
     * @return the s in bytes
     */
    private static byte[] getSerializedObject(Serializable s) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(s);
        } catch (IOException e) {
            return null;
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                //
            }
        }
        byte[] result = baos.toByteArray();

        return result;
    }


    /**
     * @param in array of byes
     * @return a deserializable item
     */
    private static Object readSerializedObject(byte[] in) {
        Object result = null;
        ByteArrayInputStream bais = new ByteArrayInputStream(in);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(bais);
            result = ois.readObject();
        } catch (Exception e) {
            return null;
        } finally {
            try {
                ois.close();
            } catch (Throwable e) {
            }
        }
        return result;
    }

}
