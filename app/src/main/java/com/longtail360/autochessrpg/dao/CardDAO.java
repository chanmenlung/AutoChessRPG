package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.Card;

import java.util.ArrayList;
import java.util.List;

public class CardDAO {
    public static final String TABLE_NAME = "CHESS";
    public static final String KEY_ID = "_id";
    public static final String LOCKED = "locked";
    public static final String RACE = "race";
    public static final String JOB = "job";
    public static final String SKILL_CODE = "skill_code";
    public static final String NAME = "name";
    public static final String CUSTOM_NAME = "custom_name";
    public static final String HEAD = "head";
    public static final String CUSTOM_HEAD = "custom_head";
    public static final String IMAGE = "image";
    public static final String CUSTOM_IMAGE = "custom_image";
    public static final String CUSTOM_SKILL_NAME = "custom_skill_name";
    public static final String CUSTOM_SKILL_BATTLE_DESC = "custom_skill_battle_desc";
    public static final String NUM_CHESS_FOR_UPGRD = "num_chess_for_upgrd";
    public static final String LEVEL_1_HP = "level_1_hp";
    public static final String LEVEL_2_HP = "level_2_hp";
    public static final String LEVEL_3_HP = "level_3_hp";
    public static final String LEVEL_1_MP = "level_1_mp";
    public static final String LEVEL_2_MP = "level_2_mp";
    public static final String LEVEL_3_MP = "level_3_mp";
    public static final String LEVEL_1_ATTACK = "level_1_attack";
    public static final String LEVEL_2_ATTACK = "level_2_attack";
    public static final String LEVEL_3_ATTACK = "level_3_attack";
    public static final String LEVEL_1_DEFENSE = "level_1_defense";
    public static final String LEVEL_2_DEFENSE = "level_2_defense";
    public static final String LEVEL_3_DEFENSE = "level_3_defense";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LOCKED + " INTEGER," +
                    RACE + " INTEGER," +
                    JOB + " INTEGER," +
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
                    LEVEL_1_HP + " INTEGER," +
                    LEVEL_2_HP + " INTEGER," +
                    LEVEL_3_HP + " INTEGER," +
                    LEVEL_1_MP + " INTEGER," +
                    LEVEL_2_MP + " INTEGER," +
                    LEVEL_3_MP + " INTEGER," +
                    LEVEL_1_ATTACK + " INTEGER," +
                    LEVEL_2_ATTACK + " INTEGER," +
                    LEVEL_3_ATTACK + " INTEGER," +
                    LEVEL_1_DEFENSE + " INTEGER," +
                    LEVEL_2_DEFENSE + " INTEGER," +
                    LEVEL_3_DEFENSE + " INTEGER,";
    private SQLiteDatabase db;
    public CardDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public Card insert(Card item) {
        ContentValues cv = new ContentValues();
        cv.put(LOCKED, item.locked);
        cv.put(RACE, item.race);
        cv.put(JOB, item.job);
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
        cv.put(LEVEL_1_HP, item.level_1_hp);
        cv.put(LEVEL_2_HP, item.level_2_hp);
        cv.put(LEVEL_3_HP , item.level_3_hp);
        cv.put(LEVEL_1_MP , item.level_1_mp);
        cv.put(LEVEL_2_MP , item.level_2_mp);
        cv.put(LEVEL_3_MP, item.level_3_mp);
        cv.put(LEVEL_1_ATTACK, item.level_1_attack);
        cv.put(LEVEL_2_ATTACK, item.level_2_attack);
        cv.put(LEVEL_3_ATTACK , item.level_3_attack);
        cv.put(LEVEL_1_DEFENSE, item.level_1_defense);
        cv.put(LEVEL_2_DEFENSE, item.level_2_defense);
        cv.put(LEVEL_3_DEFENSE, item.level_3_defense);

        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(Card item) {
        ContentValues cv = new ContentValues();
        cv.put(LOCKED, item.locked);
        cv.put(RACE, item.race);
        cv.put(JOB, item.job);
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
        cv.put(LEVEL_1_HP, item.level_1_hp);
        cv.put(LEVEL_2_HP, item.level_2_hp);
        cv.put(LEVEL_3_HP , item.level_3_hp);
        cv.put(LEVEL_1_MP , item.level_1_mp);
        cv.put(LEVEL_2_MP , item.level_2_mp);
        cv.put(LEVEL_3_MP, item.level_3_mp);
        cv.put(LEVEL_1_ATTACK, item.level_1_attack);
        cv.put(LEVEL_2_ATTACK, item.level_2_attack);
        cv.put(LEVEL_3_ATTACK , item.level_3_attack);
        cv.put(LEVEL_1_DEFENSE, item.level_1_defense);
        cv.put(LEVEL_2_DEFENSE, item.level_2_defense);
        cv.put(LEVEL_3_DEFENSE, item.level_3_defense);

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
        result.locked= cursor.getInt(1);
        result.race= cursor.getString(2);
        result.job= cursor.getString(3);
        result.skillCode= cursor.getString(4);
        result.name= cursor.getString(5);
        result.customName= cursor.getString(6);
        result.head= cursor.getString(7);
        result.customHead= cursor.getString(8);
        result.image= cursor.getString(9);
        result.customImage= cursor.getString(10);
        result.customSkillName= cursor.getString(11);
        result.customSkillBattleDesc= cursor.getString(12);
        result.numChessForUpgrd= cursor.getInt(13);
        result.level_1_hp= cursor.getInt(14);
        result.level_2_hp= cursor.getInt(15);
        result.level_3_hp= cursor.getInt(16);
        result.level_1_mp= cursor.getInt(17);
        result.level_2_mp= cursor.getInt(18);
        result.level_3_mp= cursor.getInt(19);
        result.level_1_attack= cursor.getInt(20);
        result.level_2_attack= cursor.getInt(21);
        result.level_3_attack= cursor.getInt(22);
        result.level_1_defense= cursor.getInt(23);
        result.level_2_defense= cursor.getInt(24);
        result.level_3_defense= cursor.getInt(25);
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

    public void init() {
        Card chess;
        chess = new Card(1, "race", "job", "skillCode", "name", null,
                "c1head", "customHead", "c1", "customImage", "customSkillName","customSkillBattleDesc",
                1, 200, 400, 800, 100,200, 300, 5,10,20, 2,4,6);
        insert(chess);


    }
}
