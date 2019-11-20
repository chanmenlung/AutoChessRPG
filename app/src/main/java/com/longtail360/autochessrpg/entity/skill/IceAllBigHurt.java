package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class IceAllBigHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public IceAllBigHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_iceAllBigHurt);
        desc = context.getString(R.string.skill_desc_iceAllBigHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_iceAllBigHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_iceAllBigHurt);
    }
}
