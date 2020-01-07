package com.longtail360.autochessrpg.adventure;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.utils.Logger;

import org.json.JSONException;

/**
 * Created by chanmenlung on 4/2/2019.
 */

public class EventEngine {
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
        int randomWhichEvent = advContext.mRandom.nextInt(2);
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cardActions.size());
        Card whoOpenBox = advContext.cardActions.get(randomWhoOpenBox).cardInBattle.card;
        ActionResult actionResult = new ActionResult();
        actionResult.color = ProcessLog.BLUE;
        Logger.log(tag, "randomWhichEvent:"+randomWhichEvent);
        if(randomWhichEvent == 0){//find a box, get potion from the box
            actionResult.icon1 = "item_jar";
            actionResult.title = context.getText(R.string.adv_findAJar).toString();
            int randomWhichTypePotion =  advContext.mRandom.nextInt(100);

            Item item = null;
            item = GameContext.gameContext.itemDAO.getByItemCode(Item.ITEM_HP_UP);
            advContext.itemList.add(item);
            actionResult.detail = context.getText(R.string.adv_openJarGetItem).toString()
                    .replace("{item}", item.name)
                    .replace("{card}", whoOpenBox.name);
        }else{
            actionResult = findSpring();


        }
        return actionResult;
    }

    public ActionResult findSpring() {
        int randomWhichEvent = advContext.mRandom.nextInt(2);
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cardActions.size());
        Card whoOpenBox = advContext.cardActions.get(randomWhoOpenBox).cardInBattle.card;
        ActionResult actionResult = new ActionResult();
        actionResult.color = ProcessLog.BLUE;
        teamActionEngine.restoreTeamHp(0.05);
        actionResult.icon1 = "item_water";
        actionResult.title = context.getText(R.string.adv_findASpring).toString();
        actionResult.detail = context.getText(R.string.adv_drinkFromSpring).toString()
                .replace("{card}", whoOpenBox.name);
        teamActionEngine.restoreTeamHp(0.03);
        return actionResult;
    }

    public ActionResult metBadEvent() {
        Logger.log(tag, "metBadEvent");
        int randomWhichEvent = advContext.mRandom.nextInt(2);
        int randomWhoOpenBox = advContext.mRandom.nextInt(advContext.cardActions.size());
        MyCard whoOpenBox = advContext.cardActions.get(randomWhoOpenBox).cardInBattle;
        ActionResult actionResult = new ActionResult();
        actionResult.color = ProcessLog.RED;
        Logger.log(tag, "randomWhichEvent:"+randomWhichEvent);
        if(randomWhichEvent == 0){
            teamActionEngine.restoreTeamHp(-0.05);
            actionResult.icon1 = "item_smoke";
            actionResult.title = context.getText(R.string.adv_trape).toString();
            actionResult.detail = context.getText(R.string.adv_openTrapeTeamHurt).toString();
            teamActionEngine.restoreTeamHp(-0.03);

        }else{
            int hurt = (int) (whoOpenBox.getTotalHp() * 0.05);
            whoOpenBox.battleHp = (int)(whoOpenBox.battleHp - hurt);
            if(whoOpenBox.battleHp < 0){
                whoOpenBox.battleHp = 0;
            }
            actionResult.icon1 = "item_treasurebox";
            actionResult.title = context.getText(R.string.adv_findAnBox).toString();
            actionResult.detail = context.getText(R.string.adv_openTrapeCardHurt).toString()
                    .replace("{card}", whoOpenBox.card.name)
                    .replace("{hurt}", hurt+"");
        }
        return actionResult;
    }


}
