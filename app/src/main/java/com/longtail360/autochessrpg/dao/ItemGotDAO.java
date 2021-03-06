package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.MyItem;

import java.util.ArrayList;
import java.util.List;

public class ItemGotDAO {
    public static final String TABLE_NAME = "ITEM_IN_HAND";
    public static final String KEY_ID = "_id";
    public static final String ADVENTURE_ID = "adventure_id";
    public static final String ITEM_ID = "item_id";
    public static final String ON_BODY = "on_body";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ADVENTURE_ID + " INTEGER NOT NULL," +
                    ITEM_ID + " INTEGER,"+
                    ON_BODY + " INTEGER"
                    + ")";
    private SQLiteDatabase db;
    public ItemGotDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public MyItem insert(MyItem item) {
        ContentValues cv = new ContentValues();
        cv.put(ADVENTURE_ID, item.adventureId);
        cv.put(ITEM_ID, item.itemId);
        cv.put(ON_BODY, item.onBody);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(MyItem item) {
        ContentValues cv = new ContentValues();
        cv.put(ADVENTURE_ID, item.adventureId);
        cv.put(ITEM_ID, item.itemId);
        cv.put(ON_BODY, item.onBody);
        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<MyItem> getAll() {
        List<MyItem> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public List<MyItem> listByAdventureId(long advId) {
        List<MyItem> result = new ArrayList<>();
        String where = ADVENTURE_ID + "=" + advId;
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public MyItem get(long id) {
        MyItem item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public MyItem getRecord(Cursor cursor) {
        MyItem result = new MyItem();

        result.id = cursor.getLong(0);
        result.adventureId = cursor.getInt(1);
        result.itemId = cursor.getInt(2);
        result.onBody = cursor.getInt(3);
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
