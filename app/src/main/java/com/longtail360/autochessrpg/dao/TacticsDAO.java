package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.Adventure;
import com.longtail360.autochessrpg.entity.Player;
import com.longtail360.autochessrpg.entity.tactic.Tactics;

import java.util.ArrayList;
import java.util.List;

public class TacticsDAO {
    public static final String TABLE_NAME = "ADVENTURE";
    public static final String KEY_ID = "_id";
    public static final String DATA = "data";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DATA + " BLOB"
                    + ")";



    private SQLiteDatabase db;
    public TacticsDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public Tactics insert(Tactics tactics) {
        ContentValues cv = new ContentValues();
        cv.put(DATA, tactics.data);
        long id = db.insert(TABLE_NAME, null, cv);
        tactics.id = id;
        return tactics;
    }
    public boolean update(Tactics tactics) {
        ContentValues cv = new ContentValues();
        cv.put(DATA, tactics.data);
        String where = KEY_ID + "=" + tactics.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<Tactics> getAll() {
        List<Tactics> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }
    public Tactics get(long id) {
        Tactics item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public Tactics getRecord(Cursor cursor) {
        Tactics result = new Tactics();

        result.id = cursor.getLong(0);
        result.data = new String(cursor.getBlob(1));
        return result;
    }

    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }
}
