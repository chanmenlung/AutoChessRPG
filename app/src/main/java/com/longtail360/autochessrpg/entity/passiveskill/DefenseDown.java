package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Monster;

//    <string name="passive_skill_desc_defenseDown1">敵人防禦力減少 1</string>
//    <string name="passive_skill_desc_defenseDown2">敵人防禦力減少 2</string>
//    <string name="passive_skill_desc_defenseDown3"></string>
public class DefenseDown  extends BasePassiveSkill{
    public static String KEY = "DefenseDown";

    public DefenseDown(Context context) {
        code = KEY;
        raceClass = Card.CLAZZ_KNIGHT;
//		raceClass = Card.RACE_HUMAN;
        number1 = 3;
        number2 = 6;
        number3 = -1;
        desc1 = context.getString(R.string.passive_skill_desc_defenseDown1);
        desc2 = context.getString(R.string.passive_skill_desc_defenseDown2);
        desc3 = context.getString(R.string.passive_skill_desc_defenseDown3);
    }

	@Override
	public ActionResult doActionOnBattleStart(Context context,AdvContext advContext) {
		if(isActive3) {
		
			return null;
		}
		if(isActive2) {
			for(Monster monster : advContext.battleContext.monsters) {
				monster.defense = monster.defense - 2;
				if(monster.defense < 1){
					monster.defense = 0;
				}
			}		
			return null;			
		}
		if(isActive1) {
			for(Monster monster : advContext.battleContext.monsters) {
				monster.defense = monster.defense - 1;
				if(monster.defense < 1){
					monster.defense = 0;
				}
			}	
			return null;				
		}
		return null;
	}	

		
}
