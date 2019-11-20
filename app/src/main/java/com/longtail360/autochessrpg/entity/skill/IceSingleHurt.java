package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class IceSingleHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public IceSingleHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_iceSingleHurt);
        desc = context.getString(R.string.skill_desc_iceSingleHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_iceSingleHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_iceSingleHurt);
    }
}
