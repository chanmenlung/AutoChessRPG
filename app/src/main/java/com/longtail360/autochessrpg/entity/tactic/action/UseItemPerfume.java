package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;

import org.json.JSONException;

public class UseItemPerfume  extends BaseAction{
    public static String KEY = "UseItemPerfume";
    public UseItemPerfume (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemPerfume);
    }

    @Override
    public ActionResult action (Context context, AdvContext advContext)throws JSONException {
        ActionResult result = new ActionResult();
        Item toUseItem = null;
        for(Item item : advContext.itemList){
            if(item.itemCode.equals(Item.ITEM_PERFUME)){
                toUseItem = item;
                break;
            }
        }
        return result;
    }
}