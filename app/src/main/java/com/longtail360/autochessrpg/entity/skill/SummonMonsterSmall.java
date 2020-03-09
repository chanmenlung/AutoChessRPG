package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.summon.BaseSummon;
import com.longtail360.autochessrpg.entity.summon.SummonLarge;
import com.longtail360.autochessrpg.entity.summon.SummonSmall;

public class SummonMonsterSmall extends BaseSkill{
    public static String KEY = "SummonMonsterSmall";
	private String attackTitle;
	private String attackContent;
	private int hurt = 20;
	private int count;
    public SummonMonsterSmall(Context context) {
//        code = KEY;
//        cd = 3;
//        name = context.getString(R.string.skill_name_summonMonsterSmall);
//        desc = context.getString(R.string.skill_desc_summonMonsterSmall);
//        battleDesc = context.getString(R.string.skill_battleDesc_summonMonsterSmall);
////        attackTitle = context.getString(R.string.skill_attackTitle_summonMonsterLarge); //圖騰的攻擊
////		attackContent = context.getString(R.string.skill_attackContent_summonMonsterLarge); //圖騰向{monster}發動了圖騰墜擊, {monster}受到了{value}點傷害

		code = KEY;
		cd = 3;
		name = context.getString(R.string.skill_name_summonMonsterSmall);
		desc = context.getString(R.string.skill_desc_summonMonsterSmall);
		battleDesc = context.getString(R.string.skill_battleDesc_summonMonsterSmall);
		attackTitle = context.getString(R.string.skill_attackTitle_summonMonsterSmall); //魂狼的攻擊
		attackContent = context.getString(R.string.skill_attackContent_summonMonsterSmall); //魂狼向{monster}發動了嘶咬, {monster}受到了{value}點傷害
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
				if(baseSummon.key.equals(SummonSmall.KEY)){
					hadActive = true;
				}
			}
		}
		if(hadActive){
			actionResult.doThisAction = false;
		}else {
			advContext.battleContext.summons.add(new SummonSmall(context));
			advContext.battleContext.addActionResultToLog(actionResult);
		}
		return actionResult;
	}
}
