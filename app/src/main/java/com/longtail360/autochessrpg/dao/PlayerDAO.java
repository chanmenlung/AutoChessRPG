package com.longtail360.autochessrpg.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.longtail360.autochessrpg.entity.Adventure;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.Player;
import com.longtail360.autochessrpg.entity.tactic.Tactics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerDAO {
    public static final String TABLE_NAME = "PLAYER";
    public static final String KEY_ID = "_id";
    public static final String CRYSTAL = "crystal";
    public static final String SHOW_TUTORIAL = "showtutorial";
    public static final String TACTICS_JSON = "tacticsjson";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    CRYSTAL + " INTEGER," +
                    SHOW_TUTORIAL + " INTEGER, "+
                    TACTICS_JSON + " BLOB" +
                    ")";



    private SQLiteDatabase db;
    public PlayerDAO(Context context) {
        db = GameDBHelper.getDatabase(context);
    }
    public void close() {
        db.close();
    }

    public Player insert(Player player) {
        ContentValues cv = new ContentValues();
        cv.put(CRYSTAL, player.crystal);
        cv.put(SHOW_TUTORIAL, player.isOldPlayer);
        String str = GameContext.gameContext.gson.toJson(player.tacticsList);
        cv.put(TACTICS_JSON, str.getBytes());
        long id = db.insert(TABLE_NAME, null, cv);
        player.id = id;
        return player;
    }
    public boolean update(Player item) {
        ContentValues cv = new ContentValues();
        cv.put(CRYSTAL, item.crystal);
        cv.put(SHOW_TUTORIAL, item.isOldPlayer);
        String str = GameContext.gameContext.gson.toJson(item.tacticsList);
        cv.put(TACTICS_JSON, str.getBytes());
        String where = KEY_ID + "=" + item.id;
        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    public List<Player> listAll() {
        List<Player> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    public boolean delete(long id){
        String where = KEY_ID + "=" + id;
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    public boolean deleteAll(){
        return db.delete(TABLE_NAME, null , null) > 0;
    }

    public Player get(long id) {
        Player item = null;
        String where = KEY_ID + "=" + id;
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);
        if (result.moveToFirst()) {
            item = getRecord(result);
        }
        result.close();
        return item;
    }

    public Player getRecord(Cursor cursor) {
        Player result = new Player();
        result.id = cursor.getLong(0);
        result.crystal = cursor.getInt(1);
        result.isOldPlayer = cursor.getInt(2);
        result.tacticsJson = new String(cursor.getBlob(3));
        result.tacticsList = new ArrayList<>(Arrays.asList(GameContext.gameContext.gson.fromJson(result.tacticsJson, Tactics[].class)));
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
