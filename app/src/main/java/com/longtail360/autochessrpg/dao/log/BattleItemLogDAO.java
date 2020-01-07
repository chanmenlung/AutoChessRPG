package com.longtail360.autochessrpg.dao.log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.dao.GameDBHelper;
import com.longtail360.autochessrpg.entity.log.BattleItemLog;
import com.longtail360.autochessrpg.entity.log.BattleRootLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 4/2/2019.
 */

public class BattleItemLogDAO {
    public static final String TABLE_NAME = "battle_item_log";
    public static final String KEY_ID = "_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String ICON1 = "icon1";
    public static final String ICON2 = "icon2";
    public static final String COLOR = "color";
    public static final String ICON1_TYPE = "icon1Type";
    public static final String BATTLE_ROOT_LOG_ID = "battleRootLogId";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    TITLE + " TEXT," +
                    CONTENT + " TEXT," +
                    ICON1 + " TEXT," +
                    ICON2 + " TEXT," +
                    COLOR + " INTEGER," +
                    ICON1_TYPE + " INTEGER," +
                    BATTLE_ROOT_LOG_ID + " INTEGER)";
    private SQLiteDatabase db;
    public BattleItemLogDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public BattleItemLog insert(BattleItemLog item) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, item.title);
        cv.put(CONTENT, item.content);
        cv.put(ICON1, item.icon1);
        cv.put(ICON2, item.icon2);
        cv.put(COLOR, item.color);
        cv.put(ICON1_TYPE, item.icon1Type);
        cv.put(BATTLE_ROOT_LOG_ID, item.battleRootLog.id);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(BattleItemLog item) {
        ContentValues cv = new ContentValues();
        cv.put(TITLE, item.title);
        cv.put(CONTENT, item.content);
        cv.put(ICON1, item.icon1);
        cv.put(ICON2, item.icon2);
        cv.put(COLOR, item.color);
        cv.put(ICON1_TYPE, item.icon1Type);
        cv.put(BATTLE_ROOT_LOG_ID, item.battleRootLog.id);
        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    public boolean deleteByBattleRootLogId(long id){
        String where = BATTLE_ROOT_LOG_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    public List<BattleItemLog> getAllByBattleRootLogId(long battleRootLogId){
        List<BattleItemLog> result = new ArrayList<>();
        String where = BATTLE_ROOT_LOG_ID + "=" + battleRootLogId;
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }
    public List<BattleItemLog> getAll() {
        List<BattleItemLog> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }
    public BattleItemLog get(long id) {
        BattleItemLog item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public BattleItemLog getRecord(Cursor cursor) {
        BattleItemLog result = new BattleItemLog();
        result.id = cursor.getLong(0);
        result.title = cursor.getString(1);
        result.content = cursor.getString(2);
        result.icon1 = cursor.getString(3);
        result.icon2 = cursor.getString(4);
        result.color = cursor.getInt(5);
        result.icon1Type = cursor.getInt(6);
        BattleRootLog battleRootLog = new BattleRootLog();
        battleRootLog.id = cursor.getInt(7);
        result.battleRootLog = battleRootLog;
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