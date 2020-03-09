package com.longtail360.autochessrpg.entity.tactic.action;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.ActionResult;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Item;
import com.longtail360.autochessrpg.entity.summon.BaseSummon;
import com.longtail360.autochessrpg.entity.summon.SummonLarge;
import com.longtail360.autochessrpg.entity.summon.SummonSmall;

public class UseItemSummonS extends BaseAction{
    public static String KEY = "UseItemSummonS";
    public UseItemSummonS (Context context){
        super();
        this.key = KEY;
        this.desc = context.getString(R.string.action_useItemSummonMonsterSmall);
        this.battleDesc = context.getString(R.string.item_itemSummonMonsterSmall_battleDesc);
    }


    @Override
    public ActionResult action (Context context, AdvContext advContext) {
        ActionResult result = new ActionResult();
        result.doThisAction = useItem(advContext.advId, Item.ITEM_SUMMON_MONSTER_SMALL);
        if(result.doThisAction) {
            boolean hadActive = false;
            if(advContext.battleContext.summons.size() > 0) {
                for(BaseSummon baseSummon : advContext.battleContext.summons){
                    if(baseSummon.key.equals(SummonSmall.KEY)){
                        hadActive = true;
                    }
                }
            }
            if(!hadActive){
                advContext.battleContext.summons.add(new SummonSmall(context));
            }
        }

        result.icon1 = Item.ITEM_SUMMON_MONSTER_SMALL;
        result.title = context.getString(R.string.teamUseItem).replace("{item}", context.getString(R.string.item_itemSummonMonsterSmall));
        result.content =battleDesc.replace("{value}", 30+"");
        return result;
    }
}
