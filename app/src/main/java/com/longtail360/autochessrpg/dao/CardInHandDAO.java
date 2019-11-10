package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.CardInHand;

import java.util.ArrayList;
import java.util.List;

public class CardInHandDAO {
    public static final String TABLE_NAME = "CHESS_IN_HAND";
    public static final String KEY_ID = "_id";
    public static final String ADVENTURE_ID = "adventure_id";
    public static final String CARD_ID = "card_id";
    public static final String LOCATION = "location";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ADVENTURE_ID + " INTEGER," +
                    CARD_ID + " INTEGER," +
                    LOCATION + " INTEGER"
                    + ")";
    private SQLiteDatabase db;
    public CardInHandDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public CardInHand insert(CardInHand item) {
        ContentValues cv = new ContentValues();
        cv.put(ADVENTURE_ID, item.adventureId);
        cv.put(CARD_ID, item.cardId);
        cv.put(LOCATION, item.location);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(CardInHand item) {
        ContentValues cv = new ContentValues();
        cv.put(ADVENTURE_ID, item.adventureId);
        cv.put(CARD_ID, item.cardId);
        cv.put(LOCATION, item.location);
        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<CardInHand> listAll() {
        List<CardInHand> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public List<CardInHand> listByAdventureId(long advId) {
        List<CardInHand> result = new ArrayList<>();
        String where = ADVENTURE_ID + "=" + advId;
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public CardInHand get(long id) {
        CardInHand item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public CardInHand getRecord(Cursor cursor) {
        CardInHand result = new CardInHand();

        result.id = cursor.getLong(0);
        result.adventureId = cursor.getLong(1);
        result.cardId = cursor.getLong(2);
        result.location = cursor.getInt(3);

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
