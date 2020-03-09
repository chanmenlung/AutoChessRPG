package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.utils.Logger;

public class HitDoubleTime extends BaseSkill{
    public static String KEY = "HitDoubleTime";
	public int randomValue = 20;
    public HitDoubleTime(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitDoubleTime);
        desc = context.getString(R.string.skill_desc_hitDoubleTime).replace("{value}", randomValue+"");
        battleDesc = context.getString(R.string.skill_battleDesc_hitDoubleTime);
        statusDesc = context.getString(R.string.skill_statusDesc_hitDoubleTime);
    }
	@Override
	public String getDesc() {
		return null;
	}
	public ActionResult active(Context context,AdvContext advContext){
		mySelf.windFury = true;
		ActionResult result = getActionResultForActive(context);
		result.content = battleDesc.replace("{mySelf}", mySelf.card.name);
		result.icon1 = mySelf.card.id+"";
		result.icon1Type = RootLog.ICON1_TYPE_CARD;
		advContext.battleContext.addActionResultToLog(result);
		return result;
	}

	@Override
	public ActionResult doActionOnTurnStart(Context context,AdvContext advContext) {
    	int random = advContext.mRandom.nextInt(100);
    	if(random < randomValue){
    		mySelf.windFury = true;
		}else {
    		mySelf.windFury = false;
		}
    	Logger.log(tag, "mySelf.windFury:"+mySelf.windFury);
		return null;
	}

}
