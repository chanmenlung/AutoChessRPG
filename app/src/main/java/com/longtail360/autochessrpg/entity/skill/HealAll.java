package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HealAll extends BaseSkill{
    public static String KEY = "HealAll";
	private int heal = 20;
    public HealAll(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_healAll);
        desc = context.getString(R.string.skill_desc_healAll).replace("{value}", heal+"");
        battleDesc = context.getString(R.string.skill_battleDesc_healAll).replace("{value}", heal+"");
        statusDesc = context.getString(R.string.skill_statusDesc_healAll);
    }
    @Override
    public String getDesc(Context context) {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){
		return valueUpTeam(context, advContext, "hp", heal);
	}
}
