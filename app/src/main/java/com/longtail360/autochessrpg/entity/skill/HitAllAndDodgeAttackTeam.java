package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.MyCard;
public class HitAllAndDodgeAttackTeam extends BaseSkill{
    public static String KEY = "HitAllAndDodgeAttackTeam";
	private int minHurt = 8;
	private int maxHurt = 12;
	private int deltaAgi = 5;
    public HitAllAndDodgeAttackTeam(Context context) {
        code = KEY;
        cd = 5;
        name = context.getString(R.string.skill_name_hitAllAndDodgeAttackTeam);
        desc = context.getString(R.string.skill_desc_hitAllAndDodgeAttackTeam).replace("{value}", minHurt+"-"+maxHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_hitAllAndDodgeAttackTeam)
                .replace("{agiValue}", deltaAgi+"");
    }
	
	@Override
	public ActionResult active(Context context,AdvContext advContext){
		for(MyCard c : advContext.cards) {
			c.agi = c.agi + deltaAgi;
		}
		return statusHurtAll(context,advContext, getIntByRange(minHurt, maxHurt), false, 0, MyCard.NONE_STATUS);
	}	
}
