package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class HitAllHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public HitAllHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_hitAllHurt);
        desc = context.getString(R.string.skill_desc_hitAllHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_hitAllHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_hitAllHurt);
    }
}
