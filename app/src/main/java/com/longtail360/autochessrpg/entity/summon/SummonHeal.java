package com.longtail360.autochessrpg.entity.summon;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.log.RootLog;

public class SummonHeal  extends BaseSummon{
    public static String KEY = "SummonHeal";
    private int heal = 20;
    public SummonHeal(Context context) {
        key = KEY;
        name = context.getString(R.string.skill_name_summonMonsterHeal);
        title = context.getString(R.string.skill_attackTitle_summonMonsterHeal);
        content = context.getString(R.string.skill_attackContent_summonMonsterHeal);
    }

    @Override
    public ActionResult active(Context context,AdvContext advContext){
        advContext.battleContext.valueUpTeam(advContext, "hp", heal);
        ActionResult result = new ActionResult();
        result.doThisAction = true;
        result.title = title;
        result.content = content.replace("{value}", heal+"");
        result.icon1 = "summon_heal";
        result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
        return result;
    }
}
