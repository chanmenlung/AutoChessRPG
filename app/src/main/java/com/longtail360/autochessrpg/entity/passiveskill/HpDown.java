package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Monster;

//    <string name="passive_skill_desc_hpDown1">戰鬥開始時, 對敵人造成 3% 傷害</string>
//    <string name="passive_skill_desc_hpDown2">戰鬥開始時, 對敵人造成 6% 傷害</string>
//    <string name="passive_skill_desc_hpDown3"></string>
public class HpDown  extends BasePassiveSkill{
    public static String KEY = "HpDown";

    public HpDown(Context context) {
        code = KEY;
        raceClass = Card.RACE_DEMON;
        number1 = 3;
        number2 = 6;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_hpDown1);
        desc2 = context.getString(R.string.passive_skill_desc_hpDown2);
        desc3 = context.getString(R.string.passive_skill_desc_hpDown3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		return null;
	}

	@Override
	public ActionResult doActionOnBattleStart(Context context, AdvContext advContext) {
		if(isActive3) {		
			return null;
		}
		if(isActive2) {
			for(Monster monster : advContext.battleContext.monsters) {
				int deltaHp = (int)(monster.getHp() * 0.06);
				monster.changeHp(context, advContext.battleContext, deltaHp);
			}		
			return null;
		}
		if(isActive1) {
			for(Monster monster : advContext.battleContext.monsters) {
				int deltaHp = (int)(monster.getHp() * 0.03);
				monster.changeHp(context, advContext.battleContext, deltaHp);
			}		
			return null;
		}
		return null;
	}	
}
