package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.CustomCard;
import com.longtail360.autochessrpg.entity.tactic.Tactics;

import java.util.ArrayList;
import java.util.List;

public class CustomCardDAO {
    public static final String TABLE_NAME = "CUSTOM_CARD";
    public static final String KEY_ID = "_id";
    public static final String CODE = "code";
    public static final String LOCKED = "locked";
    public static final String SHOW_BACKGROUND = "showBackground";
    public static final String CARD_NAME = "cardName";
    public static final String HEAD_BASE64 = "head";
    public static final String IMAGE_BASE64 = "image";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CODE + " INTEGER,"+
                    LOCKED + " INTEGER,"+
                    SHOW_BACKGROUND + " INTEGER,"+
                    CARD_NAME + " TEXT,"+
                    HEAD_BASE64 + " BLOB,"+
                    IMAGE_BASE64 + " BLOB"+
                    ")";



    private SQLiteDatabase db;
    public CustomCardDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public CustomCard insert(CustomCard card) {

        ContentValues cv = new ContentValues();
        cv.put(CODE, card.code);
        cv.put(LOCKED, card.locked);
        cv.put(SHOW_BACKGROUND, card.showCardBackground);
        cv.put(CARD_NAME, card.customName);
        cv.put(HEAD_BASE64, card.customHead.getBytes());
        cv.put(IMAGE_BASE64, card.customImage.getBytes());
        long id = db.insert(TABLE_NAME, null, cv);
        card.id = id;
        return card;
    }
    public boolean update(CustomCard card) {
        ContentValues cv = new ContentValues();
        cv.put(CODE, card.code);
        cv.put(LOCKED, card.locked);
        cv.put(SHOW_BACKGROUND, card.showCardBackground);
        cv.put(CARD_NAME, card.customName);
        cv.put(HEAD_BASE64, card.customHead.getBytes());
        cv.put(IMAGE_BASE64, card.customImage.getBytes());
        String where = KEY_ID + "=" + card.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    public boolean deleteAll(){
        return db.delete(TABLE_NAME, null , null) > 0;
    }


    public List<CustomCard> getAll() {
        List<CustomCard> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }
    public CustomCard get(long id) {
        CustomCard item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public CustomCard getByCode(String id) {
        CustomCard item = null;
        String where = CODE + "='" + id+"'";
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public CustomCard getRecord(Cursor cursor) {
        CustomCard result = new CustomCard();

        result.id = cursor.getLong(0);
        result.code = cursor.getString(1);
        result.locked = cursor.getInt(2);
        result.showCardBackground = cursor.getInt(3);
        result.customName = cursor.getString(4);
        result.customHead = new String(cursor.getBlob(5));
        result.customImage = new String(cursor.getBlob(6));
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
