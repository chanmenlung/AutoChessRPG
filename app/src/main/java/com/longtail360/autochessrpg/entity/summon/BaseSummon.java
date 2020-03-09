package com.longtail360.autochessrpg.entity.summon;

import android.content.Context;

import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;

public class BaseSummon {
    public String key;
    protected String name;
    protected String title;
    protected String content;
    public ActionResult active(Context context, AdvContext advContext){ return null;}
}
