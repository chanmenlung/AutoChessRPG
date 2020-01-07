package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;

import org.json.JSONException;

public class UseItemFire  extends BaseAction{
    public static String KEY = "UseItemFire";
    public UseItemFire (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemFire);
    }

    @Override
    public ActionResult action (Context context, AdvContext advContext)throws JSONException {
        ActionResult result = new ActionResult();
        Item toUseItem = null;
        for(Item item : advContext.itemList){
            if(item.itemCode.equals(Item.ITEM_FIRE)){
                toUseItem = item;
                break;
            }
        }
        return result;
    }
}