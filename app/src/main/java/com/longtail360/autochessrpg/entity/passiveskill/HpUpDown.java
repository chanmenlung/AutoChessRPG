package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.log.RootLog;

//    <string name="passive_skill_desc_hpUpDownPerTurn1">戰鬥開始時, 所有元素種族吸取敵人3點生命值</string>
//    <string name="passive_skill_desc_hpUpDownPerTurn1">戰鬥開始時, 隊伍吸取敵人3點生命值</string>
//    <string name="passive_skill_desc_hpUpDownPerTurn1"></string>
//to do add icon to actionResult
public class HpUpDown  extends BasePassiveSkill{
    public static String KEY = "HpUpDown";

    public HpUpDown(Context context) {
        code = KEY;
        raceClass = Card.RACE_SPIRIT;
//		raceClass = Card.RACE_HUMAN;
		number1 = 2;
        number2 = 4;
        number3 = 999;
        desc1 = context.getString(R.string.passive_skill_desc_hpUpDown1);
        desc2 = context.getString(R.string.passive_skill_desc_hpUpDown2);
        desc3 = context.getString(R.string.passive_skill_desc_hpUpDown3);
    }
	
	@Override
	public ActionResult active(AdvContext advContext) {
		return null;
	}

	@Override
	public ActionResult doActionOnBattleStart(Context context,AdvContext advContext) {
		if(isActive3) {
			return null;
		}
		if(isActive2) {
			ActionResult result = new ActionResult();			
			StringBuilder str = new StringBuilder();
			for(MyCard card : advContext.cards) {
				str.append(card.card.name);
				str.append(",");
				result.doThisAction = true;
			}
			advContext.battleContext.valueUpTeam(advContext, "hp", 10);
			advContext.battleContext.statusHurtAll(context,advContext, 10, true, 100, MyCard.NONE_STATUS);
			result.title = context.getString(R.string.passive_skill_desc_hpUpDown_battleDescTitle);
			result.icon1 = "skill_icon_lifesteal";
			result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
			result.content = context.getString(R.string.passive_skill_desc_hpUpDown_battleDescContent2);
			advContext.battleContext.addActionResultToLog(result);
			return result;			
		}
		if(isActive1) {
			ActionResult result = new ActionResult();			
			StringBuilder str = new StringBuilder();
			for(MyCard card : advContext.cards) {
				if(card.card.race.equals(Card.RACE_SPIRIT)) {
					str.append(card.card.name);
					str.append(",");
					result.doThisAction = true;
				}
			}

			advContext.battleContext.valueUpTeam(advContext, "hp", 10);
			advContext.battleContext.statusHurtAll(context,advContext, 10, true, 100, MyCard.NONE_STATUS);
			result.title = context.getString(R.string.passive_skill_desc_hpUpDown_battleDescTitle);
			result.icon1 = "skill_icon_lifesteal";
			result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
			result.content = context.getString(R.string.passive_skill_desc_hpUpDown_battleDescContent).replace("{cards}",str.toString());
			advContext.battleContext.addActionResultToLog(result);
			return result;			
		}
		return null;
	}
	
}
