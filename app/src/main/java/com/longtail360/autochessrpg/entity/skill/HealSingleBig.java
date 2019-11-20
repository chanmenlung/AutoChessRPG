package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class HealSingleBig extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public HealSingleBig(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_healSingleBig);
        desc = context.getString(R.string.skill_desc_healSingleBig);
        battleDesc = context.getString(R.string.skill_battleDesc_healSingleBig);
        statusDesc = context.getString(R.string.skill_statusDesc_healSingleBig);
    }
}
