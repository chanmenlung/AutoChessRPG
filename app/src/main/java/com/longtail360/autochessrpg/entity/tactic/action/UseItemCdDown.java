package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.utils.Logger;

import org.json.JSONException;

public class UseItemCdDown  extends BaseAction{
    public static String KEY = "UseItemCdDown";
	public static int deltaCd = 1;
    public UseItemCdDown (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemCdDown);
		this.battleDesc = context.getString(R.string.item_itemCdDown_battleDesc);
    }

	public String concatDesc (Context context){return desc;}
    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        ActionResult result = new ActionResult();
		result.doThisAction = useItem(advContext.advId, Item.ITEM_CD_DOWN);
		if(result.doThisAction) {
			for(MyCard card : advContext.cards) {
				card.cd--;
				if(card.cd == 0) {
					card.cd = 0;
				}
			}
		}
		result.icon1 = Item.ITEM_CD_DOWN;		
		result.title = context.getString(R.string.teamUseItem).replace("{item}", context.getString(R.string.item_itemCdDown));
		result.content =battleDesc.replace("{value}", deltaCd+"");
		Logger.log(KEY, "result.doThisAction:"+result.doThisAction);
        return result;
    }	
}
