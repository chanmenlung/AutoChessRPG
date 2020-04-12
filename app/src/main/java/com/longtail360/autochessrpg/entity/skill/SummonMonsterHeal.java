package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.summon.BaseSummon;
import com.longtail360.autochessrpg.entity.summon.SummonHeal;

public class SummonMonsterHeal extends BaseSkill{
    public static String KEY = "SummonMonsterHeal";
    public SummonMonsterHeal(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_summonMonsterHeal);
        desc = context.getString(R.string.skill_desc_summonMonsterHeal);
        battleDesc = context.getString(R.string.skill_battleDesc_summonMonsterHeal);
        statusDesc = context.getString(R.string.skill_statusDesc_summonMonsterHeal);
    }
	public ActionResult active(Context context,AdvContext advContext){
		ActionResult actionResult = getActionResultForActive(context);
		actionResult.content = battleDesc.replace("{mySelf}", mySelf.getCard(context).name);
		boolean hadActive = false;
		if(advContext.battleContext.summons.size() > 0) {
		    for(BaseSummon baseSummon : advContext.battleContext.summons){
		        if(baseSummon.key.equals(SummonHeal.KEY)){
                    hadActive = true;
                }
            }
        }
		if(hadActive){
            actionResult.doThisAction = false;
//            mySelf.randomNormalAttackMonster(context,advContext);
        }else {
            advContext.battleContext.summons.add(new SummonHeal(context));
            advContext.battleContext.addActionResultToLog(actionResult);
        }
		return actionResult;
	}
	
//	@Override
//	public ActionResult doActionOnTurnEnd(Context context,AdvContext advContext) {
//		ActionResult result = new ActionResult();
//		if(count > 0){
//			count--;
//			result.doThisAction = true;
//			result.content = battleDesc.replace("{value}", heal+"");
//			for(MyCard ca : advContext.cards) {
//				ca.battleHp = ca.battleHp + heal;
//			}
//		}else {
//			result.doThisAction = false;
//		}
//		return result;
//
//	}
}
