package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.Item;

import org.json.JSONException;

public class UseSmallRedPotion extends BaseAction{
    public static String KEY = "UseSmallRedPotion";
    public UseSmallRedPotion (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_userSmallRedPotion);
    }
    @Override
    public ActionResult action (Context context, AdvContext advContext)throws JSONException {
        ActionResult result = new ActionResult();
        Item toUseItem = null;
        for(Item item : advContext.itemList){
            if(item.itemCode.equals(Item.ITEM_HP_UP)){
                toUseItem = item;
                break;
            }
        }

//        if (toUseItem != null) {
//            advContext.items.remove(toUseItem);
//            int heal = (int)(myselfAction.getCard().buffHp * Setting.smallRedPotionEfficiency / 100);
//            int beforeHp = myselfAction.getCard().battleHp;
//            myselfAction.getCard().battleHp = myselfAction.getCard().battleHp + heal;
//            if(myselfAction.getCard().battleHp > myselfAction.getCard().buffHp) {
//                myselfAction.getCard().battleHp = myselfAction.getCard().buffHp;
//            }
//            result.title = card.name + desc;
//            result.content = card.name + desc+"，"+context.getString(R.string.action_hp_restore_from_to)
//                    .replace("{beforeHp}",beforeHp+"" )
//                    .replace("{afterHp}",myselfAction.getCard().battleHp+"" );
//            result.doThisAction = true;
//            return result;
//        } else {
//            result.doThisAction = false;
//        }
        return result;

    }

    @Override
    public String concatDesc (Context context){
        return this.desc;
    }
}
