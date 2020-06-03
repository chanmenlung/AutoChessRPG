package com.longtail360.autochessrpg.entity;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.entity.skill.BaseSkill;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class MyCard extends Character{
    private String tag = "MyCard";

    public static int TYPE_IN_HAND = 1;
    public static int TYPE_IN_TEAM = 2;
    public static int TYPE_FOR_BUY = 3;
    public long id;
    public long adventureId;
    public long cardId; //Card.id
    public int level =1;
    public int battleHp;
    public int type; //1=inHand, 2=inTeam
    public int location; //0-11
    public int locationX;//0-7
    public int locationY;//0-3

    public int relife; //1=has relife, 0=no relife, -1=has used relife
    public int totalHp;
    public int battleAttack;
    public int battleDefense;
    public List<Monster> connectMonsters = new ArrayList<>();
    public int cd;
    private Card baseCard;
    public BaseSkill skill;
    public String cardCode; //C1, C2, C3
    public String clazz;
    public String race;
    public static List<MyCard> listByAdvIdAndType(Context context, long adventureId, int type) {
        List<MyCard> result = GameContext.gameContext.myCardDAO.listByAdvIdAndType(adventureId, type);
        for(MyCard myCard : result){
            Card card = Card.get(context, myCard.cardId);
            myCard.initByCard(card);
        }
        return result;
    }

    public int getInitCd() {
        if(level == 1){
            return 5;
        }
        if(level == 2) {
            return 4;
        }
        return 3;
    }
    public void initByCard(Card card) {
        this.skill = card.skill;
        this.skill.level = this.level;
        this.clazz = card.clazz;
        this.race = card.race;
        this.cardCode =card.code;
        this.cardId = card.id;
        this.baseCard = card;
    }
    public Card getCard(Context context) {
        return Card.get(context, this.cardId);
    }

    public void setValueOnLoadHomeActivity() {
        buffCri = 0;
        buffAgi = 0;
        agi = 0;
        cri = 0;
        buffDefense = baseCard.defense;
        buffAttack = baseCard.calAttackByLevel(level);
        totalHp = baseCard.calHpByLevel(level);
        battleAttack = baseCard.calAttackByLevel(level);
        battleDefense = baseCard.defense;
        connectMonsters.clear();
        resetStatus();
    }
    public void setValueOnAdvFinish() {
        cd = baseCard.skill.cd;
        this.battleHp = this.totalHp;
        this.battleAttack = buffAttack;
        this.battleDefense = buffDefense;
        connectMonsters.clear();
        agi = buffAgi;
        cri = buffCri;
        if(relife != 0) {
            relife = 1;
        }
        resetStatus();
    }
    public void setValueOnBattleStart() {
        baseCard.skill.mySelf = this;
        this.battleAttack = buffAttack;
        this.battleDefense = buffDefense;
        connectMonsters.clear();
        agi = buffAgi;
        cri = buffCri;
        resetStatus();

    }
    public void setValueForBuyingCard() {
        this.totalHp =  baseCard.calHpByLevel(level);
        this.battleHp = totalHp;
        this.battleAttack = baseCard.calAttackByLevel(level);
        this.battleDefense = baseCard.defense;
        this.relife = 0;
        connectMonsters.clear();
        buffAttack = battleAttack;
        buffDefense = battleDefense;
        buffAgi = 0;
        buffCri = 0;
        agi = buffAgi;
        cri = buffCri;
        resetStatus();

    }

    public void setValueOnUpdatePassiveSkill() {
        this.totalHp = baseCard.calHpByLevel(level);
        this.battleHp = totalHp;
        this.battleAttack = baseCard.calAttackByLevel(level);
        this.battleDefense = baseCard.defense;
        this.relife = 0;
        connectMonsters.clear();
        buffAttack = battleAttack;
        buffDefense = baseCard.defense;;
        buffAgi = 0;
        buffCri = 0;
        agi = buffAgi;
        cri = buffCri;
        resetStatus();
    }

    public void changeBattleHp(int delta, AdvContext advContext){
        battleHp = battleHp + delta;
        if(battleHp > totalHp){
            battleHp = totalHp;
        }
        if(battleHp < 1){
            battleHp = 0;
            advContext.cards.remove(this);
            advContext.deadCards.add(this);
        }
    }

    public int getSellingPrice() {
        return baseCard.price;
    }

    public String concateNameHpMpLevelExp(Context context) {
        StringBuilder result = new StringBuilder();

        result.append(getCard(context).name)
                .append(" HP:").append(battleHp).append("/").append(totalHp);
        return result.toString();
    }
    public ActionResult randomNormalAttackMonster(Context context, AdvContext advContext) {
        cd--;
        int randomAttackWhichMonster = advContext.mRandom.nextInt(advContext.battleContext.monsters.size());
        Monster monster;
        if(advContext.battleContext.attackLowHp){
            monster = advContext.battleContext.monsters.get(0);
            for(Monster mon : advContext.battleContext.monsters){
                if(mon.getHp() < monster.getHp()){
                    monster = mon;
                }
            }
        }else {
            monster = advContext.battleContext.monsters.get(randomAttackWhichMonster);
        }
        return normalAttackMonsterWithWindFury(context, advContext, monster);
    }

    public ActionResult normalAttackMonsterWithWindFury(Context context, AdvContext advContext, Monster monster) {
        normalAttackMonster(context, advContext, monster);
        if(windFury){
            normalAttackMonster(context, advContext, monster);
        }
        return null;
    }
    public ActionResult normalAttackMonster(Context context, AdvContext advContext, Monster monster) {
        ActionResult result = new ActionResult();
        result.icon1 = this.cardId+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
        if(monster != null) {
            Logger.log(tag, "battleAttack:"+this.battleAttack);
            Logger.log(tag, "battleAttack:"+monster.defense);
            Logger.log(tag, "cri:"+this.cri);
            int randomCri = advContext.mRandom.nextInt(100);
            int hurt = 0;
            if(randomCri < cri){
                hurt = this.battleAttack * 2 + advContext.mRandom.nextInt(3)-1 - monster.defense;
            }else {
                hurt = this.battleAttack + advContext.mRandom.nextInt(3)-1 - monster.defense;

            }
            Card card = getCard(context);
            if (this.clazz.equals(Card.CLAZZ_MAGE)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", card.name);
                result.content = context.getString(R.string.battle_mageNormalAttack).replace("{card}", card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
            }
            else if(this.clazz.equals(Card.CLAZZ_WARRIOR)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", card.name);
                result.content = context.getString(R.string.battle_warriorNormalAttack).replace("{card}", card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
            }
            else if(this.clazz.equals(Card.CLAZZ_PRIEST)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", card.name);
                result.content = context.getString(R.string.battle_priestNormalAttack).replace("{card}", card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
            }
            else if(this.clazz.equals(Card.CLAZZ_HUNTER)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", card.name);
                result.content = context.getString(R.string.battle_hunterNormalAttack).replace("{card}", card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
            }
            else if(this.clazz.equals(Card.CLAZZ_KNIGHT)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", card.name);
                result.content = context.getString(R.string.battle_knightNormalAttack).replace("{card}", card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
            }
            else if(this.clazz.equals(Card.CLAZZ_SHAMAN)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", card.name);
                result.content = context.getString(R.string.battle_shamanNormalAttack).replace("{card}", card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
            }
            else if(this.clazz.equals(Card.CLAZZ_ROGUE)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", card.name);
                result.content = context.getString(R.string.battle_rogueNormalAttack).replace("{card}", card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
            }
            else if(this.clazz.equals(Card.CLAZZ_WARLOCK)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", card.name);
                result.content = context.getString(R.string.battle_warlockNormalAttack).replace("{card}",card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
            }
            else if(this.clazz.equals(Card.CLAZZ_WARRIOR)) {
                result.title = context.getString(R.string.battle_normalAttack).replace("{card}", card.name);
                result.content = context.getString(R.string.battle_warriorNormalAttack).replace("{card}", card.name)
                        .replace("{monster}", monster.label).replace("{monster}", monster.label)
                        .replace("{value}", hurt+"");
                result.doThisAction = true;
            }
            advContext.battleContext.addActionResultToLog(result);
            monster.changeHp(context,advContext.battleContext, hurt);
            card.skill.doActionOnCardAttackEnd(context, advContext, this, monster, hurt);
            for(BasePassiveSkill skill : GameContext.gameContext.passiveSkillList){
                skill.doActionOnCardAttackEnd(context, advContext, this, monster, hurt);
            }
        }
        return result;
    }


    public MyCard findLowestHp(AdvContext advContext) {
        if(advContext.cards.size() <=0){
            return null;
        }
        MyCard result = advContext.cards.get(0);
        for(MyCard card : advContext.cards){
            if(card.battleHp < result.battleHp){
                result = card;
            }
        }
        return  result;
    }



//    public void resetValue() {
//        totalHp = card.calHpByLevel(level);
//        buffCri = 0;
//        buffAgi = 0;
//        battleHp = totalHp;
//        battleAttack = card.calAttackByLevel(level);
//        battleDefense = card.defense;
//        connectMonsters.clear();
//        resetStatus();
//    }

}
