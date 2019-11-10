package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.CardForBuying;

import java.util.ArrayList;
import java.util.List;

public class CardForBuyingDAO {
    public static final String TABLE_NAME = "CARD_FOR_BUYING";
    public static final String KEY_ID = "_id";
    public static final String ADVENTURE_ID = "adventure_id";
    public static final String CARD_ID = "card_id";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ADVENTURE_ID + " INTEGER," +
                    CARD_ID + " INTEGER"
                    + ")";
    private SQLiteDatabase db;
    public CardForBuyingDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public CardForBuying insert(CardForBuying item) {
        ContentValues cv = new ContentValues();
        cv.put(ADVENTURE_ID, item.adventureId);
        cv.put(CARD_ID, item.cardId);

        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(CardForBuying item) {
        ContentValues cv = new ContentValues();
        cv.put(ADVENTURE_ID, item.adventureId);
        cv.put(CARD_ID, item.cardId);

        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<CardForBuying> listAll() {
        List<CardForBuying> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public List<CardForBuying> listByAdventureId(long advId) {
        List<CardForBuying> result = new ArrayList<>();
        String where = ADVENTURE_ID + "=" + advId;
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public CardForBuying get(long id) {
        CardForBuying item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public CardForBuying getRecord(Cursor cursor) {
        CardForBuying result = new CardForBuying();

        result.id = cursor.getLong(0);
        result.adventureId = cursor.getInt(1);
        result.cardId = cursor.getInt(2);

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
