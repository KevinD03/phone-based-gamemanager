package a207project.fall18.GameCenter.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A SqLite helper
 */
public class SQLiteHelper extends SQLiteOpenHelper {

    public SQLiteHelper(Context context) {
        super(context,"GameCenter.db",null,6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists User(id integer primary key autoincrement, username varchar(20), password varchar(20), nickname varchar(20)) ");
        db.execSQL("create table if not exists Score(id integer primary key autoincrement, userId integer, nickname varchar(20), gameType varchar(20), finalScore integer)");
        db.execSQL("create table if not exists SaveFile(id integer primary key autoincrement, username varchar(20), gameType varchar(20), auto blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }
}
