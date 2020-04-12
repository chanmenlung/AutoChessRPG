package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.utils.Logger;

import java.util.Random;

public class BaseSkill {
	protected String tag = "BaseSkill";
    public long id;
    public String code;
    public String name;
    protected String desc;
    public int cd = 5; //after {cd} turn, active skill
    public String battleDesc;
    public String statusDesc;
    public String getStatusDesc;
    public int level = 1;
	public MyCard mySelf;

	public String getDesc(Context context) {
	    return desc;
    }
	
	protected int getIntByRange(int min, int max) {
		if(min == max) {
			return min;
		}
		Random random = new Random();
		return min+random.nextInt(max-min);
	}
	public ActionResult active(Context context, AdvContext advContext) {return null;}

    public ActionResult statusHurtAll(Context context, AdvContext advContext, int hurt, boolean ignoreDefense, int posGetStatus, String status) {
        ActionResult result = advContext.battleContext.statusHurtAll( context,advContext,hurt,ignoreDefense,posGetStatus,status);
        Logger.log(tag, "statusHurtAll:"+result.doThisAction);
        if(status.equals(MyCard.ELECTRICITY_STATUS)){
            getStatusDesc = context.getString(R.string.skill_getStatus_elect);
        }else if(status.equals(MyCard.FIRE_STATUS)){
            getStatusDesc = context.getString(R.string.skill_getStatus_fire);
        }else if(status.equals(MyCard.ICE_STATUS)){
            getStatusDesc = context.getString(R.string.skill_getStatus_ice);
        }else if(status.equals(MyCard.POTION_STATUS)){
            getStatusDesc = context.getString(R.string.skill_getStatus_potion);
        }
        Card card = mySelf.getCard(context);
        if (!result.doThisAction) {
            result.doThisAction = false;
        } else {
            result.title = context.getString(R.string.battle_cardActiveSkill)
                    .replace("{mySelf}", card.name)
                    .replace("{skill}", name);
			String labels = advContext.battleContext.buildMonsterLabels(advContext.battleContext.monsters);
			result.content = battleDesc.replace ("{mySelf}", card.name)
					.replace ("{targets}", labels)
					.replace ("{value}", result.realHurt + "");
			if(result.getStatus) {
				result.content = result.content+", "+getStatusDesc.replace("{statusLabels}", labels);
			}
			result.doThisAction = true;
        }
        result.icon1 = card.id+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
        advContext.battleContext.addActionResultToLog(result);
        return result;
	}

	public ActionResult statusHurtSingle(Context context,AdvContext advContext, int hurt, boolean ignoreDefense,int posGetStatus, String status) {
		Monster monster = advContext.battleContext.getRandomMonster(advContext);
        ActionResult result = advContext.battleContext.statusHurtSingle(context,advContext,monster,hurt,ignoreDefense,posGetStatus,status);
        Card card = mySelf.getCard(context);
        if(status.equals(MyCard.ELECTRICITY_STATUS)){
            getStatusDesc = context.getString(R.string.skill_getStatus_elect);
        }else if(status.equals(MyCard.FIRE_STATUS)){
            getStatusDesc = context.getString(R.string.skill_getStatus_fire);
        }else if(status.equals(MyCard.ICE_STATUS)){
            getStatusDesc = context.getString(R.string.skill_getStatus_ice);
        }else if(status.equals(MyCard.POTION_STATUS)){
            getStatusDesc = context.getString(R.string.skill_getStatus_potion);
        }
        if (result.doThisAction) {
            result.title = context.getString(R.string.battle_cardActiveSkill)
                    .replace("{mySelf}", card.name)
                    .replace("{skill}", name);
			result.content = battleDesc.replace ("{mySelf}", card.name)
					.replace ("{monster}", monster.label)
					.replace ("{value}", result.realHurt + "");
			if(result.getStatus) {
				result.content = result.content+getStatusDesc.replace("{statusLabels}", monster.label);
			}
			result.doThisAction = true;
        }
        result.icon1 = card.id+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
        advContext.battleContext.addActionResultToLog(result);
        return result;
	}

	public ActionResult valueUpTeam(Context context, AdvContext advContext, String field, int deltaValue){
        Card card = mySelf.getCard(context);
        ActionResult result = advContext.battleContext.valueUpTeam(advContext,field,deltaValue);
        result.title = context.getString(R.string.battle_cardActiveSkill)
                .replace("{mySelf}", card.name)
                .replace("{skill}", name);
		result.content = battleDesc.replace ("{value}", deltaValue + "")
                .replace ("{mySelf}", card.name);
        result.icon1 = card.id+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
		result.doThisAction = true;
		advContext.battleContext.addActionResultToLog(result);
        return result;
	}

