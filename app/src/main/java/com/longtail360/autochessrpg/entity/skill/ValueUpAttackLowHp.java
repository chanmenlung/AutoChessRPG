package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueUpAttackLowHp extends BaseSkill{
    public static String KEY = "ValueUpAttackLowHp";

    public ValueUpAttackLowHp(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_valueUpAttackLowHp);
        desc = context.getString(R.string.skill_desc_valueUpAttackLowHp);
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpAttackLowHp);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpAttackLowHp);
    }
	
	@Override
	public ActionResult active(Context context,AdvContext advContext){
        int consume = mySelf.totalHp - mySelf.battleHp;
		int temp = consume*10/mySelf.totalHp;
        return valueUpOne( context,advContext,mySelf, "attack", temp);
	}	
}
