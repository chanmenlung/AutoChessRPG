package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.adventure.BattleContext;
import com.longtail360.autochessrpg.entity.log.BattleItemLog;
import com.longtail360.autochessrpg.entity.log.BattleRootLog;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class Monster extends Character{
    private static String tag = "Monster";
    public long id;
    public String code;
    public String name;
    private int hp;
    public int attack;
    public int defense;
    public int totalHp;

    public List<Monster> connectMonsters = new ArrayList<>();

    public void changeHp(Context context, BattleContext battleContext, int deltaHp) {
        hp = hp - deltaHp;
        if(hp < 1) {
            hp = 0;
        }
        if(connectMonsters.size() > 0) {
            List<Monster> lifeMonster = new ArrayList<>();
            for(Monster monster : connectMonsters){
                if(monster.getHp() > 0) {
                    monster.directChangeHp(deltaHp);
                    if(monster.getHp() > 0) {
                        lifeMonster.add(monster);
                    }
                }
            }
            String labels = battleContext.buildMonsterLabels(connectMonsters);
            connectMonsters = lifeMonster;
            ActionResult actionResult = new ActionResult();
            actionResult.title = context.getString(R.string.battle_connectHurt_title);
            actionResult.content = context.getString(R.string.battle_connectHurt_content)
                    .replace("{subject}", this.label)
                    .replace("{objects}", labels)
                    .replace("{value}", deltaHp+"");
            actionResult.icon1 = "skill_icon_connection";
            actionResult.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
            battleContext.addActionResultToLog(actionResult);

        }
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.totalHp = hp;
        this.hp = hp;
    }

    public void directChangeHp(int deltaHp){
        hp = hp - deltaHp;
        if(hp < 1) {
            hp = 0;
        }
    }
    public static void init(Context context) {
        String[] names = context.getResources().getStringArray(R.array.monsterNames);
        Monster monster;
        for(int i=0; i<names.length; i++) {
            int level = 1;
            int levelDefnse = 1;
            level = level + (int)(i/5);
            levelDefnse = levelDefnse + (int)(i/50);
            monster = new Monster();
            monster.name = names[i];
            monster.code = "m"+(i+1);
            monster.hp = level* 8;
            monster.attack = 8+level;
            if(i < 5){
                monster.attack = 8;
            }
            monster.defense = levelDefnse;
            GameContext.gameContext.monsterDAO.insert(monster);
        }
    }

    public void normalAttackCard(Context context, AdvContext advContext, BattleRootLog battleRootLog) {

        Logger.log(tag, "size:"+advContext.cards.size());
        for(MyCard cardAction : advContext.cards){
            Logger.log(tag, "locationY:"+cardAction.locationY);
        }
        ActionResult result = new ActionResult();
        MyCard cardAction = findAttackWhichCard(advContext);
        result.icon1 = this.code;
        result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
        result.doThisAction = true;
        int random = advContext.mRandom.nextInt(100);
        if(random < cardAction.agi) {
            result.title = context.getString(R.string.battle_monsterAttackTitle).replace("{monster}", label);
            result.content = context.getString(R.string.battle_dodgeMonsterAttack)
                    .replace("{card}", cardAction.card.name)
                    .replace("{monster}", label);
            addActionResultToLog(result, battleRootLog);
            return;
        }
        int hurt = this.attack - cardAction.battleDefense;
        if(hurt <= 0){
            hurt = 1;
        }
        for(MyCard card : advContext.cards){
            card.card.skill.doActionOnMonsterAttackStart(context,advContext,cardAction, this, hurt);
        }
        if(cardAction.divineShield){
            result.title = context.getString(R.string.battle_monsterAttackTitle).replace("{monster}", label);
            result.content = context.getString(R.string.battle_destroyDivineShield).replace("{card}", cardAction.card.name)
                                .replace("{monster}", this.label);
            cardAction.divineShield = false;
            addActionResultToLog(result,battleRootLog);
        } else {
            cardAction.battleHp = cardAction.battleHp - hurt;
            if(cardAction.battleHp < 0){
                cardAction.battleHp = 0;
            }
            String reflectHurtStr = "";
            if(cardAction.reflectShield){
                reflectHurtStr = context.getString(R.string.skill_statusDesc_reflectAttackOnMyself)
                        .replace("{card}", cardAction.card.name).replace("{monster}", this.label).replace("{value}", hurt/2+"");
            }
            result.title = context.getString(R.string.battle_monsterAttackTitle).replace("{monster}", label);
            result.content = context.getString(R.string.battle_cardGetHurt).replace("{card}", cardAction.card.name)
                    .replace("{hurt}", hurt+"")
                    +"。"
                    +reflectHurtStr
                    +"\n"
                    +cardHp(cardAction);
            addActionResultToLog(result, battleRootLog);
            if(cardAction.battleHp < 1){
                cardAction.battleHp = 0;
                advContext.cards.remove(cardAction);
                advContext.deadCards.add(cardAction);
                ActionResult cardDead = new ActionResult();
                cardDead.icon1 = cardAction.card.id+"";
                cardDead.icon1Type = RootLog.ICON1_TYPE_CARD;
                cardDead.title = context.getString(R.string.battle_cardDead).replace("{card}", cardAction.card.name);
                cardDead.content = "";
                cardDead.color = ProcessLog.RED;
                addActionResultToLog(cardDead,battleRootLog);
                if(cardAction.relife == 1){
                    ActionResult actionResult = new ActionResult();
                    cardAction.battleHp = cardAction.totalHp;
                    cardAction.relife = -1;
                    advContext.cards.add(cardAction);
                    advContext.deadCards.remove(cardAction);
                    actionResult.title = context.getString(R.string.battle_relifeTitle).replace("{card}", cardAction.card.name);
                    actionResult.content = context.getString(R.string.battle_relifeContent).replace("{card}", cardAction.card.name);
                    actionResult.icon1 = "skill_icon_relife";
                    actionResult.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
                    addActionResultToLog(actionResult,battleRootLog);
                }
            }
            if(cardAction.reflectShield){
                this.changeHp(context, advContext.battleContext, hurt/2);
            }
        }
        cardAction.reflectShield = false;
        for(MyCard myCard : advContext.cards) {
            myCard.card.skill.doActionOnMonsterAttackEnd(context,advContext,cardAction, this, hurt);
        }
        for(BasePassiveSkill passiveSkill : GameContext.gameContext.passiveSkillList) {
            passiveSkill.doActionOnMonsterAttackEnd(context,advContext,cardAction, this, hurt);
        }

    }

    private String cardHp(MyCard card) {
        StringBuilder result = new StringBuilder();
        result.append(card.card.name).append(" ")
                .append("HP:").append(card.battleHp).append("/").append(card.totalHp);
        return result.toString();
    }

    private MyCard findAttackWhichCard(AdvContext advContext) {
        //row1 = 35, row2 =30, row3 = 20, row4 = 15

//        Logger.log("card-action-size:"+advContext.cardActions.size());
        List<MyCard> taunts = findTaunts(advContext);
        List<MyCard> row1 = findRow1(advContext);
        List<MyCard> row2 = findRow2(advContext);
        List<MyCard> row3 = findRow3(advContext);
        List<MyCard> row4 = findRow4(advContext);

        Logger.log(tag, "taunts-size:"+taunts.size()+"");
        Logger.log(tag, "row1-size:"+row1.size()+"");
        Logger.log(tag, "row2-size:"+row2.size()+"");
        Logger.log(tag, "row3-size:"+row3.size()+"");
        Logger.log(tag, "row4-size:"+row4.size()+"");
        int limit = 100;
        int count = 0;

        if(taunts.size()>0){
            int whichCard = advContext.mRandom.nextInt(taunts.size());
            return taunts.get(whichCard);
        }
        while(true) {
            count++;
            if(count == limit){
                break;
            }
            int randomAttackWhichLocation = advContext.mRandom.nextInt(100);
            if(randomAttackWhichLocation < 35){ //attack row1
                if(row1.size() > 0){
                    int whichCard = advContext.mRandom.nextInt(row1.size());
                    return row1.get(whichCard);
                }
            }
            if(randomAttackWhichLocation < 65){ //attack row2
                if(row2.size() > 0){
                    int whichCard = advContext.mRandom.nextInt(row2.size());
                    return row2.get(whichCard);
                }
            }
            if(randomAttackWhichLocation < 85){ //attack row3
                if(row3.size() > 0){
                    int whichCard = advContext.mRandom.nextInt(row3.size());
                    return row3.get(whichCard);
                }
            }

            if(randomAttackWhichLocation < 100){ //attack row4
                if(row4.size() > 0){
                    int whichCard = advContext.mRandom.nextInt(row4.size());
                    return row4.get(whichCard);
                }
            }

        }
        Logger.log(tag, "error-cannot find target");
        return null;
    }

    private List<MyCard> findTaunts(AdvContext advContext){
        List<MyCard> result = new ArrayList<>();
        for(MyCard myCard : advContext.cards){
            if(myCard.taunt){
                result.add(myCard);
            }
        }
        return result;
    }
    private List<MyCard> findRow1(AdvContext advContext) {
        List<MyCard> result = new ArrayList<>();
        for(MyCard cardAction : advContext.cards){
            if(cardAction.locationY == 0){
                result.add(cardAction);
            }
        }
        return  result;
    }

    private List<MyCard> findRow2(AdvContext advContext) {
        List<MyCard> result = new ArrayList<>();
        for(MyCard cardAction : advContext.cards){
            if(cardAction.locationY == 1){
                result.add(cardAction);
            }
        }
        return  result;
    }

    private List<MyCard> findRow3(AdvContext advContext) {
        List<MyCard> result = new ArrayList<>();
        for(MyCard cardAction : advContext.cards){
            if(cardAction.locationY == 2){
                result.add(cardAction);
            }
        }
        return  result;
    }

    private List<MyCard> findRow4(AdvContext advContext) {
        List<MyCard> result = new ArrayList<>();
        for(MyCard cardAction : advContext.cards){
            if(cardAction.locationY == 3){
                result.add(cardAction);
            }
        }
        return  result;
    }

    private void addActionResultToLog(ActionResult result, BattleRootLog bRootLog) {
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
