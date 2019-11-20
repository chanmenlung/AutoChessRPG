package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class PotionAllHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public PotionAllHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_potionAllHurt);
        desc = context.getString(R.string.skill_desc_potionAllHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_potionAllHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_potionAllHurt);
    }
}
