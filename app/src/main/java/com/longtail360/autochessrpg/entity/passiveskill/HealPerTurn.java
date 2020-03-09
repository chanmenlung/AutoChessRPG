package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;

//   <string name="passive_skill_desc_healPerTurn1">隊伍每回合回復 1%生命值</string>
//    <string name="passive_skill_desc_healPerTurn2"></string>
 //   <string name="passive_skill_desc_healPerTurn3"></string>
public class HealPerTurn  extends BasePassiveSkill{
    public static String KEY = "HealPerTurn";

    public HealPerTurn(Context context) {
        code = KEY;
        raceClass = Card.CLAZZ_PRIEST;
//		raceClass = Card.RACE_HUMAN;
        number1 = 2;
        number2 = -1;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_healPerTurn1);
        desc2 = context.getString(R.string.passive_skill_desc_healPerTurn2);
        desc3 = context.getString(R.string.passive_skill_desc_healPerTurn3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		return null;
	}

	@Override
	public ActionResult doActionOnTurnStart(Context context,AdvContext advContext) {
		if(isActive3) {		
			return null;
		}
		if(isActive2) {	
			return null;
		}
		if(isActive1) {
			for(MyCard card : advContext.cards) {
				card.battleHp = card.battleHp + (int)(card.totalHp * 0.01);
				if(card.battleHp > card.totalHp) {
					card.battleHp = card.totalHp;
				}
			}
			return null;
		}
		return null;
	}
	
}
