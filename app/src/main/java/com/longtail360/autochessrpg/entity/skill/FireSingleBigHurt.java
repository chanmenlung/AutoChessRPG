package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class FireSingleBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public FireSingleBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_fireSingleBigHurt);
        desc = context.getString(R.string.skill_desc_fireSingleBigHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_fireSingleBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_fireSingleBigHurt);
    }
}
