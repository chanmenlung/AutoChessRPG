package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HitAllAndDodgeAttackTeam extends BaseSkill{
    public static String KEY = "HitAllAndDodgeAttackTeam";
	private int deltaAgi = 5;
	private int hurt = 20;
    public HitAllAndDodgeAttackTeam(Context context) {
        code = KEY;
        cd = 2;
        name = context.getString(R.string.skill_name_hitAllAndDodgeAttackTeam);
        desc = context.getString(R.string.skill_desc_hitAllAndDodgeAttackTeam).replace("{value}", deltaAgi+"");
        battleDesc = context.getString(R.string.skill_battleDesc_hitAllAndDodgeAttackTeam)
                .replace("{value}", 5+"")
                .replace("{agiValue}", deltaAgi+"");
        statusDesc = context.getString(R.string.skill_statusDesc_hitAllAndDodgeAttackTeam);
    }
    @Override
    public String getDesc() {
        return null;
    }
	public ActionResult active(Context context,AdvContext advContext){
		for(MyCard c : advContext.cards) {
			c.agi = c.agi + deltaAgi;
		}
		return statusHurtAll(context,advContext, hurt, false, 0, MyCard.NONE_STATUS);
	}	
}
