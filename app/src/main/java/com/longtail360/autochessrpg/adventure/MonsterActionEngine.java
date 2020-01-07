package com.longtail360.autochessrpg.adventure;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.BattleItemLog;
import com.longtail360.autochessrpg.entity.log.BattleRootLog;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterActionEngine extends CharacterActionEngine{
    private String tag = "MonsterActionEngine";
    public Context context;
    public AdvContext advContext;
    public Monster monster;
    public BattleRootLog bRootLog;
    public Random mRandom = new Random();
    public boolean isIceStatus;
    public MonsterActionEngine(Context context, AdvContext advContext, Monster monster){
        this.context = context;
        this.advContext = advContext;
        this.monster = monster;
    }

    public void normalAttackCard() {
        Logger.log(tag, "size:"+advContext.cardActions.size());
        for(CardActionEngine cardAction : advContext.cardActions){
            Logger.log(tag, "locationY:"+cardAction.cardInBattle.locationY);
        }
        ActionResult result = new ActionResult();
        CardActionEngine cardAction = findAttackWhichCard();
        result.icon1 = monster.code;
        result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
        result.doThisAction = true;
        int hurt = monster.attack - cardAction.cardInBattle.card.defense;
        if(hurt <= 0){
            hurt = 1;
        }
        cardAction.cardInBattle.battleHp = cardAction.cardInBattle.battleHp - hurt;
        result.title = context.getString(R.string.battle_monsterAttackTitle).replace("{monster}", monster.label);
        result.content = context.getString(R.string.battle_cardGetHurt).replace("{card}", cardAction.cardInBattle.card.name)
                .replace("{hurt}", hurt+"")
                +"\n"
                +cardHp(cardAction.cardInBattle);
        addActionResultToLog(result);
        if(cardAction.cardInBattle.battleHp < 1){
            cardAction.cardInBattle.battleHp = 0;
            advContext.cardActions.remove(cardAction);
            advContext.deadCardActions.add(cardAction);

            ActionResult cardDead = new ActionResult();
            cardDead.icon1 = cardAction.cardInBattle.card.id+"";
            cardDead.icon1Type = RootLog.ICON1_TYPE_CARD;
            cardDead.title = context.getString(R.string.battle_cardDead).replace("{card}", cardAction.cardInBattle.card.name);
            cardDead.content = "";
            cardDead.color = ProcessLog.RED;
            addActionResultToLog(cardDead);
        }
    }

    private String cardHp(MyCard card) {
        StringBuilder result = new StringBuilder();
        result.append(card.card.name).append(" ")
                .append("HP:").append(card.battleHp).append("/").append(card.getTotalHp());
        return result.toString();
    }

    private CardActionEngine findAttackWhichCard() {
        //row1 = 35, row2 =30, row3 = 20, row4 = 15

//        Logger.log("card-action-size:"+advContext.cardActions.size());
        List<CardActionEngine> row1 = findRow1();
        List<CardActionEngine> row2 = findRow2();
        List<CardActionEngine> row3 = findRow3();
        List<CardActionEngine> row4 = findRow4();


        int limit = 100;
        int count = 0;
        while(true) {
            count++;
            if(count == limit){
                break;
            }
            int randomAttackWhichLocation = mRandom.nextInt(100);
            if(randomAttackWhichLocation < 35){ //attack row1
                if(row1.size() > 0){
                    int whichCard = mRandom.nextInt(row1.size());
                    return row1.get(whichCard);
                }
            }
            if(randomAttackWhichLocation < 65){ //attack row2
                if(row2.size() > 0){
                    int whichCard = mRandom.nextInt(row2.size());
                    return row2.get(whichCard);
                }
            }
            if(randomAttackWhichLocation < 85){ //attack row2
                if(row3.size() > 0){
                    int whichCard = mRandom.nextInt(row3.size());
                    return row3.get(whichCard);
                }
            }

            if(randomAttackWhichLocation < 100){ //attack row2
                if(row4.size() > 0){
                    int whichCard = mRandom.nextInt(row4.size());
                    return row4.get(whichCard);
                }
            }

        }
        return null;
    }

    private List<CardActionEngine> findRow1() {
        List<CardActionEngine> result = new ArrayList<>();
        for(CardActionEngine cardAction : advContext.cardActions){
            if(cardAction.cardInBattle.locationY == 0){
                result.add(cardAction);
            }
        }
        return  result;
    }

    private List<CardActionEngine> findRow2() {
        List<CardActionEngine> result = new ArrayList<>();
        for(CardActionEngine cardAction : advContext.cardActions){
            if(cardAction.cardInBattle.locationY == 1){
                result.add(cardAction);
            }
        }
        return  result;
    }

    private List<CardActionEngine> findRow3() {
        List<CardActionEngine> result = new ArrayList<>();
        for(CardActionEngine cardAction : advContext.cardActions){
            if(cardAction.cardInBattle.locationY == 2){
                result.add(cardAction);
            }
        }
        return  result;
    }

    private List<CardActionEngine> findRow4() {
        List<CardActionEngine> result = new ArrayList<>();
        for(CardActionEngine cardAction : advContext.cardActions){
            if(cardAction.cardInBattle.locationY == 3){
                result.add(cardAction);
            }
        }
        return  result;
    }

    private void addActionResultToLog(ActionResult result) {
        BattleItemLog itemLog = new BattleItemLog();
        itemLog.title = result.title;
        itemLog.content = result.content;
        itemLog.color = result.color;
        itemLog.icon1Type = result.icon1Type;
        itemLog.icon1 = result.icon1;
        itemLog.icon2 = result.icon2;
        itemLog.battleRootLog = bRootLog;
        GameContext.gameContext.battleItemLogDAO.insert(itemLog);
    }

}
