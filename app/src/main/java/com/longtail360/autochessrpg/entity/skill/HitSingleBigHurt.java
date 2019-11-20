package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class HitSingleBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public HitSingleBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitSingleBigHurt);
        desc = context.getString(R.string.skill_desc_hitSingleBigHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_hitSingleBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_hitSingleBigHurt);
    }
}
