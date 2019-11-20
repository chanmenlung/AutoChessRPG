package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class FireAllHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public FireAllHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_fireAllHurt);
        desc = context.getString(R.string.skill_desc_fireAllHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_fireAllHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_fireAllHurt);
    }
}
