package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class HitAllBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public HitAllBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitAllBigHurt);
        desc = context.getString(R.string.skill_desc_hitAllBigHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_hitAllBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_hitAllBigHurt);
    }
}
