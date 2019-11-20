package com.longtail360.autochessrpg.entity.skill;

import android.content.Context;

import com.longtail360.autochessrpg.R;

public class IceAllHurt extends BaseSkill{
    public static String KEY = "ElectricAllBigHurt";

    public IceAllHurt(Context context) {
        code = KEY;
        cd = 3;
        name = context.getString(R.string.skill_name_iceAllHurt);
        desc = context.getString(R.string.skill_desc_iceAllHurt);
        battleDesc = context.getString(R.string.skill_battleDesc_iceAllHurt);
        statusDesc = context.getString(R.string.skill_statusDesc_iceAllHurt);
    }
}
