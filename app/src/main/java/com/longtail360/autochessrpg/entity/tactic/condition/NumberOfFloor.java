package com.longtail360.autochessrpg.entity.tactic.condition;

import android.content.Context;

import com.longtail360.autochessrpg.R;
import com.longtail360.autochessrpg.adventure.AdvContext;
import com.longtail360.autochessrpg.entity.tactic.OptionItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chanmenlung on 21/10/2018.
 */

public class NumberOfFloor extends BaseCondition{
    public static String KEY = "NumberOfFloor";
    public NumberOfFloor(Context context){
        super();
        this.key = KEY;
        desc = context.getString(R.string.condition_number_of_floor);
        List<OptionItem> options2 = new ArrayList<>();
        options2.add(new OptionItem("0",context.getString(R.string.condition_floor_above)));
        options2.add(new OptionItem("1",context.getString(R.string.condition_floor_below)));
        options2.add(new OptionItem("2",context.getString(R.string.condition_floor_is)));
        optionItems2 = options2;
        selectOption2 = optionItems2.get(0);
        optionItems3 = OptionItem.listNumber(1,10,1,"","");
        selectOption3 = optionItems3.get(0);
    }

    @Override
    public String concatDesc (){
        return desc+ selectOption2.label;
    }

    @Override
    public boolean checking (AdvContext advContext){
//        if (advContext.currentFloor == selectOption2.convertValueToInt() && advContext.currentArea == selectOption3.convertValueToInt()) {
//            return true;
//        } else {
//            return false;
//        }
        return false;

    }
}

