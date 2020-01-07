package com.longtail360.autochessrpg.dao.log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.dao.GameDBHelper;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.log.TeamStatus;

import java.util.ArrayList;
import java.util.List;

public class TeamStatusDAO {
    public static final String TABLE_NAME = "TEAM_STATUS";
    public static final String KEY_ID = "_id";
    public static final String CARD_IDS = "cardIds";
    public static final String LEVELS = "levels";
    public static final String HPS = "hps";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CARD_IDS + " TEXT,"+
                    LEVELS + " TEXT,"+
                    HPS + " TEXT"
                    + ")";
    ;
    private SQLiteDatabase db;
    public TeamStatusDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public TeamStatus insert(TeamStatus item) {
        ContentValues cv = new ContentValues();
        cv.put(CARD_IDS, item.cardIds);
        cv.put(LEVELS, item.levels);
        cv.put(HPS, item.hps);

        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(TeamStatus item) {
        ContentValues cv = new ContentValues();
        cv.put(CARD_IDS, item.cardIds);
        cv.put(LEVELS, item.levels);
        cv.put(HPS, item.hps);

        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<TeamStatus> listAll() {
        List<TeamStatus> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public TeamStatus get(long id) {
        TeamStatus item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public TeamStatus getRecord(Cursor cursor) {
        TeamStatus result = new TeamStatus();

        result.id = cursor.getLong(0);
        result.cardIds = cursor.getString(1);
        result.levels = cursor.getString(2);
        result.hps = cursor.getString(3);

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