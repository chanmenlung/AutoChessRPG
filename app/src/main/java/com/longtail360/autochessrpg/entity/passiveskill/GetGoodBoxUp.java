package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;

//   <string name="passive_skill_desc_getGoodBoxUp1">發現有寶物的寶箱的機率增加5%</string>
 //   <string name="passive_skill_desc_getGoodBoxUp2"></string>
//    <string name="passive_skill_desc_getGoodBoxUp3"></string>
public class GetGoodBoxUp  extends BasePassiveSkill{
    public static String KEY = "GetGoodBoxUp";

    public GetGoodBoxUp(Context context) {
        code = KEY;
        raceClass = Card.RACE_ELF;
        number1 = 3;
        number2 = -1;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_getGoodBoxUp1);
        desc2 = context.getString(R.string.passive_skill_desc_getGoodBoxUp2);
        desc3 = context.getString(R.string.passive_skill_desc_getGoodBoxUp3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
        advContext.metGoodEvent = advContext.metGoodEvent + 5;
        advContext.metBadEvent = advContext.metBadEvent - 5;
        return null;
	}	
		
}
