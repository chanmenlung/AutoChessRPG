package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class FireAllBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public FireAllBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_fireAllBigHurt);
        desc = context.getString(R.string.skill_desc_fireAllBigHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_fireAllBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_fireAllBigHurt);
    }
}
