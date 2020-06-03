package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;
import com.longtail360.autochessrpg.utils.Logger;

//   <string name="passive_skill_desc_reflectAttack1">所有獸人受到傷害時, 傷害來源受到 5% 傷害</string>
//	<string name="passive_skill_desc_reflectAttack2">隊伍任意成員受到傷害時, 傷害來源受到 5% 傷害</string>
//	<string name="passive_skill_desc_reflectAttack3"></string>
public class ReflectAttack  extends BasePassiveSkill{
    public static String KEY = "ReflectAttack";
	private int pos = 50;
    public ReflectAttack(Context context) {
        code = KEY;
        raceClass = Card.RACE_ORCS;
//		raceClass = Card.RACE_HUMAN;
        number1 = 2;
        number2 = 4;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_reflectAttack1).replace("{value}", pos+"");
        desc2 = context.getString(R.string.passive_skill_desc_reflectAttack2).replace("{value}", pos+"");
        desc3 = context.getString(R.string.passive_skill_desc_reflectAttack3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		return null;
	}

	@Override
	public ActionResult doActionOnMonsterAttackEnd(Context context, AdvContext advContext, MyCard card, Monster monster, int hurt) {
		if(isActive3) {
			return null;
		}
		if(isActive2) {
			int random = advContext.mRandom.nextInt(100);
			Logger.log(KEY, "random:"+random);
			if(random < pos) {
				String name = card.getCard(context).name;
				int realHurt = hurt - monster.defense;
				monster.changeHp(context, advContext.battleContext, realHurt);
				ActionResult result = new ActionResult();
				result.doThisAction = true;
				result.icon1 = "skill_icon_reflect";
				result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
				result.title = context.getString(R.string.passive_skill_desc_reflectAttack_battleDescTitle).replace("{card}", name);
				result.content = context.getString(R.string.passive_skill_desc_reflectAttack_battleDescContent).replace("{card}", name).replace("{monster}", monster.label).replace("{value}", realHurt+"");
				advContext.battleContext.addActionResultToLog(result);
				return result;
			}
		}
		if(isActive1) {
			String name = card.getCard(context).name;
			if(card.race.equals(Card.RACE_ORCS)) {
			int random = advContext.mRandom.nextInt(100);
			Logger.log(KEY, "random:"+random);
			if(random < pos) {
					int realHurt = hurt - monster.defense;
					monster.changeHp(context, advContext.battleContext, realHurt);
					ActionResult result = new ActionResult();
					result.doThisAction = true;
					result.icon1 = "skill_icon_reflect";
					result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
					result.title = context.getString(R.string.passive_skill_desc_reflectAttack_battleDescTitle).replace("{card}", name);
					result.content = context.getString(R.string.passive_skill_desc_reflectAttack_battleDescContent).replace("{card}", name).replace("{monster}", monster.label).replace("{value}", realHurt+"");
					result.icon1 = "skill_icon_reflect";
					result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
					advContext.battleContext.addActionResultToLog(result);
					return result;

				}
				
			}
			
			return null;				
		}
		return null;
		
	}	
}
