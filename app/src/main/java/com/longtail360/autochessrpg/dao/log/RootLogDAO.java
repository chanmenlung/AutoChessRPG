package com.longtail360.autochessrpg.dao.log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.dao.GameDBHelper;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.log.RootLog;

import java.util.ArrayList;
import java.util.List;

public class RootLogDAO {
    public static final String TABLE_NAME = "ROOT_LOG";
    public static final String KEY_ID = "_id";
    public static final String DUNGEON_ID = "dungeon_id";
    public static final String ADV_STATUS = "adv_status";
    public static final String IS_COMING_BACK = "is_coming_back";
    public static final String NUM_OF_DEAD = "num_of_dead";
    public static final String IS_HISTORY_LOG = "is_history_log";
    public static final String CURRENT_FLOOR = "currentFloor";
    public static final String CURRENT_AREA = "currentArea";
    public static final String CURRENT_BLOCK = "currentBlock";
    public static final String PROGRESS = "progress";
    public static final String STARTING_COIN = "startingCoin";
    public static final String STARTING_TIME = "starting_time";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DUNGEON_ID + " INTEGER,"+
                    ADV_STATUS + " INTEGER,"+
                    IS_COMING_BACK + " INTEGER,"+
                    NUM_OF_DEAD + " INTEGER,"+
                    IS_HISTORY_LOG + " INTEGER,"+
                    CURRENT_FLOOR + " INTEGER,"+
                    CURRENT_AREA + " INTEGER,"+
                    CURRENT_BLOCK + " INTEGER,"+
                    PROGRESS + " INTEGER,"+
                    STARTING_COIN + " INTEGER,"+
                    STARTING_TIME + " INTEGER"
                    + ")";
    ;
    private SQLiteDatabase db;
    public RootLogDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public RootLog insert(RootLog item) {
        ContentValues cv = new ContentValues();
        cv.put(DUNGEON_ID, item.dungeonId);
        cv.put(ADV_STATUS, item.advStatus);
        cv.put(IS_COMING_BACK, item.isComingBack);
        cv.put(NUM_OF_DEAD, item.numOfDead);
        cv.put(IS_HISTORY_LOG, item.isHistoryLog);
        cv.put(CURRENT_FLOOR, item.currentFloor);
        cv.put(CURRENT_AREA, item.currentArea);
        cv.put(CURRENT_BLOCK, item.currentBlock);
        cv.put(PROGRESS, item.progress);
        cv.put(STARTING_COIN, item.startingCoin);
        cv.put(STARTING_TIME, item.startingTime);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(RootLog item) {
        ContentValues cv = new ContentValues();
        cv.put(DUNGEON_ID, item.dungeonId);
        cv.put(ADV_STATUS, item.advStatus);
        cv.put(IS_COMING_BACK, item.isComingBack);
        cv.put(NUM_OF_DEAD, item.numOfDead);
        cv.put(IS_HISTORY_LOG, item.isHistoryLog);
        cv.put(CURRENT_FLOOR, item.currentFloor);
        cv.put(CURRENT_AREA, item.currentArea);
        cv.put(CURRENT_BLOCK, item.currentBlock);
        cv.put(PROGRESS, item.progress);
        cv.put(STARTING_COIN, item.startingCoin);
        cv.put(STARTING_TIME, item.startingTime);
        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<RootLog> listAll() {
        List<RootLog> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public RootLog get(long id) {
        RootLog item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public RootLog getRecord(Cursor cursor) {
        RootLog result = new RootLog();

        result.id = cursor.getLong(0);
        result.dungeonId = cursor.getLong(1);
        result.advStatus = cursor.getInt(2);
        result.isComingBack = cursor.getInt(3);
        result.numOfDead = cursor.getInt(4);
        result.isHistoryLog = cursor.getInt(5);
        result.currentFloor = cursor.getInt(6);
        result.currentArea = cursor.getInt(7);
        result.currentBlock = cursor.getInt(8);
        result.progress = cursor.getInt(9);
        result.startingCoin = cursor.getInt(10);
        result.startingTime = cursor.getLong(11);
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