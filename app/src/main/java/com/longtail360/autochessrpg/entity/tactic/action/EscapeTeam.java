package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.MyCard;

public class EscapeTeam extends BaseAction{
    public static String KEY = "AttackLowHp";
    public static int deltaAttack = 5;
    public EscapeTeam (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_escapeTeam);
        this.battleDesc = context.getString(R.string.item_itemAttackUp_battleDesc);
    }


    public String concatDesc (Context context){return desc;}
    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        ActionResult result = new ActionResult();
        result.doThisAction = useItem(advContext.advId, Item.ITEM_ATTACK_UP);
        if(result.doThisAction) {
            for(MyCard card : advContext.cards) {
                card.battleAttack = card.battleAttack + deltaAttack;
            }
        }
        result.icon1 = Item.ITEM_ATTACK_UP;
        result.title = context.getString(R.string.teamUseItem).replace("{item}", context.getString(R.string.item_itemAttackUp));
        result.content =battleDesc.replace("{value}", deltaAttack+"");
        return result;
    }
}
