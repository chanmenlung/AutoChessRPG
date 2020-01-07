package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class StopAttackPerAttack  extends BasePassiveSkill{
    public static String KEY = "StopAttackPerAttack";

    public StopAttackPerAttack(Context context) {
        code = KEY;
        desc = context.getString(R.string.skill_desc_stopAttackPerAttack);
    }
}
