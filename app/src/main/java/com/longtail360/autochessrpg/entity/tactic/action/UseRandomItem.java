package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.MyItem;

import java.util.List;

public class UseRandomItem extends BaseAction{
    public static String KEY = "UseRandomItem";
    public static int deltaAttack = 5;
    public UseRandomItem (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useRandomItem);
    }


    public String concatDesc (Context context){return desc;}
    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        ActionResult result;
        List<BaseAction> actions = BaseAction.listUseItems(context);
        result = actions.get(advContext.mRandom.nextInt(actions.size())).action(context,advContext);
        return result;
    }
}
