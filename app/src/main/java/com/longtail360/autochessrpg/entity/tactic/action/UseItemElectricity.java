package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;

import org.json.JSONException;

public class UseItemElectricity  extends BaseAction{
    public static String KEY = "UseItemElectricity";
	public static int hurt = 20;
    public UseItemElectricity (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemElectricity);
		this.battleDesc = context.getString(R.string.item_itemElectricity_battleDesc);
    }
    public String concatDesc (Context context){return desc;}
    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        ActionResult result = new ActionResult();
        result.doThisAction = useItem(advContext.advId, Item.ITEM_ELECTRICITY);
        if(result.doThisAction) {
            advContext.battleContext.statusHurtAll(context, advContext, hurt, false, 30,MyCard.ELECTRICITY_STATUS);
        }
        result.icon1 = Item.ITEM_ELECTRICITY;
        result.title = context.getString(R.string.teamUseItem).replace("{item}", context.getString(R.string.item_itemElectricity));
        result.content =battleDesc.replace("{value}", hurt+"");
        return result;
    }
}