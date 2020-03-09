package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;

//	<string name="passive_skill_desc_notDeadPerBattle1">所有不死族於死亡時獲得一次重生</string>
//	<string name="passive_skill_desc_notDeadPerBattle2">隊伍於死亡時獲得一次重生</string>
//	<string name="passive_skill_desc_notDeadPerBattle3"></string>
public class NotDeadPerBattle  extends BasePassiveSkill{
    public static String KEY = "NotDeadPerBattle";

    public NotDeadPerBattle(Context context) {
        code = KEY;
        raceClass = Card.RACE_UNDEAD;
//        raceClass = Card.RACE_HUMAN;
        number1 = 2;
        number2 = 4;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_notDeadPerBattle1);
        desc2 = context.getString(R.string.passive_skill_desc_notDeadPerBattle2);
        desc3 = context.getString(R.string.passive_skill_desc_notDeadPerBattle3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
        if(isActive3) {
            for(MyCard card : advContext.team) {
//			if(card.card.race.equals(Card.RACE_UNDEAD)) {
                if(card.relife == 0) {
                    card.relife = 1;
                }
//			}
            }
            return null;
        }
        if(isActive2) {
            for(MyCard card : advContext.team) {
//			if(card.card.race.equals(Card.RACE_UNDEAD)) {
                if(card.relife == 0) {
                    card.relife = 1;
                }
//			}
            }
            return null;
        }
        if(isActive1) {
            for(MyCard card : advContext.team) {
//			if(card.card.race.equals(Card.RACE_UNDEAD)) {
                if(card.relife == 0) {
                    card.relife = 1;
                }
//			}
            }
            return null;
        }
        return null;
		
	}		
}
