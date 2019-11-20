package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class PotionSingleHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public PotionSingleHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_potionSingleHurt);
        desc = context.getString(R.string.skill_desc_potionSingleHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_potionSingleHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_potionSingleHurt);
    }
}
