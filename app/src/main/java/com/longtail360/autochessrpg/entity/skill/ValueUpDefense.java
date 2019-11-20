package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class ValueUpDefense extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public ValueUpDefense(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_valueUpDefense);
        desc = context.getString(R.string.skill_desc_valueUpDefense);
        battleDesc = context.getString(R.string.skill_battleDesc_valueUpDefense);
        statusDesc = context.getString(R.string.skill_statusDesc_valueUpDefense);
    }
}
