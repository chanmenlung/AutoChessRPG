package com.longtail360.autochessrpg.adventure;

import android.content.Context;

import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.BattleItemLog;
import com.longtail360.autochessrpg.entity.log.BattleRootLog;
import com.longtail360.autochessrpg.entity.summon.BaseSummon;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 12/2/2019.
 */

public class BattleContext {
    private String tag ="BattleContext";
    public List<BaseSummon> summons = new ArrayList<>();
    public List<Monster> monsters = new ArrayList<>();
    public List<Monster> deadMonsters = new ArrayList<>();
    public BattleRootLog battleRootLog;
    public int turnNumber;
    public String buildMonsterLabels(List<Monster> monsters) {
        if(monsters.size() < 1){
            return null;
        }
        String monsterName = monsters.get(0).name;
        StringBuilder str = new StringBuilder();
        int size = monsters.size();
        for(int i=0; i<size; i++) {
            String label = monsters.get(i).label;
            if(i == (size-1)){
                str.append(label.substring(label.length()-1));
            }else {
                str.append(label.substring(label.length()-1));
                str.append(",");
            }
        }

        return monsterName + str.toString();
    }

    public ActionResult statusHurtAll(Context context, AdvContext advContext, int hurt, boolean ignoreDefense, int posGetStatus, String status) {
        ActionResult result = new ActionResult ();
        int monsterSize = advContext.battleContext.monsters.size();
        if (monsterSize == 0) {
            result.doThisAction = false;
        } else {
            int rand = advContext.mRandom.nextInt(100);
            boolean getStatus = false;
            if(rand < posGetStatus) {
                getStatus = true;
                result.getStatus = true;
            }
            int realHurt;
            if(ignoreDefense) {
                realHurt = hurt;
            }else {
                realHurt = hurt - advContext.battleContext.monsters.get(0).defense;
            }
            Logger.log(tag, "hurt:"+hurt);
            Logger.log(tag, "realHurt:"+realHurt);
            result.realHurt = realHurt;
            for(Monster m : advContext.battleContext.monsters) {
                m.changeHp(context, advContext.battleContext, realHurt);
                Logger.log(tag, "getStatus:"+getStatus+"status:"+status);
                if(getStatus) {
                    if(status.equals(MyCard.NONE_STATUS)){
                        m.noneStatus = true;
                    }else if(status.equals(MyCard.ELECTRICITY_STATUS)) { //get Eelectricity
                        m.eleStatus = true;
                        m.attack = m.attack - 1;
                    }else if(status.equals(MyCard.FIRE_STATUS)) { //get fire
                        m.fireStatus = true;
                        m.defense--;
                        if(m.defense < 1){
                            m.defense = 0;
                        }
                        Logger.log(tag, "m.defense:"+m.defense+"");
                    }else if(status.equals(MyCard.POTION_STATUS)) { //get potion
                        m.potionStatus = true;
                    }else if(status.equals(MyCard.ICE_STATUS)) { //get ice
                        m.iceStatus = true;
                    }else {
                        Logger.log(tag, "error:cannot get status:"+status);
                    }
                }
            }
            result.doThisAction = true;
        }
        return result;
    }

    public ActionResult statusHurtSingle(Context context, AdvContext advContext, Monster monster, int hurt, boolean ignoreDefense,int posGetStatus, String status) {
        ActionResult result = new ActionResult ();
        int monsterSize = advContext.battleContext.monsters.size();
        if (monsterSize == 0) {
            result.doThisAction = false;
        } else {
            int rand = advContext.mRandom.nextInt(100);
            boolean getStatus = false;
            if(rand < posGetStatus) {
                getStatus = true;
                result.getStatus = true;
            }
            int realHurt;
            if(ignoreDefense) {
                realHurt = hurt;
            }else {
                realHurt = hurt - advContext.battleContext.monsters.get(0).defense;
            }
            result.realHurt = realHurt;
            monster.changeHp(context, advContext.battleContext, realHurt);
            if(getStatus) {
                if(status.equals(MyCard.NONE_STATUS)){
                    monster.noneStatus = true;
                }else if(status.equals(MyCard.ELECTRICITY_STATUS)) { //get Eelectricity
                    monster.eleStatus = true;
                    monster.attack = monster.attack - 1;
                }else if(status.equals(MyCard.FIRE_STATUS)) { //get fire
                    monster.fireStatus = true;
                    monster.defense--;
                    if(monster.defense < 1){
                        monster.defense = 0;
                    }
                }else if(status.equals(MyCard.POTION_STATUS)) { //get potion
                    monster.potionStatus = true;
                }else if(status.equals(MyCard.ICE_STATUS)) { //get ice
                    monster.iceStatus = true;
                }else {
                    Logger.log(tag, "error:cannot get status:"+status);
                }
            }
            result.doThisAction = true;
        }
        return result;
    }

    public ActionResult valueUpTeam( AdvContext advContext, String field, int deltaValue){
        ActionResult result = new ActionResult ();
        for(MyCard ca : advContext.cards) {
            if(field.equals("hp")){
                ca.battleHp = ca.battleHp + deltaValue;
                if(ca.battleHp > ca.totalHp) {
                    ca.battleHp = ca.totalHp;
                }
            }else if(field.equals("attack")) {
                ca.battleAttack = ca.battleAttack + deltaValue;
            }else if(field.equals("defense")) {
                ca.battleDefense = ca.battleDefense + deltaValue;
            }
        }
        result.doThisAction = true;
        return result;
    }


    public ActionResult valueUpOne(MyCard card, String field, int deltaValue){
        ActionResult result = new ActionResult ();
        if(field.equals("hp")){
            int currentHp = card.battleHp;
            card.battleHp = card.battleHp + deltaValue;
            if(card.battleHp > card.totalHp) {
                card.battleHp = card.totalHp;
            }
            result.finalDeltaValue = card.battleHp - currentHp;
        }else if(field.equals("attack")) {
            card.battleAttack = card.battleAttack + deltaValue;
        }else if(field.equals("defense")) {
            card.battleDefense = card.battleDefense + deltaValue;
        }
        result.doThisAction = true;
        return result;
    }


    public Monster getRandomMonster(AdvContext advContext) {
        int monsterSize = advContext.battleContext.monsters.size();
        int whichIndex = advContext.mRandom.nextInt(monsterSize);
        Monster monster = advContext.battleContext.monsters.get(whichIndex);
        return monster;
    }

    public void addActionResultToLog(ActionResult result) {
        BattleItemLog itemLog = new BattleItemLog();
        itemLog.title = result.title;
        itemLog.content = result.content;
        itemLog.color = result.color;
        itemLog.icon1Type = result.icon1Type;
        itemLog.icon1 = result.icon1;
        itemLog.icon2 = result.icon2;
        itemLog.battleRootLog = battleRootLog;
        GameContext.gameContext.battleItemLogDAO.insert(itemLog);
    }
    public List<Monster> getRandomMonster(AdvContext advContext,int num) {
        List<Monster> result = new ArrayList<>();
        if(advContext.battleContext.monsters.size() <= num) {
            return advContext.battleContext.monsters;
        }
        while(true) {
            if(result.size() == num) {
                break;
            }
            Monster monster = getRandomMonster(advContext);
            if(!result.contains(monster)){
                result.add(monster);
            }
        }
        return result;
    }
}
