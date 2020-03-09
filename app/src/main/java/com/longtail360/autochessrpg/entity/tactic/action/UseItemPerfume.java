package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;

import org.json.JSONException;

public class UseItemPerfume  extends BaseAction{
    public static String KEY = "UseItemPerfume";
    public UseItemPerfume (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemPerfume);
		this.battleDesc = context.getString(R.string.item_itemPerfume_battleDesc);
    }


    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        ActionResult result = new ActionResult();
		result.doThisAction = useItem(advContext.advId, Item.ITEM_PERFUME);
        int hurt = advContext.battleContext.monsters.get(0).attack;
        if(result.doThisAction) {
            for(Monster ma : advContext.battleContext.monsters) {
                ma.skipAttack = true;
            }
		}
		result.icon1 = Item.ITEM_PERFUME;		
		result.title = context.getString(R.string.teamUseItem).replace("{item}", context.getString(R.string.item_itemPerfume));
		result.content = context.getString(R.string.skill_battleDesc_randomAttackOfMonster)
                .replace("{monster}", advContext.battleContext.buildMonsterLabels(advContext.battleContext.monsters))
                .replace("{value}", hurt+"");
        return result;
    }
}