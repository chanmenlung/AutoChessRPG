package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class PotionSingleBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public PotionSingleBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_potionSingleBigHurt);
        desc = context.getString(R.string.skill_desc_potionSingleBigHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_potionSingleBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_potionSingleBigHurt);
    }
}
