package com.longtail360.autochessrpg.entity.tactic.condition;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.Monster;
import com.longtail360.autochessrpg.entity.MyCard;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;

public class AnyOneMonsterHp extends BaseCondition{
    public static String KEY = "AnyOneMonsterHp";
    public AnyOneMonsterHp(Context context){
        this.key = KEY;
        desc = context.getString(R.string.condition_anyoneMonster_hp);
        optionItems2 = OptionItem.listNumber(10,90,10,context.getString(R.string.condition_low_than),"%");
        selectOption2 = optionItems2.get(0);
    }

    @Override
    public String concatDesc (){
        return desc+ selectOption2.label;
    }

    @Override
    public boolean checking (AdvContext advContext){
        boolean result = false;
        int value = selectOption2.convertValueToInt();
        for (Monster card : advContext.battleContext.monsters) {
            int percentage = card.getHp() *100/card.totalHp;
            if (percentage < value) {
                result = true;
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
