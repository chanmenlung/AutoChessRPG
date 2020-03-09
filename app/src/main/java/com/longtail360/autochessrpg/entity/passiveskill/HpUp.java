package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.GameContext;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.utils.Logger;

//   <string name="passive_skill_desc_hpUp1">所有人類生命值上限增加3%</string>
 //   <string name="passive_skill_desc_hpUp2">所有人類生命值上限增加6%</string>
  //  <string name="passive_skill_desc_hpUp3">隊伍生命值上限增加9%</string>
public class HpUp  extends BasePassiveSkill{
    public static String KEY = "HpUp";

    public HpUp(Context context) {
        code = KEY;
//        raceClass = Card.RACE_HUMAN; //uncomment this
		raceClass = Card.RACE_DWARF;
        number1 = 3;
        number2 = 6;
        number3 = 9;
        desc1 = context.getString(R.string.passive_skill_desc_hpUp1);
        desc2 = context.getString(R.string.passive_skill_desc_hpUp2);
        desc3 = context.getString(R.string.passive_skill_desc_hpUp3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		Logger.log(KEY, "isActive3:"+isActive3);
		Logger.log(KEY, "isActive2:"+isActive2);
		Logger.log(KEY, "isActive1:"+isActive1);
		Logger.log(KEY, "advContext.team:"+advContext.team.size());
		if(isActive3) {
			for(MyCard myCard : advContext.team) {
					myCard.totalHp = (int)(myCard.totalHp * 1.09);
				if(GameContext.gameContext.adventure.currentRootLog != null && GameContext.gameContext.adventure.currentRootLog.advStatus != RootLog.ADV_STATUS_PROGRESSING){
					myCard.battleHp = myCard.totalHp;
				}
			}
			return null;
		}
		if(isActive2) {
			for(MyCard myCard : advContext.team) {
					myCard.totalHp = (int)(myCard.totalHp * 1.06);
				if(GameContext.gameContext.adventure.currentRootLog != null && GameContext.gameContext.adventure.currentRootLog.advStatus != RootLog.ADV_STATUS_PROGRESSING){
					myCard.battleHp = myCard.totalHp;
				}
			}
			return null;
		}
		if(isActive1) {
			for(MyCard myCard : advContext.team) {
					myCard.totalHp = (int)(myCard.totalHp * 1.03);
				if(GameContext.gameContext.adventure.currentRootLog != null && GameContext.gameContext.adventure.currentRootLog.advStatus != RootLog.ADV_STATUS_PROGRESSING){
					myCard.battleHp = myCard.totalHp;

				}
			}
			return null;
		}
		return null;
	}	
	
}
