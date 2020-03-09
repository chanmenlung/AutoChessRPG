package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.utils.Logger;

//    <string name="passive_skill_desc_defenseUp1">所有海族防禦力增加 1</string>
//    <string name="passive_skill_desc_defenseUp2">隊伍防禦力增加 1</string>
//    <string name="passive_skill_desc_defenseUp3"></string>
public class DefenseUp  extends BasePassiveSkill{
    public static String KEY = "DefenseUp";

    public DefenseUp(Context context) {
        code = KEY;
        raceClass = Card.RACE_MARINE;
//		raceClass = Card.RACE_HUMAN;
        number1 = 3;
        number2 = 6;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_defenseUp1);
        desc2 = context.getString(R.string.passive_skill_desc_defenseUp2);
        desc3 = context.getString(R.string.passive_skill_desc_defenseUp3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		Logger.log(KEY, "isActive3:"+isActive3);
		Logger.log(KEY, "isActive2:"+isActive2);
		Logger.log(KEY, "isActive1:"+isActive1);
		if(isActive3) {
			return null;
		}
		if(isActive2) {
			for(MyCard card : advContext.team) {
				card.buffDefense = card.buffDefense +1;
				card.battleDefense = card.buffDefense;
			}	
			return null;			
		}
		if(isActive1) {		
			for(MyCard card : advContext.team) {
				if(card.card.race.equals(Card.RACE_MARINE)) {
					card.buffDefense = card.buffDefense +1;
					card.battleDefense = card.buffDefense;
				}
			}		
			return null;			
		}
		return null;
	}	
}
