package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;

import org.json.JSONException;

public class UseItemSummonS  extends BaseAction{
    public static String KEY = "UseItemSummonS";
    public UseItemSummonS (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemSummonMonsterSmall);
    }

    @Override
    public ActionResult action (Context context, AdvContext advContext)throws JSONException {
        ActionResult result = new ActionResult();
        Item toUseItem = null;
        for(Item item : advContext.itemList){
            if(item.itemCode.equals(Item.ITEM_SUMMON_MONSTER_SMALL)){
                toUseItem = item;
                break;
            }
        }
        return result;
    }
}