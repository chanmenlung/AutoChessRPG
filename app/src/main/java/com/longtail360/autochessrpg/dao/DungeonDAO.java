package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.Dungeon;

import java.util.ArrayList;
import java.util.List;

public class DungeonDAO {
    public static final String TABLE_NAME = "DUNGEON";
    public static final String KEY_ID = "_id";
    public static final String NAME = "name";
    public static final String MONSTER_IDS = "monster_ids";
    public static final String NUM_FLOOR = "num_floor";
    public static final String NUM_BLOCK_PER_FLOOR = "num_block_per_floor";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " TEXT," +
                    MONSTER_IDS + " TEXT," +
                    NUM_FLOOR + " INTEGER," +
                    NUM_BLOCK_PER_FLOOR + " INTEGER"
                    + ")";
            ;
    private SQLiteDatabase db;
    public DungeonDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public Dungeon insert(Dungeon item) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, item.name);
        cv.put(MONSTER_IDS, item.monsterIds);
        cv.put(NUM_FLOOR, item.numFloor);
        cv.put(NUM_BLOCK_PER_FLOOR, item.numblockPerFloor);

        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(Dungeon item) {
        ContentValues cv = new ContentValues();
        cv.put(NAME, item.name);
        cv.put(MONSTER_IDS, item.monsterIds);
        cv.put(NUM_FLOOR, item.numFloor);
        cv.put(NUM_BLOCK_PER_FLOOR, item.numblockPerFloor);

        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<Dungeon> getAll() {
        List<Dungeon> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }
    public Dungeon get(long id) {
        Dungeon item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public Dungeon getRecord(Cursor cursor) {
        Dungeon result = new Dungeon();

        result.id = cursor.getLong(0);
        result.name = cursor.getString(1);
        result.monsterIds = cursor.getString(2);
        result.numFloor = cursor.getInt(3);
        result.numblockPerFloor = cursor.getInt(4);
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
