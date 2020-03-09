package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.utils.Logger;

//    <string name="passive_skill_desc_criticalHitUp1">隊伍暴擊率上升 2%</string>
//    <string name="passive_skill_desc_criticalHitUp2">隊伍暴擊率上升 4%</string>
 //   <string name="passive_skill_desc_criticalHitUp3"> 隊伍暴擊率上升 6%</string>
public class CriticalHitUp  extends BasePassiveSkill{
    public static String KEY = "CriticalHitUp";

    public CriticalHitUp(Context context) {
        code = KEY;
        raceClass = Card.CLAZZ_ROGUE;
//		raceClass = Card.RACE_HUMAN;
        number1 = 2;
        number2 = 4;
        number3 = 6;
        desc1 = context.getString(R.string.passive_skill_desc_criticalHitUp1);
        desc2 = context.getString(R.string.passive_skill_desc_criticalHitUp2);
        desc3 = context.getString(R.string.passive_skill_desc_criticalHitUp3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		Logger.log(KEY, "isActive3:"+isActive3);
		Logger.log(KEY, "isActive2:"+isActive2);
		Logger.log(KEY, "isActive1:"+isActive1);
		if(isActive3) {
			for(MyCard card : advContext.team) {
				card.buffCri = card.buffCri + 6;
			}		
			return null;
		}
		if(isActive2) {
			for(MyCard card : advContext.team) {
				card.buffCri = card.buffCri + 4;
			}			
			return null;			
		}
		if(isActive1) {
			for(MyCard card : advContext.team) {
				card.buffCri = card.buffCri + 2;
			}		
			return null;				
		}
		return null;
	}		
}
