package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HitIgnoreDefenseSingle extends BaseSkill{
    public static String KEY = "HitIgnoreDefenseSingle";
	private int hurt = 40;
    public HitIgnoreDefenseSingle(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitIgnoreDefenseSingle);
        desc = context.getString(R.string.skill_desc_hitIgnoreDefenseSingle).replace("{value}", hurt+"");
        battleDesc = context.getString(R.string.skill_battleDesc_hitIgnoreDefenseSingle).replace("{value}", hurt+"");
        statusDesc = context.getString(R.string.skill_statusDesc_hitIgnoreDefenseSingle);
    }
    @Override
    public String getDesc() {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){
		return statusHurtSingle(context, advContext, hurt, true,0, MyCard.NONE_STATUS);
	}	
}