	public ActionResult valueUpOne(Context context, AdvContext advContext, MyCard myCard, String field, int deltaValue){
        Card card = mySelf.getCard(context);
        ActionResult result = advContext.battleContext.valueUpOne(myCard,field,deltaValue);
        result.title = context.getString(R.string.battle_cardActiveSkill)
                .replace("{mySelf}", card.name)
                .replace("{skill}", name);
		result.content = battleDesc
                .replace ("{target}", card.name)
                .replace ("{value}", deltaValue + "")
                .replace("{mySelf}",card.name);
		result.icon1 = card.id+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
        advContext.battleContext.addActionResultToLog(result);
        return result;
	}

	public ActionResult valueDownAllMonster(Context context, AdvContext advContext, String field, int deltaValue){
        Card card = mySelf.getCard(context);
        ActionResult result = new ActionResult ();
		for(Monster ma : advContext.battleContext.monsters) {
			if(field.equals("hp")){
				ma.changeHp(context, advContext.battleContext, deltaValue);
			}else if(field.equals("attack")) {
				ma.attack = ma.attack - deltaValue;
				if(ma.attack < 1) {
				    ma.attack = 0;
                }
			}else if(field.equals("defense")) {
				ma.defense = ma.defense - deltaValue;
				if(ma.defense < 1) {
				    ma.defense = 0;
                }
			}
		}
        result.icon1 = card.id+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
        result.title = context.getString(R.string.battle_cardActiveSkill)
                .replace("{mySelf}", card.name)
                .replace("{skill}", name);
		result.content = battleDesc.replace ("{value}", deltaValue + "");
		result.doThisAction = true;
        advContext.battleContext.addActionResultToLog(result);
        return result;
	}

	public ActionResult valueDownOne(Context context, AdvContext advContext, Monster monster, String field, int deltaValue){
        Card card = mySelf.getCard(context);
        ActionResult result = new ActionResult ();
		if(field.equals("hp")){
			monster.changeHp(context, advContext.battleContext, deltaValue);
		}else if(field.equals("attack")) {
			monster.attack = monster.attack - deltaValue;
		}else if(field.equals("defense")) {
			monster.defense = monster.defense - deltaValue;
		}
        result.title = context.getString(R.string.battle_cardActiveSkill)
                .replace("{mySelf}", card.name)
                .replace("{skill}", name);
        result.icon1 = card.id+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
		result.content = battleDesc.replace ("{monster}", monster.label).replace ("{value}", deltaValue + "")
                .replace ("{mySelf}", card.name);
		result.doThisAction = true;
        advContext.battleContext.addActionResultToLog(result);
        return result;
	}

	public ActionResult connectMonster(Context context, AdvContext advContext,Monster monster, int hurt, ActionResult result) { //put this function to doActionOnCardAttackStart();
        Card card = mySelf.getCard(context);
	    String labels = advContext.battleContext.buildMonsterLabels(monster.connectMonsters);
		if(monster.connectMonsters.size() > 0) {
			for(Monster ma : monster.connectMonsters) {
				ma.changeHp(context,advContext.battleContext, hurt);
			}
		}
        result.title = context.getString(R.string.battle_cardActiveSkill)
                .replace("{mySelf}", card.name)
                .replace("{skill}", name);
        result.icon1 = card.id+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
		result.content = result.content + statusDesc;
        advContext.battleContext.addActionResultToLog(result);
		return result;
	}




	protected ActionResult getActionResultForActive(Context context) {
        Card card = mySelf.getCard(context);
		ActionResult result = new ActionResult ();
		result.doThisAction = true;
		result.title = context.getString(R.string.battle_cardActiveSkill)
                .replace("{mySelf}", card.name)
                .replace("{skill}", name);
		result.icon1 = card.id+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
        return  result;
	}

	public ActionResult doActionOnBattleStart(Context context,AdvContext advContext) {return null;}
	public ActionResult doActionOnBattleEnd(Context context,AdvContext advContext) {return null;}
	public ActionResult doActionOnTurnStart(Context context,AdvContext advContext) {return null;}
	public ActionResult doActionOnTurnEnd(Context context,AdvContext advContext) {return null;}
	public ActionResult doActionOnCardAttackStart(Context context,AdvContext advContext, MyCard card, Monster monster, int hurt) {return null;}
	public ActionResult doActionOnCardAttackEnd(Context context,AdvContext advContext, MyCard card, Monster monster, int hurt) {return null;}
	public ActionResult doActionOnMonsterAttackStart(Context context,AdvContext advContext, MyCard card, Monster monster, int hurt) {return null;}
	public ActionResult doActionOnMonsterAttackEnd(Context context,AdvContext advContext, MyCard card, Monster monster, int hurt) {return null;}


