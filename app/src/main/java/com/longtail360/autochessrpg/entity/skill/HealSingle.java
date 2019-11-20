package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class HealSingle extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public HealSingle(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_healSingle);
        desc = context.getString(R.string.skill_desc_healSingle);
        battleDesc = context.getString(R.string.skill_battleDesc_healSingle);
        statusDesc = context.getString(R.string.skill_statusDesc_healSingle);
    }
}
