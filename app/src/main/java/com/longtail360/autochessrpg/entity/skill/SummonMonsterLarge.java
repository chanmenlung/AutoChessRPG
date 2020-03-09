package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.summon.BaseSummon;
import com.longtail360.autochessrpg.entity.summon.SummonHeal;
import com.longtail360.autochessrpg.entity.summon.SummonLarge;

public class SummonMonsterLarge extends BaseSkill{
    public static String KEY = "SummonMonsterLarge";
    public SummonMonsterLarge(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_summonMonsterLarge);
        desc = context.getString(R.string.skill_desc_summonMonsterLarge);
		battleDesc = context.getString(R.string.skill_battleDesc_summonMonsterLarge);
    }
	@Override
	public String getDesc(Context context) {
		return null;
	}
	@Override
	public ActionResult active(Context context,AdvContext advContext){
		ActionResult actionResult = getActionResultForActive(context);
		actionResult.content = battleDesc.replace("{mySelf}", mySelf.card.name);
		boolean hadActive = false;
		if(advContext.battleContext.summons.size() > 0) {
			for(BaseSummon baseSummon : advContext.battleContext.summons){
				if(baseSummon.key.equals(SummonLarge.KEY)){
					hadActive = true;
				}
			}
		}
		if(hadActive){
			actionResult.doThisAction = false;
		}else {
			advContext.battleContext.summons.add(new SummonLarge(context));
			advContext.battleContext.addActionResultToLog(actionResult);
		}
		return actionResult;
	}
	
//	@Override
//	public ActionResult doActionOnTurnEnd(Context context,AdvContext advContext) {
//		ActionResult result = new ActionResult();
//		if(count > 0){
//			count--;
//			Monster target = advContext.battleContext.getRandomMonster(advContext);
//			int realHurt = hurt - target.defense;
//			result.doThisAction = true;
//			result.title = attackTitle;
//			result.content = attackContent.replace("{monster}", target.label).replace("{value}", realHurt+"");
//			target.changeHp(context, advContext.battleContext, realHurt);
//
//		}else {
//			result.doThisAction = false;
//		}
//		return result;
//
//	}
}
