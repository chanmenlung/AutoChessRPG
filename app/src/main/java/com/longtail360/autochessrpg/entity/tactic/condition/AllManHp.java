package com.longtail360.autochessrpg.entity.tactic.condition;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Card;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;

public class AllManHp extends BaseCondition{
    public static String KEY = "AllManHp";
    public AllManHp (Context context){
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
    public boolean checking (AdvContext advContext){
        boolean result = true;
        int value = selectOption2.convertValueToInt();
        for (MyCard card : advContext.cards) {
            int percentage = card.battleHp *100/card.totalHp;
            if (percentage > value) {
                result = false;
                break;
            }
        }
        if(negation){
            return !result;
        }else {
            return result;
        }

    }
}
