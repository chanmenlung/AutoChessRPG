package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.MyCard;

import java.util.ArrayList;
import java.util.List;

public class MyCardDAO {
    public static final String TABLE_NAME = "MY_CARD";
    public static final String KEY_ID = "_id";
    public static final String ADVENTURE_ID = "adventure_id";
    public static final String CARD_ID = "card_id";
    public static final String LEVEL = "level";
    public static final String BATTLE_HP = "battleHp";
    public static final String RELIFE = "relife";
    public static final String TYPE = "type";
    public static final String LOCATION = "location";
    public static final String LOCATION_X = "location_x";
    public static final String LOCATION_Y = "location_y";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ADVENTURE_ID + " INTEGER," +
                    CARD_ID + " INTEGER," +
                    LEVEL + " INTEGER," +
                    BATTLE_HP + " INTEGER,"+
                    RELIFE + " INTEGER,"+
                    TYPE + " TEXT," +
                    LOCATION + " INTEGER," +
                    LOCATION_X + " INTEGER," +
                    LOCATION_Y + " INTEGER"
                    + ")";
    private SQLiteDatabase db;
    public MyCardDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public MyCard insert(MyCard item) {
        ContentValues cv = new ContentValues();
        cv.put(ADVENTURE_ID, item.adventureId);
        cv.put(CARD_ID, item.cardId);
        cv.put(LEVEL, item.level);
        cv.put(BATTLE_HP , item.battleHp);
        cv.put(RELIFE , item.relife);
        cv.put(TYPE, item.type);
        cv.put(LOCATION, item.location);
        cv.put(LOCATION_X, item.locationX);
        cv.put(LOCATION_Y, item.locationY);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(MyCard item) {
        ContentValues cv = new ContentValues();
        cv.put(ADVENTURE_ID, item.adventureId);
        cv.put(CARD_ID, item.cardId);
        cv.put(LEVEL, item.level);
        cv.put(BATTLE_HP , item.battleHp);
        cv.put(RELIFE , item.relife);
        cv.put(TYPE, item.type);
        cv.put(LOCATION, item.location);
        cv.put(LOCATION_X, item.locationX);
        cv.put(LOCATION_Y, item.locationY);

        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    public boolean deleteByAdvIdAndType(long advId, int type){
        String where = ADVENTURE_ID + "=" + advId+" and "+TYPE+"="+type;
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    public List<MyCard> listAll() {
        List<MyCard> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public List<MyCard> listByAdvIdAndType(long advId, int type) {
        List<MyCard> result = new ArrayList<>();
        String where = ADVENTURE_ID + "=" + advId+" and "+TYPE+"="+type;
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public MyCard get(long id) {
        MyCard item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public MyCard getRecord(Cursor cursor) {
        MyCard result = new MyCard();

        result.id = cursor.getLong(0);
        result.adventureId = cursor.getLong(1);
        result.cardId = cursor.getLong(2);
        result.level = cursor.getInt(3);
        result.battleHp = cursor.getInt(4);
        result.relife = cursor.getInt(5);
        result.type = cursor.getInt(6);
        result.location = cursor.getInt(7);
        result.locationX = cursor.getInt(8);
        result.locationY = cursor.getInt(9);
        return result;
    }

    public int getCount(long advId) {
        String where = ADVENTURE_ID + "=" + advId;
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME+" where "+where, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

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
