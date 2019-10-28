package com.longtail360.autochessrpg.entity;

import com.longtail360.autochessrpg.dao.AdventureDAO;
import com.longtail360.autochessrpg.dao.CardDAO;
import com.longtail360.autochessrpg.dao.CardForBuyingDAO;
import com.longtail360.autochessrpg.dao.CardInBattleDAO;
import com.longtail360.autochessrpg.dao.CardInHandDAO;
import com.longtail360.autochessrpg.dao.DungeonDAO;
import com.longtail360.autochessrpg.dao.ItemDAO;
import com.longtail360.autochessrpg.dao.ItemGotDAO;
import com.longtail360.autochessrpg.dao.MonsterDAO;

import java.util.List;

public class GameContext {

    public AdventureDAO advContextDAO;
    public CardDAO cardDAO;
    public CardInBattleDAO cardInBattleDAO;
    public CardInHandDAO cardInHandDAO;
    public CardForBuyingDAO cardForBuyingDAO;
    public DungeonDAO dungeonDAO;
    public ItemDAO itemDAO;
    public ItemGotDAO itemGotDAO;
    public MonsterDAO monsterDAO;

    public Adventure advContext;
    public List<Card> card;
    public List<CardInBattle> cardInBattles;
    public List<CardInHand> cardInHands;
    public List<CardForBuying> cardForBuyings;
    public List<Dungeon> dungeons;
    public List<Item> items;
    public List<ItemGot> itemInHands;
    public MonsterDAO monster;


    public static GameContext gameContext;
    public static Player player;

    public Adventure adventureContext;
    public static void savePlayer() {

    }

    public void save() {

    }
}
