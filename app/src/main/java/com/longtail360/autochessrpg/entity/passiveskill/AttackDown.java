package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.utils.Logger;

//<string name="passive_skill_desc_attackDown1">戰鬥開始時, 使敵人攻擊力下降 1 點</string>
    //<string name="passive_skill_desc_attackDown2">戰鬥開始時, 使敵人攻擊力下降 2 點</string>
    //<string name="passive_skill_desc_attackDown3"></string>
public class AttackDown  extends BasePassiveSkill{
    public static String KEY = "AttackDown";
	
    public AttackDown(Context context) {
        code = KEY;
        raceClass = Card.RACE_DWARF;

        number1 = 2;
        number2 = 4;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_attackDown1);
        desc2 = context.getString(R.string.passive_skill_desc_attackDown2);
        desc3 = context.getString(R.string.passive_skill_desc_attackDown3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
    	return null;
	}

	@Override
	public ActionResult doActionOnBattleStart(Context context,AdvContext advContext) {
		Logger.log(KEY, "isActive3:"+isActive3);
		Logger.log(KEY, "isActive2:"+isActive2);
		Logger.log(KEY, "isActive1:"+isActive1);
		if(isActive3) {		
			return null;
		}
		if(isActive2) {
			for(Monster monster : advContext.battleContext.monsters) {
				monster.attack = monster.attack  - 2;
			}		
			return null;			
		}
		if(isActive1) {
			for(Monster monster : advContext.battleContext.monsters) {
				monster.attack = monster.attack - 1;
			}		
			return null;				
		}
		return null;
		
	}
	
}
