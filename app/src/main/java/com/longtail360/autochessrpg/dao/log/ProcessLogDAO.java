package com.longtail360.autochessrpg.dao.log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.dao.GameDBHelper;
import com.longtail360.autochessrpg.entity.log.BattleRootLog;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.entity.log.RootLog;

import java.util.ArrayList;
import java.util.List;

public class ProcessLogDAO {
    public static final String TABLE_NAME = "PROCESS_LOG";
    public static final String KEY_ID = "_id";
    public static final String BATTLE_ROOT_LOG_ID = "battle_root_log_id";
    public static final String ROOT_LOG_ID = "rootLogId";
    public static final String LOG_TIME = "logTime";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String DETAIL = "detail";
    public static final String ICON1 = "icon1";
    public static final String ICON2 = "icon2";
    public static final String COLOR = "color";
    public static final String COIN = "coin";
    public static final String HPS = "hps";
    public static final String LEVELS = "levels";

    public static final String ITEM_KEYS = "item_keys";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BATTLE_ROOT_LOG_ID + " INTEGER,"+
                    ROOT_LOG_ID + " INTEGER,"+
                    LOG_TIME + " INTEGER,"+
                    TITLE + " TEXT NOT NULL,"+
                    CONTENT + " TEXT,"+
                    DETAIL + " TEXT,"+
                    ICON1 + " TEXT,"+
                    ICON2 + " TEXT,"+
                    COLOR + " TEXT,"+
                    COIN + " INTEGER,"+
                    HPS + " TEXT,"+
                    LEVELS + " TEXT,"+
                    ITEM_KEYS + " TEXT"
                    + ")";
    ;
    private SQLiteDatabase db;
    public ProcessLogDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public ProcessLog insert(ProcessLog item) {
        ContentValues cv = new ContentValues();
        if(item.battleRootLog != null){
            cv.put(BATTLE_ROOT_LOG_ID, item.battleRootLog.id);
        }
        cv.put(ROOT_LOG_ID, item.rootLog.id);
        cv.put(LOG_TIME, item.logTime);
        cv.put(TITLE, item.title);
        cv.put(CONTENT, item.content);
        cv.put(DETAIL, item.detail);
        cv.put(ICON1, item.icon1);
        cv.put(ICON2, item.icon2);
        cv.put(COLOR, item.color);
        cv.put(COIN, item.coin);
        cv.put(HPS, item.hps);
        cv.put(LEVELS, item.levels);
        cv.put(ITEM_KEYS, item.itemKeys);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(ProcessLog item) {
        ContentValues cv = new ContentValues();
        cv.put(BATTLE_ROOT_LOG_ID, item.battleRootLog.id);
        cv.put(ROOT_LOG_ID, item.rootLog.id);
        cv.put(LOG_TIME, item.logTime);
        cv.put(TITLE, item.title);
        cv.put(CONTENT, item.content);
        cv.put(DETAIL, item.detail);
        cv.put(ICON1, item.icon1);
        cv.put(ICON2, item.icon2);
        cv.put(COLOR, item.color);
        cv.put(COIN, item.coin);
        cv.put(HPS, item.hps);
        cv.put(LEVELS, item.levels);
        cv.put(ITEM_KEYS, item.itemKeys);
        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<ProcessLog> listAll() {
        List<ProcessLog> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public List<ProcessLog> listByRootLogId(long rootLogId) {
        List<ProcessLog> result = new ArrayList<>();
        String where = ROOT_LOG_ID + "=" + rootLogId;
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public ProcessLog get(long id) {
        ProcessLog item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public ProcessLog getRecord(Cursor cursor) {
        ProcessLog result = new ProcessLog();
        result.rootLog = new RootLog();
        result.battleRootLog = new BattleRootLog();
        result.id = cursor.getLong(0);
        result.battleRootLog.id = cursor.getLong(1);
        result.rootLog.id = cursor.getLong(2);
        result.logTime = cursor.getLong(3);
        result.title = cursor.getString(4);
        result.content = cursor.getString(5);
        result.detail = cursor.getString(6);
        result.icon1 = cursor.getString(7);
        result.icon2 = cursor.getString(8);
        result.color = cursor.getInt(9);
        result.coin = cursor.getInt(10);

        result.hps = cursor.getString(11);
        result.levels = cursor.getString(12);

        result.itemKeys = cursor.getString(13);
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