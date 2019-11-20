package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class HealAll extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public HealAll(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_healAll);
        desc = context.getString(R.string.skill_desc_healAll);
        battleDesc = context.getString(R.string.skill_battleDesc_healAll);
        statusDesc = context.getString(R.string.skill_statusDesc_healAll);
    }
}
