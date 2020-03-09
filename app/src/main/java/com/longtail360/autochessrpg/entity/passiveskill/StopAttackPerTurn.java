package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Monster;

//   <string name="passive_skill_desc_stopAttackPerTurn1">每回合敵人有 1% 停止攻擊</string>
 //   <string name="passive_skill_desc_stopAttackPerTurn2">每回合敵人有 2% 停止攻擊</string>
 //   <string name="passive_skill_desc_stopAttackPerTurn3">每回合敵人有 3% 停止攻擊</string>

public class StopAttackPerTurn extends BasePassiveSkill{
    public static String KEY = "StopAttackPerTurn";
	private int pos1 = 2;
	private int pos2 = 4;
	private int pos3 = 6;
    public StopAttackPerTurn(Context context) {
        code = KEY;
        raceClass = Card.CLAZZ_SHAMAN;
        number1 = 2;
        number2 = 4;
        number3 = 6;
        desc1 = context.getString(R.string.passive_skill_desc_stopAttackPerTurn1).replace("{value}", pos1+"'");
        desc2 = context.getString(R.string.passive_skill_desc_stopAttackPerTurn2).replace("{value}", pos2+"'");
        desc3 = context.getString(R.string.passive_skill_desc_stopAttackPerTurn3).replace("{value}", pos3+"'");
    }	
	
	@Override
	public ActionResult doActionOnTurnStart(Context context, AdvContext advContext) {
		if(isActive3) {
			int ran = advContext.mRandom.nextInt(100);
			if(ran <= pos3) {
				for(Monster monster : advContext.battleContext.monsters) {
					monster.skipAttack = true;
				}		
			}
			return null;
		}
		if(isActive2) {
			int ran = advContext.mRandom.nextInt(100);
			if(ran <= pos2) {
				for(Monster monster : advContext.battleContext.monsters) {
					monster.skipAttack = true;
				}		
			}		
			return null;			
		}
		if(isActive1) {
			int ran = advContext.mRandom.nextInt(100);
			if(ran == pos1) {
				for(Monster monster : advContext.battleContext.monsters) {
					monster.skipAttack = true;
				}		
			}
			return null;				
		}
		return null;
	}	
}
