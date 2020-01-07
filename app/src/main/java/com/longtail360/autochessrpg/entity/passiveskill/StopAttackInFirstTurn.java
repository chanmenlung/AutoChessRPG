package com.longtail360.autochessrpg.entity.passiveskill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class StopAttackInFirstTurn  extends BasePassiveSkill{
    public static String KEY = "StopAttackInFirstTurn";

    public StopAttackInFirstTurn(Context context) {
        code = KEY;
        desc = context.getString(R.string.skill_desc_stopAttackInFirstTurn);
    }
}
