package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class ValueUpAllDefense extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public ValueUpAllDefense(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpAllDefense);
        desc = context.getString(R.string.skill_desc_valueUpAllDefense);
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpAllDefense);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpAllDefense);
    }
}
