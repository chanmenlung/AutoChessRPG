package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.summon.BaseSummon;
import com.longtail360.autochessrpg.entity.summon.SummonLarge;
import com.longtail360.autochessrpg.entity.summon.SummonMiddle;

public class SummonMonsterMiddle extends BaseSkill{
    public static String KEY = "SummonMonsterMiddle";
	private String attackTitle;
	private String attackContent;
	private int hurt = 30;
	private int count = 0;
    public SummonMonsterMiddle(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_summonMonsterMiddle);
        desc = context.getString(R.string.skill_desc_summonMonsterMiddle);
        battleDesc = context.getString(R.string.skill_battleDesc_summonMonsterMiddle);
        attackTitle = context.getString(R.string.skill_attackTitle_summonMonsterMiddle); //魂狼的攻擊
		attackContent = context.getString(R.string.skill_attackContent_summonMonsterMiddle); //魂狼向{monster}發動了嘶咬, {monster}受到了{value}點傷害
    }


    @Override
	public ActionResult active(Context context,AdvContext advContext){
		ActionResult actionResult = getActionResultForActive(context);
		actionResult.content = battleDesc.replace("{mySelf}", mySelf.getCard(context).name);
		boolean hadActive = false;
		if(advContext.battleContext.summons.size() > 0) {
			for(BaseSummon baseSummon : advContext.battleContext.summons){
				if(baseSummon.key.equals(SummonMiddle.KEY)){
					hadActive = true;
				}
			}
		}
		if(hadActive){
			actionResult.doThisAction = false;
		}else {
			advContext.battleContext.summons.add(new SummonMiddle(context));
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
