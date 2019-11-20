package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.skill.*;

import java.util.ArrayList;
import java.util.List;

public class SkillDAO {
    public static final String TABLE_NAME = "SKILL";
    public static final String KEY_ID = "_id";
    public static final String CODE = "key";
    public static final String NAME = "name";
    public static final String DESC = "desc";
    public static final String CD = "cd";
    public static final String BATTLE_DESC = "battleDesc";
    public static final String STATUS_DESC = "statusDesc";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CODE + " TEXT NOT NULL," +
                    NAME + " TEXT NOT NULL," +
                    DESC + " TEXT NOT NULL," +
                    CD + " INTEGER NOT NULL," +
                    BATTLE_DESC + " TEXT NOT NULL," +
                    STATUS_DESC + " TEXT NOT NULL" +
                    ")";
    ;
    private SQLiteDatabase db;
    public SkillDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public BaseSkill insert(BaseSkill item) {
        ContentValues cv = new ContentValues();
        cv.put(CODE, item.code);
        cv.put(NAME, item.name);
        cv.put(DESC, item.desc);
        cv.put(CD, item.cd);
        cv.put(BATTLE_DESC, item.battleDesc);
        cv.put(STATUS_DESC, item.statusDesc);

        long id = db.insert(TABLE_NAME, null, cv);
        item.id = id;
        return item;
    }
    public boolean update(BaseSkill item) {
        ContentValues cv = new ContentValues();
        cv.put(CODE, item.code);
        cv.put(NAME, item.name);
        cv.put(DESC, item.desc);
        cv.put(CD, item.cd);
        cv.put(BATTLE_DESC, item.battleDesc);
        cv.put(STATUS_DESC, item.statusDesc);

        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }


    public List<BaseSkill> getAll() {
        List<BaseSkill> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }
    public BaseSkill get(long id) {
        BaseSkill item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }
    public BaseSkill getByKey(String key) {
        BaseSkill item = null;
        String where = CODE + "='" + key+"'";
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public BaseSkill getRecord(Cursor cursor) {
        BaseSkill result = new BaseSkill();

        result.id = cursor.getLong(0);
        result.code = cursor.getString(1);
        result.name = cursor.getString(2);
        result.desc = cursor.getString(3);
        result.cd = cursor.getInt(4);
        result.battleDesc = cursor.getString(5);
        result.statusDesc = cursor.getString(6);
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

    public void init(Context context) {
        BaseSkill skill = new ElectricAllBigHurt(context);
        insert(skill);
        skill = new ElectricAllHurt(context);
        insert(skill);
        skill = new ElectricSingleBigHurt(context);
        insert(skill);
        skill = new ElectricSingleHurt(context);
        insert(skill);
        skill = new ElectricAllBigHurt(context);
        insert(skill);
        skill = new FireAllBigHurt(context);
        insert(skill);
        skill = new FireAllHurt(context);
        insert(skill);
        skill = new FireSingleBigHurt(context);
        insert(skill);
        skill = new FireSingleHurt(context);
        insert(skill);
        skill = new HealAll(context);
        insert(skill);
        skill = new HealAllBig(context);
        insert(skill);
        skill = new HealSingle(context);
        insert(skill);
        skill = new HealSingleBig(context);
        insert(skill);
        skill = new HitAllBigHurt(context);
        insert(skill);
        skill = new HitAllHurt(context);
        insert(skill);
        skill = new HitSingleBigHurt(context);
        insert(skill);
        skill = new HitSingleHurt(context);
        insert(skill);
        skill = new IceAllBigHurt(context);
        insert(skill);
        skill = new IceAllHurt(context);
        insert(skill);
        skill = new IceSingleBigHurt(context);
        insert(skill);
        skill = new IceSingleHurt(context);
        insert(skill);
        skill = new PotionAllBigHurt(context);
        insert(skill);
        skill = new PotionAllHurt(context);
        insert(skill);
        skill = new PotionSingleBigHurt(context);
        insert(skill);
        skill = new PotionSingleHurt(context);
        insert(skill);
        skill = new ValueUpAllAttack(context);
        insert(skill);
        skill = new ValueUpAllDefense(context);
        insert(skill);
        skill = new ValueUpAttack(context);
        insert(skill);
        skill = new ValueUpDefense(context);
        insert(skill);

    }
}
