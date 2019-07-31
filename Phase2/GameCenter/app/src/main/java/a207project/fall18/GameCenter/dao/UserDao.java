package a207project.fall18.GameCenter.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import a207project.fall18.GameCenter.bean.User;

/**
 * An user database
 */
public class UserDao extends Dao<User> {

    public UserDao(Context mContext) {

        super(mContext);
        sqLiteHelper = new SQLiteHelper(mContext);
    }

    @Override
    public long insert(User user) {
        SQLiteDatabase db =  sqLiteHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("username", user.getUsername());
        value.put("password", user.getPassword());
        value.put("nickname", user.getNickname());
        long result = db.insert("User", null, value);
        db.close();

        return result;
    }

    @Override
    public boolean delete(int ID) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        int result = db.delete("User", "id=?", new String[]{String.valueOf(ID)});
        boolean R = (result > 0) ? true : false;
        db.close();
        return R;
    }

    @Override
    public boolean update(User user) {
        SQLiteDatabase db = sqLiteHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("password", user.getPassword());
        value.put("nickname", user.getNickname());
        int result = db.update("User", value, "username=?", new String[]{user.getUsername()});
        boolean R = (result > 0) ? true : false;
        db.close();
        return R;
    }

    @Override
    public List<User> query(String s) {
        return null;
    }

    private boolean find(User user){
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cs = db.query("User", null, "username=?", new String[]{user.getUsername()}, null, null, null);
        boolean result = cs.moveToNext();
        cs.close();
        db.close();
        return result;
    }

    /**
     * @param user user
     * @return if the user can login in
     */
    public boolean login(User user){
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cs = db.query("User", null, "username=? and password=?", new String[]{user.getUsername(), user.getPassword()}, null, null, null);
        if (cs != null && cs.getCount() >= 1){
            while (cs.moveToNext()){
                user.setId(cs.getInt(cs.getColumnIndex("id")));
                user.setNickname(cs.getString(3));
            }
            cs.close();
            db.close();
            return true;
        }

        return false;
    }

    /**
     * @param user new user
     * @return user info in long
     */
    public long register(User user){
        long registResult = 0;
        boolean result = find(user);
        if (!result) {
            registResult = insert(user);
        }
        return registResult;
    }

}
