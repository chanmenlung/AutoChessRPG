package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longtail360.autochessrpg.dao.AdventureDAO;
import com.longtail360.autochessrpg.dao.CardDAO;
import com.longtail360.autochessrpg.dao.CustomCardDAO;
import com.longtail360.autochessrpg.dao.DungeonDAO;
import com.longtail360.autochessrpg.dao.ItemDAO;
import com.longtail360.autochessrpg.dao.ItemGotDAO;
import com.longtail360.autochessrpg.dao.MonsterDAO;
import com.longtail360.autochessrpg.dao.MyCardDAO;
import com.longtail360.autochessrpg.dao.PlayerDAO;
import com.longtail360.autochessrpg.dao.SkillDAO;
import com.longtail360.autochessrpg.dao.log.BattleItemLogDAO;
import com.longtail360.autochessrpg.dao.log.BattleRootLogDAO;
import com.longtail360.autochessrpg.dao.log.ProcessLogDAO;
import com.longtail360.autochessrpg.dao.log.RootLogDAO;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.utils.GooglePlayHandler;
import com.longtail360.autochessrpg.utils.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class GameContext {
    private static String tag = "GameContext";
    public static String PLAYER_FILE_NAME = "player.txt";
    public AdventureDAO advDAO;
    public CardDAO cardDAO;
    public SkillDAO skillDAO;
    public DungeonDAO dungeonDAO;
    public ItemDAO itemDAO;
    public ItemGotDAO itemGotDAO;
    public MonsterDAO monsterDAO;
    public MyCardDAO myCardDAO;
    public RootLogDAO rootLogDAO;
    public ProcessLogDAO processLogDAO;
//    public TeamStatusDAO teamStatusDAO;
    public BattleRootLogDAO battleRootLogDAO;
    public BattleItemLogDAO battleItemLogDAO;
    public PlayerDAO playerDAO;
    public CustomCardDAO customCardDAO;

    public Adventure adventure;
    public Player player;
    public GooglePlayHandler googlePlayHandler;
    public Gson gson;
    public static GameContext gameContext;
    public List<BasePassiveSkill> passiveSkillList;
    public GameContext() {
        gson  = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        googlePlayHandler = new GooglePlayHandler();
//        player =  gson.fromJson(playerStr, Player.class);
//        readPlayerData(context);
    }

//    public void savePlayerData(Context context){
//        String str = gson.toJson(player.tacticsList);
//        googlePlayHandler.write(context, str, null);
//        Logger.log(tag,"player:"+str);
//        writeToFile(PLAYER_FILE_NAME,str, context);
//    }

//    private String createNewPlayer() {
//        player = new Player();
//        player.crystal = Setting.INIT_CRYSTAL;
//        String str = gson.toJson(player);
////        writeToFile(PLAYER_FILE_NAME,str, context);
//        return str;
//    }

//    private void writeToFile(String fileName, String data, Context context) {
//        try {
//            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
//            outputStreamWriter.write(data);
//            outputStreamWriter.close();
//        }
//        catch (IOException e) {
//            Logger.log(tag,"File write failed: " + e.toString());
//        }
//    }

//    private void readPlayerData(Context context) {
//        String playerStr = readFromFile(PLAYER_FILE_NAME, context);
//        Logger.log(tag, "playerStr:"+playerStr);
//        player =  gson.fromJson(playerStr, Player.class);
//    }


//    private String readFromFile(String fileName, Context context) {
//
//        String ret = "";
//
//        try {
//            InputStream inputStream = context.openFileInput(fileName);
//
//            if ( inputStream != null ) {
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String receiveString = "";
//                StringBuilder stringBuilder = new StringBuilder();
//
//                while ( (receiveString = bufferedReader.readLine()) != null ) {
//                    stringBuilder.append(receiveString);
//                }
//
//                inputStream.close();
//                ret = stringBuilder.toString();
//            }
//        }
//        catch (FileNotFoundException e) {
//            Logger.log(tag,"File not found: " + e.toString());
//            return createNewPlayer();
//
//        } catch (IOException e) {
//            Logger.log(tag,"Can not read file: " + e.toString());
//        }
//
//        return ret;
//    }

}
