package com.longtail360.autochessrpg.entity.tactic.condition;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;

public class AllManHp extends BaseCondition{
    public static String KEY = "AllManHp";
    public AllManHp (Context context, Card card){
        super(card);
        this.key = KEY;
        desc = context.getString(R.string.condition_all_card_hp);
        optionItems2 = OptionItem.listNumber(10,90,10,context.getString(R.string.condition_low_than),"%");
        selectOption2 = optionItems2.get(0);
    }

    @Override
    public String concatDesc (){
        return desc+ selectOption2.label;
    }

    @Override
    public boolean checking (AdvContext advContext, Card myself){
        boolean result = true;
//        for (int i = 0; i < advContext.cardActions.size(); i++) {
//            if ((advContext.cardActions.get(i).getCard().battleHp * 100 / advContext.cardActions.get(i).getCard().buffHp) > selectOption2.convertValueToInt()) {
//                result = false;
//                break;
//            }
//        }
        return result;

    }
}
