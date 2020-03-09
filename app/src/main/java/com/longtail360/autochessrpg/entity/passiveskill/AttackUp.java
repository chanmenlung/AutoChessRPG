package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;

//   <string name="passive_skill_desc_attackUp1">隊伍攻擊上升 1 點</string>
 //   <string name="passive_skill_desc_attackUp2">隊伍攻擊上升 2 點</string>
 //   <string name="passive_skill_desc_attackUp3"> 隊伍攻擊上升 3 點</string>
public class AttackUp  extends BasePassiveSkill{
    public static String KEY = "AttackUp";

    public AttackUp(Context context) {
        code = KEY;
        raceClass = Card.CLAZZ_WARRIOR;
        number1 = 3;
        number2 = 6;
        number3 = 9;
        desc1 = context.getString(R.string.passive_skill_desc_attackUp1);
        desc2 = context.getString(R.string.passive_skill_desc_attackUp2);
        desc3 = context.getString(R.string.passive_skill_desc_attackUp3);		
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		if(isActive3) {
			for(MyCard card : advContext.team) {
				card.battleAttack = card.battleAttack + 3;
			}		
			return null;
		}
		if(isActive2) {
			for(MyCard card : advContext.team) {
				card.battleAttack = card.battleAttack + 2;
			}		
			return null;			
		}
		if(isActive1) {
			for(MyCard card : advContext.team) {
				card.battleAttack = card.battleAttack + 1;
			}		
			return null;				
		}
		return null;
	}	
		
}
