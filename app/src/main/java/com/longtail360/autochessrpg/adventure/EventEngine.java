package com.longtail360.autochessrpg.adventure;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.MyItem;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.utils.Logger;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 4/2/2019.
 */

public class EventEngine {
    private int SPRING_CURE_HP = 50;
    private int BOW_HURT = 20;
    private int POTION_HURT = 20;

    private String tag = "EventEngine";
    private AdvContext advContext;
    private Context context;
    private TeamActionEngine teamActionEngine;
    public EventEngine(Context context, AdvContext advContext){
        this.context = context;
        this.advContext = advContext;
        teamActionEngine= new TeamActionEngine(context,advContext);
    }

    public ActionResult createEpmtyEvent(){
        ActionResult log = new ActionResult();
        log.title = "this is testing";
        log.icon1 = "icon_swordcross";
        return log;
    }
    public ActionResult metMonsterEvent(){
        Logger.log(tag, "metMonsterEvent");
        int randomWhichTypeMonster = advContext.mRandom.nextInt(advContext.dungeon.getMonsterArray().length);
        int randomNumOfMonster = advContext.mRandom.nextInt(3)+3;
        String monsterKey = advContext.monsterKeys[randomWhichTypeMonster];
        Monster monster = GameContext.gameContext.monsterDAO.getByCode(monsterKey);
        ActionResult actionResult = new ActionResult();
        actionResult.title = context.getResources().getString(R.string.adv_metMonster).replace("{numOfMonster}", randomNumOfMonster+"")
                .replace("{monsterName}", monster.name+"");
        actionResult.icon1 = "item_cross_sword";
        actionResult.icon2 = monster.code;
        actionResult.isBattle = true;
        actionResult.monsterKey =monsterKey;
        actionResult.monsterName = monster.name;
        actionResult.numOfMonster = randomNumOfMonster;
        return actionResult;
    }
    public ActionResult metGoodEvent() {
        Logger.log(tag, "metGoodEvent");
        int randomWhichEvent = advContext.mRandom.nextInt(7)+1;
        switch (randomWhichEvent){
            case 1:
                return findSpringCure();
            case 2:
                return findSpringNone();
            case 3:
                return findBoxGetItem();
            case 4:
                return findJarGetItem();
            case 5:
                return findRoomCure();
            case 6:
                return findRoomGetItem();
            case 7:
                return findRoomNone();
        }
        return null;
    }
    public ActionResult metBadEvent() {
        int randomWhichEvent = advContext.mRandom.nextInt(7)+1;

        switch (randomWhichEvent){
            case 1:
                return findSpringPotion();
            case 2:
                return findJarBow();
            case 3:
                return findJarBowDodge();
            case 4:
                return findBoxPotion();
            case 5:
                return findBoxPotionDodge();
            case 6:
                return findBoxBow();
            case 7:
                return findBoxBowDodge();
        }
        return null;
    }

    public ActionResult findSpringCure() {
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        MyCard whoOpenBox = advContext.cards.get(randomWhoOpenBox);
        int randomDesc = advContext.mRandom.nextInt(2);
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.BLUE;
        actionResult.icon1 = "item_water";
        actionResult.title = context.getResources().getString(R.string.adv_event_findASpring);
        if(randomDesc == 0) {
            actionResult.detail = context.getResources().getString(R.string.adv_event_findASpring_cure).replace("{card}", whoOpenBox.card.name);
        }else {
            actionResult.detail = context.getResources().getString(R.string.adv_event_findASpring_cure2).replace("{card}", whoOpenBox.card.name);
        }
        for(MyCard myCard : advContext.cards){
            myCard.battleHp = myCard.battleHp + SPRING_CURE_HP + advContext.mRandom.nextInt(5);
            if(myCard.battleHp >= myCard.totalHp){
                myCard.battleHp = myCard.totalHp;
            }
        }

        return actionResult;
    }

    public ActionResult findSpringNone() {
        ActionResult actionResult = new ActionResult();
        actionResult.icon1 = "item_water";
        actionResult.title = context.getResources().getString(R.string.adv_event_findASpring);
        actionResult.detail = context.getResources().getString(R.string.adv_event_findASpring_none);
        return actionResult;
    }

