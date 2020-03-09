package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;

public class HitSingleAndDodgeAttack extends BaseSkill{
    public static String KEY = "HitSingleAndDodgeAttack";
	private int deltaAgi = 10;
	private int hurt =30;
    public HitSingleAndDodgeAttack(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitSingleAndDodgeAttack);
        desc = context.getString(R.string.skill_desc_hitSingleAndDodgeAttack).replace("{agi}", deltaAgi+"").replace("{hurt}", hurt+"");
        battleDesc = context.getString(R.string.skill_battleDesc_hitSingleAndDodgeAttack);
        statusDesc = context.getString(R.string.skill_statusDesc_hitSingleAndDodgeAttack);
    }
    @Override
    public String getDesc() {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){
        Monster monster = advContext.battleContext.getRandomMonster(advContext);
        ActionResult result = advContext.battleContext.statusHurtSingle(context,advContext,monster,hurt,false,0,MyCard.NONE_STATUS);
		mySelf.agi = mySelf.agi + deltaAgi;
        result.icon1 = mySelf.card.id+"";
        result.icon1Type = RootLog.ICON1_TYPE_CARD;
        result.title = context.getString(R.string.battle_cardActiveSkill)
                .replace("{mySelf}", mySelf.card.name)
                .replace("{skill}", name);
        result.content = battleDesc;
        result.content = result.content.replace("{agi}", deltaAgi+"").replace("{hurt}", hurt+"")
                .replace("{monster}", monster.label);
        advContext.battleContext.addActionResultToLog(result);
		return result;
	}	
}
