package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HealSingle extends BaseSkill{
    public static String KEY = "HealSingle";
	private int heal = 30;
    public HealSingle(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_healSingle);
        desc = context.getString(R.string.skill_desc_healSingle).replace("{value}", heal+"");
        battleDesc = context.getString(R.string.skill_battleDesc_healSingle).replace("{value}", heal+"");
        statusDesc = context.getString(R.string.skill_statusDesc_healSingle);
    }
    @Override
    public String getDesc() {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){
        MyCard target = mySelf.findLowestHp(advContext);
		return valueUpOne(context,advContext, target,"hp", heal);
	}		
}
