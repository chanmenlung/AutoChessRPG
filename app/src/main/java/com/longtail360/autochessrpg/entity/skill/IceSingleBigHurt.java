package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class IceSingleBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public IceSingleBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_iceSingleBigHurt);
        desc = context.getString(R.string.skill_desc_iceSingleBigHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_iceSingleBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_iceSingleBigHurt);
    }
}
