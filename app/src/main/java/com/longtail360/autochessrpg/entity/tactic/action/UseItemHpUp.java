package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;

import org.json.JSONException;

public class UseItemHpUp  extends BaseAction{
    public static String KEY = "UseItemHpUp";
	public static int deltaHp = 20; //call by Item.java
    public UseItemHpUp (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemHpUp);
		this.battleDesc = context.getString(R.string.item_itemHpUp_battleDesc);
    }

	public String concatDesc (Context context){return desc;}
    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        ActionResult result = new ActionResult();
		result.doThisAction = useItem(advContext.advId, Item.ITEM_HP_UP);
		if(result.doThisAction) {
			for(MyCard card : advContext.cards) {
				card.battleHp = card.battleHp + deltaHp;
				if(card.battleHp >= card.totalHp) {
					card.battleHp = card.totalHp;
				}
			}
		}
		result.icon1 = Item.ITEM_HP_UP;		
		result.title = context.getString(R.string.teamUseItem).replace("{item}", context.getString(R.string.item_itemHpUp));
		result.content =battleDesc.replace("{value}", deltaHp+"");
        return result;
    }
}
