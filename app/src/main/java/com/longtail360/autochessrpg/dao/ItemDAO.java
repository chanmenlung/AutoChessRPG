package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public static final String TABLE_NAME = "ITEM";
    public static final String KEY_ID = "_id";
    public static final String ITEM_CODE = "item_code";
    public static final String IMAGE_NAME = "image_name";
    public static final String NAME = "name";
    public static final String DESC = "desc";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ITEM_CODE + " TEXT NOT NULL,"+
                    IMAGE_NAME + " TEXT NOT NULL,"+
                    NAME + " TEXT NOT NULL," +
                    DESC + " TEXT NOT NULL"
                    + ")";
            ;
    private SQLiteDatabase db;
    public ItemDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public Item insert(Item item) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM_CODE, item.itemCode);
        cv.put(IMAGE_NAME, item.imageName);
        cv.put(NAME, item.name);
        cv.put(DESC, item.desc);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(Item item) {
        ContentValues cv = new ContentValues();
        cv.put(ITEM_CODE, item.itemCode);
        cv.put(IMAGE_NAME, item.imageName);
        cv.put(NAME, item.name);
        cv.put(DESC, item.desc);
        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<Item> listAll() {
        List<Item> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public Item getByItemCode(String code){
        Item item = null;
        String where = ITEM_CODE + "='" + code+"'";
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }
    public Item get(long id) {
        Item item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public Item getRecord(Cursor cursor) {
        Item result = new Item();

        result.id = cursor.getLong(0);
        result.itemCode = cursor.getString(1);
        result.imageName = cursor.getString(2);
        result.name = cursor.getString(3);
        result.desc = cursor.getString(4);
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
