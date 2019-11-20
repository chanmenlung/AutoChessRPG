package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class ValueUpAllAttack extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public ValueUpAllAttack(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpAllAttack);
        desc = context.getString(R.string.skill_desc_valueUpAllAttack);
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpAllAttack);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpAllAttack);
    }
}
