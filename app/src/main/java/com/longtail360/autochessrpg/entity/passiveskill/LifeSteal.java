package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;

//    <string name="passive_skill_desc_lifeSteal1">所有角色攻擊時, 回復 1 點生命值</string>
//    <string name="passive_skill_desc_lifeSteal2">所有角色攻擊時, 回復 2 點生命值</string>
//    <string name="passive_skill_desc_lifeSteal3">所有角色攻擊時, 回復 3 點生命值</string>
public class LifeSteal  extends BasePassiveSkill{
    public static String KEY = "LifeSteal";

    public LifeSteal(Context context) {
        code = KEY;
        raceClass = Card.CLAZZ_MAGE;
//		raceClass = Card.RACE_HUMAN;
        number1 = 3;
        number2 = 6;
        number3 = 9;
        desc1 = context.getString(R.string.passive_skill_desc_lifeSteal1);
        desc2 = context.getString(R.string.passive_skill_desc_lifeSteal2);
        desc3 = context.getString(R.string.passive_skill_desc_lifeSteal3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		return null;
	}

	@Override
	public ActionResult doActionOnCardAttackEnd(Context context, AdvContext advContext, MyCard card, Monster monster, int hurt) {
		if(isActive3) {
			card.battleHp = card.battleHp + 3;
			if(card.battleHp > card.totalHp) {
				card.battleHp = card.totalHp;
			}
			return null;
		}
		if(isActive2) {
			card.battleHp = card.battleHp + 2;
			if(card.battleHp > card.totalHp) {
				card.battleHp = card.totalHp;
			}			
			return null;			
		}
		if(isActive1) {
			card.battleHp = card.battleHp + 1;
			if(card.battleHp > card.totalHp) {
				card.battleHp = card.totalHp;
			}		
			return null;				
		}
		return null;
	}	
}
