package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class AttackDown  extends BasePassiveSkill{
    public static String KEY = "AttackDown";

    public AttackDown(Context context) {
        code = KEY;
        desc = context.getString(R.string.skill_desc_attackDown);
    }
}
