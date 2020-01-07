package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class CriticalHitUp  extends BasePassiveSkill{
    public static String KEY = "CriticalHitUp";

    public CriticalHitUp(Context context) {
        code = KEY;
        desc = context.getString(R.string.skill_desc_criticalHitUp);
    }
}
