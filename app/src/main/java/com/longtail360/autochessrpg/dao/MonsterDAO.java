package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterDAO {
    public static final String TABLE_NAME = "MONSTER";
    public static final String KEY_ID = "_id";
    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String HP = "hp";
    public static final String ATTACK = "attack";
    public static final String DEFENSE = "defense";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CODE + " TEXT," +
                    NAME + " TEXT," +
                    HP + " INTEGER,"+
                    ATTACK + " INTEGER,"+
                    DEFENSE + " INTEGER"
                    + ")";
    private SQLiteDatabase db;
    public MonsterDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public Monster insert(Monster item) {
        ContentValues cv = new ContentValues();
        cv.put(CODE, item.code);
        cv.put(NAME, item.name);
        cv.put(HP, item.getHp());
        cv.put(ATTACK, item.attack);
        cv.put(DEFENSE, item.defense);

        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(Monster item) {
        ContentValues cv = new ContentValues();
        cv.put(CODE, item.code);
        cv.put(NAME, item.name);
        cv.put(HP, item.getHp());
        cv.put(ATTACK, item.attack);
        cv.put(DEFENSE, item.defense);

        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<Monster> getAll() {
        List<Monster> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }
    public Monster get(long id) {
        Monster item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public Monster getByCode(String code) {
        Monster item = null;
        String where = CODE + "='" + code+"'";
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public Monster getRecord(Cursor cursor) {
        Monster result = new Monster();

        result.id = cursor.getLong(0);
        result.code = cursor.getString(1);
        result.name = cursor.getString(2);
        result.setHp(cursor.getInt(3));
        result.attack = cursor.getInt(4);
        result.defense = cursor.getInt(5);

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
