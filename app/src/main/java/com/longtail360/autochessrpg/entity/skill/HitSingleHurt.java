package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class HitSingleHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public HitSingleHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitSingleHurt);
        desc = context.getString(R.string.skill_desc_hitSingleHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_hitSingleHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_hitSingleHurt);
    }
}
