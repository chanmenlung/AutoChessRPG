package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;

//   <string name="passive_skill_desc_cdDown1">戰鬥開始時, 隊伍技能冷卻值(CD)減 1</string>
 //   <string name="passive_skill_desc_cdDown2"></string>
//    <string name="passive_skill_desc_cdDown3"></string>
public class CdDown  extends BasePassiveSkill{
    public static String KEY = "CdDown";

    public CdDown(Context context) {
        code = KEY;
        raceClass = Card.RACE_DIVINITY;
//		raceClass = Card.RACE_HUMAN;
        number1 = 3;
        number2 = -1;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_cdDown1);
        desc2 = context.getString(R.string.passive_skill_desc_cdDown2);
        desc3 = context.getString(R.string.passive_skill_desc_cdDown3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		return null;
	}

	@Override
	public ActionResult doActionOnBattleStart(Context context, AdvContext advContext) {
//		if(isActive3) {
//			return null;
//		}
//		if(isActive2) {
//			return null;
//		}
		if(isActive1) {
			for(MyCard card : advContext.cards) {
				card.cd = card.cd - 1;
			}			
			return null;				
		}
		return null;
		
	}	
}
