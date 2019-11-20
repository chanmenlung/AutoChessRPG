package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class ElectricAllHurt  extends BaseSkill{
    public static String KEY = "ElectricAllHurt";

    public ElectricAllHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_electricAllHurt);
        desc = context.getString(R.string.skill_desc_electricAllHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_electricAllHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_electricAllHurt);
    }
}