    public ActionResult findSpringPotion() {
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        MyCard whoOpenBox = advContext.cards.get(randomWhoOpenBox);
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.RED;
        actionResult.icon1 = "item_water";
        actionResult.title = context.getResources().getString(R.string.adv_event_findASpring);
        actionResult.detail = context.getResources().getString(R.string.adv_event_findASpring_potion)
                .replace("{card}", whoOpenBox.card.name);

        return actionResult;
    }

    public ActionResult findJarBow() {
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        int hurt = BOW_HURT+advContext.mRandom.nextInt(5);
        MyCard whoOpenBox = advContext.cards.get(randomWhoOpenBox);
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.RED;
        actionResult.icon1 = "item_jar";
        actionResult.title = context.getResources().getString(R.string.adv_event_findAJar);
        actionResult.detail =  context.getResources().getString(R.string.adv_event_findAJar_getBow).replace("{card}", whoOpenBox.card.name)
                                .replace("{value}", hurt+"");
        whoOpenBox.changeBattleHp(-1*hurt, advContext);
        return actionResult;
    }

    public ActionResult findJarBowDodge() {
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        MyCard whoOpenBox = advContext.cards.get(randomWhoOpenBox);
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.BLUE;
        actionResult.icon1 = "item_jar";
        actionResult.title = context.getResources().getString(R.string.adv_event_findAJar);
        actionResult.detail =  context.getResources().getString(R.string.adv_event_findAJar_getBowDodge).replace("{card}", whoOpenBox.card.name);
        return actionResult;
    }

    public ActionResult findJarGetItem() {
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        Item item = randomGetItem();
        MyCard whoOpenBox = advContext.cards.get(randomWhoOpenBox);
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.BLUE;
        actionResult.icon1 = "item_jar";
        actionResult.title = context.getResources().getString(R.string.adv_event_findAJar);
        actionResult.detail =  context.getResources().getString(R.string.adv_event_findAJar_getBowDodge).replace("{card}", whoOpenBox.card.name)
                .replace("{item}", item.name);
        return actionResult;
    }

    public ActionResult findBoxGetItem() {
        Logger.log(tag, "metBadEvent");
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        Item item = randomGetItem();
        Card whoOpenBox = advContext.cards.get(randomWhoOpenBox).card;
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.BLUE;
        actionResult.icon1 = "item_treasurebox";
        actionResult.title = context.getResources().getString(R.string.adv_event_findAnBox);
        actionResult.detail = context.getResources().getString(R.string.adv_event_findABox_getItem).replace("{card}", whoOpenBox.name)
                                        .replace("{item}", item.name);
        return actionResult;
    }

    public ActionResult findBoxPotion() {
        Logger.log(tag, "metBadEvent");
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        Card whoOpenBox = advContext.cards.get(randomWhoOpenBox).card;
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.RED;
        actionResult.icon1 = "item_treasurebox";
        actionResult.title = context.getResources().getString(R.string.adv_event_findAnBox);
        actionResult.detail = context.getResources().getString(R.string.adv_event_findABox_getPotion).replace("{card}", whoOpenBox.name);
        for(MyCard card : advContext.cards) {
            card.battleHp = card.battleHp - POTION_HURT - advContext.mRandom.nextInt(5);
            if(card.battleHp < 1){
                card.battleHp = 0;
                advContext.deadCards.add(card);
            }
        }
        advContext.cards.removeAll(advContext.deadCards);
        return actionResult;
    }

    public ActionResult findBoxPotionDodge() {
        Logger.log(tag, "metBadEvent");
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        Card whoOpenBox = advContext.cards.get(randomWhoOpenBox).card;
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.BLUE;
        actionResult.icon1 = "item_treasurebox";
        actionResult.title = context.getResources().getString(R.string.adv_event_findAnBox);
        actionResult.detail = context.getResources().getString(R.string.adv_event_findABox_getPotionDodge).replace("{card}", whoOpenBox.name);
        return actionResult;
    }

    public ActionResult findBoxBow() {
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        int hurt = BOW_HURT+advContext.mRandom.nextInt(5);
        MyCard whoOpenBox = advContext.cards.get(randomWhoOpenBox);
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.RED;
        actionResult.icon1 = "item_treasurebox";
        actionResult.title =  context.getResources().getString(R.string.adv_event_findAnBox);
        actionResult.detail = context.getResources().getString(R.string.adv_event_findABox_getBow).replace("{card}", whoOpenBox.card.name)
                            .replace("{value}", hurt+"");
        whoOpenBox.changeBattleHp(-1*hurt, advContext);
        return actionResult;
    }

