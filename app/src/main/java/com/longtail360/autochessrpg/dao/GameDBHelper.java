package com.longtail360.autochessrpg.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.longtail360.autochessrpg.dao.log.BattleItemLogDAO;
import com.longtail360.autochessrpg.dao.log.BattleRootLogDAO;
import com.longtail360.autochessrpg.dao.log.ProcessLogDAO;
import com.longtail360.autochessrpg.dao.log.RootLogDAO;

/**
 * Created by chanmenlung on 20/1/2019.
 */

public class GameDBHelper extends SQLiteOpenHelper {
    // 資料庫名稱
    public static final String DATABASE_NAME = "longtail_autorpg.db";
    // 資料庫版本，資料結構改變的時候要更改這個數字，通常是加一
    public static final int VERSION = 43;
    public static boolean isResetDB = true;
    // 資料庫物件，固定的欄位變數
    private static SQLiteDatabase database;

    // 建構子，在一般的應用都不需要修改
    public GameDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
    }

    // 需要資料庫的元件呼叫這個方法，這個方法在一般的應用都不需要修改
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new GameDBHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }

        return database;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AdventureDAO.CREATE_TABLE);
        db.execSQL(CardDAO.CREATE_TABLE);
        db.execSQL(DungeonDAO.CREATE_TABLE);
        db.execSQL(ItemDAO.CREATE_TABLE);
        db.execSQL(ItemGotDAO.CREATE_TABLE);
        db.execSQL(MonsterDAO.CREATE_TABLE);
        db.execSQL(SkillDAO.CREATE_TABLE);
        db.execSQL(MyCardDAO.CREATE_TABLE);

        db.execSQL(BattleItemLogDAO.CREATE_TABLE);
        db.execSQL(BattleRootLogDAO.CREATE_TABLE);
        db.execSQL(ProcessLogDAO.CREATE_TABLE);
        db.execSQL(RootLogDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AdventureDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CardDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DungeonDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ItemGotDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MonsterDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SkillDAO.TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS " +BattleItemLogDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +BattleRootLogDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +ProcessLogDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +RootLogDAO.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " +MyCardDAO.TABLE_NAME);
        onCreate(db);
    }
}
