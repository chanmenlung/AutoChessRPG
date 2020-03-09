package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HealSingleBig extends BaseSkill{
    public static String KEY = "HealSingleBig";
	private int heal = 40;
    public HealSingleBig(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_healSingleBig);
        desc = context.getString(R.string.skill_desc_healSingleBig).replace("{value}", heal+"");
        battleDesc = context.getString(R.string.skill_battleDesc_healSingleBig).replace("{value}", heal+"");
        statusDesc = context.getString(R.string.skill_statusDesc_healSingleBig);
    }
    @Override
    public String getDesc() {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){
        MyCard target = mySelf.findLowestHp(advContext);
		return valueUpOne(context, advContext, target,"hp", heal);
	}	
}
