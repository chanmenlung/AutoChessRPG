package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.longtail360.autochessrpg.dao.AdventureDAO;
import com.longtail360.autochessrpg.dao.CardDAO;
import com.longtail360.autochessrpg.dao.DungeonDAO;
import com.longtail360.autochessrpg.dao.ItemDAO;
import com.longtail360.autochessrpg.dao.ItemGotDAO;
import com.longtail360.autochessrpg.dao.MonsterDAO;
import com.longtail360.autochessrpg.dao.MyCardDAO;
import com.longtail360.autochessrpg.dao.SkillDAO;
import com.longtail360.autochessrpg.dao.log.BattleItemLogDAO;
import com.longtail360.autochessrpg.dao.log.BattleRootLogDAO;
import com.longtail360.autochessrpg.dao.log.ProcessLogDAO;
import com.longtail360.autochessrpg.dao.log.RootLogDAO;
import com.longtail360.autochessrpg.dao.log.TeamStatusDAO;
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
    public TeamStatusDAO teamStatusDAO;
    public BattleRootLogDAO battleRootLogDAO;
    public BattleItemLogDAO battleItemLogDAO;

    public Adventure adventure;
    public List<Card> card;
    public List<MyCard> cardInBattles;
    public List<MyCard> cardInHands;
    public List<MyCard> cardForBuyings;
    public List<Dungeon> dungeons;
    public List<Item> items;
    public List<ItemGot> itemGots;
    public MonsterDAO monster;
    private Gson gson;
    private Player player;
    public static GameContext gameContext;

    public GameContext(Context context) {
        gson  = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        readPlayerData(context);
    }

    public Player getPlayer(Context context){
        if(player == null){
            readPlayerData(context);
            savePlayerData(context);
        }
        return player;
    }

    public void savePlayerData(Context context){
        if(player == null){
            readPlayerData(context);
        }
        String str = gson.toJson(player);
        Logger.log(tag,"player:"+str);
        writeToFile(PLAYER_FILE_NAME,str, context);
    }

    private String createNewPlayer(Context context) {
        player = new Player();
        player.crystal = Setting.INIT_CRYSTAL;
        String str = gson.toJson(player);
        writeToFile(PLAYER_FILE_NAME,str, context);
        return str;
    }

    private void writeToFile(String fileName, String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Logger.log(tag,"File write failed: " + e.toString());
        }
    }

    private void readPlayerData(Context context) {
        String playerStr = readFromFile(PLAYER_FILE_NAME, context);
        Logger.log(tag, "playerStr:"+playerStr);
        player =  gson.fromJson(playerStr, Player.class);
    }


    private String readFromFile(String fileName, Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(fileName);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Logger.log(tag,"File not found: " + e.toString());
            return createNewPlayer(context);

        } catch (IOException e) {
            Logger.log(tag,"Can not read file: " + e.toString());
        }

        return ret;
    }

}