    public static BaseSkill getByCode(Context context, String code) {
        BaseSkill skill;
        if(code.equals(Connect2Monster.KEY)) {
            skill = new Connect2Monster(context);
        }else if(code.equals(ConnectAllMonster.KEY)) {
            skill = new ConnectAllMonster(context);
        }else if(code.equals(ConnectMyselfAndAllMonster.KEY)) {
            skill = new ConnectMyselfAndAllMonster(context);
        }else if(code.equals(ConnectMyselfAndMonster.KEY)) {
            skill = new ConnectMyselfAndMonster(context);
        }else if(code.equals(ElectricAllBigHurt.KEY)) {
            skill = new ElectricAllBigHurt(context);
        }else if(code.equals(ElectricAllHurt.KEY)) {
            skill = new ElectricAllHurt(context);
        }else if(code.equals(ElectricSingleBigHurt.KEY)) {
            skill = new ElectricSingleBigHurt(context);
        }else if(code.equals(ElectricSingleHurt.KEY)) {
            skill = new ElectricSingleHurt(context);
        }else if(code.equals(FireAllBigHurt.KEY)) {

            skill = new FireAllBigHurt(context);
        }else if(code.equals(FireAllHurt.KEY)) {

            skill = new FireAllHurt(context);
        }else if(code.equals(FireSingleBigHurt.KEY)) {

            skill = new FireSingleBigHurt(context);
        }else if(code.equals(FireSingleHurt.KEY)) {

            skill = new FireSingleHurt(context);
        }else if(code.equals(HealAll.KEY)) {

            skill = new HealAll(context);
        }else if(code.equals(HealAllBig.KEY)) {

            skill = new HealAllBig(context);
        }else if(code.equals(HealSingle.KEY)) {

            skill = new HealSingle(context);
        }else if(code.equals(HealSingleBig.KEY)) {

            skill = new HealSingleBig(context);
        }else if(code.equals(HitAllAndDodgeAttackTeam.KEY)) {

            skill = new HitAllAndDodgeAttackTeam(context);
        }else if(code.equals(HitAllBigHurt.KEY)) {

            skill = new HitAllBigHurt(context);
        }else if(code.equals(HitAllHurt.KEY)) {

            skill = new HitAllHurt(context);
        }else if(code.equals(HitDoubleTime.KEY)) {

            skill = new HitDoubleTime(context);
        }else if(code.equals(HitIgnoreDefenseSingle.KEY)) {

            skill = new HitIgnoreDefenseSingle(context);
        }else if(code.equals(HitIgnoreDefenseSingleBig.KEY)) {

            skill = new HitIgnoreDefenseSingleBig(context);
        }else if(code.equals(HitPercentage.KEY)) {

            skill = new HitPercentage(context);
        }else if(code.equals(HitSingleAndDodgeAttack.KEY)) {

            skill = new HitSingleAndDodgeAttack(context);
        }else if(code.equals(HitSingleBigHurt.KEY)) {

            skill = new HitSingleBigHurt(context);
        }else if(code.equals(HitSingleHurt.KEY)) {

            skill = new HitSingleHurt(context);
        }else if(code.equals(IceAllBigHurt.KEY)) {

            skill = new IceAllBigHurt(context);
        }else if(code.equals(IceAllHurt.KEY)) {

            skill = new IceAllHurt(context);
        }else if(code.equals(IceSingleBigHurt.KEY)) {

            skill = new IceSingleBigHurt(context);
        }else if(code.equals(IceSingleHurt.KEY)) {

            skill = new IceSingleHurt(context);
        }else if(code.equals(PotionAllBigHurt.KEY)) {

            skill = new PotionAllBigHurt(context);
        }else if(code.equals(PotionAllHurt.KEY)) {

            skill = new PotionAllHurt(context);
        }else if(code.equals(PotionSingleBigHurt.KEY)) {

            skill = new PotionSingleBigHurt(context);
        }else if(code.equals(PotionSingleHurt.KEY)) {

            skill = new PotionSingleHurt(context);
        }else if(code.equals(RandomAttackOfMonster.KEY)) {

            skill = new RandomAttackOfMonster(context);
        }else if(code.equals(ReflectAttackOnMyself.KEY)) {

            skill = new ReflectAttackOnMyself(context);
        }else if(code.equals(ReflectAttackOnTeam.KEY)) {

            skill = new ReflectAttackOnTeam(context);
        }else if(code.equals(SummonMonsterHeal.KEY)) {

            skill = new SummonMonsterHeal(context);
        }else if(code.equals(SummonMonsterLarge.KEY)) {

            skill = new SummonMonsterLarge(context);
        }else if(code.equals(SummonMonsterMiddle.KEY)) {

            skill = new SummonMonsterMiddle(context);
        }else if(code.equals(SummonMonsterSmall.KEY)) {

            skill = new SummonMonsterSmall(context);
        }else if(code.equals(TauntAndDefenseUp.KEY)) {

            skill = new TauntAndDefenseUp(context);
        }else if(code.equals(TauntAndDodgeAttackUp.KEY)) {

            skill = new TauntAndDodgeAttackUp(context);
        }else if(code.equals(TauntAndDefenseUpAndNotDead.KEY)) {

            skill = new TauntAndDefenseUpAndNotDead(context);
        }else if(code.equals(TauntAndHeal.KEY)) {

            skill = new TauntAndHeal(context);
        }else if(code.equals(ValueDownAllAttack.KEY)) {

            skill = new ValueDownAllAttack(context);
        }else if(code.equals(ValueDownAllDefense.KEY)) {

            skill = new ValueDownAllDefense(context);
        }else if(code.equals(ValueDownOneAttack.KEY)) {

            skill = new ValueDownOneAttack(context);
        }else if(code.equals(ValueDownOneDefense.KEY)) {

            skill = new ValueDownOneDefense(context);
        }else if(code.equals(ValueUpAllAttack.KEY)) {

            skill = new ValueUpAllAttack(context);
        }else if(code.equals(ValueUpAllDefense.KEY)) {

            skill = new ValueUpAllDefense(context);
        }else if(code.equals(ValueUpAttackDeadNumber.KEY)) {

            skill = new ValueUpAttackDeadNumber(context);
        }else if(code.equals(ValueUpAttackLowHp.KEY)) {

            skill = new ValueUpAttackLowHp(context);
        }else if(code.equals(ValueUpAttackTurnNumber.KEY)) {

            skill = new ValueUpAttackTurnNumber(context);
        }else if(code.equals(ValueUpDefenseDeadNumber.KEY)) {

            skill = new ValueUpDefenseDeadNumber(context);
        }else if(code.equals(ValueUpDefenseLowHp.KEY)) {

            skill = new ValueUpDefenseLowHp(context);
        }else if(code.equals(ValueUpDefenseTurnNumber.KEY)) {

            skill = new ValueUpDefenseTurnNumber(context);
        }else if(code.equals(ValueUpOneAttack.KEY)) {

            skill = new ValueUpOneAttack(context);
        }else if(code.equals(ValueUpOneDefense.KEY)) {

            skill = new ValueUpOneDefense(context);
        }else if(code.equals(ZeroHurtOnMyself.KEY)) {

            skill = new ZeroHurtOnMyself(context);
        }else if(code.equals(ZeroHurtOnTeam.KEY)) {

            skill = new ZeroHurtOnTeam(context);
        }else {
            skill = null;
        }
        return skill;
    }

//    public static void init(Context context) {
//        BaseSkill skill = new Connect2Monster(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ConnectMyselfAndAllMonster(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ConnectMyselfAndMonster(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ConnectMyselfAndMonster(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ElectricAllBigHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ElectricAllHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ElectricSingleBigHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ElectricSingleHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new FireAllBigHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new FireAllHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new FireSingleBigHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new FireSingleHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HealAll(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HealAllBig(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HealSingle(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HealSingleBig(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HitAllAndDodgeAttackTeam(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HitAllBigHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HitAllHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HitDoubleTime(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HitIgnoreDefenseSingle(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HitIgnoreDefenseSingleBig(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HitPercentage(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HitSingleAndDodgeAttack(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HitSingleBigHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new HitSingleHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new IceAllBigHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new IceAllHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new IceSingleBigHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new IceSingleHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new PotionAllBigHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new PotionAllHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new PotionSingleBigHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new PotionSingleHurt(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new RandomAttackOfMonster(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ReflectAttackOnMyself(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ReflectAttackOnTeam(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new SummonMonsterHeal(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new SummonMonsterLarge(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new SummonMonsterMiddle(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new TauntAndDefenseUp(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new TauntAndDodgeAttackUp(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new TauntAndDefenseUpAndNotDead(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new TauntAndHeal(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueDownAllAttack(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueDownAllDefense(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueUpAllAttack(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueUpAllDefense(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueUpAttackDeadNumber(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueUpAttackLowHp(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueUpAttackTurnNumber(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueUpOneDefense(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueUpDefenseDeadNumber(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueUpDefenseLowHp(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ValueUpDefenseTurnNumber(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ZeroHurtOnMyself(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//        skill = new ZeroHurtOnTeam(context);
//        GameContext.gameContext.skillDAO.insert(skill);
//    }
}