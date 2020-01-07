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

public class BattleEngine {
    private String tag = "BattleEngine";
    private Context context;
    private AdvContext advContext;
    private BattleContext battleContext;
    private BattleRootLog bRootLog;
    private int numOfMonster;

    public BattleEngine(Context context, AdvContext advContext) {
        this.context = context;
        this.advContext = advContext;
    }

    public void createAnBattle(ProcessLog processLog, String monsterKey, String monsterName, int numOfMonster) {
        this.numOfMonster = numOfMonster;
        bRootLog = new BattleRootLog();
        bRootLog.monsterImage = monsterKey;
        bRootLog.monsterName = monsterName;
        bRootLog.processLog = processLog;
        initBattleContext(monsterKey, numOfMonster);
        GameContext.gameContext.battleRootLogDAO.insert(bRootLog); //insert root log before battle;

        initialTeamStatusSummary();
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
    }

    private void processBattle() {
        int numOfTurn = 0;
        while (true) {
            numOfTurn++;
            if (numOfTurn > 2) {
                break;
            }
            doLogicOnTurnStart();
            //card attack monster turn

            for(int i=0; i<advContext.cardActions.size(); i++){
                CardActionEngine cardAction = advContext.cardActions.get(i);

                ActionResult actionResult = cardAction.randomNormalAttackMonster();
                Logger.log(tag, "actionResult.icon1:"+actionResult.icon1);
                addActionResultToLog(actionResult);
                doLogicAfterCardDoAllAction(cardAction.cardInBattle.card);
                if (checkMonsterAllDead()) {
                    return;
                }
            }
            doLogicAfterAllCardDoAllAction();

            //monster attack card turn
            for(MonsterActionEngine monsterAction : battleContext.monsterActions){
                if (checkTeamAllDead()) {
                    return;
                }
                if(monsterAction.isIceStatus){
                    monsterAction.isIceStatus = false;
                    continue;
                }
                monsterAction.normalAttackCard();

            }
            doLogicOnTurnEnd();

        }
    }

    private void sneakAttack() { //偷襲

    }

    private boolean checkMonsterAllDead() {
        if(battleContext.monsterActions.size() > 0){
            return false;
        }else {
            return true;
        }
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

    private void initBattleContext(String monsterKey, int numOfMonster) {
        battleContext = new BattleContext();
        for (int i = 0; i < numOfMonster; i++) {
            Monster monster = GameContext.gameContext.monsterDAO.getByCode(monsterKey);
            monster.label = monster.name + getChar(i);
            MonsterActionEngine monsterActionEngine = new MonsterActionEngine(context, advContext, monster);
            monsterActionEngine.bRootLog = bRootLog;
            battleContext.monsterActions.add(monsterActionEngine);
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

    private void doLogicAfterCardDoAllAction(Card card) {

    }

    private void doLogicOnTurnEnd() {

    }

    private int checkEscape() { //1:escape success, 2: escape fail, 3:do not need to escape
        BattleItemLog result = new BattleItemLog();
        result.battleRootLog = bRootLog;
        boolean escape = false;
        int escapeRate = advContext.mRandom.nextInt(10);
        int teamTotalHp = 0;
        int currentHp = 0;
        for(MyCard card : advContext.team){
            teamTotalHp = teamTotalHp + card.getTotalHp();
        }
        for(CardActionEngine ca : advContext.cardActions) {
            currentHp = currentHp + ca.cardInBattle.card.hp;
        }
        double hpRatio = currentHp/teamTotalHp;
        if(hpRatio < 0.2){
            escape = true;
        }
        if (escape && escapeRate < 5) { //escape success
            result.title = context.getString(R.string.adv_escapeSuccessTitle);
            result.content = context.getString(R.string.adv_escapeSuccessDetail);
            result.icon1 = "item_boots";
            result.color = ProcessLog.YELLOW;
            GameContext.gameContext.battleItemLogDAO.insert(result);
            return 1;
        } else if (escape && escapeRate >= 5) { //escape fail
            result.title = context.getString(R.string.adv_escapeFailTitle);
            result.content = context.getString(R.string.adv_escapeFailDetail);
            result.icon1 = "item_boots";
            result.color = ProcessLog.YELLOW;
            GameContext.gameContext.battleItemLogDAO.insert(result);
            return 2;
        } else {
            return 3;
        }

    }

    private void initMonsterStatusSummary() {
        Monster monster = battleContext.monsterActions.get(0).monster;
        BattleItemLog log = new BattleItemLog();
        log.battleRootLog = bRootLog;
        log.color = ProcessLog.YELLOW;
        log.title = battleContext.monsterActions.get(0).monster.name;
        log.content ="";
        log.icon1 = monster.code;
        log.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
        GameContext.gameContext.battleItemLogDAO.insert(log);
    }

    private boolean checkTeamAllDead() {
        if(advContext.cardActions.size() > 0){
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
                .replace("{numOfMonster}", battleContext.monsterActions.size() + "")
                .replace("{monsterName}", bRootLog.monsterName);
        log.content = concateTeamSummary();
        log.icon1 = "item_cross_sword";
        GameContext.gameContext.battleItemLogDAO.insert(log);
    }

    private String concateTeamSummary() {
        StringBuilder result = new StringBuilder();
        for(MyCard card : advContext.team){
            if(card != null){
                result.append(card.concateNameHpMpLevelExp());
                result.append("\n");
            }
        }
        return result.toString();
    }
}
