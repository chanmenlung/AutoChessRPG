package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class PotionAllBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public PotionAllBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_potionAllBigHurt);
        desc = context.getString(R.string.skill_desc_potionAllBigHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_potionAllBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_potionAllBigHurt);
    }
}
