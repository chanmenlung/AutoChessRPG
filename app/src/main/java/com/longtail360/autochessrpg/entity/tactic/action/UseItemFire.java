package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;

import org.json.JSONException;

public class UseItemFire  extends BaseAction{
    public static String KEY = "UseItemFire";
    public static int hurt = 20;
    public UseItemFire (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemFire);
		this.battleDesc = context.getString(R.string.item_itemFire_battleDesc);
    }

    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        ActionResult result = new ActionResult();
		result.doThisAction = useItem(advContext.advId, Item.ITEM_FIRE);
		if(result.doThisAction) {
		    advContext.battleContext.statusHurtAll(context, advContext, hurt, false, 30,MyCard.FIRE_STATUS);
		}
		result.icon1 = Item.ITEM_FIRE;
		result.title = context.getString(R.string.teamUseItem).replace("{item}", context.getString(R.string.item_itemFire));
		result.content =battleDesc.replace("{value}", hurt+"");
        return result;
    }
}