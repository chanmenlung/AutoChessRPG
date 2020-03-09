package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;

import org.json.JSONException;

public class UseItemPotion  extends BaseAction{
    public static String KEY = "UseItemPotion";
    public static int hurt = 20;
    public UseItemPotion (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemPotion);
		this.battleDesc = context.getString(R.string.item_itemPotion_battleDesc);
    }


    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        ActionResult result = new ActionResult();
        result.doThisAction = useItem(advContext.advId, Item.ITEM_POTION);
        if(result.doThisAction) {
            advContext.battleContext.statusHurtAll(context, advContext, hurt, false, 30,MyCard.POTION_STATUS);
        }
        result.icon1 = Item.ITEM_POTION;
        result.title = context.getString(R.string.teamUseItem).replace("{item}", context.getString(R.string.item_itemPotion));
        result.content =battleDesc.replace("{value}", hurt+"");
        return result;
    }
}