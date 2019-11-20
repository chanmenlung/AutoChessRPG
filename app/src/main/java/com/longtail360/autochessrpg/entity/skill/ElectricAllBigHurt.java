package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class ElectricAllBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public ElectricAllBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_electricAllBigHurt);
        desc = context.getString(R.string.skill_desc_electricAllBigHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_electricAllBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_electricAllBigHurt);
    }
}
