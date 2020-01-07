package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class HealPerTurn  extends BasePassiveSkill{
    public static String KEY = "HealPerTurn";

    public HealPerTurn(Context context) {
        code = KEY;
        desc = context.getString(R.string.skill_desc_healPerTurn);
    }
}
