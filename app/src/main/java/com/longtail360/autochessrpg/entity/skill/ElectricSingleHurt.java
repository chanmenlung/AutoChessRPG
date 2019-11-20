package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class ElectricSingleHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public ElectricSingleHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_electricSingleHurt);
        desc = context.getString(R.string.skill_desc_electricSingleHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_electricSingleHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_electricSingleHurt);
    }
}
