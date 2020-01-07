package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class SummonMonster  extends BasePassiveSkill{
    public static String KEY = "SummonMonster";

    public SummonMonster(Context context) {
        code = KEY;
        desc = context.getString(R.string.skill_desc_summonMonster);
    }
}
