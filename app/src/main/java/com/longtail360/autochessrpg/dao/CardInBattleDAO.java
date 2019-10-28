package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.CardInBattle;

import java.util.ArrayList;
import java.util.List;

public class CardInBattleDAO {
    public static final String TABLE_NAME = "CARD_IN_BATTLE";
    public static final String KEY_ID = "_id";
    public static final String ADVENTURE_ID = "adventure_id";
    public static final String CARD_ID = "card_id";
    public static final String LOCATION_X = "location_x";
    public static final String LOCATION_Y = "location_y";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    ADVENTURE_ID + " INTEGER" +
                    CARD_ID + " INTEGER" +
                    LOCATION_X + " INTEGER" +
                    LOCATION_Y + " INTEGER";
    private SQLiteDatabase db;
    public CardInBattleDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public CardInBattle insert(CardInBattle item) {
        ContentValues cv = new ContentValues();
        cv.put(ADVENTURE_ID, item.adventureId);
        cv.put(CARD_ID, item.cardId);
        cv.put(LOCATION_X, item.locationX);
        cv.put(LOCATION_Y, item.locationY);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(CardInBattle item) {
        ContentValues cv = new ContentValues();
        cv.put(ADVENTURE_ID, item.adventureId);
        cv.put(CARD_ID, item.cardId);
        cv.put(LOCATION_X, item.locationX);
        cv.put(LOCATION_Y, item.locationY);

        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<CardInBattle> listAll() {
        List<CardInBattle> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public List<CardInBattle> listByAdventureId(long advId) {
        List<CardInBattle> result = new ArrayList<>();
        String where = ADVENTURE_ID + "=" + advId;
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public CardInBattle get(long id) {
        CardInBattle item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public CardInBattle getRecord(Cursor cursor) {
        CardInBattle result = new CardInBattle();

        result.id = cursor.getLong(0);
        result.adventureId = cursor.getLong(1);
        result.cardId = cursor.getLong(2);
        result.locationX = cursor.getInt(3);
        result.locationY = cursor.getInt(4);
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
