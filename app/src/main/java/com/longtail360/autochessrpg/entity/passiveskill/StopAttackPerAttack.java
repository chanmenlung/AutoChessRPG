package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.utils.Logger;

//    <string name="passive_skill_desc_stopAttackPerAttack1">所有蟲族每次攻擊時, 有3%使敵人下回合停止攻擊</string>
//	<string name="passive_skill_desc_stopAttackPerAttack2">隊伍每次攻擊時, 有3%使敵人下回合停止攻擊</string>
//	<string name="passive_skill_desc_stopAttackPerAttack3"></string>
public class StopAttackPerAttack  extends BasePassiveSkill{
    public static String KEY = "StopAttackPerAttack";
	private int pos = 5;
    public StopAttackPerAttack(Context context) {
        code = KEY;
        raceClass = Card.RACE_GOO;
//		raceClass = Card.RACE_HUMAN;
        number1 = 3;
        number2 = 6;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_stopAttackPerAttack1).replace("{value}", pos+"");
        desc2 = context.getString(R.string.passive_skill_desc_stopAttackPerAttack2).replace("{value}", pos+"");;
        desc3 = context.getString(R.string.passive_skill_desc_stopAttackPerAttack3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		return null;
	}

	@Override
	public ActionResult doActionOnCardAttackEnd(Context context, AdvContext advContext, MyCard card, Monster monster, int hurt) {
		Logger.log(KEY, "isActive3:"+isActive3);
		Logger.log(KEY, "isActive2:"+isActive2);
		Logger.log(KEY, "isActive1:"+isActive1);
		Logger.log(KEY, "advContext.team:"+advContext.team.size());
		if(isActive3) {		
			return null;
		}
		if(isActive2) {
			int rand = advContext.mRandom.nextInt(100);
			if(rand < pos) {
				monster.skipAttack = true;
			}
			return null;			
		}
		if(isActive1) {
			if(card.card.race.equals(Card.RACE_GOO)) {
				int rand = advContext.mRandom.nextInt(100);
				if(rand < pos) {
					monster.skipAttack = true;
				}
			}
			return null;				
		}
		return null;
	}	
}
