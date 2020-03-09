package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;

import org.json.JSONException;

public class UseItemIce  extends BaseAction{
    public static String KEY = "UseItemIce";
    public static int hurt = 20;
    public UseItemIce (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemIce);
		this.battleDesc = context.getString(R.string.item_itemIce_battleDesc);
    }


    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        ActionResult result = new ActionResult();
		result.doThisAction = useItem(advContext.advId, Item.ITEM_ICE);
        if(result.doThisAction) {
            advContext.battleContext.statusHurtAll(context, advContext, hurt, false, 30,MyCard.ICE_STATUS);
        }
		result.icon1 = Item.ITEM_ICE;		
		result.title = context.getString(R.string.teamUseItem).replace("{item}", context.getString(R.string.item_itemIce));
		result.content =battleDesc.replace("{value}", hurt+"");
        return result;
    }
}