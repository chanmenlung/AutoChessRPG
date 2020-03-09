package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.entity.summon.SummonMiddle;
import com.longtail360.autochessrpg.entity.summon.SummonSmall;

//    <string name="passive_skill_desc_summonMonster1">召換圖騰為你作戰 </string>
//	<string name="passive_skill_desc_summonMonster2">召換始狼魂為你作戰</string>
//	<string name="passive_skill_desc_summonMonster3"></string>
public class SummonMonster  extends BasePassiveSkill{
    public static String KEY = "SummonMonster";

    public SummonMonster(Context context) {
        code = KEY;
        raceClass = Card.CLAZZ_WARLOCK;
//        raceClass = Card.RACE_HUMAN;
        number1 = 2;
        number2 = 4;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_summonMonster1);
        desc2 = context.getString(R.string.passive_skill_desc_summonMonster2);
        desc3 = context.getString(R.string.passive_skill_desc_summonMonster3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
    	return null;
	}

	@Override
	public ActionResult doActionOnBattleStart(Context context,AdvContext advContext) {
		if(isActive3) {			
			return null;
		}
		if(isActive2) {
			ActionResult result = new ActionResult();
			result.title = context.getString(R.string.skill_name_summonMonsterMiddle);
			result.content = context.getString(R.string.skill_battleDesc_summonMonsterMiddle);
			result.icon1 = "summon_middle";
			result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
			advContext.battleContext.addActionResultToLog(result);
			advContext.battleContext.summons.add(new SummonMiddle(context));
			return null;			
		}
		if(isActive1) {
			ActionResult result = new ActionResult();
			result.title = context.getString(R.string.skill_name_summonMonsterSmall);
			result.content = context.getString(R.string.skill_battleDesc_summonMonsterSmall);
			result.icon1 = "summon_small";
			result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
			advContext.battleContext.addActionResultToLog(result);
			advContext.battleContext.summons.add(new SummonSmall(context));
			return null;				
		}
		return null;
		
	}	
}
