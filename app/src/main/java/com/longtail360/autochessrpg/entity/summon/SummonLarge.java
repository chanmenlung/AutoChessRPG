package com.longtail360.autochessrpg.entity.summon;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.log.RootLog;

public class SummonLarge extends BaseSummon{
    public static String KEY = "SummonLarge";
    private int hurt = 50;
    public SummonLarge(Context context){
        key = KEY;
        name = context.getString(R.string.skill_name_summonMonsterLarge);
        title = context.getString(R.string.skill_attackTitle_summonMonsterLarge); //始生龍的攻擊
        content = context.getString(R.string.skill_attackContent_summonMonsterLarge); //始生龍向{monster}發動了龍之氣息, {monster}受到了{value}點傷害
    }
    @Override
    public ActionResult active(Context context, AdvContext advContext){
        ActionResult result = new ActionResult();
        Monster target = advContext.battleContext.getRandomMonster(advContext);
        int realHurt = hurt - target.defense;
        result.doThisAction = true;
        result.title = title;
        result.content = content.replace("{monster}", target.label).replace("{value}", realHurt+"");
        result.icon1 = "summon_large";
        result.icon1Type = RootLog.ICON1_TYPE_NOT_CARD;
        target.changeHp(context, advContext.battleContext, realHurt);
        return result;
    }
}
