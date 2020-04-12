package com.longtail360.autochessrpg.adventure;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.activity.MonsterValueActivity;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.BattleItemLog;
import com.longtail360.autochessrpg.entity.log.BattleRootLog;
import com.longtail360.autochessrpg.entity.log.ProcessLog;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.entity.passiveskill.BasePassiveSkill;
import com.longtail360.autochessrpg.entity.skill.*;
import com.longtail360.autochessrpg.entity.summon.BaseSummon;
import com.longtail360.autochessrpg.entity.tactic.Tactics;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class BattleEngine {
    private String tag = "BattleEngine";
    private Context context;
    private AdvContext advContext;
    private BattleContext battleContext;
    private BattleRootLog bRootLog;
    private int numOfMonster;
    private SharedPreferences prefs;
    public BattleEngine(Context context, AdvContext advContext) {
        this.context = context;
        this.advContext = advContext;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void createAnBattle(ProcessLog processLog, String monsterKey, String monsterName, int numOfMonster) {
        Logger.log(tag,"===== start battle ======");
        this.numOfMonster = numOfMonster;
        bRootLog = new BattleRootLog();
        bRootLog.monsterImage = monsterKey;
        bRootLog.monsterName = monsterName;
        bRootLog.processLog = processLog;
        initBattleContext(monsterKey, numOfMonster);
        GameContext.gameContext.battleRootLogDAO.insert(bRootLog); //insert root log before battle;

        initialTeamStatusSummary();
        advContext.battleContext.battleRootLog = bRootLog;
//        initMonsterStatusSummary();
        int escape = checkEscape();
        if (escape == 1) { //do escape success
            return;
        } else if (escape == 2) { // escape fail
            processBattle();
        } else {  //do not need to escape
            sneakAttack();
            processBattle();
        }
        if (!checkTeamAllDead()) {
            finalTeamStatusSummary();
        }
        Logger.log(tag,"===== end battle ======");
    }

    private void processBattle() {
        battleContext.summons.clear();
        for(MyCard card : advContext.team) {
            Logger.log(tag, "cri:"+card.cri);
        }
        //for testing only start
//        for(MyCard card : advContext.cards) {
//            card.card.skill = BaseSkill.getByCode(context, prefs.getString(MonsterValueActivity.SKILL,null));
//            card.card.skill = BaseSkill.getByCode(context, ReflectAttackOnTeam.KEY);
//            card.cd = card.card.skill.cd;
//        }
        //for testing only end
        for(MyCard myCard : advContext.team) {
            myCard.setValueOnBattleStart();
        }
        for(BasePassiveSkill skill : GameContext.gameContext.passiveSkillList){
            skill.doActionOnBattleStart(context, advContext);
        }
        battleContext.turnNumber = 0;
        while (true) {
            battleContext.turnNumber++;
            if (battleContext.turnNumber > 1000) {
                break;
            }
            doLogicOnTurnStart();
            for(BasePassiveSkill skill : GameContext.gameContext.passiveSkillList){
                skill.doActionOnTurnStart(context, advContext);
            }
            //card attack monster turn
            ActionResult result =null;
            for(Tactics ta : GameContext.gameContext.player.tacticsList){
                if(ta.active == 1){
                    if(ta.doChecking(advContext)) {
                        result = ta.action.action(context,advContext);
                        if(result.doThisAction) {
                            advContext.battleContext.addActionResultToLog(result);
                        }
                    }
                }
            }
            removeDeadMonster();
            if (checkMonsterAllDead()) {
                return;
            }
            for(int i=0; i<advContext.cards.size(); i++){
                MyCard cardAction = advContext.cards.get(i);
                cardAction.skill.doActionOnTurnStart(context, advContext);
                if(cardAction.battleHp > 0) {
                    if(cardAction.cd == 0) {
                        cardAction.skill.active(context, advContext);
                        cardAction.cd = cardAction.getInitCd();
                    }
                    cardAction.randomNormalAttackMonster(context, advContext);
                    removeDeadMonster();
//                    doLogicAfterCardDoAllAction(cardAction.card);
                    if (checkMonsterAllDead()) {
                        return;
                    }
                }
            }
            doLogicAfterAllCardDoAllAction();
            //to do
            for(BaseSummon summon : advContext.battleContext.summons){
                ActionResult actionResult = summon.active(context,advContext);
                advContext.battleContext.addActionResultToLog(actionResult);
            }
            removeDeadMonster();
            if (checkMonsterAllDead()) {
                return;
            }
            //monster attack card turn
            for(Monster monsterAction : battleContext.monsters){
                if (checkTeamAllDead()) {
                    return;
                }
                if(monsterAction.iceStatus){
                    monsterAction.iceStatus = false;
                    ActionResult actionResult = new ActionResult();
                    actionResult.icon1 = monsterAction.code;
                    actionResult.icon1Type =  RootLog.ICON1_TYPE_NOT_CARD;
                    actionResult.title = context.getString(R.string.skill_unlockStatus_ice).replace("{monster}", monsterAction.label);
                    actionResult.content = "";
                    battleContext.addActionResultToLog(actionResult);
                    continue;
                }
                if(monsterAction.skipAttack){
                    monsterAction.skipAttack = false;
                    continue;
                }
                monsterAction.normalAttackCard(context,advContext,bRootLog);
            }
            removeDeadMonster();
            if (checkMonsterAllDead()) {
                return;
            }
            getPotionHurt();
            doLogicOnTurnEnd();
            if (checkMonsterAllDead()) {
                return;
            }

        }
    }

    private void sneakAttack() { //偷襲

    }

    private void removeDeadMonster() {
        for(Monster monsterAction : battleContext.monsters){
            if(monsterAction.getHp() < 1){
                advContext.battleContext.deadMonsters.add(monsterAction);
            }
        }
        battleContext.monsters.removeAll(advContext.battleContext.deadMonsters);
    }
    private void getPotionHurt() {
        int statusHurt = 5;
        List<Monster> potionMonsters = new ArrayList<>();
        for(Monster monster : advContext.battleContext.monsters){
            if(monster.potionStatus){
                potionMonsters.add(monster);
            }
        }
        if(potionMonsters.size() > 0){
            ActionResult result = new ActionResult();
            result.title = context.getString(R.string.skill_statusTitle_potion);
            result.content = context.getString(R.string.skill_statusDesc_potion)
                    .replace("{statusLabels}", advContext.battleContext.buildMonsterLabels(potionMonsters))
                    .replace("{value}", statusHurt+"");
            result.icon1 = advContext.battleContext.monsters.get(0).code;
            result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
            result.doThisAction = true;
            advContext.battleContext.addActionResultToLog(result);
            removeDeadMonster();
            for(Monster monster : advContext.battleContext.monsters) {
                monster.changeHp(context,battleContext, statusHurt);
            }
        }
    }

    private boolean checkMonsterAllDead() {
        if(battleContext.monsters.size() > 0){
            return false;
        }else {
            return true;
        }
    }

    private void initBattleContext(String monsterKey, int numOfMonster) {
        battleContext = new BattleContext();
        for (int i = 0; i < numOfMonster; i++) {
            Monster monster = GameContext.gameContext.monsterDAO.getByCode(monsterKey);
            monster.label = monster.name + getChar(i);
            if(prefs.getInt(MonsterValueActivity.HP,0) > 0) {
                monster.setHp(prefs.getInt(MonsterValueActivity.HP,0));
            }
            if( prefs.getInt(MonsterValueActivity.ATTACK,0) > 0) {
                monster.attack =  prefs.getInt(MonsterValueActivity.ATTACK,0);
            }
            if(prefs.getInt(MonsterValueActivity.DEFENSE,0) > 0){
                monster.defense = prefs.getInt(MonsterValueActivity.DEFENSE,0);
            }
            battleContext.monsters.add(monster);
        }
        advContext.battleContext = battleContext;

    }

    private char getChar(int i) {
        return "abcdefghijklmnopqrstuvwxyz".charAt(i);
    }

    private void doLogicAfterAllCardDoAllAction() {
    }

    private void doLogicOnTurnStart() {
        //do not handle this at this moment, should be handle
//        for (CardActionEngine cardAction : advContext.cardActions) {
//            if(cardAction.getCard().fireStatus){
//                cardAction.getCard().battleHp = (int)(cardAction.getCard().battleHp * 0.95);
//                BattleItemLog result = new BattleItemLog();
//                result.title = context.getString(R.string.fireStatusTitle);
//                result.content = context.getString(R.string.fireStatusContent).replace("{card}", cardAction.getCard().name).replace("{hp}", cardAction.getCard().battleHp+"");
//                result.color = ProcessLogItem.YELLOW;
//                result.icon1 =cardAction.getCard().uuid;
//                result.icon1Type =RootLog.ICON1_TYPE_CARD;
//                advContext.battleItemLogDAO.insert(result);
//            }
//            if(cardAction.getCard().potionStatus){
//                cardAction.getCard().battleHp = (int)(cardAction.getCard().battleHp * 0.95);
//                BattleItemLog result = new BattleItemLog();
//                result.title = context.getString(R.string.potionStatusTitle);
//                result.content = context.getString(R.string.potionStatusContent).replace("{card}", cardAction.getCard().name).replace("{hp}", cardAction.getCard().battleHp+"");
//                result.color = ProcessLogItem.YELLOW;
//                result.icon1 =cardAction.getCard().uuid;
//                result.icon1Type =RootLog.ICON1_TYPE_CARD;
//                advContext.battleItemLogDAO.insert(result);
//            }
//        }
    }

//    private void doLogicAfterCardDoAllAction(Card card) {
//
//    }

    private void doLogicOnTurnEnd() {

    }

    private int checkEscape() { //1:escape success, 2: escape fail, 3:do not need to escape
//        BattleItemLog result = new BattleItemLog();
//        result.battleRootLog = bRootLog;
//        boolean escape = false;
//        int escapeRate = advContext.mRandom.nextInt(10);
//        int teamTotalHp = 0;
//        int currentHp = 0;
//        for(MyCard card : advContext.team){
//            teamTotalHp = teamTotalHp + card.totalHp;
//        }
//        for(MyCard ca : advContext.cards) {
//            currentHp = currentHp + ca.card.hp;
//        }
//        double hpRatio = currentHp/teamTotalHp;
//        if(hpRatio < 0.2){
//            escape = true;
//        }
//        if (escape && escapeRate < 5) { //escape success
//            result.title = context.getString(R.string.adv_escapeSuccessTitle);
//            result.content = context.getString(R.string.adv_escapeSuccessDetail);
//            result.icon1 = "item_boots";
//            result.color = ProcessLog.YELLOW;
//            GameContext.gameContext.battleItemLogDAO.insert(result);
//            return 1;
//        } else if (escape && escapeRate >= 5) { //escape fail
//            result.title = context.getString(R.string.adv_escapeFailTitle);
//            result.content = context.getString(R.string.adv_escapeFailDetail);
//            result.icon1 = "item_boots";
//            result.color = ProcessLog.YELLOW;
//            GameContext.gameContext.battleItemLogDAO.insert(result);
//            return 2;
//        } else {
//            return 3;
//        }
        return 3;

    }

    private void initMonsterStatusSummary() {
        Monster monster = battleContext.monsters.get(0);
        BattleItemLog log = new BattleItemLog();
        log.battleRootLog = bRootLog;
        log.color = ProcessLog.YELLOW;
        log.title = battleContext.monsters.get(0).name;
        log.content ="";
        log.icon1 = monster.code;
        log.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
        GameContext.gameContext.battleItemLogDAO.insert(log);
    }

    private boolean checkTeamAllDead() {
        if(advContext.cards.size() > 0){
            return false;
        }else {
            return true;
        }
    }


    private void finalTeamStatusSummary() {
        int coin = 5 * numOfMonster;
        advContext.totalCoin = advContext.totalCoin + coin;

        BattleItemLog log = new BattleItemLog();
        log.battleRootLog = bRootLog;
        log.color = ProcessLog.YELLOW;
        log.title = context.getString(R.string.battle_winBattleTitle).replace("{monster}", bRootLog.monsterName);
        log.content = concateTeamSummary();
        log.icon1 = "item_star";
        GameContext.gameContext.battleItemLogDAO.insert(log);
    }

    private void initialTeamStatusSummary() {
        BattleItemLog log = new BattleItemLog();
        log.battleRootLog = bRootLog;
        log.color = ProcessLog.YELLOW;
        log.title = context.getString(R.string.battle_battleLogTitle)
                .replace("{numOfMonster}", battleContext.monsters.size() + "")
                .replace("{monsterName}", bRootLog.monsterName);
        log.content = concateTeamSummary();
        log.icon1 = "item_cross_sword";
        GameContext.gameContext.battleItemLogDAO.insert(log);
    }

    private String concateTeamSummary() {
        StringBuilder result = new StringBuilder();
        for(MyCard card : advContext.team){
            if(card != null){
                result.append(card.concateNameHpMpLevelExp(context));
                result.append("\n");
            }
        }
        return result.toString();
    }
}
