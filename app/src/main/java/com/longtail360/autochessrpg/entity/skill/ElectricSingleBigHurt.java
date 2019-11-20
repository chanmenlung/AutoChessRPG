package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class ElectricSingleBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public ElectricSingleBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_electricSingleBigHurt);
        desc = context.getString(R.string.skill_desc_electricSingleBigHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_electricSingleBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_electricSingleBigHurt);
    }
}
