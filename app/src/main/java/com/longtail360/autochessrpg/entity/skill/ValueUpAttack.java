package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class ValueUpAttack extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public ValueUpAttack(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpAttack);
        desc = context.getString(R.string.skill_desc_valueUpAttack);
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpAttack);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpAttack);
    }
}
