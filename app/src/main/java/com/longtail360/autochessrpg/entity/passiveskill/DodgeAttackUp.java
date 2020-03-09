package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;

//    <string name="passive_skill_desc_dodgeAttackUp1">隊伍迴避率上升 2%</string>
//    <string name="passive_skill_desc_dodgeAttackUp2">隊伍迴避率上升 4%</string>
//    <string name="passive_skill_desc_dodgeAttackUp3"></string>
public class DodgeAttackUp  extends BasePassiveSkill{
    public static String KEY = "DodgeAttackUp";

    public DodgeAttackUp(Context context) {
        code = KEY;
        raceClass = Card.CLAZZ_HUNTER;
//		raceClass = Card.RACE_HUMAN;
        number1 = 3;
        number2 = 6;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_dodgeAttackUp1);
        desc2 = context.getString(R.string.passive_skill_desc_dodgeAttackUp2);
        desc3 = context.getString(R.string.passive_skill_desc_dodgeAttackUp3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		if(isActive3) {
			return null;
		}
		if(isActive2) {
			for(MyCard card : advContext.team) {
				card.buffAgi = card.buffAgi +99;
				card.agi = card.buffAgi;
			}	
			return null;			
		}
		if(isActive1) {		
			for(MyCard card : advContext.team) {
				card.buffAgi = card.buffAgi +99;
				card.agi = card.buffAgi;
			}		
			return null;			
		}
		return null;
	}	
	
}
