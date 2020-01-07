package com.longtail360.autochessrpg.entity.tactic.condition;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;

/**
 * Created by chanmenlung on 21/10/2018.
 */

public class NumberOfMonster extends BaseCondition{
    public static String KEY = "NumberOfMonster";
    public NumberOfMonster(Context context){
        super();
        this.key = KEY;
        desc = context.getString(R.string.condition_number_of_monster);
        optionItems2 = OptionItem.listNumber(1,5,1,"",context.getString(R.string.condition_number_greater_than));
        selectOption2 = optionItems2.get(0);
    }

    @Override
    public String concatDesc (){
        return desc+ selectOption2.label;
    }

    @Override
    public boolean checking (AdvContext advContext){
        boolean result = false;
        return result;

    }
}
