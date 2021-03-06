package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.Adventure;

import java.util.ArrayList;
import java.util.List;

public class AdventureDAO {
    public static final String TABLE_NAME = "ADVENTURE";
    public static final String KEY_ID = "_id";
    public static final String CURRENT_ROOT_LOG_ID = "current_root_log_id";
    public static final String CURRENT_DUNGEON_ID = "current_dungeon_id";
    public static final String COIN = "coin";
    public static final String FINAL_MARK = "final_mark";
    public static final String LEVEL = "level";
    public static final String EXP = "exp";
    public static final String HP = "hp";
    public static final String LOCK_BUYING_CARD = "lock_buying_card";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CURRENT_ROOT_LOG_ID + " INTEGER," +
                    CURRENT_DUNGEON_ID + " INTEGER," +
                    COIN + " INTEGER, "+
                    LEVEL + " INTEGER, "+
                    EXP + " INTEGER, "+
                    HP + " INTEGER, "+
                    FINAL_MARK + " INTEGER," +
                    LOCK_BUYING_CARD + " INTEGER"
                    + ")";



    private SQLiteDatabase db;
    public AdventureDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public Adventure insert(Adventure item) {
        ContentValues cv = new ContentValues();
        cv.put(CURRENT_ROOT_LOG_ID, item.currentRootLogId);
        cv.put(CURRENT_DUNGEON_ID, item.currentDungeonId);
        cv.put(COIN, item.coin);
        cv.put(LEVEL, item.level);
        cv.put(EXP, item.exp);
        cv.put(HP, item.hp);
        cv.put(FINAL_MARK, item.finalMark);
        cv.put(LOCK_BUYING_CARD, item.lockBuyingCard);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(Adventure item) {
        ContentValues cv = new ContentValues();
        cv.put(CURRENT_ROOT_LOG_ID, item.currentRootLogId);
        cv.put(CURRENT_DUNGEON_ID, item.currentDungeonId);
        cv.put(COIN, item.coin);
        cv.put(LEVEL, item.level);
        cv.put(EXP, item.exp);
        cv.put(HP, item.hp);
        cv.put(FINAL_MARK, item.finalMark);
        cv.put(LOCK_BUYING_CARD, item.lockBuyingCard);
        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<Adventure> getAll() {
        List<Adventure> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }
    public Adventure get(long id) {
        Adventure item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public Adventure getRecord(Cursor cursor) {
        Adventure result = new Adventure();

        result.id = cursor.getLong(0);
        result.currentRootLogId = cursor.getLong(1);
        result.currentDungeonId = cursor.getInt(2);
        result.coin = cursor.getInt(3);
        result.level = cursor.getInt(4);
        result.exp = cursor.getInt(5);
        result.hp= cursor.getInt(6);
        result.finalMark = cursor.getInt(7);
        result.lockBuyingCard = cursor.getInt(8);
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
