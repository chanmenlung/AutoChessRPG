package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class FireSingleHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public FireSingleHurt(Context context) {
        code = KEY;
        name = context.getString(R.string.skill_name_fireSingleHurt);
        desc = context.getString(R.string.skill_desc_fireSingleHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_fireSingleHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_fireSingleHurt);
    }
}
