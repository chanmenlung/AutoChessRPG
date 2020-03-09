package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class ValueUpDefenseLowHp extends BaseSkill{
    public static String KEY = "ValueUpDefenseLowHp";
	private int upValue = 1;
	private int upValue2 = 2;
    public ValueUpDefenseLowHp(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpDefenseLowHp);
        desc = context.getString(R.string.skill_desc_valueUpDefenseLowHp).replace("{value}", upValue+"").replace("{value}", upValue2+"");
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpDefenseLowHp);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpDefenseLowHp);
    }
    @Override
    public String getDesc(Context context) {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){
        int percent = mySelf.battleHp * 100 / mySelf.totalHp;
        int deltaValue;
        if(percent > 40) {
            deltaValue = upValue;
        }else {
            deltaValue = upValue2;
        }
		return valueUpOne(context,advContext,mySelf, "defense", deltaValue);
	}	
}