    public ActionResult findBoxBowDodge() {
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        MyCard whoOpenBox = advContext.cards.get(randomWhoOpenBox);
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.BLUE;
        actionResult.icon1 = "item_treasurebox";
        actionResult.title =  context.getResources().getString(R.string.adv_event_findAnBox);
        actionResult.detail = context.getResources().getString(R.string.adv_event_findABox_getBowDodge).replace("{card}", whoOpenBox.card.name);
        return actionResult;
    }

    public ActionResult findRoomGetItem() {
        Item item = randomGetItem();
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.BLUE;
        actionResult.icon1 = "item_room";
        actionResult.title = context.getResources().getString(R.string.adv_event_findARoom);
        actionResult.detail = context.getResources().getString(R.string.adv_event_findARoom_getItem).replace("{item}", item.name);
        return actionResult;
    }

    public ActionResult findRoomNone() {
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        Card whoOpenBox = advContext.cards.get(randomWhoOpenBox).card;
        int randomDesc = advContext.mRandom.nextInt(3);
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.BLUE;
        actionResult.icon1 = "item_room";
        actionResult.title = context.getResources().getString(R.string.adv_event_findARoom);
        if(randomDesc == 0) {
            actionResult.detail = context.getResources().getString(R.string.adv_event_findARoom_none_desc1);
        }else if(randomDesc == 1) {
            actionResult.detail = context.getResources().getString(R.string.adv_event_findARoom_none_desc2);
        }else {
            actionResult.detail = context.getResources().getString(R.string.adv_event_findARoom_none_desc3);
        }
        return actionResult;
    }

    public ActionResult findRoomCure() {
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cards.size());
        Card whoOpenBox = advContext.cards.get(randomWhoOpenBox).card;
        int randomDesc = advContext.mRandom.nextInt(3);
        ActionResult actionResult = new ActionResult();
//        actionResult.color = ProcessLog.BLUE;
        actionResult.icon1 = "item_room";
        actionResult.title = context.getResources().getString(R.string.adv_event_findARoom);
        if(randomDesc == 0) {
            actionResult.detail = context.getResources().getString(R.string.adv_event_findARoom_cure_desc1);
        }else if(randomDesc == 1) {
            actionResult.detail = context.getResources().getString(R.string.adv_event_findARoom_cure_desc2).replace("{card}", whoOpenBox.name);
        }else {
            actionResult.detail = context.getResources().getString(R.string.adv_event_findARoom_cure_desc3).replace("{card}", whoOpenBox.name);
        }
        for(MyCard card : advContext.cards) {
            card.battleHp  = card.battleHp + SPRING_CURE_HP+advContext.mRandom.nextInt(5);
            if(card.battleHp > card.totalHp){
                card.battleHp = card.totalHp;
            }
        }

        return actionResult;
    }

    private Item randomGetItem() {
        List<String> items = new ArrayList<>();
        items.add(Item.ITEM_HP_UP);
        items.add(Item.ITEM_CD_DOWN);
        items.add(Item.ITEM_ATTACK_UP);
        items.add(Item.ITEM_FIRE);
        items.add(Item.ITEM_ELECTRICITY);
        items.add(Item.ITEM_POTION);
        items.add(Item.ITEM_ICE);
        items.add(Item.ITEM_SUMMON_MONSTER_HEAL);
        items.add(Item.ITEM_SUMMON_MONSTER_SMALL);
        items.add(Item.ITEM_SUMMON_MONSTER_MIDDLE);
        items.add(Item.ITEM_SUMMON_MONSTER_LARGE);
        items.add(Item.ITEM_PERFUME);

        int randomItem = advContext.mRandom.nextInt(items.size());

        Item item = null;
        item = GameContext.gameContext.itemDAO.getByItemCode(items.get(randomItem));
        MyItem myItem = new MyItem();
        myItem.adventureId = advContext.advId;
        myItem.item = item;
        myItem.onBody = 1;
        myItem.itemId = item.id;
        advContext.currentItemList.add(myItem);
        GameContext.gameContext.itemGotDAO.insert(myItem);

        return  item;

    }
}
