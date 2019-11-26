package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.skill.BaseSkill;

import java.util.ArrayList;
import java.util.List;

public class CardDAO {
    public static final String TABLE_NAME = "CARD";
    public static final String KEY_ID = "_id";
    public static final String CODE = "code";
    public static final String LOCKED = "locked";
    public static final String RACE = "race";
    public static final String CLAZZ = "clazz";
    public static final String SKILL_CODE = "skill_code"; //skill_key
    public static final String NAME = "name";
    public static final String CUSTOM_NAME = "custom_name";
    public static final String HEAD = "head";
    public static final String CUSTOM_HEAD = "custom_head";
    public static final String IMAGE = "image";
    public static final String CUSTOM_IMAGE = "custom_image";
    public static final String CUSTOM_SKILL_NAME = "custom_skill_name";
    public static final String CUSTOM_SKILL_BATTLE_DESC = "custom_skill_battle_desc";
    public static final String NUM_CHESS_FOR_UPGRD = "num_chess_for_upgrd";
    public static final String HP = "hp";
    public static final String ATTACK = "attack";
    public static final String DEFENSE = "defense";
    public static final String PRICE = "price";
    public static final String RARITY = "rarity";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CODE + " TEXT," +
                    LOCKED + " INTEGER," +
                    RACE + " INTEGER," +
                    CLAZZ + " INTEGER," +
                    SKILL_CODE + " TEXT," +
                    NAME + " TEXT," +
                    CUSTOM_NAME + " TEXT," +
                    HEAD + " TEXT," +
                    CUSTOM_HEAD + " TEXT," +
                    IMAGE + " TEXT," +
                    CUSTOM_IMAGE + " TEXT," +
                    CUSTOM_SKILL_NAME + " TEXT," +
                    CUSTOM_SKILL_BATTLE_DESC + " TEXT," +
                    NUM_CHESS_FOR_UPGRD + " INTEGER," +
                    HP + " INTEGER," +
                    ATTACK + " INTEGER," +
                    DEFENSE + " INTEGER,"+
                    PRICE + " INTEGER," +
                    RARITY + " INTEGER"
                    + ")";
    private SQLiteDatabase db;
    public CardDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public Card insert(Card item) {
        ContentValues cv = new ContentValues();
        cv.put(CODE, item.code);
        cv.put(LOCKED, item.locked);
        cv.put(RACE, item.race);
        cv.put(CLAZZ, item.clazz);
        cv.put(SKILL_CODE, item.skillCode);
        cv.put(NAME, item.name);
        cv.put(CUSTOM_NAME, item.customName);
        cv.put(HEAD, item.head);
        cv.put(CUSTOM_HEAD, item.customHead);
        cv.put(IMAGE, item.image);
        cv.put(CUSTOM_IMAGE, item.customImage);
        cv.put(CUSTOM_SKILL_NAME, item.customSkillName);
        cv.put(CUSTOM_SKILL_BATTLE_DESC, item.customSkillBattleDesc);
        cv.put(NUM_CHESS_FOR_UPGRD, item.numChessForUpgrd);
        cv.put(HP, item.hp);
        cv.put(ATTACK, item.attack);
        cv.put(DEFENSE, item.defense);
        cv.put(PRICE, item.price);
        cv.put(RARITY, item.rarity);
        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(Card item) {
        ContentValues cv = new ContentValues();
        cv.put(CODE, item.code);
        cv.put(LOCKED, item.locked);
        cv.put(RACE, item.race);
        cv.put(CLAZZ, item.clazz);
        cv.put(SKILL_CODE, item.skillCode);
        cv.put(NAME, item.name);
        cv.put(CUSTOM_NAME, item.customName);
        cv.put(HEAD, item.head);
        cv.put(CUSTOM_HEAD, item.customHead);
        cv.put(IMAGE, item.image);
        cv.put(CUSTOM_IMAGE, item.customImage);
        cv.put(CUSTOM_SKILL_NAME, item.customSkillName);
        cv.put(CUSTOM_SKILL_BATTLE_DESC, item.customSkillBattleDesc);
        cv.put(NUM_CHESS_FOR_UPGRD, item.numChessForUpgrd);
        cv.put(HP, item.hp);
        cv.put(ATTACK, item.attack);
        cv.put(DEFENSE, item.defense);
        cv.put(PRICE, item.price);
        cv.put(RARITY, item.rarity);
        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<Card> getAll() {
        List<Card> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public int countByRare(int rare){
        int result = 0;
        String where = " where "+RARITY + "=" + rare;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME +where, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }
    public List<Card> getAllByRare(int rare) {
        List<Card> result = new ArrayList<>();
        String where = RARITY + "=" + rare;
        Cursor cursor = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }
    public Card get(long id) {
        Card item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public Card getRecord(Cursor cursor) {
        Card result = new Card();

        result.id = cursor.getLong(0);
        result.code = cursor.getString(1);
        result.locked= cursor.getInt(2);
        result.race= cursor.getString(3);
        result.clazz= cursor.getString(4);
        result.skillCode= cursor.getString(5);
        result.name= cursor.getString(6);
        result.customName= cursor.getString(7);
        result.head= cursor.getString(8);
        result.customHead= cursor.getString(9);
        result.image= cursor.getString(10);
        result.customImage= cursor.getString(11);
        result.customSkillName= cursor.getString(12);
        result.customSkillBattleDesc= cursor.getString(13);
        result.numChessForUpgrd= cursor.getInt(14);
        result.hp= cursor.getInt(15);
        result.attack= cursor.getInt(16);
        result.defense= cursor.getInt(17);
        result.price = cursor.getInt(18);
        result.price = cursor.getInt(19);
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
