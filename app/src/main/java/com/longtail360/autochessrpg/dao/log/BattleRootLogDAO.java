package com.longtail360.autochessrpg.dao.log;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.dao.GameDBHelper;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.log.BattleRootLog;
import com.longtail360.autochessrpg.entity.log.ProcessLog;

import java.util.ArrayList;
import java.util.List;

public class BattleRootLogDAO {
    public static final String TABLE_NAME = "BATTLE_ROOT_LOG";
    public static final String KEY_ID = "_id";
    public static final String NUM_OF_TURNS = "numOfTurns";
    public static final String SUCCESS = "success";
    public static final String MONSTER_IMAGE = "monsterImage";
    public static final String MONSTER_NAME = "monsterName";
    public static final String PROCESS_LOG_ID = "processLogId";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NUM_OF_TURNS + " INTEGER,"+
                    SUCCESS + " INTEGER,"+
                    MONSTER_IMAGE + " TEXT,"+
                    MONSTER_NAME + " TEXT,"+
                    PROCESS_LOG_ID + " INTEGER"
                    + ")";
    ;
    private SQLiteDatabase db;
    public BattleRootLogDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public BattleRootLog insert(BattleRootLog item) {
        ContentValues cv = new ContentValues();
        cv.put(NUM_OF_TURNS, item.numOfTurns);
        cv.put(SUCCESS, item.success);
        cv.put(MONSTER_IMAGE, item.monsterImage);
        cv.put(MONSTER_NAME, item.monsterName);
        cv.put(PROCESS_LOG_ID, item.processLog.id);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(BattleRootLog item) {
        ContentValues cv = new ContentValues();
        cv.put(NUM_OF_TURNS, item.numOfTurns);
        cv.put(SUCCESS, item.success);
        cv.put(MONSTER_IMAGE, item.monsterImage);
        cv.put(MONSTER_NAME, item.monsterName);
        cv.put(PROCESS_LOG_ID, item.processLog.id);

        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<BattleRootLog> listAll() {
        List<BattleRootLog> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public BattleRootLog getByProcessId(long id) {
        BattleRootLog item = null;
        String where = PROCESS_LOG_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public BattleRootLog get(long id) {
        BattleRootLog item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public BattleRootLog getRecord(Cursor cursor) {
        BattleRootLog result = new BattleRootLog();
        result.processLog = new ProcessLog();
        result.id = cursor.getLong(0);
        result.numOfTurns = cursor.getInt(1);
        result.success = cursor.getInt(2);
        result.monsterImage = cursor.getString(3);
        result.monsterName = cursor.getString(4);
        result.processLog.id = cursor.getLong(5);
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