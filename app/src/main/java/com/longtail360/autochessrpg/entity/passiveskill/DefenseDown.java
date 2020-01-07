package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class DefenseDown  extends BasePassiveSkill{
    public static String KEY = "DefenseDown";

    public DefenseDown(Context context) {
        code = KEY;
        desc = context.getString(R.string.skill_desc_defenseDown);
    }
}
